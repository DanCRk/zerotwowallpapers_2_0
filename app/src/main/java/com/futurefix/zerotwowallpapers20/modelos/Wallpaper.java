package com.futurefix.zerotwowallpapers20.modelos;

public class Wallpaper {
    private String id;
    private String nombre;
    private String tag1;
    private String tag2;
    private String tag3;
    private String tag4;
    private String url;

    public Wallpaper(){

    }

    public Wallpaper(String id, String nombre, String tag1, String tag2, String tag3, String tag4, String url) {
        this.id = id;
        this.nombre = nombre;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        return id.equals(((Wallpaper)o).id);
    }

}
