package com.futurefix.zerotwowallpapers20;

import java.util.ArrayList;
import java.util.List;

public class Auxiliar {

    public static Boolean estadoactualCheckBox;
    public static int estadoSelectorColumnas=2;
    /*
    * 1 x 1 el estado es 0
    * 2 x 2 el estado es 1
    * 3 x 3 el estado es 2
    * 4 x 4 el estado es 3
    * */
    public static int iteradorAnuncios=1;

    public static List<String> identi = new ArrayList<>();

    public Auxiliar() {
    }

    public static void guardarEstadoCheckBox(boolean bol){
        estadoactualCheckBox = bol;
    }

    public static void guardarEstadoelectorColumnas(int intero){estadoSelectorColumnas = intero;}


}
