package com.jszheng.heap.mway;

import com.jszheng.manipulator.TreeManipulator;

import static com.jszheng.Env.osName;

public abstract class AbstractBinomialHeapManipulator<E extends BinomialHeap> extends TreeManipulator<E> {

    public AbstractBinomialHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        addOperation("delete min", tree -> {
            tree.deleteMin();
            printTree();
        });
    }

    @Override
    protected void addOtherOp() {
        addOperation("後序走訪", BinomialHeap::postOrder);
        addOperation("print", BinomialHeap::print);
    }

    @Override
    protected void addSearchOp() {
        addOperation("search", tree -> {
            Object searchData = getInput("Search Data:");

            BinomialTreeNode node = tree.search((Comparable) searchData);
            if (node != null)
                System.out.println("Found " + tree.getNodeString(node));

            else {
                boolean isWindows = osName.contains("Windows");
                System.out.println("Not Found " + (isWindows ? ":(" : "😞"));
            }
        });

        addOperation("search min", tree -> {
            Object obj = tree.searchMin();
            System.out.println(obj);
        });
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Binomial Heap");
        System.out.println("a. 二項式堆積『並非』二元樹");
        System.out.println("b. 此實作為『最小』二項式堆積");
        System.out.println("c. 此實作節點結構採 Circular Doubly Linked List");
        System.out.println("d. 根據不同合併策略，結果可能不唯一\n");
    }

    @Override
    protected void printTree() {
        tree.print();
    }
}
