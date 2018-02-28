package com.jszheng.heap;

import com.jszheng.manipulator.TreeManipulator;

public abstract class DoubleEndedHeapManipulator extends TreeManipulator {

    public DoubleEndedHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            ((DoubleEndedHeap) bt).deleteMax();
            printBt();
        });

        addOperation("delete min", bt -> {
            ((DoubleEndedHeap) bt).deleteMin();
            printBt();
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
