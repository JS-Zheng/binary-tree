package com.jszheng.heap.maxheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.manipulator.TreeManipulator;

public class MaxHeapManipulator extends TreeManipulator {

    public MaxHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            ((MaxHeap) bt).deleteMax();
            printBt();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search max", bt -> {
            Object obj = ((MaxHeap) bt).searchMax();
            System.out.println(obj);
        });
    }

    @Override
    protected BinaryTree createTree() {
        return new MaxHeap(baseBt());
    }
}
