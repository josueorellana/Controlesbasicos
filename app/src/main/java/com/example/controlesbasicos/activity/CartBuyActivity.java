package com.example.controlesbasicos.activity;

import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controlesbasicos.MainActivity;
import com.example.controlesbasicos.databinding.ActivityMainBinding;
public class CartBuyActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ImageView salirdecarrito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy);

        salirdecarrito = findViewById(R.id.backcartbtn);

        salirdecarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CartBuyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}