package com.example.controlesbasicos.activity;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fmetzli.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}