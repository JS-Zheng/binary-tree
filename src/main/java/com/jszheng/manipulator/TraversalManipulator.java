package com.jszheng.manipulator;

import com.jszheng.base.BinaryTree;

class TraversalManipulator extends Manipulator<TreeOperation<BinaryTree>> {

    private BinaryTree bt;

    TraversalManipulator(BinaryTree bt) {
        this.bt = bt;
    }

    @Override
    public boolean handleOperation(int operationId) {
        if (!validateInput(operationId))
            return false;

        this.operations.get(operationId - 1).execute(bt);
        return true;
    }

    @Override
    protected String getChoosePrompt() {
        return "";
    }

    @Override
    protected void addDefaultOperation() {
        addOperation("前序", bt -> bt.traverse("Pre"));

        addOperation("中序", bt -> bt.traverse("In"));

        addOperation("後序", bt -> bt.traverse("Post"));

        addOperation("階層", bt -> bt.traverse("Level"));
    }
}
