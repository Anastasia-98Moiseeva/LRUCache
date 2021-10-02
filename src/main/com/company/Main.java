package com.company;

import java.util.Random;

public class Main {

    static void useSingleThreadLRUCache() {
        LRUCache<Integer, Integer> cache = new SingleThreadLRUCache<>(3);
        int[] items = new int[]{8, 9, 6, 3};
        for (int i = 0; i < items.length; i++) {
            System.out.println("Put value " + items[i] + " with key " + i);
            cache.put(i, items[i]);
            System.out.println("Cache: " + cache);
        }
        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println("Get value with key " + i);
            System.out.println("Value " + cache.get(i));
            System.out.println("Cache: " + cache);
        }
    }

    static void useMultiThreadLRUCache() {
        LRUCache<Integer, Integer> cache = new MultiThreadLRUCache<>(5);
        int[] items = new int[]{8, 9, 6, 3, 4, 1, 2, 7};
        for (int i = 0; i < items.length; i++) {
            int finalI = i;
            new Thread(() -> {
                Random rand = new Random();
                System.out.println("Put value " + items[finalI] + " with key " + finalI);
                cache.put(finalI, items[finalI]);
                if (rand.nextBoolean()) {
                    System.out.println("Get value with key " + finalI);
                    cache.get(finalI);
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        System.out.println("############# Single thread #############");
        useSingleThreadLRUCache();
        System.out.println("\n############# Multi thread #############");
        useMultiThreadLRUCache();
    }
}
