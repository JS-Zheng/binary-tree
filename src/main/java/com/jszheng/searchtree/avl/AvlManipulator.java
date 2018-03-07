package com.jszheng.searchtree.avl;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class AvlManipulator extends AbstractBstManipulator<AvlTree> {

    public AvlManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: AVL Tree");
        System.out.println("節點結構 -- data(平衡因子)\n");
    }

    @Override
    protected AvlTree createTree() {
        return new AvlTree<>(baseBt());
    }
}
