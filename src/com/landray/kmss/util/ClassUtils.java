package com.landray.kmss.util;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassUtils {


    /**
     * 针对EKP特有的短类名设计的缓存，存储内容大致是 KmReviewMain->com.landray.kmss.km.review.model.KmReviewMain
     * 这些信息是从数据字典里读取到的，所以只适用于加载ModelClass
     * 它存在初始化的过程，所以有个
     */
    private volatile static Map<String, String> modelNames;
    /**
     * 针对一些曾经请求过，但是无法加载的类，进行标记，防止重复犯错
     * 无法找到的类一般不多见，初始化容量256，如果这个容器内容过大，表示业务可能在错误的调用
     */
    private static Map<Object, Boolean> unreachableClassCache = new ConcurrentHashMap<>(256);

    public static Class<?> forNameNullable(String name) {
        try {
            return forNameInner(name, org.springframework.util.ClassUtils.getDefaultClassLoader(), true);
        }catch (Exception e){
            return null;
        }
    }


    private static ClassLoader getDefaultClassLoader() {
        return null;
    }
    /**
     * 找类对象的具体实现逻辑
     * @param className 可以是类全名，如果是短类名，则必须在EKP的数据字典里有对应的全路径
     * @param classLoader 类加载器，可空，一般是当前线程的类加载器
     * @param ignoreException 是否忽略过程中的异常
     * @return 存在即返回class对象，如果不存在，当ignoreException=true表示忽略异常，如果没找到就返回null，否则抛异常。
     * @throws ClassNotFoundException 当ignoreException=false且class对象不存在的时候
     * @throws LinkageError 当ignoreException=false且class对象不存在的时候
     */
    private static Class<?> forNameInner(String className, ClassLoader classLoader, boolean ignoreException) throws ClassNotFoundException {
        /*
            * 优化点
            * #1 优先处理不规范的入参（短类名），尽可能避免真实的 Class.forName
            * #2 添加了ignoreException选项，防止构造异常信息（JDK构造异常是很耗时的）
            * #3 记录加载失败的参数，快速返回（避免无谓的Class.forName）
         */
        String clazzFullName = className;
//        转换className 这样可以有效的避免Class.forName
        if(!className.contains(".")) {
            clazzFullName = buildModelName().get(className);
            if(StringUtils.isBlank(clazzFullName)){
                if(!ignoreException){
                    throw new ClassNotFoundException("Class not found:"+clazzFullName);
                }else{

                    return null;
                }
            }
        }

//        如果曾经加载过，直接返回
        if(unreachableClassCache.containsKey(clazzFullName)){
            if(!ignoreException){
                throw new ClassNotFoundException("Class not found:"+clazzFullName);
            }else{
                return null;
            }
        }

        return null;
    }

    private static Map<String, String> buildModelName() {
        if(modelNames == null){

        }
    }

}
