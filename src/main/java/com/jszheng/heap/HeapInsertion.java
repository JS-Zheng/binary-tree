package com.jszheng.heap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.completebt.CompleteBtInsertion;

/*
 * O(Log n)
 */
public abstract class HeapInsertion<E extends Comparable<? super E>> extends CompleteBtInsertion<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        return data != null && super.insert(bt, data);
    }
}
