package com.jszheng.heap.interval;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class IntervalHeapManipulator extends DoubleEndedHeapManipulator {

    public IntervalHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new IntervalHeap(baseBt());
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 區間堆積 (Interval Heap)");
        System.out.println("a. 為一種雙向優先權佇列 (double-ended priority queue)");
        System.out.println("b. 為一種 完整二元樹 (Complete Binary Tree)");
        System.out.println("c. 由 Leeuwen 發表於 1988 年");
        System.out.println("d. 根節點之左區間為最小值、右區間為最大值");
        System.out.println("e. 尋找最小、最大值: O(1)");
        System.out.println("f. 插入 與 刪除最小、最大值: O(log n)");
        System.out.println();
    }
}
