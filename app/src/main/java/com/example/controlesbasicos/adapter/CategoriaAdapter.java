package com.example.controlesbasicos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fmetzli.databinding.ViewholderListBinding;
import com.example.fmetzli.domain.CategoriaDomain;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.Viewholder> {
    ArrayList<CategoriaDomain> datos;
    Context context;
    ViewholderListBinding binding;

    public CategoriaAdapter (ArrayList<CategoriaDomain> datos){this.datos = datos;}

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
        //return datos.size();


    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(ViewholderListBinding binding) {
            super(binding.getRoot());
        }
    }
}

