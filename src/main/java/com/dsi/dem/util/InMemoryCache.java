package com.dsi.dem.util;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

import java.util.ArrayList;

/**
 * Created by sabbir on 1/6/17.
 */
public class InMemoryCache<K, T> {

    private long timeToLive;
    private LRUMap memoryCacheMap;

    protected class MemoryCacheObject {
        long lastAccessed = System.currentTimeMillis();
        public T value;

        MemoryCacheObject(T value) {
            this.value = value;
        }
    }

    public InMemoryCache(long memoryTimeToLive, final long memoryTimerInterval, int maxItems) {
        this.timeToLive = memoryTimeToLive * 1000;

        memoryCacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && memoryTimerInterval > 0) {

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(memoryTimerInterval * 1000);
                    } catch (InterruptedException ignored) {
                    }
                    cleanup();
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (memoryCacheMap) {
            memoryCacheMap.put(key, new MemoryCacheObject(value));
        }
    }

    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (memoryCacheMap) {
            MemoryCacheObject c = (MemoryCacheObject) memoryCacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    void remove(K key) {
        synchronized (memoryCacheMap) {
            memoryCacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (memoryCacheMap) {
            return memoryCacheMap.size();
        }
    }

    @SuppressWarnings("unchecked")
    void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey;

        synchronized (memoryCacheMap) {
            MapIterator itr = memoryCacheMap.mapIterator();

            deleteKey = new ArrayList<K>((memoryCacheMap.size() / 2) + 1);
            K key;
            MemoryCacheObject c;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (MemoryCacheObject) itr.getValue();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (memoryCacheMap) {
                memoryCacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}
