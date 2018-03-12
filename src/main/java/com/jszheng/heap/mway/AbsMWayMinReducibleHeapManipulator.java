package com.jszheng.heap.mway;

import com.jszheng.node.TreeNode;

import static com.jszheng.util.ScannerUtil.errorPrompt;
import static com.jszheng.util.ScannerUtil.getLine;

public abstract class AbsMWayMinReducibleHeapManipulator<T extends AbstractMWayHeap & MinReducibleHeap>
        extends AbstractMWayMinHeapManipulator<T> {

    public AbsMWayMinReducibleHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        addOperation("delete", tree -> {
            Object input = getInput("Delete Data:");
            tree.delete((Comparable) input);
            tree.print();
        });

        super.addDeleteOp();

        addOperation("decrease key", tree -> {
            String str = getLine("格式: 資料 新鍵 (以空白間隔)");
            str = str.trim();
            String[] data = str.split("\\s+");

            if (data[0].equals("") || data.length < 2) {
                System.out.println(errorPrompt);
                return;
            }

            try {
                Object searchValue;
                if (dataType == Integer.class) {
                    searchValue = Integer.valueOf(data[0]);
                } else
                    searchValue = data[0];
                int newKey = Integer.valueOf(data[1]);

                TreeNode node = tree.search((Comparable) searchValue);

                if (node != null) {
                    tree.decreaseKey(node, newKey);
                    tree.print();
                } else {
                    System.out.println("Not found this node.");
                }

            } catch (NumberFormatException e) {
                System.out.println(errorPrompt);
            }
        });
    }
}
