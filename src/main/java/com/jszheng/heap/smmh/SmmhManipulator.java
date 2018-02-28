package com.jszheng.heap.smmh;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class SmmhManipulator extends DoubleEndedHeapManipulator {

    public SmmhManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new SymmetricMinMaxHeap(baseBt());
    }
}
