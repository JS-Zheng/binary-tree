package com.jszheng.heap.deap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class DeapManipulator extends DoubleEndedHeapManipulator {

    public DeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Deap");
        System.out.println("a. 為一種雙向優先權佇列 (double-ended priority queue)");
        System.out.println("b. 為一種 完整二元樹 (Complete Binary Tree)");
        System.out.println("c. 由 Svante 發表於 1987 年，稱其為 Min-max Heap 的改良");
        System.out.println("d. 根節點之左子樹為 Min Heap、右子樹為 Max Heap");
        System.out.println("   不同於 SMMH，SMMH 並不區分 Min Heap 與 Max Heap");
        System.out.println("e. 根節點為最小值，其左子或右子為最大值");
        System.out.println("f. 尋找最小、最大值: O(1)");
        System.out.println("g. 插入 與 刪除最小、最大值: O(log n)");
        System.out.println("https://pdfs.semanticscholar.org/e1e5/eafb44abc04834caf752b147256335a73bb3.pdf");
        System.out.println();
    }

    @Override
    protected BinaryTree createTree() {
        return new Deap(baseBt());
    }
}
