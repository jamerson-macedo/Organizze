package com.organizze.jmdevelopers.organizze.Helper;

import android.util.Base64;

public class Base64Custom {
    public static String codificar(String valor){ // remove espacos no comeco e fim
        return Base64.encodeToString(valor.getBytes(),Base64.DEFAULT).replaceAll("\\n|//r","");

    }
    public static String decodificar(String valor){
        return new String( Base64.decode(valor,Base64.DEFAULT));
    }
}
