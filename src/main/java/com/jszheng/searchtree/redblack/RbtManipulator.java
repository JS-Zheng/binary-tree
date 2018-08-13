package com.jszheng.searchtree.redblack;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class RbtManipulator extends AbstractBstManipulator<RedBlackTree> {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected RedBlackTree createTree() {
        return new RedBlackTree();
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (Red-Black Tree)");
        System.out.println("a. 節點結構 -- data(顏色)");
        System.out.println("b. 為一種 self-balancing 二元搜尋樹");
        System.out.println("c. 由 Bayer 發表於 1971 年，當時名為 symmetric binary B-trees");
        System.out.println("d. Guibas 與 Sedgewick 於 1978 年，引入了紅/黑色的性質");
        System.out.println("e. 其廣泛應用於各 library 中，如: C++ STL 的 set、map");
        System.out.println("   以及 Java 的 TreeSet、TreeMap...");
        System.out.println("f. 此實作支援 Bottom-up 與 Top-down 插入，經測試 隨機插入1000萬筆測資，平均：");
        System.out.println("   Bottom-up -- 左旋:18412132(次)  右旋:18415803  父叔變色:32457403  耗費:13.64s");
        System.out.println("   Top-down  -- 左旋:19106700(次)  右旋:19112413  父叔變色:40884210  耗費:14.72s");
        System.out.println("   Java TreeSet (採 Bottom-up) -- 耗費:13.85s");
        System.out.println("g. 插入、搜尋 與刪除: O(log n)");
        System.out.println();
    }

    @Override
    protected void addInsertOp() {
        super.addInsertOp();
        addOperation("insert (Top-down)", new TopDownInsertOperation());
    }

    class TopDownInsertOperation extends InsertOperation {
        @Override
        protected void executeInsert(Comparable[] data) {
            tree.insertByTopDown(data);
        }
    }
}
