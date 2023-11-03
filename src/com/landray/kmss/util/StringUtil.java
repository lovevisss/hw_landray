package com.landray.kmss.util;

public class StringUtil {

    public static String replace(String srcText, String fromStr, String toStr){
        if(srcText == null){
            return null;
        }
        StringBuffer rtnVal = new StringBuffer();
        String rightText = srcText;
        for(int i = rightText.indexOf(fromStr); i > -1; i= rightText.indexOf(fromStr)){
            rtnVal.append(rightText.substring(0, i));
            rtnVal.append(toStr);
            rightText = rightText.substring(i + fromStr.length());
        }
        rtnVal.append(rightText);
        return rtnVal.toString();
    }

    public static boolean isNull(String str){
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotNull(String str){
        return !isNotNull(str);
    }
}
