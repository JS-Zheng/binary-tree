package com.jszheng.traversal;

import com.jszheng.util.ReflectionUtil;

public class TraversalAlgoFactory {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static TraversalAlgo create(String order, boolean recursive, boolean isFullMode, TraversalNodeHandler handler) {
        // Default Value
        order = order != null ? capFirstChar(order) : "Level";

        Class clz = getClassByOrder(order, recursive);

        // Try !recursive
        if (clz == null)
            clz = getClassByOrder(order, !recursive);

        return create(clz, isFullMode, handler);
    }

    public static TraversalAlgo create(Class clz, boolean isFullMode, TraversalNodeHandler handler) {
        TraversalAlgo algo = null;
        if (clz != null) {
            try {
                algo = (TraversalAlgo) ReflectionUtil.newInstanceByClz(clz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (algo != null) {
            if (handler == null)
                handler = new DefaultNodeHandler<>();

            algo.setNodeHandler(handler);
            algo.setFullBtMode(isFullMode);
        }

        return algo;
    }

    private static String capFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Class getClassByOrder(String order, boolean recursive) {
        try {
            String algoClz = getTraversalClzName(order, recursive);
            return Class.forName(algoClz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static String getTraversalClzName(String order, boolean recursive) {
        String packageName = getTraversalPackageName(order);
        String simpleName = getTraversalClzSimpleName(order, recursive);
        return packageName + "." + simpleName;
    }

    private static String getTraversalClzSimpleName(String order, boolean recursive) {
        String recursiveStr = recursive ? "Recursive" : "";
        return order + "Order" + recursiveStr + "Traversal";
    }

    private static String getTraversalPackageName(String order) {
        order = order.toLowerCase();
        return TraversalAlgo.class.getPackage().getName() + "." + order;
    }
}
