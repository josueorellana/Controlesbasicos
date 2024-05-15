package com.example.controlesbasicos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.controlesbasicos.ListaCategoriaActivity;
import com.example.controlesbasicos.R;
import com.example.controlesbasicos.adapter.PopularAdapter;
import com.example.controlesbasicos.databinding.ActivityMainBinding;
import com.example.controlesbasicos.domain.PopularDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageView listacategoria;
    private ImageView carrito;
    private ImageView mencat;
    private ImageView womancateg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        statusBarColor();
        initRecyclerView();


        //Con esto hacemos que un texto o imagen sirva como boton para llevarnos a otra pantalla
        listacategoria = findViewById(R.id.allcategorias);

        listacategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListaCategoriaActivity.class);
                startActivity(intent);


            }
        });
        mencat = findViewById(R.id.categoria1);

        mencat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatCaballeros.class);
                startActivity(intent);
            }
        });
        womancateg = findViewById(R.id.categoria2);

        womancateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatDamas.class);
                startActivity(intent);
            }
        });
        carrito = findViewById(R.id.carritoshopimg);

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartBuyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void statusBarColor() {
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.gris_oscuro));
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();

        items.add(new PopularDomain("Camisa de vestir color negro","item_1",15,4,100,"Nuestra camisa CLASICA LISA Pierre Cardin, es perfecta para lucirla en tu día a día."));

        items.add(new PopularDomain("Zapatos de vesir color negro","item_14",10,5,90,"Nuestra camisa CLASICA LISA Pierre Cardin, es perfecta para lucirla en tu día a día."));

        items.add(new PopularDomain("Zapatos rojos para dama","item_22",9,4,90,"Zapatos de vestir para mujer de distintas tallas."));

        items.add(new PopularDomain("Perfume para dama","item_211",10,4.5,100,"Nuestra camisa CLASICA LISA Pierre Cardin, es perfecta para lucirla en tu día a día."));

        items.add(new PopularDomain("Sombrero para dama con ala grande","item_222",10,5.0 ,50,"Nuestra camisa CLASICA LISA Pierre Cardin, es perfecta para lucirla en tu día a día."));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }



}