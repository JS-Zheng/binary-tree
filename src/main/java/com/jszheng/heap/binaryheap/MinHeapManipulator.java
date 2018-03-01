package com.jszheng.heap.binaryheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MinHeap;
import com.jszheng.manipulator.TreeManipulator;

public class MinHeapManipulator extends TreeManipulator {

    public MinHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete min", bt -> {
            ((MinHeap) bt).deleteMin();
            printBt();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search min", bt -> {
            Object obj = ((MinHeap) bt).searchMin();
            System.out.println(obj);
        });
    }

    @Override
    protected BinaryTree createTree() {
        return new MinBinaryHeap(baseBt());
    }
}
