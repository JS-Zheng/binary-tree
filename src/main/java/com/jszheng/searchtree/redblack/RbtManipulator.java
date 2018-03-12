package com.jszheng.searchtree.redblack;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class RbtManipulator extends AbstractBstManipulator<RedBlackTree> {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addInsertOp() {
        super.addInsertOp();
        addOperation("insert (Top-down)", new TopDownInsertOperation());
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (Red-Black Tree)");
        System.out.println("a. 節點結構 -- data(顏色)");
        System.out.println("b. 為一種 self-balancing 二元搜尋樹");
        System.out.println("c. 由 Bayer 發表於 1971 年，當時名為 symmetric binary B-trees");
        System.out.println("d. Guibas 與 Sedgewick 於 1978 年，引入了紅/黑色的性質");
        System.out.println("e. 廣泛應用於各 library 中，如: C++ STL 的 set、map");
        System.out.println("   或 Java 的 TreeSet、TreeMap...");
        System.out.println("f. 此實作支援 Bottom-up 與 Top-down 插入");
        System.out.println("g. 插入、搜尋 與刪除: O(log n)");
        System.out.println();
    }

    @Override
    protected RedBlackTree createTree() {
        return new RedBlackTree<>(baseBt());
    }

    class TopDownInsertOperation extends InsertOperation {
        @Override
        protected void executeInsert(Comparable[] data) {
            tree.insertByTopDown(data);
        }
    }
}
