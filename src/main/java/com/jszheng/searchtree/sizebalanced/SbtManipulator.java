package com.jszheng.searchtree.sizebalanced;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class SbtManipulator extends AbstractBstManipulator<SizeBalancedTree> {

    public SbtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: SizeBalanced Tree");
        System.out.println("節點結構 -- data(size)\n");
    }

    @Override
    protected SizeBalancedTree createTree() {
        return new SizeBalancedTree<>(baseBt());
    }
}
