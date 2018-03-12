package com.jszheng.heap.leftist;

import com.jszheng.heap.binary.AbsMinBinaryHeapManipulator;

public class MinLeftistTreeManipulator extends AbsMinBinaryHeapManipulator<MinLeftistTree<Comparable<? super Comparable>>> {

    public MinLeftistTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 最小左傾樹 (Min Leftist Tree)");
        System.out.println("a. 節點結構 --  data(到外部節點之最短路徑長度)");
        System.out.println("b. 為一種單向優先權佇列 (single-ended priority queue)");
        System.out.println("c. 其特色是易於合併、且插入與刪除操作皆依此實作");
        System.out.println("d. 每個節點的鍵值都比子節點小");
        System.out.println("e. 尋找最大值: O(1)");
        System.out.println("f. 插入、合併 與刪除最小值: Θ(log n)");
        System.out.println();
    }

    @Override
    protected MinLeftistTree<Comparable<? super Comparable>> createTree() {
        return new MinLeftistTree<>(baseBt());
    }
}
