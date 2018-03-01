package com.jszheng.heap.leftisttree;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class LeftistTreeInsertion<E extends Comparable<? super E>> implements InsertionAlgo<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        if (bt.isEmpty())
            bt.setRoot(data);
        else {
            TreeNode<E> node = bt.getRoot().newNode();
            node.setData(data);
            ((LeftistTree<E>) bt).merge(node);
        }

        return true;
    }
}
