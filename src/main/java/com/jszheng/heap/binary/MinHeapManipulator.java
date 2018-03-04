package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MinHeap;
import com.jszheng.manipulator.BinTreeManipulator;

public class MinHeapManipulator extends BinTreeManipulator {

    public MinHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete min", bt -> {
            ((MinHeap) bt).deleteMin();
            printTree();
        });
    }

    @Override
    protected BinaryTree createTree() {
        return new MinBinaryHeap(baseBt());
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search min", bt -> {
            Object obj = ((MinHeap) bt).searchMin();
            System.out.println(obj);
        });
    }
}
