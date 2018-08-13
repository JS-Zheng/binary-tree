package com.jszheng.heap.smmh;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeapManipulator;

public class SmmhManipulator extends DoubleEndedHeapManipulator {

    public SmmhManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinaryTree createTree() {
        return new SymmetricMinMaxHeap();
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 對稱式最小最大堆積 (Symmetric Min-Max Heap)");
        System.out.println("a. 為一種雙向優先權佇列 (double-ended priority queue)");
        System.out.println("b. 為一種 完整二元樹 (Complete Binary Tree)");
        System.out.println("c. 由 Arvind 與 Rangan 發表於 1999 年，稱其為 Min-max Heap 與 Deap 之改良");
        System.out.println("   不同於它們，SMMH 不區分出 Min Heap 與 Max Heap，作者認為這會使計算變得複雜");
        System.out.println("d. 根節點不存放資料，此外，每個 左子點 需小於等於 其右兄弟");
        System.out.println("e. 尋找最小、最大值: O(1)");
        System.out.println("f. 插入 與 刪除最小、最大值: O(log n)");
        System.out.println("https://pdfs.semanticscholar.org/351a/044138834e032c11ddf07d6d656bfa4d2bcc.pdf");
        System.out.println();
    }
}
