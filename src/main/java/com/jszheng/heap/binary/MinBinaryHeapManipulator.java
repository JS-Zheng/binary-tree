package com.jszheng.heap.binary;

public class MinBinaryHeapManipulator extends AbsMinBinaryHeapManipulator<MinBinaryHeap<Comparable<? super Comparable>>> {

    public MinBinaryHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        super.addDeleteOp();
        addOperation("delete min", bt -> {
            bt.deleteMin();
            printTree();
        });
    }

    @Override
    protected void addSearchOp() {
        super.addSearchOp();
        addOperation("search min", bt -> {
            Object obj = bt.searchMin();
            System.out.println(obj);
        });
    }

    @Override
    protected MinBinaryHeap<Comparable<? super Comparable>> createTree() {
        return new MinBinaryHeap();
    }
}
