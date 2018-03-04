package com.jszheng.heap;

public interface MeldableHeap<HEAP, E> extends Heap<E> {

    void merge(HEAP node);
}
