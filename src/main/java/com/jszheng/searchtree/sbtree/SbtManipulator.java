package com.jszheng.searchtree.sbtree;

import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstManipulator;

public class SbtManipulator extends BstManipulator {

    public SbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: SizeBalanced Tree");
        System.out.println("節點結構 -- data(size)\n");
    }

    @Override
    protected BinarySearchTree createTree() {
        return new SizeBalancedTree(baseBt());
    }
}
