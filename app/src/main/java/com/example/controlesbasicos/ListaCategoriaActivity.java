package com.example.fmetzli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fmetzli.activity.MainActivity;
import com.example.fmetzli.activity.loginActivity;

public class ListaCategoriaActivity extends AppCompatActivity {

    private Button salircategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);

        salircategorias = findViewById(R.id.SalirCategoria);

        salircategorias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ListaCategoriaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}