package com.example.controlesbasicos.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controlesbasicos.DB;
import com.example.controlesbasicos.DBHelper;
import com.example.controlesbasicos.RegistroUsuarioActivity;
import com.example.fmetzli.R;

public class loginActivity extends AppCompatActivity {
    private Cursor fila;
    private EditText NombreUsuario;
    private EditText PasswordUsuario;
    private Button LoginUsuario,RegistroDeUsuario;
    Cursor cUsuario;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.getWritableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NombreUsuario = findViewById(R.id.NombreDeUsuario);
        PasswordUsuario = findViewById(R.id.passwordDeUsuario);
        LoginUsuario = findViewById(R.id.InicioDeSesion);
        RegistroDeUsuario = findViewById(R.id.RegistrarUsuario);

        LoginUsuario.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {

                try {
                    DBHelper admin=new DBHelper(getApplicationContext(),"db_Productos",null,1);

                    SQLiteDatabase db=admin.getWritableDatabase();
                    String usuario=NombreUsuario.getText().toString();
                    String contrasena=PasswordUsuario.getText().toString();

                    fila=db.rawQuery("SELECT correo, password FROM Usuario WHERE correo='"+
                            usuario+"' and password='"+contrasena+"'",null);

                    if(fila.moveToFirst()){
                        String usua=fila.getString(0);
                        String pass=fila.getString(1);

                        if (usuario.equals(usua)&&contrasena.equals(pass)){
                            NombreUsuario.setText("");
                            PasswordUsuario.setText("");

                            Intent intent = new Intent(loginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast toastd=Toast.makeText(getApplicationContext(),"Datos incorrectos",Toast.LENGTH_LONG);
                        toastd.show();
                    }

                } catch (Exception e) {//capturamos los errores que ubieran
                    Toast toast=Toast.makeText(getApplicationContext(),"Error" + e.getMessage(),Toast.LENGTH_LONG);
                    //mostramos el mensaje
                    toast.show();
                }
                /*
                String usuario = NombreUsuario.getText().toString();
                String Password = PasswordUsuario.getText().toString();

                if (cUsuario.getString(1)== usuario); {
                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        });

        RegistroDeUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(loginActivity.this, RegistroUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }

    public void InicioSesion(View v){
        /*Creamos un objeto de la clase DBHelper e
        instanciamos el constructor y damos el nonbre de
         la base de datos y la version*/
        DB admin = new DB(getApplicationContext(), "", null, 1);
        /*Abrimos la base de datos como escritura*/
        SQLiteDatabase db=admin.getWritableDatabase();
        /*Creamos dos variables string y capturamos los datos
         ingresado por el usuario y lo convertimos a string*/
        String usuario=NombreUsuario.getText().toString();
        String contrasena=PasswordUsuario.getText().toString();
        /*inicializamos al cursor y llamamos al objeto de la base
        de datos para realizar un sentencia query where donde
         pasamos las dos variables nombre de usuario y password*/
        fila=db.rawQuery("SELECT * FROM Usuario WHERE correo='"+
                usuario+"' and password='"+contrasena+"'",null);
        /*Realizamos un try catch para captura de errores*/
        try {
            /*Condicional if preguntamos si cursor tiene algun dato*/
            if(fila.moveToFirst()){
//capturamos los valores del cursos y lo almacenamos en variable
                String usua=fila.getString(0);
                String pass=fila.getString(1);
                //preguntamos si los datos ingresados son iguales
                if (usuario.equals(usua)&&contrasena.equals(pass)){
                    //si son iguales entonces vamos a otra ventana
                    //Menu es una nueva actividad empty
                    Intent ven=new Intent(this, MainActivity.class);
                    //lanzamos la actividad
                    startActivity(ven);
                    //limpiamos las las cajas de texto
                    NombreUsuario.setText("");
                    PasswordUsuario.setText("");
                }
            }//si la primera condicion no cumple entonces que envie un mensaje toast
            else {
                Toast toast=Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG);
                //mostramos el toast
                toast.show();
            }

        } catch (Exception e) {//capturamos los errores que ubieran
            Toast toast=Toast.makeText(this,"Error" + e.getMessage(),Toast.LENGTH_LONG);
            //mostramos el mensaje
            toast.show();
        }
    }
}

