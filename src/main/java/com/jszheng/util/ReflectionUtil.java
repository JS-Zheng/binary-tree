package com.jszheng.util;


public class ReflectionUtil {

    public static boolean debug = false;

    public static Object newInstanceByClz(Class clz) throws Exception {
        Object instance;
        String clzName = clz.getName();
        instance = clz.newInstance(); // 實例 clzName 物件

        printClzName(clzName);

        return instance;
    }

    public static Object newInstanceByClzWithParam(Class clz, Class[] typeList, Object[] params) throws Exception {
        Object instance;
        String clzName = clz.getName();
        instance = clz.getDeclaredConstructor(typeList).newInstance(params); // 實例 clzName 物件

        printClzName(clzName);

        return instance;
    }

    private static void printClzName(String clzName) {
        if (debug) {
            System.out.println("==========實例物件==========");
            System.out.println(clzName);
            System.out.println("===========================");
        }
    }
}
