package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MaxHeap;
import com.jszheng.manipulator.BinTreeManipulator;

public class MaxHeapManipulator extends BinTreeManipulator {

    public MaxHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            ((MaxHeap) bt).deleteMax();
            printTree();
        });
    }

    @Override
    protected BinaryTree createTree() {
        return new MaxBinaryHeap(baseBt());
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search max", bt -> {
            Object obj = ((MaxHeap) bt).searchMax();
            System.out.println(obj);
        });
    }
}
