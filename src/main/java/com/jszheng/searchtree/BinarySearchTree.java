package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.search.SearchResult;

public interface BinarySearchTree<E> extends BinaryTree<E> {

    void delete(E data);

    void delete(E data, boolean replaceByLMax);

    @Override
    BinarySearchTree<E> newTree();

    SearchResult<E> searchMax();

    SearchResult<E> searchMin();
}
