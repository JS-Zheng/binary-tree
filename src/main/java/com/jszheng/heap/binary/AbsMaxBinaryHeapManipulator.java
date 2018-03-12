package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MaxHeap;
import com.jszheng.manipulator.BinTreeManipulator;
import com.jszheng.node.BinTreeNode;

public abstract class AbsMaxBinaryHeapManipulator
        <T extends BinaryTree<Comparable<? super Comparable>>
                & MaxHeap<Comparable<? super Comparable>, BinTreeNode<Comparable<? super Comparable>>>> extends BinTreeManipulator<T> {

    protected AbsMaxBinaryHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete max", bt -> {
            bt.deleteMax();
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
    }
}
