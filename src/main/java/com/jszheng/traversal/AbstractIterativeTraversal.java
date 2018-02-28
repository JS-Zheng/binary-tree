package com.jszheng.traversal;

public abstract class AbstractIterativeTraversal<E> implements TraversalAlgo<E> {

    private TraversalNodeHandler<E> handler;

    @Override
    public TraversalNodeHandler<E> getNodeHandler() {
        if (handler == null)
            handler = new DefaultNodeHandler<>();

        return handler;
    }

    @Override
    public void setNodeHandler(TraversalNodeHandler<E> handler) {
        this.handler = handler;
    }
}
