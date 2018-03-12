package com.jszheng.heap.mway;

public class BinomialHeapManipulator extends AbstractMWayMinHeapManipulator<BinomialHeap> {

    public BinomialHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 二項式堆積 (Binomial Heap)");
        System.out.println("a. 二項式堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』二項式堆積");
        System.out.println("c. 為一種單向優先權佇列 (single-ended priority queue)");
        System.out.println("d. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("e. 根據不同合併策略，結果可能不唯一");
        System.out.println("f. 合併、刪除最小值 時間為: O(log n)，此處的合併指的是 consolidate 之結合操作");
        System.out.println("   而非 Horowitz 所指的『合併』 root list -- O(1)");
        System.out.println("g. 插入攤銷時間 --  O(1)");
        System.out.println();
    }

    @Override
    protected BinomialHeap createTree() {
        return new BinomialHeap<>();
    }
}
