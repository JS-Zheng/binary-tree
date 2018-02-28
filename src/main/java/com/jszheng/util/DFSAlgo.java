package com.jszheng.util;

import java.util.Stack;

public interface DFSAlgo<Param, Item, Result> {

    default Stack<Item> createStack() {
        return new Stack<>(); // To support null element.
    }

    default Result execute(Param param) {
        Stack<Item> stack = createStack();

        // Prevent Init Error.
        if (!init(param))
            return null;

        Item item = firstItem(param);
        stack.push(item);


        while (loopCondition(stack)) {

            Item queueItem = stack.pop();

            if (!onDataPopped(stack, queueItem))
                break;
        }

        return onFinish(param);
    }

    Item firstItem(Param param);

    boolean init(Param param);

    default boolean loopCondition(Stack<Item> stack) {
        return !stack.isEmpty();
    }

    boolean onDataPopped(Stack<Item> stack, Item item);

    default Result onFinish(Param param) {
        // do nothing by default.
        return null;
    }
}
