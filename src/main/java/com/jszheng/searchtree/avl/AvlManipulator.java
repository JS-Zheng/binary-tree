package com.jszheng.searchtree.avl;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class AvlManipulator extends AbstractBstManipulator<AvlTree> {

    public AvlManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Avl 樹 (Avl Tree)");
        System.out.println("a. 節點結構 -- data(平衡因子)");
        System.out.println("b. 被稱最早發明的 self-balancing 二元搜尋樹 (1962)");
        System.out.println("c. 得名於發明者 Adelson-Velsky and Landis");
        System.out.println("d. 插入、搜尋 與刪除: O(log n)");
        System.out.println("e. 若無指明，『高度平衡樹』通常即指 Avl Tree");
        System.out.println();
    }

    @Override
    protected AvlTree createTree() {
        return new AvlTree<>(baseBt());
    }
}
