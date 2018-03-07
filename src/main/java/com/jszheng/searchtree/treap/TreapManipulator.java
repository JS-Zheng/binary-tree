package com.jszheng.searchtree.treap;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class TreapManipulator extends AbstractBstManipulator<Treap> {

    public TreapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註] -- 樹堆 (Treap)");
        System.out.println("節點結構: data(隨機優先權)\n");
    }

    @Override
    protected Treap createTree() {
        return new Treap<>(baseBt());
    }
}
