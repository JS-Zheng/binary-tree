package com.jszheng.heap;

import com.jszheng.manipulator.BinTreeManipulator;

public abstract class DoubleEndedHeapManipulator<E extends AbsBinDoubleEndedHeap> extends BinTreeManipulator<E> {

    public DoubleEndedHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            bt.deleteMax();
            printTree();
        });

        addOperation("delete min", bt -> {
            bt.deleteMin();
            printTree();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search max", bt -> {
            Object obj = bt.searchMax();
            System.out.println(obj);
        });

        addOperation("search min", bt -> {
            Object obj = bt.searchMin();
            System.out.println(obj);
        });
    }
}
