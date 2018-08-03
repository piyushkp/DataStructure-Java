package code.ds;

/**
 * Created by ppatel2 on 11/22/2016.
 */
// Leaky Bucket as a meter implementation with lazy update of the bucket level.
public class LeakyBucket {

  //Level of the bucket at timestamp lastUpdate.
  private int currentBudget;
  //Maximum level/capacity of the bucket.
  private final int capacity;
  //Rate at which the bucket leaks.
  private final int rate;
  //DateTime when the level was last updated.
  private long lastUpdate;

  public LeakyBucket(final int capacity, final int rate) {
    this.currentBudget = capacity;
    this.capacity = capacity;
    this.rate = rate;
    this.lastUpdate = System.currentTimeMillis();
  }

  //number of tokens to be added to the bucket
  public final synchronized boolean consume(final int nbTokens) {
    if (nbTokens < 0) {
      throw new IllegalArgumentException(
          String.format("Cannot add negative number of tokens: %s", nbTokens));
    }
    int seconds = (int) Math.floor((System.currentTimeMillis() - this.lastUpdate) / 1000);
    currentBudget = Math.min(capacity, currentBudget + rate * seconds);
    this.lastUpdate = System.currentTimeMillis();
    if (currentBudget >= nbTokens) {
      currentBudget -= nbTokens;
      return true;
    }
    return false;
  }
}
