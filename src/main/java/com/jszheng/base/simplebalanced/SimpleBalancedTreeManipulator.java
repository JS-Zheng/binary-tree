package com.jszheng.base.simplebalanced;

import com.jszheng.base.BinaryTree;
import com.jszheng.manipulator.BinTreeManipulator;

public class SimpleBalancedTreeManipulator extends BinTreeManipulator {

    public SimpleBalancedTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 『並非』二元搜尋樹");
        System.out.println("常用於說明 AVL Tree 與 SizeBalanced Tree 之平衡原理\n");
    }

    @Override
    protected BinaryTree createTree() {
        return new SimpleBalancedTree(baseBt());
    }
}
