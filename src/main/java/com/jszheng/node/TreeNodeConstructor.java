package com.jszheng.node;

@FunctionalInterface
public interface TreeNodeConstructor<E> {

    void setDataByArr(E[] data);
}
