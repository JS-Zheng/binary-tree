package com.jszheng.base.complete;

import com.jszheng.base.BinaryTree;
import com.jszheng.manipulator.BinTreeManipulator;

public class CompleteBtManipulator extends BinTreeManipulator {

    public CompleteBtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new CompleteBtImpl(baseBt());
    }
}
