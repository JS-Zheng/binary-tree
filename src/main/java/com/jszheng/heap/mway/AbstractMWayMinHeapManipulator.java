package com.jszheng.heap.mway;

import com.jszheng.heap.MinHeap;
import com.jszheng.manipulator.TreeManipulator;

import static com.jszheng.Env.osName;

public abstract class AbstractMWayMinHeapManipulator<T extends AbstractMWayHeap & MinHeap> extends TreeManipulator<T> {

    AbstractMWayMinHeapManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void addDeleteOp() {
        addOperation("delete min", tree -> {
            tree.deleteMin();
            printTree();
        });
    }

    @Override
    protected void addSearchOp() {
        addOperation("search", tree -> {
            Object searchData = getInput("Search Data:");

            AbstractMWayHeapNode node = tree.search((Comparable) searchData);
            if (node != null)
                System.out.println("Found " + tree.getNodeString(node));

            else {
                boolean isWindows = osName.contains("Windows");
                System.out.println("Not Found " + (isWindows ? ":(" : "😞"));
            }
        });

        addOperation("search min", tree -> {
            Object obj = tree.searchMin();
            System.out.println(obj);
        });
    }

    @Override
    protected void printTree() {
        tree.print();
    }
}
