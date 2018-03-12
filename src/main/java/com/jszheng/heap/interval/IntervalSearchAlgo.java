package com.jszheng.heap.interval;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;
import com.jszheng.traversal.TraversalNodeHandler;

public class IntervalSearchAlgo<E extends Comparable<? super E>> implements SearchAlgo<E> {

    private IntervalHeap<E> heap;

    @Override
    public SearchResult<E> search(BinaryTree<E> bt, E data) {
        this.heap = (IntervalHeap<E>) bt;
        IntervalSearchHandler handler = new IntervalSearchHandler(data);
        heap.traverse("Level", handler);
        return handler.result;
    }

    private class IntervalSearchHandler implements TraversalNodeHandler<E> {

        SearchResult<E> result = new SearchResult<>();
        E target;

        IntervalSearchHandler(E target) {
            this.target = target;
        }

        @Override
        public boolean handle(BinTreeNode<E> node) {
            int count = result.getSearchCount();
            result.setSearchCount(++count);

            E lData = heap.lDataOf(node);
            E rData = heap.rDataOf(node);

            if (lData != null && lData.equals(target)) {
                result.setNode(node);
                return false;
            }

            if (rData != null && rData.equals(target)) {
                result.setNode(node);
                return false;
            }

            return true;
        }
    }
}
