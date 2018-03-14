package com.jszheng.searchtree.sizebalanced;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.searchtree.BstInsertion;
import com.jszheng.searchtree.BstSearch;

import java.util.ArrayList;
import java.util.List;

/*
 * O(Log n)
 */
class SbtInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    private List<SbTreeNode<E>> nodeList = new ArrayList<>();

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        nodeList.clear();
        return super.insert(bt, data);
    }

    @Override
    protected SizeBalancedTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (SizeBalancedTree<E>) bt;
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new SbtInsertionSearch();
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        SizeBalancedTree<E> sbt = getBt();
        BinTreeNode<E> parent = newNode.getParent();

        while (parent != null) {
            int compareResult = newNode.getData().compareTo(parent.getData());
            boolean isNewNodeInLeftSubTree = compareResult < 0;
            sbt.maintain((SbTreeNode<E>) parent, isNewNodeInLeftSubTree);
            parent = parent.getParent();
        }
    }

    class SbtInsertionSearch extends BstSearch<E> {

        @Override
        protected void fixAfterSearch(BinTreeNode<E> node) { // 此點已存在
            for (SbTreeNode<E> n : nodeList)
                n.size--;
        }

        @Override
        protected boolean handleNode(BinTreeNode<E> node) {
            SbTreeNode<E> sbNode = (SbTreeNode<E>) node;
            sbNode.size++;
            nodeList.add(sbNode);
            return true;
        }
    }
}
