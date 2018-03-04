package com.jszheng.heap;

import com.jszheng.manipulator.BinTreeManipulator;

public abstract class DoubleEndedHeapManipulator extends BinTreeManipulator {

    public DoubleEndedHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            ((DoubleEndedHeap) bt).deleteMax();
            printTree();
        });

        addOperation("delete min", bt -> {
            ((DoubleEndedHeap) bt).deleteMin();
            printTree();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search max", bt -> {
            Object obj = ((DoubleEndedHeap) bt).searchMax();
            System.out.println(obj);
        });

        addOperation("search min", bt -> {
            Object obj = ((DoubleEndedHeap) bt).searchMin();
            System.out.println(obj);
        });
    }
}
