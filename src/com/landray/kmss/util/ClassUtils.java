package com.landray.kmss.util;

public class ClassUtils {


    public static Class<?> forNameNullable(String name) {
        try {
            return forNameInner(name, org.springframework.util.ClassUtils.getDefaultClassLoader(), true);
        }catch ()
    }


    private static ClassLoader getDefaultClassLoader() {
        return null;
    }

    private static Class<?> forNameInner(String className, ClassLoader classLoader, boolean ignoreException){
        return null;
    }

}
