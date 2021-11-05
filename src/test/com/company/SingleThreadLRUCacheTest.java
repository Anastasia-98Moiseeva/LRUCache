package com.company;

import org.junit.Assert;
import org.junit.Test;

public class SingleThreadLRUCacheTest {

    @Test
    public void singleThreadTest() {
        int capacity = 3;
        LRUCache<Integer, Integer> lruCache = new SingleThreadLRUCache<Integer, Integer>(capacity);
        int[] data = new int[] {8, 9, 6, 3};

        for (int i = 0; i < data.length; i++) {
            lruCache.put(i, data[i]);
        }

        int[] expected = new int[] {9, 6, 3};
        for (int i = data.length - capacity; i < data.length; i++) {
            Assert.assertEquals(expected[i - 1], (int) lruCache.get(i));
        }
    }
}
