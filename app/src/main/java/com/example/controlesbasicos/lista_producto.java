package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class lista_producto extends AppCompatActivity {
    Bundle parametros = new Bundle();
    FloatingActionButton btn;
    ListView lts;
    Cursor cProducto;
    com.ugb.controlesbasicos.DB db_producto;
    producto misProductos;

    final ArrayList<producto> alProducto=new ArrayList<producto>();
    final ArrayList<producto> alProductoCopy=new ArrayList<producto>();
    JSONArray datosJSON;
    JSONArray datosJSONFiltrados;
    JSONObject jsonObject;

    ObtenerDatosServidor datosServidor;
    com.ugb.controlesbasicos.DetectarInternet di;
    int posicion = 0;
    String respuesta = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto);

        db_producto = new com.ugb.controlesbasicos.DB(lista_producto.this, "", null, 1);

        btn = findViewById(R.id.btnAbrirNuevosProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
            }
        });
        try {
            di = new com.ugb.controlesbasicos.DetectarInternet(getApplicationContext());
            if(di.hayConexionInternet()){
                obtenerDatosProductosServidor();
            }else {
                obtenerProducto();
            }
            subirDatos();
        } catch (Exception e) {
            mostrarMsg("Error al detectar si hay conexion"+ e.getMessage());
        }
        buscarProducto();
    }

    private void obtenerDatosProductosServidor(){
        try {
            datosServidor = new ObtenerDatosServidor();
            String data = datosServidor.execute().get();
            jsonObject = new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            filtrarProductos(datosJSON);
            MostrarProductos();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos desde el servidor: "+ e.getMessage());
        }
    }
    private void MostrarProductos() {
        try {
            int estado_activo = 1;

            if (datosJSON.length()>0 ) {
                lts = findViewById(R.id.ltsProducto);

                alProducto.clear();
                alProductoCopy.clear();

                JSONObject misProductosJSONObject;
                for (int i=0; i<datosJSON.length(); i++) {
                    misProductosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    int estado = Integer.parseInt(misProductosJSONObject.getString("estado"));
                    if(estado == estado_activo) {
                        misProductos = new producto(
                                misProductosJSONObject.getString("_id"),
                                misProductosJSONObject.getString("_rev"),
                                misProductosJSONObject.getString("idProducto"),
                                misProductosJSONObject.getString("codigo"),
                                misProductosJSONObject.getString("descripcion"),
                                misProductosJSONObject.getString("marca"),
                                misProductosJSONObject.getString("presentacion"),
                                misProductosJSONObject.getString("precio"),
                                misProductosJSONObject.getString("costo"),
                                misProductosJSONObject.getString("stock"),
                                misProductosJSONObject.getString("foto"),
                                misProductosJSONObject.getString("estado"),
                                misProductosJSONObject.getString("porcentaje")
                        );
                        alProducto.add(misProductos);
                    }
                }

                com.ugb.controlesbasicos.adaptadorImagenes adImagenes = new com.ugb.controlesbasicos.adaptadorImagenes(getApplicationContext(), alProducto);
                lts.setAdapter(adImagenes);
                alProductoCopy.addAll(alProducto);

                registerForContextMenu(lts);
            } else {
                alProducto.clear();
                com.ugb.controlesbasicos.adaptadorImagenes adImagenes = new com.ugb.controlesbasicos.adaptadorImagenes(getApplicationContext(), alProducto);
                lts.setAdapter(adImagenes);
                adImagenes.notifyDataSetChanged();
                alProducto.clear();
                alProductoCopy.clear();

                mostrarMsg("No hay datos que mostrar.");

            }
        } catch (Exception e) {
            mostrarMsg("Error MostrarProductos:" + e.getMessage());
        }
    }

    private void filtrarProductos(JSONArray datosJSON) {
        int estado_activo = 1;

        try {
            datosJSONFiltrados = new JSONArray(); // Crear un nuevo JSONArray para almacenar los productos filtrados

            for (int i = 0; i < datosJSON.length(); i++) {
                JSONObject misProductosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                int estado = Integer.parseInt(misProductosJSONObject.getString("estado"));
                if (estado == estado_activo) {
                    JSONObject productoFiltrado = new JSONObject();
                    productoFiltrado.put("_id", misProductosJSONObject.getString("_id"));
                    productoFiltrado.put("_rev", misProductosJSONObject.getString("_rev"));
                    productoFiltrado.put("idProducto", misProductosJSONObject.getString("idProducto"));
                    productoFiltrado.put("codigo", misProductosJSONObject.getString("codigo"));
                    productoFiltrado.put("descripcion", misProductosJSONObject.getString("descripcion"));
                    productoFiltrado.put("marca", misProductosJSONObject.getString("marca"));
                    productoFiltrado.put("presentacion", misProductosJSONObject.getString("presentacion"));
                    productoFiltrado.put("precio", misProductosJSONObject.getString("precio"));
                    productoFiltrado.put("costo", misProductosJSONObject.getString("costo"));
                    productoFiltrado.put("stock", misProductosJSONObject.getString("stock"));
                    productoFiltrado.put("foto", misProductosJSONObject.getString("foto"));
                    productoFiltrado.put("estado", misProductosJSONObject.getString("estado"));
                    productoFiltrado.put("porcentaje", misProductosJSONObject.getString("porcentaje"));

                    datosJSONFiltrados.put(productoFiltrado); // Agregar el objeto JSONObject al JSONArray
                }
            }
        } catch (JSONException e) {
            mostrarMsg("Error al filtrar productos:" + e.getMessage());
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try{
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("codigo"));//1 es el nombre del producto
        }catch (Exception e){
            mostrarMsg("Error al mostrar el menu: "+ e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;
                case R.id.mnxModificar:
                    parametros.putString("accion","modificar");
                    parametros.putString("productos", datosJSONFiltrados.getJSONObject(posicion).toString());
                    abrirActividad(parametros);

                    break;
                case R.id.mnxEliminar:
                    parametros.putString("accion","eliminar");
                    parametros.putString("productos", datosJSONFiltrados.getJSONObject(posicion).toString());
                    eliminarProducto();
                    break;
            }
            return true;
        } catch (Exception e) {
            mostrarMsg("Error en menu: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }

    private void eliminarProducto(){
        try {
            JSONObject datosProductos = new JSONObject();
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_producto.this);

            confirmacion.setTitle("Esta seguro de Eliminar a: ");
            confirmacion.setMessage(datosJSONFiltrados.getJSONObject(posicion).getString("idProducto"));
            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {

                        String respuesta = db_producto.administrar_Productos("eliminar", new String[]{ datosJSONFiltrados.getJSONObject(posicion).getString("idProducto")});
                        if (respuesta.equals("ok")) {
                            mostrarMsg("producto eliminado con exito.");
                            limpiarPantalla();

                            JSONObject jsonObject = new JSONObject(parametros.getString("productos"));

                            di = new com.ugb.controlesbasicos.DetectarInternet(getApplicationContext());

                            if(di.hayConexionInternet()){
                                datosProductos.put("_id", jsonObject.getString("_id"));
                                datosProductos.put("_rev", jsonObject.getString("_rev"));
                                datosProductos.put("idProducto", jsonObject.getString("idProducto"));
                                datosProductos.put("codigo", jsonObject.getString("codigo"));
                                datosProductos.put("descripcion", jsonObject.getString("descripcion"));
                                datosProductos.put("marca", jsonObject.getString("marca"));
                                datosProductos.put("presentacion", jsonObject.getString("presentacion"));
                                datosProductos.put("precio", jsonObject.getString("precio"));
                                datosProductos.put("costo", jsonObject.getString("costo"));
                                datosProductos.put("stock", jsonObject.getString("stock"));
                                datosProductos.put("foto", jsonObject.getString("foto"));
                                datosProductos.put("estado", "0");
                                datosProductos.put("porcentaje", jsonObject.getString("porcentaje"));

                                com.ugb.controlesbasicos.EnviarDatosServidor objGuardarDatosServidor = new com.ugb.controlesbasicos.EnviarDatosServidor(getApplicationContext());
                                respuesta = objGuardarDatosServidor.execute(datosProductos.toString()).get();

                                JSONObject respuestaJSONObject = new JSONObject(respuesta);

                                if( respuestaJSONObject.getBoolean("ok") ){
                                    mostrarMsg("producto eliminado con exito de la DB.");
                                    obtenerDatosProductosServidor();
                                }else{
                                    obtenerProducto();
                                    respuesta = "Error al guardar en servidor: "+ respuesta;
                                }

                            }else{
                                obtenerProducto();
                                respuesta = "Error al guardar en servidor: "+ respuesta;
                            }

                        } else {
                            mostrarMsg("Error al eliminar producto: " + respuesta);
                        }
                    }catch (Exception e){
                        mostrarMsg("Error al eliminar Datos: "+e.getMessage());
                    }
                }
            });
            confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }

    private void subirDatos(){
        try{
            JSONObject misDatosJSONObject = new JSONObject();
            if (datosJSON.length()>0){
                for (int i=0; i<datosJSON.length(); i++) {
                    misDatosJSONObject = datosJSON.getJSONObject(i);

                    if(misDatosJSONObject.getString("_id").equals("")&& misDatosJSONObject.getString("_rev").equals("")){
                        guardarDatosServidor(misDatosJSONObject);
                        mostrarMsg(misDatosJSONObject.toString());
                    }
                }
                guardarDatosServidor(misDatosJSONObject);
            }

        }catch (Exception e){mostrarMsg("Error al sincronizar: "+e.getMessage());}
    }

    private void limpiarPantalla(){
        alProducto.clear();
        com.ugb.controlesbasicos.adaptadorImagenes adImagenes = new com.ugb.controlesbasicos.adaptadorImagenes(getApplicationContext(), alProducto);
        lts.setAdapter(adImagenes);
        adImagenes.notifyDataSetChanged();
        alProducto.clear();
        alProductoCopy.clear();
    }
    private void guardarDatosServidor(JSONObject datosProductos){
        try {
            mostrarMsg("guardarDatosServidor");
            String respuesta = "";
            datosProductos.remove("_id");
            datosProductos.remove("_rev");
            com.ugb.controlesbasicos.EnviarDatosServidor objGuardarDatosServidor = new com.ugb.controlesbasicos.EnviarDatosServidor(getApplicationContext());
            respuesta = objGuardarDatosServidor.execute(datosProductos.toString()).get();
            JSONObject respuestaJSONObject = new JSONObject(respuesta);
            mostrarMsg(respuesta);
            if (respuestaJSONObject.getBoolean("ok")) {
                mostrarMsg("Datos sincronizados");
                obtenerDatosProductosServidor();
            }
            else{
                mostrarMsg("Error al sincronizar los datos");
            }
        }catch (Exception e) {

            mostrarMsg("Error en server: "+e.getMessage());
        }

    }

    private void abrirActividad(Bundle parametros){
        Intent abriVentana = new Intent(getApplicationContext(), MainActivity.class);
        abriVentana.putExtras(parametros);
        startActivity(abriVentana);
    }
    private void obtenerProducto(){
        try {
            datosJSON = new JSONArray(); // Inicializar datosJSON fuera del bloque if

            cProducto = db_producto.consultar_Productos();
            if (cProducto.moveToFirst()) {
                datosJSON = new JSONArray();
                do {
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue = new JSONObject();
                    jsonObject.put("_id",cProducto.getString(0));
                    jsonObject.put("_rev",cProducto.getString(1));
                    jsonObject.put("idProducto", cProducto.getString(2));
                    jsonObject.put("codigo", cProducto.getString(3));
                    jsonObject.put("descripcion", cProducto.getString(4));
                    jsonObject.put("marca", cProducto.getString(5));
                    jsonObject.put("presentacion", cProducto.getString(6));
                    jsonObject.put("precio", cProducto.getString(7));
                    jsonObject.put("costo", cProducto.getString(8));
                    jsonObject.put("stock",cProducto.getString(9));
                    jsonObject.put("foto",cProducto.getString(10));
                    jsonObject.put("estado",cProducto.getString(11));
                    jsonObject.put("porcentaje",cProducto.getString(12));

                    jsonObjectValue.put("value", jsonObject);
                    datosJSON.put(jsonObjectValue);
                } while(cProducto.moveToNext());
                MostrarProductos();
                filtrarProductos(datosJSON);
            } else {
                MostrarProductos();
                mostrarMsg("No hay productos que mostrar");
            }
        } catch (Exception e) {
            mostrarMsg("Error obtenerProducto: "+ e.getMessage());
        }
    }

    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void buscarProducto(){
        TextView tempVal;
        tempVal = findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    alProducto.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alProducto.addAll(alProductoCopy);
                    }else{
                        for( producto Producto : alProductoCopy ){
                            String codigo = Producto.getCodigo();
                            String descripcion = Producto.getDescripcion();
                            String marca = Producto.getMarca();
                            String presentacion = Producto.getPresentacion();
                            if( codigo.toLowerCase().trim().contains(valor) ||
                                    descripcion.toLowerCase().trim().contains(valor) ||
                                    marca.trim().contains(valor) ||
                                    presentacion.trim().toLowerCase().contains(valor) ){
                                alProducto.add(Producto);
                            }
                        }
                        com.ugb.controlesbasicos.adaptadorImagenes adImagenes = new com.ugb.controlesbasicos.adaptadorImagenes(getApplicationContext(), alProducto);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+e.getMessage() );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}