package com.jszheng.heap.leftist;

import com.jszheng.heap.binary.AbsMinBinaryHeapManipulator;

public class MinLeftistTreeManipulator extends AbsMinBinaryHeapManipulator<MinLeftistTree<Comparable<? super Comparable>>> {

    public MinLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected MinLeftistTree<Comparable<? super Comparable>> createTree() {
        return new MinLeftistTree<>(baseBt());
    }
}
