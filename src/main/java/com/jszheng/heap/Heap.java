package com.jszheng.heap;

import com.jszheng.base.Tree;
import com.jszheng.node.TreeNode;

/*
 * Introduction to Algorithms:
 *
 * The term “heap” was originally coined in the context of heapsort,
 * but it has since come to refer to “garbage-collected storage,”
 * such as the programming languages Java and Lisp provide.
 *
 * Our heap data structure is not garbage-collected storage,
 * and whenever we refer to heaps in this book,
 * we shall mean a data structure rather than an aspect of garbage collection.
 */
public interface Heap<E, Node extends TreeNode<E>> extends Tree<E, Node> {
}
