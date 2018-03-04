package com.jszheng.heap.deap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.CompleteBtInsertion;
import com.jszheng.node.BinTreeNode;

public class DeapInsertion<E extends Comparable<? super E>> extends CompleteBtInsertion<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        boolean isBtEmpty = bt.isEmpty();
        if (data == null) return false;

        if (isBtEmpty) super.insert(bt, null);
        return super.insert(bt, data);
    }

    @Override
    protected Deap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (Deap<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        Deap<E> deap = getBt();
        deap.insertionFixUp(newNode);
    }
}
