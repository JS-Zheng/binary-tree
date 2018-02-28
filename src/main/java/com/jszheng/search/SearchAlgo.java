package com.jszheng.search;

import com.jszheng.base.BinaryTree;

@FunctionalInterface
public interface SearchAlgo<E> {

    SearchResult<E> search(BinaryTree<E> bt, E data);
}
