package com.jszheng.searchtree.treap;

import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstManipulator;

public class TreapManipulator extends BstManipulator {

    public TreapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註] -- 樹堆 (Treap)");
        System.out.println("節點結構: data(隨機優先權)\n");
    }

    @Override
    protected BinarySearchTree createTree() {
        return new Treap<>(baseBt());
    }
}
