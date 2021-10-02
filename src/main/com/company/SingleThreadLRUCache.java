package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SingleThreadLRUCache<T, R> implements LRUCache<T, R> {
    private final int capacity;
    private final Map<T, R> elementsMap;
    private final List<T> elementKeysQueue;

    public SingleThreadLRUCache(int capacity) {
        this.capacity = capacity;
        this.elementsMap = new HashMap<>();
        this.elementKeysQueue = new LinkedList<>();
    }

    @Override
    public void put(T key, R value) {
        if (!elementsMap.containsKey(key) && elementsMap.size() == capacity) {
            elementsMap.remove(elementKeysQueue.remove(0));
        }
        elementKeysQueue.remove(key);
        elementKeysQueue.add(key);
        elementsMap.put(key, value);
    }

    @Override
    public R get(T key) {
        if (!elementsMap.containsKey(key)) {
            return null;
        }
        elementKeysQueue.remove(key);
        elementKeysQueue.add(key);
        return elementsMap.get(key);
    }

    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder();
        for (R value : elementsMap.values()) {
            strResult.append(value);
        }
        return strResult.toString();
    }
}
