package com.jszheng.heap.binary;

public class MaxHeapManipulator extends AbsMaxBinaryHeapManipulator<MaxBinaryHeap<Comparable<? super Comparable>>> {

    public MaxHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected MaxBinaryHeap<Comparable<? super Comparable>> createTree() {
        return new MaxBinaryHeap<>(baseBt());
    }
}
