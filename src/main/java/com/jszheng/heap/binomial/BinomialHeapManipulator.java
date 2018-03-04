package com.jszheng.heap.binomial;

import com.jszheng.manipulator.TreeManipulator;

public class BinomialHeapManipulator extends TreeManipulator<BinomialHeap> {

    public BinomialHeapManipulator(Class dataType) {
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
        addOperation("search min", tree -> {
            Object obj = tree.searchMin();
            System.out.println(obj);
        });
    }

    @Override
    protected void comment() {
        System.out.println("[註]: Binomial Heap");
        System.out.println("二項式堆積『並非』二元樹\n");
    }

    @Override
    protected BinomialHeap createTree() {
        return new BinomialHeap();
    }

    @Override
    protected void printTree() {
        tree.print();
    }
}
