package com.jszheng.searchtree.treap;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class TreapManipulator extends AbstractBstManipulator<Treap> {

    public TreapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 樹堆 (Treap)");
        System.out.println("a. 節點結構: data(隨機優先權)");
        System.out.println("b. 是一種 randomized 二元搜尋樹");
        System.out.println("c. Treap 一詞，來自於 Tree + Heap");
        System.out.println("d. 其操作仰賴隨機加權值，因此結果並不唯一");
        System.out.println("e. 此實作預設加權上限為 65535");
        System.out.println("f. Avg Case -- 插入、搜尋 與刪除: O(log n)");
        System.out.println("g. Worst Case -- 插入、搜尋 與刪除: O(n)");
        System.out.println();
    }

    @Override
    protected Treap createTree() {
        return new Treap<>(baseBt());
    }
}
