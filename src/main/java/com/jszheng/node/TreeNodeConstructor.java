package com.jszheng.node;

@FunctionalInterface
interface TreeNodeConstructor<E> {

    void setDataByArr(E[] data);
}
