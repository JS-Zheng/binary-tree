package com.jszheng.manipulator;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.printer.BtPrinter;
import com.jszheng.printer.MyBtPrinter;
import com.jszheng.util.ScannerUtil;

import static com.jszheng.Env.osName;
import static com.jszheng.util.ScannerUtil.*;

public abstract class TreeManipulator extends Manipulator<TreeOperation> {

    protected BinaryTree bt;
    private Class dataType;
    private BtPrinter printer = new MyBtPrinter(1, 1, 1);

    protected TreeManipulator(Class dataType) {
        this.dataType = dataType;
        bt = createTree();
        comment();
    }

    @Override
    public boolean handleOperation(int operationId) {
        if (!super.handleOperation(operationId))
            return false;

        this.operations.get(operationId - 1).execute(bt);
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
    }

    protected void addInsertOp() {
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

            printBt();
        });
    }

    protected void addOtherOp() {
        addOperation("traverse", bt -> {
            TraversalManipulator manipulator = new TraversalManipulator(bt);
            int operationId = ScannerUtil.getInteger(manipulator.getPrompt());
            if (operationId == 0) return;
            manipulator.handleOperation(operationId);
            System.out.println();
        });

        addOperation("print", bt -> printer.printBt(bt));
    }

    protected void addSearchOp() {
        addOperation("search", bt -> {
            Object searchData = getInput("Search Data:");
            TreeNode node = bt.search(searchData);
            if (node != null)
                System.out.println("Found " + bt.getNodeString(node)
                        + " on Level" + node.getLevel());
            else {
                boolean isWindows = osName.contains("Windows");
                System.out.println("Not Found " + (isWindows ? ":(" : "😞"));
            }
        });
    }

    protected BinaryTree baseBt() {
        return new LinkedBinaryTree();
    }

    protected void comment() {
        // default do nothing
    }

    protected abstract BinaryTree createTree();

    protected Object getInput(String prompt) {
        if (dataType == Integer.class) {
            return getInteger(prompt);
        } else {
            return getString(prompt);
        }
    }

    protected void printBt() {
        printer.printBt(bt);
    }
}
