package com.jszheng.heap.mway;

public interface BinomialTreeNodeHandler<E extends Comparable<? super E>> {

    boolean handle(BinomialTreeNode<E> node);
}
