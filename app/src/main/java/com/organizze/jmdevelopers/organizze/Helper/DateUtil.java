package com.organizze.jmdevelopers.organizze.Helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {


    public static String dataatual() {
        // pegando a data atual do sistema
        Long date = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String datastring = dateFormat.format(date);
        return datastring;

    }

    public static String removerbarra(String data) {
        // divide tudo e joga cada local em um array
        String retorno[] = data.split("/");
        String dia = retorno[0];
        String mes = retorno[1];
        String ano = retorno[2];
        String mesano = mes + ano;
        return mesano;

    }
}
