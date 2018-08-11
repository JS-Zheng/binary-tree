package com.jszheng.heap.leftist;

import com.jszheng.heap.binary.AbsMaxBinaryHeapManipulator;

public class MaxLeftistTreeManipulator extends AbsMaxBinaryHeapManipulator<MaxLeftistTree<Comparable<? super Comparable>>> {

    public MaxLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected MaxLeftistTree<Comparable<? super Comparable>> createTree() {
        return new MaxLeftistTree<>(baseBt());
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 最大左傾樹 (Max Leftist Tree)");
        System.out.println("a. 節點結構 --  data(到外部節點之最短路徑長度)");
        System.out.println("b. 為一種單向優先權佇列 (single-ended priority queue)");
        System.out.println("c. 其特色是易於合併、且插入與刪除操作皆依此實作");
        System.out.println("d. 每個節點的鍵值都比子節點大");
        System.out.println("e. 尋找最大值: O(1)");
        System.out.println("f. 插入、合併 與刪除最大值: Θ(log n)");
        System.out.println();
    }
}
