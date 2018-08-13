package com.jszheng.heap.mway;

import com.jszheng.Env;

public class FibonacciHeap<E extends Comparable<? super E>> extends BinomialHeap<E>
        implements MinReducibleHeap<E, BinomialTreeNode<E>> {

    public FibonacciHeap() {
    }

    public FibonacciHeap(boolean mergeWithSort) {
        super(mergeWithSort);
    }

    @Override
    public void decreaseKey(BinomialTreeNode<E> node, E newKey) {
        if (node == null) return;
        if (Env.debug)
            System.out.println("[decrease key] make key of node: " + node.getData() + " to " + newKey);

        if (newKey.compareTo(node.data) > 0) {
            System.out.println("[decrease key] new key is greater than current key.\n");
            return;
        }

        node.data = newKey;
        BinomialTreeNode<E> parent = node.parent;

        if (parent != null && node.compareTo(parent) < 0) {
            cut(node, parent);
            cascadingCut(parent); // 瀑布/階層式截斷
        }

        if (node.compareTo(min) < 0)
            min = node;

        if (Env.debug) System.out.println();
    }

    @Override
    public E delete(E value) {
        BinomialTreeNode<E> node = search(value);
        if (node != null) return delete(node);
        else if (Env.debug) System.out.println("[delete] not found\n");
        return null;
    }

    public E delete(BinomialTreeNode<E> node) {
        E data = node.data;
        decreaseKeyToMin(node);
        deleteMin(); // it will find new min
        if (Env.debug) System.out.println();
        return data;
    }

    @Override
    public FibonacciHeap<E> newTree() {
        return new FibonacciHeap<>(mergeWithSort);
    }

    @Override
    protected void insertNode(BinomialTreeNode<E> node) {
        concatRootList(node);
        E rootData = min.data;
        if (node.data.compareTo(rootData) < 0)
            min = node;
    }

    // 移除 parent 的 子節點 -- child，並將其新增至 min list
    @Override
    void cut(BinomialTreeNode<E> child, BinomialTreeNode<E> parent) {
        super.cut(child, parent);
        concatRootList(child);
    }

    @Override
    void isolateNodeFromSibling(BinomialTreeNode<E> node) {
        super.isolateNodeFromSibling(node);
        node.childCut = false;
    }

    private void cascadingCut(BinomialTreeNode<E> parent) {
        BinomialTreeNode<E> grandParent = parent.parent;
        if (grandParent == null) return;
        if (!parent.childCut)
            parent.childCut = true;
        else {
            if (Env.debug)
                System.out.println("[cut] cascading cut: " + parent.data + " was marked.");

            cut(parent, grandParent);
            cascadingCut(grandParent);
        }
    }

    private void decreaseKeyToMin(BinomialTreeNode<E> node) {
        BinomialTreeNode<E> parent = node.parent;

        if (parent != null) {
            cut(node, parent);
            cascadingCut(parent); // 瀑布/階層式截斷
        }

        min = node;
    }
}