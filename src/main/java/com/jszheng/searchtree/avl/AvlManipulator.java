package com.jszheng.searchtree.avl;

import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstManipulator;

public class AvlManipulator extends BstManipulator {

    public AvlManipulator(Class dataType) {
        super(dataType);

    }

    @Override
    protected void comment() {
        System.out.println("[註]: AVL Tree");
        System.out.println("節點結構 -- data(平衡因子)\n");
    }

    @Override
    protected BinarySearchTree createTree() {
        return new AvlTree(baseBt());
    }
}
