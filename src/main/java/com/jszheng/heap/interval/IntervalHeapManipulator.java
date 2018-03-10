package com.jszheng.heap.interval;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class IntervalHeapManipulator extends DoubleEndedHeapManipulator {

    public IntervalHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new IntervalHeap(baseBt());
    }
}
