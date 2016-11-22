package code.ds;

/**
 * Created by ppatel2 on 11/22/2016.
 */
// Leaky Bucket as a meter implementation with lazy update of the bucket level.
public class LeakyBucket {
    //Level of the bucket at timestamp lastUpdate.
    private int level;
    //Maximum level/capacity of the bucket.
    private final int capacity;
    //Rate at which the bucket leaks.
    private final int rate;
    //DateTime when the level was last updated.
    private long lastUpdate;

    public LeakyBucket(final int capacity, final int rate) {
        this.level = 0;
        this.capacity = capacity;
        this.rate = rate;
        this.lastUpdate = System.currentTimeMillis();
    }
    //number of tokens to be added to the bucket
    public final synchronized boolean consume(final int nbTokens) {
        if (nbTokens < 0) {
            throw new IllegalArgumentException(String.format("Cannot add negative number of tokens: %s", nbTokens));
        }
        int level = getLevel();
        if (level + nbTokens > capacity) {
            return false;
        }
        this.level = this.level + nbTokens;
        return true;
    }
    //Calculate (lazy) the current level and return it.
    public final synchronized int getLevel() {
        int seconds = (int) Math.floor((System.currentTimeMillis() - this.lastUpdate) / 1000);
        this.lastUpdate = System.currentTimeMillis();
        this.level = Math.max(0, this.level - rate * seconds);
        return level;
    }
}
