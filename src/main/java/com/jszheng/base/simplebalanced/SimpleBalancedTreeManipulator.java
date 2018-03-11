package com.jszheng.base.simplebalanced;

import com.jszheng.manipulator.BinTreeManipulator;

public class SimpleBalancedTreeManipulator extends BinTreeManipulator<SimpleBalancedTree> {

    public SimpleBalancedTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 簡單平衡樹 (Simple-balanced Tree)");
        System.out.println("a. 『並非』二元搜尋樹");
        System.out.println("b. 常用於說明 SizeBalanced Tree 之平衡原理");
        System.out.println();
    }

    @Override
    protected SimpleBalancedTree createTree() {
        return new SimpleBalancedTree<>(baseBt());
    }
}
