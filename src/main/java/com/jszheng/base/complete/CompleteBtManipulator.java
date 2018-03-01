package com.jszheng.base.complete;

import com.jszheng.base.BinaryTree;
import com.jszheng.manipulator.TreeManipulator;

public class CompleteBtManipulator extends TreeManipulator {

    public CompleteBtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new CompleteBtImpl(baseBt());
    }
}
