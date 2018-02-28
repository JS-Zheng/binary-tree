package com.jszheng.searchtree.rbtree;

import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstManipulator;

public class RbtManipulator extends BstManipulator {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (RedBlack Tree)");
        System.out.println("節點結構 -- data(顏色)\n");
    }

    @Override
    protected BinarySearchTree createTree() {
        return new RedBlackTree(baseBt());
    }
}
