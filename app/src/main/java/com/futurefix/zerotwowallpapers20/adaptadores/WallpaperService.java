package com.futurefix.zerotwowallpapers20.adaptadores;

import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperService {

    public static List<Wallpaper> wallpaperZero = new ArrayList<>();
    public static List<Wallpaper> wallpaperAux = new ArrayList<>();

    public static List<Wallpaper> favoritos = new ArrayList<>();

    public static void addWallpaperZero(Wallpaper wallpaper){
        wallpaperZero.add(wallpaper);
    }
    public static void addWallpaperAux(Wallpaper wallpaper){ wallpaperAux.add(wallpaper); }

    public static void addWallpaperFavoritos(Wallpaper wallpaper) {favoritos.add(wallpaper);}
    public static void removeWallpaperFavoritos(Wallpaper wallpaper) {favoritos.remove(wallpaper);}

    // Metodo para retornar el tamaño de la lista de wpps

    public static int cuantosList(){ return favoritos.size(); }

}
