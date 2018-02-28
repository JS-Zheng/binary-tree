package com.jszheng.heap.deap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class DeapManipulator extends DoubleEndedHeapManipulator {

    public DeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new Deap(baseBt());
    }
}
