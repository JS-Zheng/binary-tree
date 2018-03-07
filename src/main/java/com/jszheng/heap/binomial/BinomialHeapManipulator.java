package com.jszheng.heap.binomial;

public class BinomialHeapManipulator extends AbstractBinomialHeapManipulator<BinomialHeap> {

    public BinomialHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinomialHeap createTree() {
        return new BinomialHeap<>();
    }
}
