package com.jszheng.manipulator;

import com.jszheng.base.Tree;

import static com.jszheng.util.ScannerUtil.*;

public abstract class TreeManipulator<TreeType extends Tree> extends Manipulator<TreeOperation<TreeType>> {

    protected TreeType tree;
    protected Class dataType;

    protected TreeManipulator(Class dataType) {
        this.dataType = dataType;
        tree = createTree();
        comment();
    }

    @Override
    public boolean handleOperation(int operationId) {
        if (!super.handleOperation(operationId))
            return false;

        this.operations.get(operationId - 1).execute(tree);
        return true;
    }

    @Override
    protected String getChoosePrompt() {
        return "========== C、請選擇操作 ==========\n";
    }

    @Override
    protected final void addDefaultOperation() {
        addInsertOp();
        addDeleteOp();
        addSearchOp();
        addOtherOp();
    }

    @Override
    protected void afterExecute(int typeId) {
        System.out.println();
    }

    protected void addDeleteOp() {
        // default do nothing
    }

    protected void addOtherOp() {
        // default do nothing
    }

    protected void addSearchOp() {
        // default do nothing
    }

    protected void comment() {
        // default do nothing
    }

    protected abstract TreeType createTree();

    protected Object getInput(String prompt) {
        if (dataType == Integer.class) {
            return getInteger(prompt);
        } else {
            return getString(prompt);
        }
    }

    protected abstract void printTree();

    private void addInsertOp() {
        addOperation("insert", bt -> {
            String str = getLine("Insert Data (多筆資料以空白間隔):");
            str = str.trim();
            String[] data = str.split("\\s+");

            if (data[0].equals("")) {
                System.out.println(errorPrompt);
                return;
            }

            if (dataType == Integer.class) {
                Integer[] intData = new Integer[data.length];
                try {
                    for (int i = 0, l = data.length; i < l; i++) {
                        intData[i] = Integer.valueOf(data[i]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(errorPrompt);
                    return;
                }
                bt.insert(intData);
            } else
                bt.insert(data);

            printTree();
        });
    }


}
