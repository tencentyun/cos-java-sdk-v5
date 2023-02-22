package com.qcloud.cos.retry;

public class RetryTokenBucket {
    private volatile int availableCapacity;
    private final int maxCapacity;

    private final Object lock = new Object();

    public RetryTokenBucket(final int maxCapacity) {
        this.availableCapacity = maxCapacity;
        this.maxCapacity = maxCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public boolean apply(int applyedCapacity) {
        if (applyedCapacity < 0) {
            throw new IllegalArgumentException("capacity to apply cannot be negative");
        }

        if (availableCapacity < 0) {
            return true;
        }

        synchronized (lock) {
            if (availableCapacity - applyedCapacity >= 0) {
                availableCapacity = availableCapacity - applyedCapacity;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Releases a single unit of capacity back to the pool, making it available
     * to consumers.
     */
    public void release() {
        release(1);
    }

    public void release(int releaseCapacity) {
        if (releaseCapacity < 0) {
            throw new IllegalArgumentException("capacity to release cannot be negative");
        }

        if (availableCapacity >= 0 && availableCapacity != maxCapacity) {
            synchronized (lock) {
                availableCapacity = Math.min(availableCapacity + releaseCapacity, maxCapacity);
            }
        }
    }

}
