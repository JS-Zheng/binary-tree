package com.jszheng.heap.mway;

public class FibonacciHeapManipulator extends AbsMWayMinReducibleHeapManipulator<FibonacciHeap> {

    public FibonacciHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected FibonacciHeap createTree() {
        return new FibonacciHeap(false);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 費氏堆積 (Fibonacci Heap)");
        System.out.println("a. 節點結構 -- 若某子節點曾失去過 child，以 (#) 符號標記");
        System.out.println("b. 費氏堆積『並非』二元樹");
        System.out.println("c. 此實作為『最小』費氏堆積，且插入時不進行 consolidate");
        System.out.println("d. 為一種單向優先權佇列 (single-ended priority queue)");
        System.out.println("e. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("f. 根據不同合併策略，結果可能不唯一");
        System.out.println("g. 插入、合併 與獲取最小值: O(1)");
        System.out.println("h. 實際時間 -- 減少鍵值: O(n)  刪除任意值、刪除最小值: O(n) ");
        System.out.println("i. 攤銷時間 -- 減少鍵值: O(1)  刪除任意值、刪除最小值: O(log n)");
        System.out.println("j. 移除極值 合併策略採 CLRS 之作法 -- consolidate start from right sibling of target");
        System.out.println();
    }
}
