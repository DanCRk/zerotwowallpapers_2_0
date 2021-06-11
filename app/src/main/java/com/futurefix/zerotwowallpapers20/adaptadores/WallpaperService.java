package com.futurefix.zerotwowallpapers20.adaptadores;

import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperService {

    public static List<Wallpaper> wallpaperZero = new ArrayList<>();

    public static List<Wallpaper> Categorias = new ArrayList<>();
    public static List<Wallpaper> WallpaperCat = new ArrayList<>();

    public static void addWallpaperCategorias(Wallpaper wallpaper){ Categorias.add(wallpaper);}

    public static void addWallpaperZero(Wallpaper wallpaper){
        wallpaperZero.add(wallpaper);
    }

    public static void addWallpaperWallpaperCat(Wallpaper wallpaper){ WallpaperCat.add(wallpaper);}

    public static void clearList(){ WallpaperCat.clear(); }

    public static int cuantosList(){ return WallpaperCat.size(); }

}
