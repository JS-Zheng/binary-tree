//package com.jszheng.heap.fibonacci;
//
//import com.jszheng.heap.MeldableHeap;
//import com.jszheng.heap.MinHeap;
//import com.jszheng.heap.binomial.BinomialHeap;
//import com.jszheng.node.BinomialTreeNode;
//import com.jszheng.node.TreeNode;
//
//public class FibonacciHeap<E extends Comparable<? super E>> implements MinHeap<E>, MeldableHeap<BinomialHeap<E>, E> {
//
//    int number = 0;
//    private BinomialTreeNode<E> root; //  sibling 串列之最左節點
//
//    @Override
//    public E deleteMin() {
//        return null;
//    }
//
//    @Override
//    public E searchMin() {
//        return null;
//    }
//
//    @Override
//    public void insert(E... data) {
//
//    }
//
//    @Override
//    public TreeNode<E> getRoot() {
//        return null;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public void merge(BinomialHeap<E> node) {
//
//    }
//
//    private void insertNodeToRootList() {
//        // todo
//    }
//
//    private void insertValue(E value) {
//        BinomialTreeNode<E> node = new BinomialTreeNode<>();
//        node.data = value;
//        if (root == null)
//            root = node;
//        else {
//            insertNodeToRootList();
//            E rootData = root.data;
//            if (value.compareTo(rootData) < 0)
//                root = node;
//        }
//
//        number++;
//    }
//
//    private void union(FibonacciHeap<E> fh) {
//
//    }
//}
