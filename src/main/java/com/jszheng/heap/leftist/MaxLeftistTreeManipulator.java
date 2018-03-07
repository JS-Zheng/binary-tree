package com.jszheng.heap.leftist;

import com.jszheng.heap.binary.AbsMaxBinaryHeapManipulator;

public class MaxLeftistTreeManipulator extends AbsMaxBinaryHeapManipulator<MaxLeftistTree<Comparable<? super Comparable>>> {

    public MaxLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected MaxLeftistTree<Comparable<? super Comparable>> createTree() {
        return new MaxLeftistTree<>(baseBt());
    }
}
