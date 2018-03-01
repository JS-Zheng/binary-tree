package com.jszheng.heap.leftisttree;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.binaryheap.MinHeapManipulator;

public class MinLeftistTreeManipulator extends MinHeapManipulator {

    public MinLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new MinLeftistTree(baseBt());
    }
}
