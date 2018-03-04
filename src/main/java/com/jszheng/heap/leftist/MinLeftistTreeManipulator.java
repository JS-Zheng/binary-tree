package com.jszheng.heap.leftist;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.binary.MinHeapManipulator;

public class MinLeftistTreeManipulator extends MinHeapManipulator {

    public MinLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new MinLeftistTree(baseBt());
    }
}
