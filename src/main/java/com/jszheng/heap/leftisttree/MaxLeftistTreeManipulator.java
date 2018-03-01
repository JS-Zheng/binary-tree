package com.jszheng.heap.leftisttree;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.binaryheap.MaxHeapManipulator;

public class MaxLeftistTreeManipulator extends MaxHeapManipulator {

    public MaxLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new MaxLeftistTree(baseBt());
    }
}
