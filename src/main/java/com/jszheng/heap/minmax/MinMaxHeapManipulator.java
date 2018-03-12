package com.jszheng.heap.minmax;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class MinMaxHeapManipulator extends DoubleEndedHeapManipulator {

    public MinMaxHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 最小最大堆積 (Min-max Heap)");
        System.out.println("a. 為一種雙向優先權佇列 (double-ended priority queue)");
        System.out.println("b. 為一種 完整二元樹 (Complete Binary Tree)");
        System.out.println("c. 由 Atkinson、Sack、Santoro 與 Strothotte 發表於 1986 年");
        System.out.println("d. 根節點為最小值，其左子或右子則為最大值");
        System.out.println("e. 尋找最小、最大值: O(1)");
        System.out.println("f. 插入 與 刪除最小、最大值: O(log n)");
        System.out.println("http://cglab.ca/~morin/teaching/5408/refs/minmax.pdf");
        System.out.println();
    }

    @Override
    protected BinaryTree createTree() {
        return new MinMaxHeap(baseBt());
    }
}
