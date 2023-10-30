package com.landray.kmss.util;

public class StringUtil {


    public static boolean isNull(String str){
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotNull(String str){
        return !isNotNull(str);
    }
}
