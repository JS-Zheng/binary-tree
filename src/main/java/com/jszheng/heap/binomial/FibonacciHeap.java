package com.jszheng.heap.binomial;

import com.jszheng.Env;

public class FibonacciHeap<E extends Comparable<? super E>> extends BinomialHeap<E> {

    public FibonacciHeap() {
    }

    public FibonacciHeap(boolean mergeWithSort) {
        super(mergeWithSort);
    }

    public void decreaseKey(BinomialTreeNode<E> node, E newKey) {
        if (newKey.compareTo(node.data) > 0)
            throw new RuntimeException("new key is greater than current key.");

        node.data = newKey;
        BinomialTreeNode<E> parent = node.parent;
        if (parent != null && node.compareTo(parent) < 0) {
            cut(node, parent);
            cascadingCut(parent); // 瀑布/階層式截斷
        }

        if (node.compareTo(root) < 0)
            root = node;
    }

    @Override
    protected void insertData(E value) {
        BinomialTreeNode<E> node = newNode();
        node.data = value;

        if (isEmpty())
            setRoot(node);
        else {
            concatRootList(node);
            E rootData = root.data;
            if (value.compareTo(rootData) < 0)
                root = node;
        }
    }

    private void cascadingCut(BinomialTreeNode<E> parent) {
        BinomialTreeNode<E> grandParent = parent.parent;
        if (grandParent == null) return;
        if (!parent.mark)
            parent.mark = true;
        else {
            if (Env.debug) {
                System.out.println("[cut] cascading cut: " + parent.getData() + " was marked.");
                System.out.println();
            }

            cut(parent, grandParent);
            cascadingCut(grandParent);
        }
    }

    // 移除 parent 的 子節點 -- child，將其新增至 root list
    private void cut(BinomialTreeNode<E> child, BinomialTreeNode<E> parent) {
        BinomialTreeNode<E> lSibling = child.getLeftSibling();
        BinomialTreeNode<E> rSibling = child.getRightSibling();
        if (lSibling != null && rSibling != null) {
            lSibling.rLink = rSibling;
            rSibling.lLink = lSibling;
        } else if (lSibling != null) {
            lSibling.rLink = child.rLink;
            lSibling.isRLinkCircular = true;
            lSibling.rLink.lLink = lSibling;
        } else if (rSibling != null) {
            parent.child = rSibling; // set new child
            rSibling.lLink = child.lLink;
            rSibling.isLLinkCircular = true;
            rSibling.lLink.rLink = rSibling;
        } else {
            parent.child = null;
        }

        parent.degree--;

        isolateNodeFromSibling(child);
        concatRootList(child);
    }
}