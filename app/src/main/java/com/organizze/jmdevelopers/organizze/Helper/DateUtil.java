package com.organizze.jmdevelopers.organizze.Helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {


    public static String dataatual(){
        // pegando a data atual do sistema
        Long date=System.currentTimeMillis();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        String datastring= dateFormat.format(date);
        return datastring;

    }
}
