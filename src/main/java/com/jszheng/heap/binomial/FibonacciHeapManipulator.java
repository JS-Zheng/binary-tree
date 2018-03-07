package com.jszheng.heap.binomial;

import static com.jszheng.util.ScannerUtil.errorPrompt;
import static com.jszheng.util.ScannerUtil.getLine;

public class FibonacciHeapManipulator extends AbstractBinomialHeapManipulator<FibonacciHeap> {

    public FibonacciHeapManipulator(Class dataType) {
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

                BinomialTreeNode node = tree.search((Comparable) searchValue);

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

    @Override
    protected void comment() {
        System.out.println("[註]: Fibonacci Heap");
        System.out.println("a. 費氏堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』費氏堆積");
        System.out.println("c. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("d. 根據不同合併策略，結果可能不唯一");
        System.out.println("e. 移除極值 攤銷時間 (amortized time) -- O(log n)");
        System.out.println("f. 移除極值 合併策略採 CLRS 之作法 -- consolidate start from right sibling of target");
        System.out.println("g. 若某子節點曾失去過 child，以 (#) 符號標記\n");
    }

    @Override
    protected FibonacciHeap createTree() {
        return new FibonacciHeap(false);
    }
}
