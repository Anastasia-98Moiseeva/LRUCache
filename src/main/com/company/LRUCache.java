package com.company;

public interface LRUCache<T, R> {
    void put(T key, R value);

    R get(T key);
}
