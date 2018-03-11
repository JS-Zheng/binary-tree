package com.jszheng.searchtree.redblack;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class RbtManipulator extends AbstractBstManipulator<RedBlackTree> {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (Red-Black Tree)");
        System.out.println("a. 節點結構 -- data(顏色)");
        System.out.println("b. 插入、搜尋 與刪除: O(log n)");
        System.out.println("c. ");
        System.out.println("d. 插入採 Bottom-up 法，旋轉不超過兩次");
        System.out.println();
    }

    @Override
    protected void addInsertOp() {
        super.addInsertOp();
        addOperation("insert (Top-down)", new TopDownInsertOperation());
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
