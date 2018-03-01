package com.jszheng.heap.minmax;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class MinMaxHeapManipulator extends DoubleEndedHeapManipulator {

    public MinMaxHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new MinMaxHeap(baseBt());
    }
}
