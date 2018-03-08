package com.jszheng.heap.mway;

public class BinomialHeapManipulator extends AbstractMWayMinHeapManipulator<BinomialHeap> {

    public BinomialHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Binomial Heap");
        System.out.println("a. 二項式堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』二項式堆積");
        System.out.println("c. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("d. 根據不同合併策略，結果可能不唯一\n");
    }

    @Override
    protected BinomialHeap createTree() {
        return new BinomialHeap<>();
    }
}
