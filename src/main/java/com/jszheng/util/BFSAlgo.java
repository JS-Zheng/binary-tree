package com.jszheng.util;

import java.util.LinkedList;
import java.util.Queue;

public interface BFSAlgo<Param, Item, Result> {

    default Queue<Item> createQueue() {
        return new LinkedList<>(); // To support null element.
    }

    default Result execute(Param param) {

        Queue<Item> queue = createQueue();

        // Prevent Init Error.
        if (!init(param))
            return null;

        Item item = firstItem(param);
        queue.offer(item);


        while (loopCondition(queue)) {

            Item queueItem = queue.poll();

            if (!onDataPolled(queue, queueItem))
                break;
        }

        return onFinish(param);
    }

    Item firstItem(Param param);

    boolean init(Param param);

    default boolean loopCondition(Queue<Item> queue) {
        return !queue.isEmpty();
    }

    /**
     * @param queue BFS queue
     * @param item  polled from queue
     * @return true if success, otherwise return false to stop the while-loop
     */
    boolean onDataPolled(Queue<Item> queue, Item item);

    default Result onFinish(Param param) {
        // do nothing by default.
        return null;
    }
}
