package com.company;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MultiThreadLRUCache<T, R> implements LRUCache<T, R> {
    private final int capacity;
    private final Map<T, R> elementsMap;
    private final Queue<T> elementKeysQueue;
    private final ReadWriteLock lock;

    public MultiThreadLRUCache(int capacity) {
        this.capacity = capacity;
        this.elementsMap = new ConcurrentHashMap<>();
        this.elementKeysQueue = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public void put(T key, R value) {
        lock.writeLock().lock();
        try {
            if (!elementsMap.containsKey(key) && elementsMap.size() == capacity) {
                elementsMap.remove(elementKeysQueue.poll());
            }
            elementKeysQueue.remove(key);
            elementKeysQueue.add(key);
            elementsMap.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public R get(T key) {
        lock.readLock().lock();
        try {
            if (!elementsMap.containsKey(key)) {
                return null;
            }
            elementKeysQueue.remove(key);
            elementKeysQueue.add(key);
            return elementsMap.get(key);
        } finally {
            lock.readLock().unlock();
        }
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
