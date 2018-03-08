package com.jszheng.heap.mway;

public class PairingHeapManipulator extends AbsMWayMinReducibleHeapManipulator<PairingHeap> {

    public PairingHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Pairing Heap");
        System.out.println("a. 成對堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』成對堆積");
        System.out.println("c. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("d. 減少鍵值 攤銷時間 (amortized time) -- o(log n)");
        System.out.println("e. 移除極值 合併策略採 two-pass method");
        System.out.println("f. 儘管理論上 decreaseKey & deleteMin 時間複雜度較差，『實務』上效能大多優於費氏堆積\n");
    }

    @Override
    protected PairingHeap createTree() {
        return new PairingHeap();
    }
}
