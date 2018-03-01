package com.jszheng.heap;

public interface MaxHeap<E extends Comparable<? super E>> extends Heap<E> {

    E deleteMax();

    E searchMax();
}
