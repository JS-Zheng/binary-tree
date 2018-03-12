package com.jszheng.searchtree.redblack;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class RbtManipulator extends AbstractBstManipulator<RedBlackTree> {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addInsertOp() {
        super.addInsertOp();
        addOperation("insert (Top-down)", new TopDownInsertOperation());
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (Red-Black Tree)");
        System.out.println("a. 節點結構 -- data(顏色)");
        System.out.println("b. 插入、搜尋 與刪除: O(log n)");
        System.out.println("c. 支援 Bottom-up 與 Top-down 插入");
        System.out.println("d. 經測試，此實作插入 100 萬筆隨機資料：");
        System.out.println("   Bottom-up 左旋:421868 右旋:421639 變色:414243 耗費:2.6s");
        System.out.println("   Top-down  左旋:281453 右旋:281657 變色:550669 耗費:3.5s");
        System.out.println();
    }

    @Override
    protected RedBlackTree createTree() {
        return new RedBlackTree<>(baseBt());
    }

    class TopDownInsertOperation extends InsertOperation {
        @Override
        protected void executeInsert(Comparable[] data) {
            System.out.println("!!");
            tree.insertByTopDown(data);
        }
    }
}
