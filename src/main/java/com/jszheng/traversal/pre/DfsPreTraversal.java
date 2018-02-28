package com.jszheng.traversal.pre;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DfsPreTraversal<E> extends AbstractIterativeTraversal<E> {

    @Override
    public List<TreeNode<E>> traverse(BinaryTree<E> bt) { // No support FullBt Mode, due to my lazy.
        TreeNode<E> currentNode = bt.getRoot(), prev = null, next;
        TreeNode<E> lChildTmp, rChildTmp, parentTmp;

        Set<TreeNode<E>> mSet = new LinkedHashSet<>();

        while (currentNode != null) {
            if ((parentTmp = currentNode.getParent()) == prev ||
                    (parentTmp != null && parentTmp.equals(prev))) { // arrived from parent.

                if ((lChildTmp = currentNode.getLeftChild()) != null) next = lChildTmp;
                else if ((rChildTmp = currentNode.getRightChild()) != null) next = rChildTmp;
                else next = parentTmp;

            } else if ((lChildTmp = currentNode.getLeftChild()) == prev ||
                    ((lChildTmp != null) && lChildTmp.equals(prev))) { // arrived from leftChild.

                if ((rChildTmp = currentNode.getRightChild()) != null) next = rChildTmp;
                else next = currentNode.getParent();

            } else { // arrived from rightChild.
                next = currentNode.getParent();
            }

            if (!mSet.contains(currentNode)) {
                mSet.add(currentNode);
                if (!getNodeHandler().handle(currentNode))
                    return new LinkedList<>(mSet);
            }

            prev = currentNode;
            currentNode = next;
        }

        getNodeHandler().afterTraversed(bt);
        return new LinkedList<>(mSet);
    }
}
