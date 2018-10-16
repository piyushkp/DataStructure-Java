package code.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.regex.*;

/**
 * Created by ppatel2 on 11/9/2016.
 */
public class test {


  static List<List<Integer>> data;

  public static void main(String[] args) {
    //List<String> out = iprange2cidr("192.168.1.8", "192.168.1.9");
    /*long s = ip2long("1.1.1.111");
    String st = long2ip(s);
    long e = s + 9;

    List<String> out1 = iprange2cidr(s, e);
    System.out.println(Arrays.toString(out1.toArray()));*/
    //System.out.print(print(6));

    /*FriendNetwork fn = new FriendNetwork();
    fn.addUser("piyush");
    fn.addUser("Hemal");
    fn.addUser("binka");
    fn.addUser("nayan");
    fn.addUser("khyati");
    fn.addUser("francia");
    fn.addUser("struti");

    fn.addFriendship("piyush", "francia");
    fn.addFriendship("hemal", "binka");
    fn.addFriendship("hemal", "nayan");
    fn.addFriendship("piyush", "khyati");
    fn.addFriendship("binka", "struti");
    fn.addFriendship("piyush", "hemal");

    fn.removeFriendship("piyush", "hemal");

    Set<String> frd = fn.getDirectFriends("piyush");

    Set<String> indirectfrds = fn.getIndirectFriends("piyush");

    System.out.println(fn.getUser("piyush").getFriendships().toString());
    */
    System.out.println(countUniqueWords("Java is great. Grails is also great"));
    printUniquedWords("Java is great. Grails is also great");

  }

  public static String print(int precision) {
    StringBuilder builder = new StringBuilder();
    for (int r = 0; r < 10; r++) {
      builder.append("| ");
      for (int c = 0; c < 10; c++) {
        String s = Double.toString(1233424234);
        if (s.length() < precision) {
          for (int i = 0; i < precision - s.length(); i++) {
            builder.append(' ');
          }
        }
        builder.append(s);
        builder.append("  ");
      }
      builder.append("|\n");
    }
    return builder.toString();
  }

  //http://stackoverflow.com/questions/5020317/in-java-given-an-ip-address-range-return-the-minimum-list-of-cidr-blocks-that
  public static List<String> iprange2cidr(String ipStart, String ipEnd) {
    long start = ip2long(ipStart);
    long end = ip2long(ipEnd);

    ArrayList<String> result = new ArrayList<String>();
    while (end >= start) {
      byte maxSize = 32;
      while (maxSize > 0) {
        long mask = iMask(maxSize - 1);
        long maskBase = start & mask;

        if (maskBase != start) {
          break;
        }

        maxSize--;
      }
      double x = Math.log(end - start + 1) / Math.log(2);
      byte maxDiff = (byte) (32 - Math.floor(x));
      if (maxSize < maxDiff) {
        maxSize = maxDiff;
      }
      String ip = long2ip(start);
      result.add(ip + "/" + maxSize);
      start += Math.pow(2, (32 - maxSize));
    }
    return result;
  }

  public static List<String> iprange2cidr(long start, long end) {
    //long start = ipStart;
    //long end = ipEnd;

    ArrayList<String> result = new ArrayList<>();
    while (end >= start) {
      byte max = 32;
      while (max > 0) {
        long mask = iMask(max - 1);
        long maskBase = start & mask;

        if (maskBase != start) {
          break;
        }
        max--;
      }
      double x = Math.log(end - start + 1) / Math.log(2);
      byte maxDiff = (byte) (32 - Math.floor(x));
      if (max < maxDiff) {
        max = maxDiff;
      }
      String ip = long2ip(start);
      result.add(ip + "/" + max);
      start += Math.pow(2, (32 - max));
    }
    return result;
  }

  private static long iMask(int s) {
    return Math.round(Math.pow(2, 32) - Math.pow(2, (32 - s)));
  }

  private static long ip2long(String ipstring) {
    String[] ipAddressInArray = ipstring.split("\\.");
    long num = 0;
    long ip = 0;
    for (int x = 3; x >= 0; x--) {
      ip = Long.parseLong(ipAddressInArray[3 - x]);
      num |= ip << (x << 3);
    }
    return num;
  }

  private static String long2ip(long longIP) {
    StringBuffer sbIP = new StringBuffer("");
    sbIP.append(String.valueOf(longIP >>> 24));
    sbIP.append(".");
    sbIP.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
    sbIP.append(".");
    sbIP.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
    sbIP.append(".");
    sbIP.append(String.valueOf(longIP & 0x000000FF));

    return sbIP.toString();
  }

  static int[] pourWater(int[] heights, int V, int K) {
    for (int i = 0; i < V; ++i) {
      int l = K, r = K, n = heights.length;
      while (l > 0 && heights[l] >= heights[l - 1]) {
        --l;
      }
      while (l < K && heights[l] == heights[l + 1]) {
        ++l;
      }
      while (r < n - 1 && heights[r] >= heights[r + 1]) {
        ++r;
      }
      while (r > K && heights[r] == heights[r - 1]) {
        --r;
      }
      if (heights[l] < heights[K]) {
        ++heights[l];
      } else {
        ++heights[r];
      }
    }
    return heights;
  }

  public static int countUniqueWords(String str){
    HashMap<String, Integer> map = new HashMap<>();
    String[] words = str.split(" ");
    int count = 0;
    for(String wrd:words){
     if(!map.containsKey(wrd)){
       map.put(wrd, 1);
     }
     else{
       map.put(wrd, map.get(wrd) + 1);
     }
    }
    for (Integer value : map.values()) {
      if(value == 1){
        count++;
      }
    }
    return count;
  }

  static void printUniquedWords(String str)
  {
    // Extracting words from string
    Pattern p = Pattern.compile("[a-zA-Z]+");
    Matcher m = p.matcher(str);

    // Map to store count of a word
    HashMap<String, Integer> hm = new HashMap<>();

    // if a word found
    while (m.find())
    {
      String word = m.group();

      // If this is first occurrence of word
      if(!hm.containsKey(word))
        hm.put(word, 1);
      else
        // increment counter of word
        hm.put(word, hm.get(word) + 1);

    }

    // Traverse map and print all words whose count
    // is  1
    Set<String> s = hm.keySet();
    Iterator<String> itr = s.iterator();

    while(itr.hasNext())
    {
      String w = itr.next();

      if (hm.get(w) == 1)
        System.out.println(w);
    }
  }
}




