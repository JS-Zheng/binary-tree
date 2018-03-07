package com.jszheng.searchtree;

public final class BstManipulator extends AbstractBstManipulator<BinarySearchTree> {

    public BstManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinarySearchTree createTree() {
        return new BinarySearchTree<>(baseBt());
    }
}
