package com.jszheng.searchtree.sizebalanced;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class SbtManipulator extends AbstractBstManipulator<SizeBalancedTree> {

    public SbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected SizeBalancedTree createTree() {
        SizeBalancedBase base = new SizeBalancedBase();
        return new SizeBalancedTree(base);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 大小平衡樹 (Size-Balanced Tree)");
        System.out.println("a. 節點結構 -- data(size)");
        System.out.println("b. 為一種 self-balancing 二元搜尋樹");
        System.out.println("c. 由中國人陳啟峰 發表於 2007 年");
        System.out.println("d. 易於尋找樹中『第 k 大』之元素");
        System.out.println("e. 此實作主要為 Set 觀點，因此不允許插入重複鍵值，");
        System.out.println("   也不允許移除失敗後，改為移除搜尋路徑上的末端節點 (原始論文方法)");
        System.out.println("f. 針對 e. 的兩種解法 -- 1.backtrack修復size(耗時)  2.使用Dummy Node(耗空間)");
        System.out.println("g. 插入、搜尋 與刪除: O(log n)");
        System.out.println();
    }
}
