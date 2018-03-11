package com.jszheng;

public class Env {

    public static final boolean dev = true;
    public final static String osName = System.getProperty("os.name");
    public final static String COMPLETE_BINARY_TREE = "完整二元樹";
    public final static String SIMPLE_BALANCED_TREE = "簡單平衡樹";
    public final static String BINARY_SEARCH_TREE = "二元搜尋樹";
    public final static String SPLAY_TREE = "伸展樹";
    public final static String TREAP = "Treap";
    public final static String AVL_TREE = "AVL樹";
    public final static String RED_BLACK_TREE = "紅黑樹";
    public final static String SIZE_BALANCED_TREE = "大小平衡樹";
    public final static String MIN_HEAP = "Min-Heap";
    public final static String MAX_HEAP = "Max-Heap";
    public final static String BINOMIAL_HEAP = "二項式堆積";
    public final static String FIBONACCI_HEAP = "費氏堆積";
    public final static String PAIRING_HEAP = "成對堆積";
    public final static String MIN_LEFTIST_TREE = "最小左傾樹";
    public final static String MAX_LEFTIST_TREE = "最大左傾樹";
    public final static String MIN_MAX_HEAP = "Min-Max Heap";
    public final static String INTERVAL_HEAP = "區間堆積";
    public final static String DEAP = "DEAP";
    public final static String SMMH = "SMMH";
    public static final String HUFFMAN_TREE = "霍夫曼樹";
    public static boolean debug = true;
    public static int rotateLeftCount = 0;
    public static int rotateRightCount = 0;

    public static void clearRotateStatist() {
        rotateLeftCount = 0;
        rotateRightCount = 0;
    }
}
