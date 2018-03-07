package com.jszheng.searchtree.redblack;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class RbtManipulator extends AbstractBstManipulator<RedBlackTree> {

    public RbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 紅黑樹 (RedBlack Tree)");
        System.out.println("節點結構 -- data(顏色)\n");
    }

    @Override
    protected RedBlackTree createTree() {
        return new RedBlackTree<>(baseBt());
    }
}
