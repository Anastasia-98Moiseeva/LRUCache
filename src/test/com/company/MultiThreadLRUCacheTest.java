package com.company;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadLRUCacheTest {

    @Test
    public void multipleThreadTest() {
        int capacity = 3;
        LRUCache<Integer, Integer> lruCache = new SingleThreadLRUCache<Integer, Integer>(capacity);
        int[] data = new int[]{8, 9, 6, 3, 4, 1, 2, 7};
        Set<Integer> expected = new HashSet<>();
        for (int j: data) {
            expected.add(j);
        }
        for (int i = 0; i < data.length; i++) {
            int finalI = i;
            new Thread(() -> {
                lruCache.put(finalI, data[finalI]);
            }).start();
        }
        for (int i = 0; i < data.length; i++) {
            Integer item = lruCache.get(i);
            Assert.assertTrue(item == null || expected.contains(item));
        }
    }
}
