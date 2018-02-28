package com.jszheng.deletion;

import com.jszheng.base.BinaryTree;

@FunctionalInterface
public interface DeletionAlgo<E> {

    boolean delete(BinaryTree<E> bt, E data);
}
