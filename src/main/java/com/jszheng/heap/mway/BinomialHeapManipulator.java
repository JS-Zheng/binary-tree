package com.jszheng.heap.mway;

public class BinomialHeapManipulator extends AbstractBinomialHeapManipulator<BinomialHeap> {

    public BinomialHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected BinomialHeap createTree() {
        return new BinomialHeap<>();
    }
}
