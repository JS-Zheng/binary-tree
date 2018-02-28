package com.jszheng.insertion;

import com.jszheng.base.BinaryTree;

@FunctionalInterface
public interface InsertionAlgo<E> {

    boolean insert(BinaryTree<E> bt, E data);
}
