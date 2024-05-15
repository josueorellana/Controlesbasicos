package com.example.controlesbasicos.adapter;
    /* este apartado es para que los items se agreguen en casillas individuales
    en el apartado popular productos*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.fmetzli.databinding.ViewholderListBinding;
import com.example.controlesbasicos.domain.PopularDomain;

import java.util.ArrayList;


public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder>{
    ArrayList<PopularDomain> items;
    Context context;
    ViewholderListBinding binding;


    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ViewholderListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        binding.Titulodeproducto1.setText(items.get(position).getTitle());
        binding.Precioproducto1.setText("$"+ items.get(position).getPrice());
        binding.scoretxt.setText(""+items.get(position).getScore());

        int drawableResourced=holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl()
                ,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(binding.camisa);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    @Override
    public int getItemCount() {return items.size();}


    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(ViewholderListBinding binding) {
            super(binding.getRoot());
        }
    }
}
