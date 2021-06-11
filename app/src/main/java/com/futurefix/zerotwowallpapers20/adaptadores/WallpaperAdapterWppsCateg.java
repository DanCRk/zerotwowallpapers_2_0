package com.futurefix.zerotwowallpapers20.adaptadores;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.futurefix.zerotwowallpapers20.R;
import com.futurefix.zerotwowallpapers20.VistaWallpaper;
import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WallpaperAdapterWppsCateg extends RecyclerView.Adapter<WallpaperAdapterWppsCateg.WallpaperHolder>{
    List<Wallpaper> lista;
    int layout;
    Activity activity;
    Context context;

    public WallpaperAdapterWppsCateg(List<Wallpaper> lista, int layout, Activity activity, Context context) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
        this.context = context;
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
        Glide.with(activity).load(wallpaper.getUrl()).into(holder.wallpView);
        holder.animCarga.loop(false);
        holder.animCarga.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VistaWallpaper.class);
            intent.putExtra("ItemUrl", wallpaper.getUrl());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public static class WallpaperHolder extends RecyclerView.ViewHolder {
        ImageView wallpView;
        LottieAnimationView animCarga;

        public WallpaperHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            wallpView = itemView.findViewById(R.id.item_url);
            animCarga = itemView.findViewById(R.id.animacion_view_wpp);
        }
    }
}
