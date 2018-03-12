package com.jszheng.searchtree.sizebalanced;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class SbtManipulator extends AbstractBstManipulator<SizeBalancedTree> {

    public SbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 大小平衡樹 (Size-Balanced Tree)");
        System.out.println("a. 節點結構 -- data(size)");
        System.out.println("b. 為一種 self-balancing 二元搜尋樹");
        System.out.println("c. 由中國人陳啟峰於 2007 年發表");
        System.out.println("d. 性能良好且易於尋找樹中『第 k 大』之元素");
        System.out.println("e. 插入、搜尋 與刪除: O(log n)");
        System.out.println();
    }

    @Override
    protected SizeBalancedTree createTree() {
        return new SizeBalancedTree<>(baseBt());
    }
}
