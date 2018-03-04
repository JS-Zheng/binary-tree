package com.jszheng.manipulator;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.printer.BtPrinter;
import com.jszheng.printer.MyBtPrinter;
import com.jszheng.util.ScannerUtil;

import static com.jszheng.Env.osName;

public abstract class BinTreeManipulator extends TreeManipulator<BinaryTree> {

    private BtPrinter printer = new MyBtPrinter(1, 1, 1);

    protected BinTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addOtherOp() {
        addOperation("traverse", bt -> {
            TraversalManipulator manipulator = new TraversalManipulator(bt);
            int operationId = ScannerUtil.getInteger(manipulator.getPrompt());
            if (operationId == 0) return;
            manipulator.handleOperation(operationId);
            System.out.println();
        });

        addOperation("print", bt -> printer.print(bt));
    }

    @Override
    protected void addSearchOp() {
        addOperation("search", bt -> {
            Object searchData = getInput("Search Data:");
            BinTreeNode node = bt.search(searchData);
            if (node != null)
                System.out.println("Found " + bt.getNodeString(node)
                        + " on Level" + node.getLevel());
            else {
                boolean isWindows = osName.contains("Windows");
                System.out.println("Not Found " + (isWindows ? ":(" : "😞"));
            }
        });
    }

    @Override
    protected void printTree() {
        printer.print(tree);
    }

    protected BinaryTree baseBt() {
        return new LinkedBinaryTree();
    }
}
