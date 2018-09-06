package code.ds;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ppatel2 on 6/26/2017.
 */
public class Custom_DS {

  /*Implement a data structure supporting the following operations:
  Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1. Key is guaranteed to be a non-empty string.
  Dec(Key) - If Key's value is 1, remove it from the data structure. Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing. Key is guaranteed to be a non-empty string.
  GetMaxKey() - Returns one of the keys with maximal value. If no element exists, return an empty string "".
  GetMinKey() - Returns one of the keys with minimal value. If no element exists, return an empty string "".
  Challenge: Perform all these in O(1) time complexity. */
  class AllOne {

    /**
     * Initialize your data structure here.
     */
    public AllOne() {

    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {

    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {

    }

    /**
     * Returns one of the keys with maximal value.
     */
    public String getMaxKey() {
      return "";
    }

    /**
     * Returns one of the keys with Minimal value.
     */
    public String getMinKey() {
      return "";
    }
  }

  /** Design a custom data-structure that can store numbers like a dynamically sized array and supports all of the following operations in O(1)
   get(int index)
   set(int idx, int value)
   setAll(int value) */

  class Combo{
    LocalDateTime timeStamp;
    Integer value;

    Combo(LocalDateTime time, Integer value){
      this.timeStamp = time;
      this.value = value;
    }
  }

  class MyDS {
    private Combo defaultValue = new Combo(LocalDateTime.now(), null);
    private HashMap<Integer, Combo> map;

    public MyDS() {
      map = new HashMap<>();
    }

    public void setAll(int val) {
      defaultValue.timeStamp = LocalDateTime.now();
      defaultValue.value = val;
    }

    public void set(int index, int val) {
      Combo c = new Combo(LocalDateTime.now(), val);
      map.put(index, c);
    }

    public Integer get(int index) {
      if(!map.containsKey(index))
        return null;
      if (map.get(index).timeStamp.isAfter(defaultValue.timeStamp))
        return map.get(index).value;
      else
        return defaultValue.value;
    }
  }
}
