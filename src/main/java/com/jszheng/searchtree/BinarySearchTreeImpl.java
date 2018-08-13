package com.jszheng.searchtree;

public class BinarySearchTreeImpl<E extends Comparable<? super E>> extends AbstractBst<E> {


    @Override
    public BinarySearchTreeImpl<E> newTree() {
        return new BinarySearchTreeImpl<>();
    }
}
