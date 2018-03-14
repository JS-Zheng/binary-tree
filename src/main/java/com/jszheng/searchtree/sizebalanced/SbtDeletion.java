package com.jszheng.searchtree.sizebalanced;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.BstSearch;

import java.util.ArrayList;
import java.util.List;

class SbtDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    private List<SbTreeNode<E>> nodeList = new ArrayList<>();

    @Override
    public boolean delete(BinaryTree<E> bt, E data) {
        nodeList.clear();
        return super.delete(bt, data);
    }

    @Override
    protected SizeBalancedTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (SizeBalancedTree<E>) bst;
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new SbtDeleteSearch();
    }

    // Degree 2 (just replace data)
    protected void deleteNodeByExtremaChild(BinarySearchTree<E> bst, BinTreeNode<E> targetNode, boolean replaceByLMax) {
        SbTreeNode<E> child = (SbTreeNode<E>) (replaceByLMax ? targetNode.getLeftChild() : targetNode.getRightChild());
        SbTreeNode<E> lastChild = null;

        while (child != null) {
            lastChild = child;
            child.size--;
            child = replaceByLMax ? child.getRightChild() : child.getLeftChild();
        }

        delete(bst, lastChild);
        assert lastChild != null;
        E extremaChildData = lastChild.getData();
        targetNode.setData(extremaChildData);

        if (Env.debug) {
            System.out.println("[delete] replace node data with " + extremaChildData);
        }
    }

    class SbtDeleteSearch extends BstSearch<E> {

        @Override
        protected boolean handleNode(BinTreeNode<E> node) {
            SbTreeNode<E> sbNode = (SbTreeNode<E>) node;
            sbNode.size--;
            nodeList.add(sbNode);
            return true;
        }

        @Override
        protected void onNodeNotFound(SearchResult<E> result) { // 此點不存在
            for (SbTreeNode<E> sbNode : nodeList)
                sbNode.size++;
        }
    }
}
