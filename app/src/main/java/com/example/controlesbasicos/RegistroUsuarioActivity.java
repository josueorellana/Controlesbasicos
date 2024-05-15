package com.example.controlesbasicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controlesbasicos.activity.loginActivity;
import com.example.fmetzli.R;

public class RegistroUsuarioActivity extends AppCompatActivity {

    String id ="";
    TextView tempval;
    private Button cancelar, guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        cancelar = findViewById(R.id.CancelarRegistro);
        guardar = findViewById(R.id.GuardarRegistro);

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(RegistroUsuarioActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempval = findViewById(R.id.NombreUsuario);
                String nombre = tempval.getText().toString();

                tempval = findViewById(R.id.edadUsuario);
                String edad = tempval.getText().toString();

                tempval = findViewById(R.id.numeroUsuario);
                String telefono = tempval.getText().toString();

                tempval = findViewById(R.id.correoUsuario);
                String correo = tempval.getText().toString();

                tempval = findViewById(R.id.contraseñaUsuario);
                String password = tempval.getText().toString();

                DB db = new DB(getApplicationContext(), "", null, 1);
                String[] datos = new String[]{id, nombre, edad, telefono, correo, password};
                String respuesta = db.administrar_produtos(datos);
                if (respuesta.equals("ok")) {
                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistroUsuarioActivity.this, loginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error:  "+ respuesta, Toast.LENGTH_LONG).show();
                }
                limpiarDatos();
            }
        });
    }
    public void limpiarDatos(){
        EditText nombre = findViewById(R.id.NombreUsuario);
        EditText edad = findViewById(R.id.edadUsuario);
        EditText telefono = findViewById(R.id.numeroUsuario);
        EditText correo = findViewById(R.id.correoUsuario);
        EditText password= findViewById(R.id.contraseñaUsuario);

        nombre.setText("");
        edad.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
    }
}