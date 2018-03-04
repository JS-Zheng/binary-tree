package com.jszheng.heap;

public interface MinHeap<E extends Comparable<? super E>> extends Heap<E> {

    E deleteMin();

    E searchMin();
}
