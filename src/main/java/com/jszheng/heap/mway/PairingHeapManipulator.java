package com.jszheng.heap.mway;

public class PairingHeapManipulator extends AbsMWayMinReducibleHeapManipulator<PairingHeap> {

    public PairingHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 成對堆積 (Pairing Heap)");
        System.out.println("a. 成對堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』成對堆積");
        System.out.println("c. 為一種單向優先權佇列 (single-ended priority queue)");
        System.out.println("d. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("e. 此實作移除極值之 合併策略 採 two-pass method");
        System.out.println("f. 插入、獲取最小值: O(1)");
        System.out.println("g. 實際時間 -- 合併、減少鍵值: O(1)      刪除任意值、刪除最小值: O(n) ");
        System.out.println("h. 攤銷時間 -- 合併、減少鍵值: o(log n)  刪除任意值、刪除最小值: O(log n)");
        System.out.println("i. 儘管理論上 decreaseKey & deleteMin 時間複雜度較差，『實務』上效能大多優於費氏堆積");
        System.out.println();
    }

    @Override
    protected PairingHeap createTree() {
        return new PairingHeap();
    }
}
