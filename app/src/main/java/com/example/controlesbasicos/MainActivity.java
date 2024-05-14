package com.ugb.controlesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Button btn;
    EditText precionEdit, costoEdit;
    FloatingActionButton btnRegresar;
    String accion = "nuevo", id = "", foto = "", rev = "", idProducto = "", estado = "1", porcentaje = "1";
    Intent tomarFotoIntent;
    ImageView img;
    utilidades utls;
    com.ugb.controlesbasicos.DetectarInternet di;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utls = new utilidades();
        img = findViewById(R.id.imgFoto);
        precionEdit = findViewById(R.id.txtprecio);
        costoEdit = findViewById(R.id.txtprecio);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoProducto();
            }
        });

        precionEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mostrarMsg("Calculando... ");
                    String precioString = ((EditText) findViewById(R.id.txtprecio)).getText().toString();

                    String costoString = ((EditText) findViewById(R.id.txtcosto)).getText().toString();

                    double precio = Double.parseDouble(precioString);
                    double costo = Double.parseDouble(costoString);

                    double porcentajeGanancia = costo + (costo*(precio/100));

                    TextView lblPorcentajeGanancia = findViewById(R.id.textporcentaje);
                    lblPorcentajeGanancia.setText(String.format("%.2f", porcentajeGanancia) + " %");
                } catch  (Exception e) {
                    mostrarMsg("Escribiendo... "+ e.getMessage());
                }
            }
        });

        costoEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String precioString = ((EditText) findViewById(R.id.txtprecio)).getText().toString();

                    String costoString = ((EditText) findViewById(R.id.txtcosto)).getText().toString();

                    double precio = Double.parseDouble(precioString);
                    double costo = Double.parseDouble(costoString);

                    double porcentajeGanancia = ((precio - costo) / costo) * 100;

                    TextView lblPorcentajeGanancia = findViewById(R.id.textporcentaje);
                    lblPorcentajeGanancia.setText(String.format("%.2f", porcentajeGanancia) + " %");
                } catch  (Exception e) {
                    mostrarMsg("Escribiendo al calcular"+ e.getMessage());
                }
            }
        });

        btnRegresar = findViewById(R.id.btnRegresarListaProducto);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaProducto();
            }
        });


        btn = findViewById(R.id.btnGuardarProducto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    try {
                        String precioString = ((EditText) findViewById(R.id.txtprecio)).getText().toString();

                        String costoString = ((EditText) findViewById(R.id.txtcosto)).getText().toString();

                        double precio = Double.parseDouble(precioString);
                        double costo = Double.parseDouble(costoString);

                        double porcentajeGanancia = ((precio - costo) / costo) * 100;

                        TextView lblPorcentajeGanancia = findViewById(R.id.textporcentaje);
                        lblPorcentajeGanancia.setText( String.format("%.2f", porcentajeGanancia) + "%");
                    }catch (NumberFormatException e){
                        mostrarMsg("Error: Los valores ingresados no son válidos.");
                    } catch (Exception e) {
                        mostrarMsg("Error al calcular el porcentaje de ganancia: " + e.getMessage());
                    }
                    tempVal = findViewById(R.id.txtcodigo);
                    String codigo = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtdescripcion);
                    String descripcion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtmarca);
                    String marca = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtpresentacion);
                    String presentacion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtprecio);
                    String precio = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtcosto);
                    String costo = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtstock);
                    String stock = tempVal.getText().toString();

                    tempVal = findViewById(R.id.textporcentaje);
                    String procentaje = tempVal.getText().toString();

                    JSONObject datosProductos = new JSONObject();

                    if ( accion.equals("modificar") && id.length()>0 && rev.length()>0){
                        datosProductos.put("_id", id);
                        datosProductos.put("_rev", rev);

                    }
                    datosProductos.put("idProducto", idProducto);
                    datosProductos.put("codigo", codigo);
                    datosProductos.put("descripcion", descripcion);
                    datosProductos.put("marca", marca);
                    datosProductos.put("presentacion", presentacion);
                    datosProductos.put("precio", precio);
                    datosProductos.put("costo", costo);
                    datosProductos.put("stock", stock);
                    datosProductos.put("foto", foto);
                    datosProductos.put("estado", "1");
                    datosProductos.put("porcentaje", porcentaje);
                    String respuesta = "";

                    di = new com.ugb.controlesbasicos.DetectarInternet(getApplicationContext());

                    if(di.hayConexionInternet()){
                        com.ugb.controlesbasicos.EnviarDatosServidor objGuardarDatosServidor = new com.ugb.controlesbasicos.EnviarDatosServidor(getApplicationContext());
                        respuesta = objGuardarDatosServidor.execute(datosProductos.toString()).get();

                        JSONObject respuestaJSONObject = new JSONObject(respuesta);

                        if( respuestaJSONObject.getBoolean("ok") ){
                            id = respuestaJSONObject.getString("id");
                            rev = respuestaJSONObject.getString("rev");
                        }else{
                            respuesta = "Error al guardar en servidor: "+ respuesta;
                        }
                    }

                    com.ugb.controlesbasicos.DB db = new com.ugb.controlesbasicos.DB(getApplicationContext(), "", null, 1);
                    String[] datos = new String[]{id, rev, idProducto, codigo, descripcion, marca, presentacion, precio, costo, stock, foto, estado, porcentaje};
                    respuesta = db.administrar_Productos(accion, datos);

                    if (respuesta.equals("ok")) {
                        Toast.makeText(getApplicationContext(), "Producto Registrado con Exito.", Toast.LENGTH_SHORT).show();
                        regresarListaProducto();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error:  " + respuesta, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    mostrarMsg("Error al guardar: "+ e.getMessage());
                }
            }
        });

        mostrarDatosProducto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imagenBitmap = BitmapFactory.decodeFile(foto);
                img.setImageBitmap(imagenBitmap);
            } else {
                mostrarMsg("Se cancelo la toma de la foto");
            }
        } catch (Exception e) {
            mostrarMsg("Error al mostrar la camara: " + e.getMessage());
        }
    }

    private void tomarFotoProducto() {
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
        File fotoProducto = null;
        try {
            fotoProducto = crearImagenProducto();
            if (fotoProducto != null) {
                Uri uriFotoProducto = FileProvider.getUriForFile(MainActivity.this, "com.ugb.controlesbasicos.fileprovider", fotoProducto);
                tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                startActivityForResult(tomarFotoIntent, 1);
            } else {
                mostrarMsg("NO pude tomar la foto");
            }
        } catch (Exception e) {
            mostrarMsg("Error al tomar la foto: " + e.getMessage());
        }

        mostrarMsg("No se selecciono una foto...");
    }



    private File crearImagenProduct() throws Exception{
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        foto = image.getAbsolutePath();
        return image;
    }

    private File crearImagenProducto() throws Exception{

        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }

        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        foto = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void regresarListaProducto(){
        Intent abrirVentana = new Intent(getApplicationContext(), com.ugb.controlesbasicos.lista_producto.class);
        startActivity(abrirVentana);
    }

    private void mostrarDatosProducto(){
        try {
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if( accion.equals("modificar") ){
                JSONObject jsonObject = new JSONObject(parametros.getString("productos"));
                id = jsonObject.getString("_id");
                rev = jsonObject.getString("_rev");
                idProducto = jsonObject.getString("idProducto");

                tempVal = findViewById(R.id.txtcodigo);
                tempVal.setText(jsonObject.getString("codigo"));

                tempVal = findViewById(R.id.txtdescripcion);
                tempVal.setText(jsonObject.getString("descripcion"));

                tempVal = findViewById(R.id.txtmarca);
                tempVal.setText(jsonObject.getString("marca"));

                tempVal = findViewById(R.id.txtpresentacion);
                tempVal.setText(jsonObject.getString("presentacion"));

                tempVal = findViewById(R.id.txtprecio);
                tempVal.setText(jsonObject.getString("precio"));

                tempVal = findViewById(R.id.txtcosto);
                tempVal.setText(jsonObject.getString("costo"));

                tempVal = findViewById(R.id.txtstock);
                tempVal.setText(jsonObject.getString("stock"));

                tempVal = findViewById(R.id.textporcentaje);
                tempVal.setText(jsonObject.getString("porcentaje") + " %");

                foto = jsonObject.getString("foto");
                Bitmap bitmap = BitmapFactory.decodeFile(foto);
                img.setImageBitmap(bitmap);


            }else {
                idProducto = utls.generarIdUnico();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "302: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
