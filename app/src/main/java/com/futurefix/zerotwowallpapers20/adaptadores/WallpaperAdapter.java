package com.futurefix.zerotwowallpapers20.adaptadores;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.futurefix.zerotwowallpapers20.CategoriasWpps;
import com.futurefix.zerotwowallpapers20.R;
import com.futurefix.zerotwowallpapers20.VistaWallpaper;
import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>{
    List<Wallpaper> lista;
    int layout;
    Fragment fragment;
    Context context;
    int codigo;

    final public int CODE_CATEGORIAS = 22;
    final public int CODE_WALLPAPER = 21;

    public WallpaperAdapter(List<Wallpaper> lista, int layout, Fragment fragment, Context context, int codigo) {
        this.lista = lista;
        this.layout = layout;
        this.fragment = fragment;
        this.context = context;
        this.codigo = codigo;
    }

    @NonNull
    @NotNull
    @Override
    public WallpaperHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new WallpaperHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WallpaperHolder holder, int position) {
        Wallpaper wallpaper = lista.get(position);
        if (codigo==CODE_CATEGORIAS){
            Glide.with(fragment).load(wallpaper.getUrl()).into(holder.catView);
            holder.nombreCat.setText(wallpaper.getNombre());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CategoriasWpps.class);
                intent.putExtra("ItemTag", wallpaper.getTag1());
                intent.putExtra("ItemNombre", wallpaper.getNombre());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }else if (codigo==CODE_WALLPAPER){
            Glide.with(fragment).load(wallpaper.getUrl()).into(holder.wallpView);
            holder.animCarga.loop(false);
            holder.animCarga.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), VistaWallpaper.class);
                intent.putExtra("ItemUrl", wallpaper.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public static class WallpaperHolder extends RecyclerView.ViewHolder {
        ImageView wallpView, catView;
        TextView nombreCat;
        LottieAnimationView animCarga;

        public WallpaperHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            wallpView = itemView.findViewById(R.id.item_url);
            animCarga = itemView.findViewById(R.id.animacion_view_wpp);
            catView = itemView.findViewById(R.id.item_url_cat);
            nombreCat = itemView.findViewById(R.id.titulo_cat);
        }
    }
}
