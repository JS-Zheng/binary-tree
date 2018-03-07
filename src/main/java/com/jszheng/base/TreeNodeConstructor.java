package com.jszheng.base;

@FunctionalInterface
interface TreeNodeConstructor<E> {

    void setDataByArr(E[] data);
}
