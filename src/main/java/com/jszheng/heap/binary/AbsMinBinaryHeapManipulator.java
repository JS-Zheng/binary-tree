package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MinHeap;
import com.jszheng.manipulator.BinTreeManipulator;
import com.jszheng.node.BinTreeNode;

public abstract class AbsMinBinaryHeapManipulator
        <T extends BinaryTree<Comparable<? super Comparable>>
                & MinHeap<Comparable<? super Comparable>, BinTreeNode<Comparable<? super Comparable>>>> extends BinTreeManipulator<T> {

    protected AbsMinBinaryHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete min", bt -> {
            bt.deleteMin();
            printTree();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search min", bt -> {
            Object obj = bt.searchMin();
            System.out.println(obj);
        });
    }
}
