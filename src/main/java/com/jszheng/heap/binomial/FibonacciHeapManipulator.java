package com.jszheng.heap.binomial;

public class FibonacciHeapManipulator extends BinomialHeapManipulator {

    public FibonacciHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Fibonacci Heap");
        System.out.println("a. 費氏堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』費氏堆積");
        System.out.println("c. 節點結構採 Circular Doubly Linked List");
        System.out.println("d. 根據不同合併策略，結果可能不唯一");
        System.out.println("e. 移除極值 攤銷時間 (amortized time) -- O(log n)\n");

    }

    @Override
    protected BinomialHeap createTree() {
        return new FibonacciHeap(false);
    }
}
