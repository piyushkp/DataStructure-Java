package code.ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by Piyush Patel.
 */
public class StringImp {

  public static void main(String[] args) {
    //System.out.print("String");
    //char set1[] = {'a', 'b', 'c'};
    //printAllKLength(set1,3);
    //System.out.print(ransomNote2("aaaba", "aaabbb"));
    //int num = decode1("https://www.google.com/search?q=chinese+to+english&ie=utf-8&oe=utf-8");
    System.out.println(wordPatternMatch("abab", "applepple"));
    //System.out.println(isPalindrome1("L*&EVe)))l1"));
    //String str = "{\"id\": \"0002\",\"type\": \"donut\",\"name\": \"Cake\",\"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
    //printJSON(str);

  }

  /* Compress a given string. Input: aaaaabbccc  Output: a5b2c3
   *  TIme = O(n) space O(n) */
  public static String compressString(String s) {
    if (s == null || s.length() < 3) {
      return s;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      sb.append(s.charAt(i));
      int count = 1;
      while (i + 1 < s.length() && s.charAt(i + 1) == s.charAt(i)) {
        i++;
        count++;
      }
      sb.append(count);
    }
    return sb.length() >= s.length() ? s : sb.toString();
  }

  // in-place Time = O(n) space O(1)
  public static String compressInPlace(char[] chars) {
    int j = 0;
    for (int i = 1, cnt = 1; i <= chars.length; i++) {
      if (i < chars.length && chars[i] == chars[i - 1]) {
        cnt++;
      } else {
        chars[j++] = chars[i - 1];
        if (cnt != 1) {
          for (char c : String.valueOf(cnt).toCharArray()) {
            chars[j++] = c;
          }
          cnt = 1;
        }
      }
    }
    return String.valueOf(chars, 0, j);
  }


  //input = aabcccccaaa output = ab5c3a
  public static String encode(String source) {
    StringBuffer dest = new StringBuffer();
    for (int i = 0; i < source.length(); i++) {
      int runLength = 1;
      while (i + 1 < source.length() && source.charAt(i) == source.charAt(i + 1)) {
        runLength++;
        i++;
      }
      if (runLength >= 3) {
        dest.append(runLength);
      }
      dest.append(source.charAt(i));
    }
    return dest.toString();
  }

  public static String decode(String source) {
    StringBuffer dest = new StringBuffer();
    Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      int number = Integer.parseInt(matcher.group());
      matcher.find();
      while (number-- != 0) {
        dest.append(matcher.group());
      }
    }
    return dest.toString();
  }

  //Find first non repeated character in a String in just one pass. more space but in one pass
  public static Character getFirstNotRepeatedChar(String input) {
    int[] flags = new int[256]; //all is initialized by 0
    for (int i = 0; i < input.length(); i++) { // O(n)
      flags[(int) input.charAt(i)]++;
    }
    for (int i = 0; i < flags.length; i++) { // O(256)
      if (flags[i] == 1) {
        return input.charAt(i);
      }
    }
    return null;
  }

  private static char firstNonRepeatingChar(String word) {
    Set<Character> repeating = new HashSet<Character>();
    List<Character> nonrepeating = new ArrayList<Character>();
    for (int i = 0; i < word.length(); i++) {
      char letter = word.charAt(i);
      if (repeating.contains(letter)) {
        continue;
      }
      if (nonrepeating.contains(letter)) {
        nonrepeating.remove(letter);
        repeating.add(letter);
      } else {
        nonrepeating.add(letter);
      }
    }
    return nonrepeating.get(0);
  }

  //Find first non repeating character in stream of characters
  // use DLL and store reference in map so you can delete in O(1)
  class streamNonRepeatingChar {

    LinkedList<Character> list = new LinkedList();
    HashMap<Character, Character> map = new HashMap<>();
    //char appear more than two times
    HashSet<Character> set = new HashSet<>();

    private void insert(char c) {
      if (!set.contains(c)) {
        if (map.containsKey(c)) {
          list.remove(map.get(c));
          map.remove(c);
          set.add(c);
        } else {
          Character cobj = new Character(c);
          list.addLast(cobj);
          map.put(c, cobj);
        }
      }
    }

    private Character getNonRepeating() {
      return list.getLast();
    }
  }

  //Return maximum occurring character in the input string
  //Most Frequent Character in a String
  public char findMostFrequent(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int count = 0;
    char res = ' ';
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      Character.toLowerCase(c);
      if (!Character.isLetterOrDigit(c)) {
        continue;
      }
      if (c == ' ') {
        continue;
      }
      map.put(c, map.getOrDefault(c, 0) + 1);
      if (map.get(c) > count) {
        count = map.get(c);
        res = c;
      }
    }
    return res;
  }

  //Given a list of chars, return the 2nd most frequently occurring char.
  static char getSecondMostChar(String s) {
    int[] count = new int[256];
    for (char _ch : s.toCharArray()) {
      count[_ch]++;
    }
    int largest = 0, second = 0;
    for (int i = 0; i < 256; i++) {
      if (count[i] > count[largest]) {
        second = largest;
        largest = i;
      } else if (count[i] > count[second] && count[i] != count[largest]) {
        second = i;
      }
    }
    return (char) second;
  }

  //Given an balanced expression, find if it contains duplicate parenthesis or not.
  //A set of parenthesis are duplicate if same subexpression is surrounded by multiple parenthesis
  private static boolean isRedudantExpresssion(String s) {
    Stack<Character> _stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (c == ')') {
        char top = _stack.pop();
        if (top == '(') {
          return true;
        } else {
          while (top != '(') {
            top = _stack.pop();
          }
        }
      } else {
        _stack.push(c);
      }
    }
    return false;
  }

  //Write an algorithm to determine if all of the delimiters in an expression are matched and closed
  //{(abc)22}[14(xyz)2] should pass
  static boolean isBalanced(String input) {
    Stack<Character> stack = new Stack<Character>();
    for (char c : input.toCharArray()) {
      if (isOpeningBracket(c)) {
        stack.push(c);
      } else if (isClosingBracket(c)) {
        if (stack.isEmpty() || !isMatchingBrackets(stack.pop(), c)) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

  private static boolean isOpeningBracket(char c) {
    return "({[".indexOf(c) > -1;
  }

  private static boolean isClosingBracket(char c) {
    return ")}]".indexOf(c) > -1;
  }

  private static boolean isMatchingBrackets(char opening, char closing) {
    switch (opening) {
      case '(':
        return closing == ')';
      case '{':
        return closing == '}';
      case '[':
        return closing == ']';
      default:
        return false;
    }
  }

  //Given two strings str1 and str2 and below operations that can performed on str1. Find minimum number of edits
  //(operations) required to convert ‘str1′ into ‘str2′. Edit Distance problem/Levenshtein distance
  //Input:   str1 = "geek", str2 = "gesek"     Output:  1
  //We can convert str1 into str2 by inserting a 's'.
  //Time Complexity: O(m x n)     Auxiliary Space: O(m x n)
  int editDistDP(String str1, String str2, int m, int n) {
    // Create a table to store results of subproblems
    int dp[][] = new int[m + 1][n + 1];
    if (n == 0) {
      return m;
    }
    if (m == 0) {
      return n;
    }
    for (int i = 0; i <= m; i++) {
      dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
      dp[0][j] = j;
    }
    // d[i][j] = edit distance between word1[0..(i-1)] and word2[0..(j-1)]
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        // If last characters are same, ignore last char and recur for remaining string
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        }
        //If last characters are not same, consider all three operations on last character of first string,
        //recursively compute minimum cost for all three operations and take minimum of three values.
        else {
          dp[i][j] = 1 + Math.min(Math.min(dp[i][j - 1],  // Insert
              dp[i - 1][j]),  // Remove
              dp[i - 1][j - 1]); // Replace
        }
      }
    }
    return dp[m][n];
  }

  //Given a list of word and a target word, output all the words for each the edit distance with the target no greater than k.
  //e.g. [abc, abd, abcd, adc], target "ac", k = 1, output = [abc, adc]
    /*Naive Solution: A naive solution would be, for each word in the list, calculate the edit distance with the target
    word. If it is equal or less than k, output to the result list. If we assume that the average length of the words is m,
    and the total number of words in the list is n, the total time complexity is O(n * m^2).
    A better solution with a trie:, Best solution is using BK tree, see below*/
  class TrieNode {

    TrieNode[] children;
    boolean isLeaf;

    public TrieNode() {
      children = new TrieNode[26];
    }
  }

  class Trie {

    TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    // Add a word into trie
    public void add(String s) {
      if (s == null || s.length() == 0) {
        return;
      }
      TrieNode p = root;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (p.children[c - 'a'] == null) {
          p.children[c - 'a'] = new TrieNode();
        }
        if (i == s.length() - 1) {
          p.children[c - 'a'].isLeaf = true;
        }
        p = p.children[c - 'a'];
      }
    }
  }

  public List<String> getKEditDistance(String[] words, String target, int k) {
    List<String> result = new ArrayList<String>();
    if (words == null || words.length == 0 || target == null || target.length() == 0 || k < 0) {
      return result;
    }
    Trie trie = new Trie();
    for (String word : words) {
      trie.add(word);
    }
    TrieNode root = trie.root;
    int[] prev = new int[target.length() + 1];
    for (int i = 0; i < prev.length; i++) {
      prev[i] = i;
    }
    getKEditDistanceHelper("", target, k, root, prev, result);
    return result;
  }

  private void getKEditDistanceHelper(String curr, String target, int k, TrieNode root,
      int[] prevDist, List<String> result) {
    if (root.isLeaf) {
      if (prevDist[target.length()] <= k) {
        result.add(curr);
      } else {
        return;
      }
    }
    for (int i = 0; i < 26; i++) {
      if (root.children[i] == null) {
        continue;
      }
      int[] currDist = new int[target.length() + 1];
      currDist[0] = curr.length() + 1;
      for (int j = 1; j < prevDist.length; j++) {
        if (target.charAt(j - 1) == (char) (i + 'a')) {
          currDist[j] = prevDist[j - 1];
        } else {
          currDist[j] = Math.min(Math.min(prevDist[j - 1], prevDist[j]),
              currDist[j - 1]) + 1;
        }
      }
      getKEditDistanceHelper(curr + (char) (i + 'a'), target, k,
          root.children[i], currDist, result);
    }
  }

  //Best solution for K edit Distance using BK tree
  //spell checker / Find all words from Dictionary that are K edit distance away from target word
  //https://nullwords.wordpress.com/2013/03/13/the-bk-tree-a-data-structure-for-spell-checking/
  public class BKTree {

    Node root;

    public void add(String word) {
      if (root != null) {
        root.add(word);
      } else {
        root = new Node(word);
      }
    }

    private class Node {

      String RootWord;
      Map<Integer, Node> children;

      public Node(String word) {
        this.RootWord = word;
        children = new TreeMap<>();
      }

      public void add(String word) {
        int distance = LevenshteinDistance(word, this.RootWord);
        Node child = children.get(distance);
        if (child != null) {
          child.add(word);
        } else {
          children.put(distance, new Node(word));
        }
      }
    }

    public List<String> search(String query, int maxDistance) {
      if (query == null) {
        throw new NullPointerException();
      }
      if (maxDistance < 0) {
        throw new IllegalArgumentException("maxDistance must be non-negative");
      }
      List<String> matches = new ArrayList<>();
      Queue<Node> queue = new ArrayDeque<>();
      queue.add(root);
      while (!queue.isEmpty()) {
        Node node = queue.remove();
        String element = node.RootWord;
        int distance = LevenshteinDistance(element, query);
        if (distance <= maxDistance) {
          matches.add(element);
        }
        for (int i = distance - maxDistance; i <= distance + maxDistance; i++) {
          if (i > 0) {
            Node childNode = node.children.get(i);
            if (childNode != null) {
              queue.add(childNode);
            }
          }
        }
      }
      return matches;
    }

    public int LevenshteinDistance(String first, String second) {
      if (first.length() == 0) {
        return second.length();
      }
      if (second.length() == 0) {
        return first.length();
      }
      int lenFirst = first.length();
      int lenSecond = second.length();
      int[][] d = new int[lenFirst + 1][lenSecond + 1];
      for (int i = 0; i <= lenFirst; i++) {
        d[i][0] = i;
      }
      for (int i = 0; i <= lenSecond; i++) {
        d[0][i] = i;
      }
      for (int i = 1; i <= lenFirst; i++) {
        for (int j = 1; j <= lenSecond; j++) {
          int match = (first.charAt(i - 1) == second.charAt(j - 1)) ? 0 : 1;
          d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + match);
        }
      }
      return d[lenFirst][lenSecond];
    }
  }

  //Given two string s1 and s2, find if s1 can be converted to s2 with exactly one edit.
  //Time = O(m + n) space =O(1)
  static boolean isEditDistanceOne(String s1, String s2) {
    // Find lengths of given strings
    int m = s1.length(), n = s2.length();
    // If difference between lengths is more than 1, then strings can't be at one distance
    if (Math.abs(m - n) > 1) {
      return false;
    }
    int count = 0; // Count of edits
    int i = 0, j = 0;
    while (i < m && j < n) {
      // If current characters don't match
      if (s1.charAt(i) != s2.charAt(j)) {
        if (count == 1) {
          return false;
        }
        // If length of one string is more, then only possible edit is to remove a character
        if (m > n) {
          i++;
        } else if (m < n) {
          j++;
        } else {//If lengths of both strings is same
          i++;
          j++;
        }
        // Increment count of edits
        count++;
      } else {// If current characters match
        i++;
        j++;
      }
    }
    // If last character is extra in any string
    if (i < m || j < n) {
      count++;
    }
    return count == 1;
  }

  //Given a dictionary as a hashtable and a word. Find the minimum # of deletions needed on the word in order to make it a valid word in the dictionary.
  //Better approach is to use Trie. http://stackoverflow.com/questions/4868969/implementing-a-simple-trie-for-efficient-levenshtein-distance-calculation-java
  static int numberofMinDeletion(String word, HashSet<String> dic) {
    int mindelete = word.length();
    for (String item : dic) {
      if (word == item) {
        return 0;
      } else if (item.length() >= word.length()) {
        continue;
      } else {
        mindelete = Math.min(mindelete, minDeletionToTransformWord(word, item));
      }
    }
    if (mindelete == word.length()) {
      return -1;
    }
    return mindelete;
  }

  static int minDeletionToTransformWord(String s1, String s2) {
    // Find lengths of given strings
    int m = s1.length(), n = s2.length();
    int count = 0; // Count of edits
    int i = 0, j = 0;
    while (i < m && j < n) {
      // If current characters don't match
      if (s1.charAt(i) != s2.charAt(j)) {
        // If length of one string is more, then only possible edit is to remove a character
        if (m > n) {
          i++;
        }
        count++;
      } else {// If current characters match
        i++;
        j++;
      }
    }
    // If last character is extra in word string
    while (i < m) {
      count++;
      i++;
    }
    if (j < n) {
      return m;
    }
    return count;
  }
  //Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a
  //valid dictionary word. Return all such possible sentences.
  //s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"], the solution is ["cats and dog", "cat sand dog"].
  //Input = "smokedhard" dict ={"smoke","smoked","hard"} output = "smoked hard"
  //Before we solve it for any string check if we have already solve it. We can use another HashMap to store the result
  //of already solved strings. When­ever any recursive call returns false, store that string in HashMap

  //word break max two words in O(n)
  public static String WordBreakMaxTwo(String input, Set<String> dict) {
    int len = input.length();
    for (int i = 1; i < len; i++) {
      String prefix = input.substring(0, i);
      if (dict.contains(prefix)) {
        String suffix = input.substring(i, len);
        if (dict.contains(suffix)) {
          return prefix + " " + suffix;
        }
      }
    }
    return null;
  }

  //General Solution, Time = O(n2) assuming substring is O(1)
  static Map<String, String> memoized = new HashMap<>();

  public static String wordBreakUsingDP(String input, Set<String> dict) {
    if (dict.contains(input)) {
      return input;
    }
    if (memoized.containsKey(input)) {
      return memoized.get(input);
    }
    int len = input.length();
    for (int i = 1; i < len; i++) {
      String prefix = input.substring(0, i);
      if (dict.contains(prefix)) {
        String suffix = input.substring(i, len);
        String segSuffix = wordBreakUsingDP(suffix, dict);
        if (segSuffix != null) {
          //memoized.put(input, prefix + " " + segSuffix);
          return prefix + " " + segSuffix;

        }
      }
      memoized.put(input, null);
    }
    return null;
  }

  //Given an input string and a dictionary of words, find out if the input string can be segmented into a
  //space-separated sequence of dictionary words.
  //Consider the following dictionary { i, like, sam, sung, samsung, mobile, ice,cream, icecream, man, go, mango}
  //Input:  ilike  Output: Yes The string can be segmented as "i like".
  public static boolean wordBreak(String s, Set<String> dict) {
    boolean[] f = new boolean[s.length() + 1];
    f[0] = true;
    for (int i = 1; i <= s.length(); i++) {
      for (int j = 0; j < i; j++) {
        if (f[j] && dict.contains(s.substring(j, i))) {
          f[i] = true;
          break;
        }
      }
    }
    return f[s.length()];
  }

  //wordbreak II: Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
  //add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.
  //Input: s = "catsanddog" wordDict = ["cat", "cats", "and", "sand", "dog"]
  //Output: ["cats and dog", "cat sand dog"]
  //O(len(wordDict) * len(s / minWordLenInDict))
  HashMap<String, List<String>> map = new HashMap<>();

  public List<String> wordBreakII(String s, Set<String> wordDict) {
    if (map.containsKey(s)) {
      return map.get(s);
    }

    LinkedList<String> res = new LinkedList<>();
    if (s.length() == 0) {
      res.add("");
      return res;
    }
    for (String word : wordDict) {
      if (s.startsWith(word)) {
        List<String> sublist = wordBreakII(s.substring(word.length()), wordDict);
        for (String sub : sublist) {
          res.add(word + (sub.isEmpty() ? "" : " ") + sub);
        }
      }
    }
    map.put(s, res);
    return res;
  }

  // second approach
  public List<String> wordBreakIII(String s, Set<String> wordDict) {
    if (map.containsKey(s)) {
      return map.get(s);
    }
    int len = s.length();
    List<String> ret = new ArrayList<>();
    if (wordDict.contains(s)) {
      ret.add(s);
    }
    for (int i = 1; i < len; i++) {
      String curr = s.substring(i);
      if (wordDict.contains(curr)) {
        List<String> strs = wordBreakIII(s.substring(0, i), wordDict);
        if (strs.size() != 0) {
          for (Iterator<String> it = strs.iterator(); it.hasNext(); ) {
            ret.add(it.next() + " " + curr);
          }
        }
      }
    }
    map.put(s, ret);
    return ret;
  }

  /*A utility function to check whether a word is present in dictionary or not.An array of strings is used for
     dictionary.Using array of strings for dictionary is definitely not a good idea. We have used for simplicity of
    the program*/
  Boolean dictionaryContains(String word) {
    String dictionary[] = {"mobile", "samsung", "sam", "sung", "man", "mango", "icecream", "and",
        "go", "i", "like", "ice", "cream"};
    for (int i = 0; i < dictionary.length; i++) {
      if (dictionary[i].compareTo(word) == 0) {
        return true;
      }
    }
    return false;
  }

  //Given two sequences, find the length of longest common subsequence present in both of them.
  // Time  O(mn)
  int lcs(char[] X, char[] Y, int m, int n) {
    int L[][] = new int[m + 1][n + 1];
    int i, j;
   /* Following steps build L[m+1][n+1] in bottom up fashion. Note
      that L[i][j] contains length of LCS of X[0..i-1] and Y[0..j-1] */
    for (i = 0; i <= m; i++) {
      for (j = 0; j <= n; j++) {
        if (i == 0 || j == 0) {
          L[i][j] = 0;
        } else if (X[i - 1] == Y[j - 1]) {
          L[i][j] = L[i - 1][j - 1] + 1;
        } else {
          L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
        }
      }
    }
    /* L[m][n] contains length of LCS for X[0..n-1] and Y[0..m-1] */
    return L[m][n];
  }

  //give you two strings S and T, find the shortest string in S which contains all the characters in T
  //Input string1: “this is a test string” string2: “tist” Output string: “t stri”
  //S = "ADOBECODEBANC" T = "ABC" out = BANC
  public static String minSubString(String s, String t) {
    int[] map = new int[128];
    for (char c : t.toCharArray()) {
      map[c]++;
    }
    int start = 0, end = 0, minStart = 0, minLen = Integer.MAX_VALUE, counter = t.length();
    while (end < s.length()) {
      final char c1 = s.charAt(end);
      if (map[c1] > 0) {
        counter--;
      }
      map[c1]--;
      end++;
      while (counter == 0) {
        if (minLen > end - start) {
          minLen = end - start;
          minStart = start;
        }
        final char c2 = s.charAt(start);
        map[c2]++;
        if (map[c2] > 0) {
          counter++;
        }
        start++;
      }
    }

    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
  }

  /* Given a string, find the length of the longest substring without repeating characters.
       For example, the longest substring without repeating letters for “abcabcbb” is “abc” */
  public int lengthOfLongestSubstring(String s) {
    int[] map = new int[128];
    int start = 0, end = 0, maxLen = 0, counter = 0;
    while (end < s.length()) {
      final char c1 = s.charAt(end);
      if (map[c1] > 0) {
        counter++;
      }
      map[c1]++;
      end++;

      while (counter > 0) {
        final char c2 = s.charAt(start);
        if (map[c2] > 1) {
          counter--;
        }
        map[c2]--;
        start++;
      }
      maxLen = Math.max(maxLen, end - start);
    }
    return maxLen;
  }

  //Given a string, find the length of the longest substring T that contains at most k distinct(unique) characters.
  //For example, Given s = “eceba” and k = 2, out = ece
  //https://stackoverflow.com/questions/17646052/solve-number-of-substrings-having-two-unique-characters-in-on
  public static int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] map = new int[256];
    int start = 0, end = 0, maxLen = Integer.MIN_VALUE, counter = 0;

    while (end < s.length()) {
      final char c1 = s.charAt(end);
      if (map[c1] == 0) {
        counter++;
      }
      map[c1]++;
      end++;

      while (counter > k) {
        final char c2 = s.charAt(start);
        if (map[c2] == 1) {
          counter--;
        }
        map[c2]--;
        start++;
      }

      maxLen = Math.max(maxLen, end - start);
    }
    return maxLen;
  }

  //Find all the repeating sub-string sequence of specified length in a large string sequence.
  //The sequences returned i.e. the output must be sorted alphabetically
  //Input String: "ABCACBABC" repeated sub-string length: 3 Output: ABC
  //Input String: "ABCABCA" repeated sub-string length: 2 Output: AB, BC, CA
  public static void lrs(String s, int sequenceLength) {
    // form the N suffixes
    int N = s.length();
    String[] suffixes = new String[N];
    for (int i = 0; i < N; i++) {
      suffixes[i] = s.substring(i, N);
    }
    // sort them
    Arrays.sort(suffixes);
    // find longest repeated substring by comparing adjacent sorted suffixes
    String lrs = "";
    for (int i = 0; i < N - 1; i++) {
      String x = lrsUtil(suffixes[i], suffixes[i + 1], sequenceLength);
      if (x.length() == sequenceLength) {
        System.out.println(x);
      }
    }
  }

  public static String lrsUtil(String s, String t, int sequenceLength) {
    int n = Math.min(s.length(), t.length());
    if (n >= sequenceLength) {
      if (s.substring(0, sequenceLength) == t.substring(0, sequenceLength)) {
        return s.substring(0, sequenceLength);
      }
    }
    return "";
  }

  //Run of length: count the number of individual occurrences of repeated letters
  //i.e aa.aa = 1 , Bookkeepers are cool = 4 , WoooooW = 1
  public static int count_runs(String target) {
    char prev = target.charAt(0);
    int rpt = 0;
    for (int i = 1; i < target.length(); i++) {
      char curr = target.charAt(i);
      if (curr == prev) {
        rpt++;
        while (i < target.length() - 1 && (target.charAt(i + 1) == curr
            || target.charAt(i + 1) == ' ' || target.charAt(i + 1) == '.')) {
          i++;
        }
      } else {
        prev = curr;
      }
    }
    return rpt;
  }

  //For a given source string and a target string, you should output the "first" index(from 0) of target string in source string.
  //If target is not exist in source, just return -1.
  // can be improve using KMP algorithm
  public static int strStr(String source, String target) {
    if (source == null || target == null) {
      return -1;
    }
    //Two Pointer check for target
    int i, j;
    for (i = 0; i < source.length() - target.length() + 1; i++) {
      for (j = 0; j < target.length(); j++) {
        if (source.charAt(i + j) != target.charAt(j)) {
          break;
        }
      }
      if (j == target.length()) {
        return i;
      }
    }
    return -1;
  }

  //number of occurrences of substring in string
  private static int findSubOccur(String str, String findStr) {
    int lastIndex = 0;
    int count = 0;
    while (lastIndex != -1) {
      lastIndex = str.indexOf(findStr, lastIndex);
      if (lastIndex != -1) {
        count++;
        lastIndex += findStr.length();
      }
    }
    System.out.println(count);
    return count;
  }

  //Remove duplicate characters in a given string keeping only the first occurrences.
  private String removeDuplicate(String s) {
    if (s == null) {
      return null;
    }
    HashSet<Character> _set = new HashSet<Character>();
    StringBuffer result = new StringBuffer();
    char[] _ch = s.toCharArray();
    for (int i = 0; i < _ch.length; i++) {
      if (!_set.contains(_ch[i])) {
        _set.add(_ch[i]);
        result.append(_ch[i]);
      }
    }
    return result.toString();
  }

  //Remove duplicate with O(n) and no space. It can also be used to find whether all chars are unique or not
  public static void RemoveDuplicates(char[] str) {
    int check = 0;
    for (int i = 0; i < str.length; i++) {
      int val = str[i] - 'a';
      if ((check & (1 << val)) > 0) {
        str[i] = '\0';
        continue;
      }
      check = check | (1 << val);
    }
    for (int j = 0; j < str.length; j++) {
      if (str[j] == '\0') {
        continue;
      } else {
        System.out.print(str[j]);
      }
    }
  }

  //Permutations of the string
  public static void permute(String str) {
    int length = str.length();
    char[] in = str.toCharArray();
    doPermute(in, length, 0);
  }

  static void doPermute(char[] in, int length, int level) {
    if (level == length) {
      System.out.println(Arrays.toString(in));
      return;
    }
    for (int i = level; i < length; ++i) {
      swap(in, i, level);
      doPermute(in, length, level + 1);
      swap(in, i, level);
    }
  }

  /*A "derangement" of a sequence is a permutation where no element appears in its original position.
     For example ECABD is a derangement of ABCDE, given a string, may contain duplicate char, please find all the derangement*/
  public static List<char[]> getDerangement(char[] in) {
    HashMap<Integer, Character> map = new HashMap<>();
    List<char[]> result = new ArrayList<>();
    char[] ori = new char[in.length];
    for (int i = 0; i < in.length; i++) {
      map.put(i, in[i]);
      ori[i] = in[i];
    }
    return getDerangementUtil(in, ori, map, 0, result);
  }

  public static List<char[]> getDerangementUtil(char[] in, char[] ori,
      HashMap<Integer, Character> map, int level, List<char[]> result) {
    if (level == in.length) {
      // when duplicate chars in input, result would contains duplicates so use Set to avoid duplicates
      result.add(in);
      System.out.println(Arrays.toString(in));
      return result;
    }
    for (int i = level; i < in.length; ++i) {
      if (map.get(i) != in[level]) {
        if (i != level && (in[i] == ori[level] || in[level] == ori[i])) {
          continue; // for duplicates
        }
        swap(in, i, level);
        getDerangementUtil(in, ori, map, level + 1, result);
        swap(in, i, level);
      }
    }
    return result;
  }

  private static void swap(char[] chars, int i, int j) {
    char temp = chars[i];
    chars[i] = chars[j];
    chars[j] = temp;
  }

  private static void swap(String[] chars, int i, int j) {
    String temp = chars[i];
    chars[i] = chars[j];
    chars[j] = temp;
  }

  //combination of the string
  static void combine(String str) {
    int length = str.length();
    char[] instr = str.toCharArray();
    StringBuilder outstr = new StringBuilder();
    doCombine(instr, outstr, length, 0, 0);
  }

  static void doCombine(char[] instr, StringBuilder outstr, int length, int level, int start) {
    for (int i = start; i < length; i++) {
      outstr.append(instr[i]);
      System.out.println(outstr);
      if (i < length - 1) {
        doCombine(instr, outstr, length, level + 1, i + 1);
      }
      outstr.setLength(outstr.length() - 1);
    }
  }

  //Print all possible strings of length k that can be formed from a set of 'a','b','c', where there can only be a
  //maximum of 1 'b's and can only have up to two consecutive 'c's.
  //Time  = O(n^k)
  public static void printAllKLength(char set[], int k) {
    int n = set.length;
    System.out.print(printAllKLengthRec(set, "", n, k));
  }

  static int count = 0;

  public static int printAllKLengthRec(char set[], String prefix, int n, int k) {
    // Base case: k is 0, print prefix
    if (k == 0) {
      count++;
      System.out.println(prefix);
      return count;
    }
    // One by one add all characters from set and recursively call for k equals to k-1
    for (int i = 0; i < n; ++i) {
      // Next character of input added
      if (!(prefix.contains("b") && set[i] == 'b') &&
          !((prefix.substring(Math.max(prefix.length() - 2, 0)).equals("cc")) && set[i] == 'c')) {
        String newPrefix = prefix + set[i];
        // k is decreased, because we have added a new character
        printAllKLengthRec(set, newPrefix, n, k - 1);
      }
    }
    return count;
  }

  //Given two (dictionary) words as Strings, determine if they are isomorphic. given "foo", "app"; returns true
  //given "turtle", "tletur"; returns true
  //Ordered only i.e. ofo won't map to app encoding would be 010 and 011
  public static boolean isIsomorphic(String s1, String s2) {
    if (s1.length() != s2.length()) {
      return false;
    } else if (s1.length() == 1) {
      return true;
    } else {
      Map<Character, Integer> map1 = new HashMap<Character, Integer>();
      StringBuffer encodingString1 = new StringBuffer();
      Map<Character, Integer> map2 = new HashMap<Character, Integer>();
      StringBuffer encodingString2 = new StringBuffer();
      for (int i = 0; i < s1.length(); i++) {
        if (!map1.containsKey(s1.charAt(i))) {
          map1.put(s1.charAt(i), i);
        }
        encodingString1.append(map1.get(s1.charAt(i)));
        if (!map2.containsKey(s2.charAt(i))) {
          map2.put(s2.charAt(i), i);
        }
        encodingString2.append(map2.get(s2.charAt(i)));
      }
      return encodingString1.toString().equals(encodingString2.toString());
    }
  }

  /* Input pair can be considered in any order. For e.g. "A B C D A" - Min distance between A and D is 1. With order
   * preserved it would have been 3.*/
  HashMap<String, List<Integer>> _map = new HashMap<String, List<Integer>>();

  public void WordDistanceFinder(List<String> words) {
    for (int i = 0; i < words.size(); i++) {
      if (!_map.containsKey(words.get(i))) {
        _map.put(words.get(i), new LinkedList<>());
      }
      _map.get(words.get(i)).add(i);
    }
  }

  public int distance(String wordOne, String wordTwo) {
    if (!_map.containsKey(wordOne) || !_map.containsKey(wordTwo)) {
      return -1;
    }
    if (wordOne.equals(wordTwo)) {
      return 0;
    }
    int _minDistance = Integer.MAX_VALUE;
    for (int i : _map.get(wordOne)) {
      for (int j : _map.get(wordTwo)) {
        _minDistance = Math.min(_minDistance, Math.abs(i - j));
      }
    }
    return _minDistance;
  }

  //Without any space complexity. Time = O(N) Space = O(1)
  private static int minDistanceFinder(String[] strings, String targetString,
      String targetString2) {
    int index1 = -1;
    int index2 = -1;
    int minDistance = Integer.MAX_VALUE;
    int tempDistance = 0;
    for (int x = 0; x < strings.length; x++) {
      if (strings[x].equals(targetString)) {
        index1 = x;
      }
      if (strings[x].equals(targetString2)) {
        index2 = x;
      }
      if (index1 != -1 && index2 != -1) { // both words have to be found
        tempDistance = (int) Math.abs(index2 - index1);
        if (tempDistance < minDistance) {
          minDistance = tempDistance;
        }
      }
    }
    return minDistance;
  }

  // Check whether two strings are anagram  or not
  // For example, “abcd” and “dabc” are anagram of each other.
  // Check if two strings are permutaion to each other or not
  private static boolean areAnagram(String s1, String s2) {
    if (s1.length() != s2.length()) {
      return false;
    }
    int[] counter = new int[256];
    for (int i = 0; i < s1.length(); i++) {
      counter[s1.charAt(i)]++;
      counter[s2.charAt(i)]--;
    }
    for (int i = 0; i < 256; i++) {
      if (counter[i] != 0) {
        return false;
      }
    }
    return true;
  }

  //Check if two strings are k-anagrams or not.
  private static boolean areKAnagram(String s1, String s2, int K) {
    if (s1.length() != s2.length()) {
      return false;
    }
    int[] counter = new int[256];
    for (int i = 0; i < s1.length(); i++) {
      counter[s1.charAt(i)]++;
    }
    int count = 0;
    for (int i = 0; i < s2.length(); i++) {
      if (counter[s2.charAt(i)] > 0) {
        counter[s2.charAt(i)]--;
      } else {
        count++;
      }
    }
    if (count > K) {
      return false;
    }
    return true;
  }

  //Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function search(char pat[], char txt[]) that prints
  //all occurrences of pat[] and its permutations (or anagrams) in txt[].
  //Find if the given string contains an anagram of another smaller string.
  // Find all permutation of string s in string b/
  public static List<Integer> anagramsMatch(String s, String p) {
    List<Integer> list = new ArrayList<>();
    int[] count = new int[256];
    int[] tc = new int[256];
    for (int i = 0; i < p.length(); i++) {
      count[p.charAt(i)]++;
      tc[s.charAt(i)]++;
    }
    if (matchCount(count, tc)) {
      list.add(0);
    }
    for (int i = p.length(); i < s.length(); i++) {
      tc[s.charAt(i - p.length())]--;
      tc[s.charAt(i)]++;
      if (matchCount(count, tc)) {
        list.add(i - p.length() + 1);
      }
    }
    for (int num : list) {
      System.out.print("Found at Index " + num);
    }
    return list;
  }

  private static boolean matchCount(int[] a, int[] b) {
    for (int i = 0; i < a.length; i++) {
      if (a[i] != b[i]) {
        return false;
      }
    }
    return true;
  }

  // Given an array of words, print all words with reverse together. For example, if the given array is
  // {“cat”, “dog”, “tac”, “god”, “act”}, then output "cat" and "tac"
  static List<List<String>> printUtil(String[] input) {
    HashMap<String, Integer> map = new HashMap<>();
    List<List<String>> output = new ArrayList<>();
    for (int i = 0; i < input.length; i++) {
      String key = ReverseString(input[i]);
      if (!map.containsKey(key)) {
        map.put(key, i);
      }
    }
    for (int i = 0; i < input.length; i++) {
      List<String> out = new ArrayList<>();
      if (map.containsKey(input[i]) && map.get(input[i]) != i) {
        out.add(input[i]);
        out.add(input[map.get(input[i])]);
      }
      output.add(out);
    }
    return output;
  }

  //Given an array of words, print all anagrams together. For example, if the given array is
  // {“cat”, “dog”, “tac”, “god”, “act”}, then output may be “cat tac act dog god”
  static void printAnagramsUtil(String[] input) {
    HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
    for (int i = 0; i < input.length; i++) {
      char[] content = input[i].toCharArray();
      Arrays.sort(content);
      String key = new String(content);
      if (!map.containsKey(key)) {
        map.put(key, new LinkedList<>());
      }
      map.get(key).add(i);
    }
    for (String cur : map.keySet()) {
      if (map.get(cur).size() > 1) {
        for (int i = 0; i < map.get(cur).size(); i++) {
          System.out.print(input[map.get(cur).get(i)] + " ");
        }
        System.out.println();
      }
    }
  }
  //Find total number of unique anagrams in a dictionary?


  //reverse the string
  private static String ReverseString(String str) {
    char[] inputstream = str.toCharArray();
    int length = str.length() - 1;
    for (int i = 0; i < length; i++, length--) {
      inputstream[i] ^= inputstream[length];
      inputstream[length] ^= inputstream[i];
      inputstream[i] ^= inputstream[length];
    }
    return new String(inputstream);
  }

  //Reverse words in a string
  // in place reversal of words.Time = O(n) space O(1)
  public static void reverseWords(char[] s) {
    reverse(s, 0, s.length - 1); // reverse the whole sentense
    int start = 0;
    for (int i = 0; i < s.length; i++) // reverse each word
    {
      if (s[i] == ' ') {
        reverse(s, start, i - 1);
        start = i + 1;
      }
    }
    reverse(s, start, s.length - 1); // reverse the last word
  }

  static void reverse(char[] s, int start, int end) {
    while (start < end) {
      char tmp = s[start];
      s[start++] = s[end];
      s[end--] = tmp;
    }
  }

  //Reverse words in a string
  public static String reverseWords(String sentence) {
    StringBuilder sb = new StringBuilder(sentence.length() + 1);
    String[] words = sentence.split(" ");
    for (int i = words.length - 1; i >= 0; i--) {
      sb.append(words[i]).append(' ');
    }
    sb.setLength(sb.length() - 1);  // Strip trailing space
    return sb.toString();
  }

  //find count of common characters presented in an array of strings or array of character arrays
  private void CountOfCommonCharacters(String s, String S1, String S2) {
    char[] _chars = s.toCharArray();
    Set<String> _set = new HashSet<String>();
    for (int i = 0; i < _chars.length; i++) {
      char c = _chars[i];
      if (S1.indexOf(c) != -1 && S2.indexOf(c) != -1) {
        _set.add(String.valueOf(c));
      }
    }
    for (String string : _set) {
      System.out.println(string);
    }
  }

  //word wrap
  void wrapthis(String para, int w) {
    String c[] = para.split(" ");
    int n = c.length;
    int cost[] = new int[n];
    int[][] espace = new int[n][n];
    int line[] = new int[n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j <= i; j++) {
        int t = 0;
        if (i == j) {
          t = c[i].length();
        } else {
          if (i > j) {
            for (int k = j; k <= i; k++) {
              t = t + c[k].length();
            }
          } else {
            for (int k = i; k <= j; k++) {
              t = t + c[k].length();
            }
          }
        }
        //System.out.print(" t:"+t);
        if (t > w) {
          espace[i][j] = -1;
          espace[j][i] = -1;
        } else {
          espace[i][j] = w - t - (i - j);
          espace[j][i] = w - t - (i - j);
        }
        System.out.print(" " + espace[i][j]);
      }
      System.out.println();
    }
    int es = w - c[0].length();
    cost[0] = es * es * es;
    for (int j = 1; j < n; j++) {
      int t;
      int tl = c[j].length();
      cost[j] = Integer.MAX_VALUE;
      for (int i = 1; i <= j; i++) {
        if (espace[i][j] != -1) {
          t = cost[i - 1] + espace[i][j] * espace[i][j] * espace[i][j];
          //System.out.println("t:"+t);
          if (t < cost[j]) {
            cost[j] = t;
            line[j] = i;
          }
        }
      }
      System.out.println("optimal line" + line[j]);
    }
    System.out.println("optimal cost" + cost[n - 1]);
    int pre;
    System.out.print(" " + c[0]);
    pre = 0;
    for (int i = 1; i < n; i++) {
      if (line[i] == pre) {
        System.out.print(" " + c[i]);
      } else {
        System.out.println();
        System.out.print(" " + c[i]);
        pre = line[i];
      }
    }
  }

  //Return the smallest character that is strictly larger than the search character,
  //If no such character exists, return the smallest character in the array
  //given sorted list of letters, sorted in ascending order
  //e.g  ['c', 'f', 'j', 'p', 'v'], 'k' => 'p' and ['c', 'f', 'j', 'p', 'v'], 'z' => 'c'
  public char smallest_character(String str, char c) {
    int l = 0, r = str.length() - 1;
    char ret = str.charAt(0);
    while (l <= r) {
      int m = l + (r - l) / 2;
      if (str.charAt(m) > c) {
        ret = str.charAt(m);
        r = m - 1;
      } else {
        l = m + 1;
      }
    }
    return ret;
  }

  //Given a sorted array of strings which is interspersed with empty strings, write a method to find the location of
  //a given string.
  //input = stringList = {"apple", "", "", "banana", "", "", "", "carrot", "duck", "", "", "eel", "", "flower"}
  public static int searchI(String[] strings, String str, int first, int last) {
    while (first <= last) {
      /* Move mid to the middle */
      int mid = (last + first) / 2;
      /* If mid is empty, find closest non-empty string */
      if (strings[mid].isEmpty()) {
        int left = mid - 1;
        int right = mid + 1;
        while (true) {
          if (left < first && right > last) {
            return -1;
          } else if (right <= last && !strings[right].isEmpty()) {
            mid = right;
            break;
          } else if (left >= first && !strings[left].isEmpty()) {
            mid = left;
            break;
          }
          right++;
          left--;
        }
      }
      int res = strings[mid].compareTo(str);
      if (res == 0) { // Found it!
        return mid;
      } else if (res < 0) { // Search right
        first = mid + 1;
      } else { // Search left
        last = mid - 1;
      }
    }
    return -1;
  }

  //Given a string S, find the longest palindromic substring in S. O(NlogN) time and O(N) space
  //Better algorithm in linear time Manacher’s Algorithm
  // Input = “abcbabcbabcba” output = “abcbabcba”
  public static String LongestPalindromeImprove(String s) {
    s += "^" + ReverseString(s);
    int N = s.length();
    String[] suffixes = new String[N];
    for (int i = 0; i < N; i++) {
      suffixes[i] = s.substring(i, N);
    }
    // sort them
    Arrays.sort(suffixes);
    // find longest common prefix
    int max = Integer.MIN_VALUE;
    java.util.HashMap<String, Integer> map = new HashMap<String, Integer>();
    for (int i = 0; i < N - 1; i++) {
      int x = lcp(suffixes[i], suffixes[i + 1]);
      String key = suffixes[i].substring(0, x);
      if (!map.containsKey(key)) {
        map.put(key, x);
      }
    }
    Map.Entry<String, Integer> maxEntry = null;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    return maxEntry.getKey();
  }

  // longest common prefix of s and t
  private static int lcp(String s, String t) {
    int N = Math.min(s.length(), t.length());
    for (int i = 0; i < N; i++) {
      if (s.charAt(i) != t.charAt(i)) {
        return i;
      }
    }
    return N;
  }

  /*Airbnb: Given a list of strings, find all the palindromic pairs of the string and output the concatenated palindrome.
    e.g. [abc, cba], output is abccba, cbaabc.            e.g. [aabc, cb], output is cbaabc
    //Put all words in a Set. For each word, get all it’s prefix and suffix. Search for reversed(prefix) and reversed(suffix) in the Set.
    //If found, check if the rest of the string is a palindrome or not. Time complexity O(n^2 k) where k is avg length of word and n is number of words.
    better solution is O(n k^2) since words can be big list
    another solution using trie*/
  public static List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i = 0; i < words.length; ++i) {
      map.put(words[i], i);
    }
    for (int k = 0; k < words.length; ++k) {
      String word = words[k];
      int n = word.length();
      for (int i = 0; i < n + 1; ++i) {
        String prefix = new StringBuilder(word.substring(0, i)).reverse().toString();
        String suffix = new StringBuilder(word.substring(i, n)).reverse().toString();
        if (i != 0 && map.containsKey(suffix) && map.get(suffix) != k && isPalindrome(prefix)) {
          ans.add(Arrays.asList(map.get(suffix), k));
        }
        if (map.containsKey(prefix) && map.get(prefix) != k && isPalindrome(suffix)) {
          ans.add(Arrays.asList(k, map.get(prefix)));
        }
      }
    }
    return ans;
  }

  //palindromic pairs with trie and time = O(nk ^2)
  //https://leetcode.com/problems/palindrome-pairs/discuss/79195/O(n*k2)-java-solution-with-Trie-structure
  class TrieNode2 {

    TrieNode2[] next;
    int index;
    List<Integer> list;

    TrieNode2() {
      next = new TrieNode2[26];
      index = -1;
      list = new ArrayList<>();
    }
  }

  public List<List<Integer>> palindromePairsImproved(String[] words) {
    List<List<Integer>> res = new ArrayList<>();
    TrieNode2 root = new TrieNode2();
    for (int i = 0; i < words.length; i++) {
      addWord(root, words[i], i);
    }
    for (int i = 0; i < words.length; i++) {
      search(words, i, root, res);
    }
    return res;
  }

  private void addWord(TrieNode2 root, String word, int index) {
    for (int i = word.length() - 1; i >= 0; i--) {
      int j = word.charAt(i) - 'a';
      if (root.next[j] == null) {
        root.next[j] = new TrieNode2();
      }
      if (isPalindrome(word, 0, i)) {
        root.list.add(index);
      }
      root = root.next[j];
    }
    root.list.add(index);
    root.index = index;
  }

  private void search(String[] words, int i, TrieNode2 root, List<List<Integer>> res) {
    for (int j = 0; j < words[i].length(); j++) {
      if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
        res.add(Arrays.asList(i, root.index));
      }
      root = root.next[words[i].charAt(j) - 'a'];
      if (root == null) {
        return;
      }
    }
    for (int j : root.list) {
      if (i == j) {
        continue;
      }
      res.add(Arrays.asList(i, j));
    }
  }

  //Given a list of words, filter it such that it would only contain those words that have palindrome in an original list.
  public static List<String> filterList(List<String> input) {
    List<String> out = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < input.size(); ++i) {
      map.put(input.get(i), i);
    }
    for (int k = 0; k < input.size(); ++k) {
      String word = input.get(k);
      int n = word.length();
      for (int i = 0; i < n + 1; ++i) {
        String prefix = new StringBuilder(word.substring(0, i)).reverse().toString();
        String suffix = new StringBuilder(word.substring(i, n)).reverse().toString();
        if (i != 0 && map.containsKey(suffix) && map.get(suffix) != k && isPalindrome(prefix)) {
          out.add(suffix);
        }
        if (map.containsKey(prefix) && map.get(prefix) != k && isPalindrome(suffix)) {
          out.add(prefix);
        }
      }
    }
    return out;
  }

  //implement a function to find if a given string is a palindrome
  public static boolean isPalindrome(String word) {
    if (word.length() < 2) {
      return true;
    }
    for (int i = 0; i < word.length() / 2; ++i) {
      if (word.charAt(i) != word.charAt(word.length() - i - 1)) {
        return false;
      }
    }
    return true;
  }

  //implement a function to find if a given string is a palindrome, Must be case insensitive, and ignore special characters
  public static boolean isPalindrome1(String word) {
    word = word.toLowerCase();
    if (word.length() < 2) {
      return true;
    }
    int l = 0, r = word.length() - 1;
    while (l < r) {
      while (!Character.isLetterOrDigit(word.charAt(l))) {
        l++;
      }
      while (!Character.isLetterOrDigit(word.charAt(r))) {
        r--;
      }
      if (word.charAt(l) != word.charAt(r)) {
        return false;
      }
      l++;
      r--;
    }
    return true;
  }

  //Check if characters of a given string can be rearranged to form a palindrome
  //Permutation of string can form palindrome
  static boolean canFormPalindrome(String str, int totalCharsToCheck) {
    int[] map = new int[128];
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      map[str.charAt(i)]++;
      if (map[str.charAt(i)] % 2 == 0) {
        count--;
      } else {
        count++;
      }
    }
    return count <= totalCharsToCheck + 1;
  }

  public static boolean hasPalindrome(String s) {
    BitSet occurrences = new BitSet();
    for (int i = 0; i < s.length(); i++) {
      occurrences.flip(Character.getNumericValue(s.charAt(i)));
    }
    return occurrences.stream().count() <= 1;
  }

  // input string is abxa. it will be determined whether removing one character from the line can result in the string being a palindrome
  public static boolean isAlmostPalindrome(String s) {
    for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
      if (s.charAt(i) != s.charAt(j)) {
        return isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1);
      }
    }
    // Exact palindrome. Any palindrome is also an almost-palindrome, by deleting one of the middle characters.
    return true;
  }

  private static boolean isPalindrome(String s, int firstIndex, int lastIndex) {
    for (int i = firstIndex, j = lastIndex; i < j; i++, j--) {
      if (s.charAt(i) != s.charAt(j)) {
        return false;
      }
    }
    return true;
  }

  //Given a string return the longest palindrome that can be constructed by removing or shuffling characters.
  // If there are multiple correct answers you need to return only 1 palindrome.
  public static String LongestPalindromeRemoveShuffle(String s) {
    String output = "";
    String center = "";
    int[] counter = new int[26];
    for (int i = 0; i < s.length(); i++) {
      counter[(int) s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < counter.length; i++) {
      int times = counter[i] / 2;
      String repeated = new String(new char[times])
          .replace("\0", Character.toString((char) (i + 'a')));
      output += repeated;
      if (counter[i] % 2 != 0) {
        center = Character.toString((char) (i + 'a'));
      }
    }
    return output + center + new StringBuilder(output).reverse().toString();
  }
  //Given a number, find the next smallest palindrome larger than the number. For example if the number is 125, next smallest palindrome is 131
  //https://github.com/yxjiang/algorithms/blob/master/src/main/java/algorithm/recursive/NextPalindrome.java
  //http://www.ardendertat.com/2011/12/01/programming-interview-questions-19-find-next-palindrome-number/

  //Given an integer n, find the closest integer (not including itself), which is a palindrome.
  //The 'closest' is defined as absolute difference minimized between two integers.
  //Input: "123"  Output: "121"
  //https://leetcode.com/problems/find-the-closest-palindrome/solution/#

  //A Program to check if strings are rotations of each other or not
  //given s1 = ABCD and s2 = CDAB, return true
  boolean areRotations(String s1, String s2) {
    String temp = s1 + s1;
    if (temp.contains(s2)) {
      return true;
    }
    return false;
  }

  //Output top N positive integer in string comparison order.
  // For example, let's say N=1000, output should be 1, 10, 100, 1000, 101, 102, ... 109, 11, 110,
  static void printRec(String str, int n) {
    if (Integer.parseInt(str) > n) {
      return;
    }
    System.out.println(str);
    for (int i = 0; i < 10; i++) {
      printRec(str + i, n);
    }
  }

  //Find the longest sequence of prefix shared by all the words in a string.
  //"abcdef abcdxxx abcdabcdef abcyy" => "abc"
  public String longPrefix(String str) {
    String arr[] = str.split(" ");
    int len = arr[0].length();
    int p;
    for (int i = 1; i < arr.length; i++) {
      p = 0;
      while (p < len && p < arr[i].length()
          && arr[0].charAt(p) == arr[i].charAt(p)) {
        p++;
      }
      len = p;
    }
    return arr[0].substring(0, len);
  }

  /* Write a program to determine whether an input string str1 is a substring of another input string str2.*/
  //Given two strings, find if first string is a subsequence of second
  //Input: str1 = "AXY", str2 = "ADXCPY"  Output: True (str1 is a subsequence of str2)
  // Returns true if str1[] is a subsequence of str2[]. m is length of str1 and n is length of str2
  boolean isSubSequence(char str1[], char str2[], int m, int n) {
    int j = 0; // For index of str1 (or subsequence
    // Traverse str2 and str1, and compare current character
    // of str2 with first unmatched char of str1, if matched
    // then move ahead in str1
    for (int i = 0; i < n && j < m; i++) {
      if (str1[j] == str2[i]) {
        j++;
      }
    }
    // If all characters of str1 were found in str2
    return (j == m);
  }

  //Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function to search the index of subString
  //Naive Pattern Searching. Best case O(n) worst case  O(m*(n-m+1))
  //Better approach is KMP (Knuth Morris Pratt) Pattern Searching
  void search(String pat, String txt) {
    int M = pat.length();
    int N = txt.length();
    /* A loop to slide pat[] one by one */
    for (int i = 0; i <= N - M; i++) {
      int j;
      /* For current index i, check for pattern match */
      for (j = 0; j < M; j++) {
        if (txt.charAt(i + j) != pat.charAt(j)) {
          break;
        }
      }
      if (j == M)  // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
      {
        System.out.print("Pattern found at index " + i);
      }
    }
  }

  //KMP Algorithm to find substing in a string. Time complexity is O(m + n) where m is length of text and n is length of pattern
  //http://tekmarathon.com/2013/05/14/algorithm-to-find-substring-in-a-string-kmp-algorithm/
  public void searchSubString(char[] text, char[] ptrn) {
    int i = 0, j = 0;
    // pattern and text lengths
    int ptrnLen = ptrn.length;
    int txtLen = text.length;
    // initialize new array and preprocess the pattern
    int[] b = preProcessPattern(ptrn);
    while (i < txtLen) {
      while (j >= 0 && text[i] != ptrn[j]) {
        j = b[j];
      }
      i++;
      j++;
      // a match is found
      if (j == ptrnLen) {
        System.out.println("found substring at index:" + (i - ptrnLen));
        j = b[j];
      }
    }
  }

  //p    :  a   b   c   a   b   d   a   b   c
  //p[i] :  0   1   2   3   4   5   6   7   8
  //b[i] : -1   0   0   0   1   2   0   1   2   3
  public int[] preProcessPattern(char[] ptrn) {
    int i = 0, j = -1;
    int ptrnLen = ptrn.length;
    int[] b = new int[ptrnLen + 1];
    b[i] = j;
    while (i < ptrnLen) {
      while (j >= 0 && ptrn[i] != ptrn[j]) {
        // if there is mismatch consider the next widest border The borders to be examined are obtained
        // in decreasing order from the values b[i], b[b[i]] etc.
        j = b[j];
      }
      i++;
      j++;
      b[i] = j;
    }
    return b;
  }

  //Determine the K most frequent words given a terabyte of strings.
  class WordFreq {

    String word;
    int freq;

    public WordFreq(final String w, final int c) {
      word = w;
      freq = c;
    }
  }

  void findTopKFrequentWords(String[] s, int k) {
    HashMap<String, Integer> _hash = new HashMap<String, Integer>();
    PriorityQueue<WordFreq> _minHeap = new PriorityQueue<WordFreq>();
    for (int i = 0; i < s.length; i++) {
      if (_hash.containsKey(s[i])) {
        int count = _hash.get(s[i]);
        _hash.put(s[i], count + 1);
      } else {
        _hash.put(s[i], 1);
      }
    }
    int count = 0;
    for (Map.Entry<String, Integer> entry : _hash.entrySet()) {
      if (count < k) {
        _minHeap.add(new WordFreq(entry.getKey(), entry.getValue()));
        count++;
      } else if (entry.getValue() > _minHeap.peek().freq) {
        _minHeap.poll();
        _minHeap.add(new WordFreq(entry.getKey(), entry.getValue()));
      }
    }
    while (!_minHeap.isEmpty()) {
      System.out.println(_hash.get(_minHeap.poll().word));
    }
  }

  //Find if a given string can be represented from a substring by iterating the substring “n” times
  //Input: str = "abcabcabc"  Output: true The given string is 3 times repetition of "abc"
  // Returns true if str is repetition of one of its sub Strings else return false.
  Boolean isRepeat(char str[]) {
    // Find length of string and create an array to store lps values used in KMP
    int n = str.length;
    int[] lps = new int[n];
    // Pre-process the pattern (calculate lps[] array)
    computeLPSArray(str, n, lps);
    // Find length of longest suffix which is also prefix of str.
    int len = lps[n - 1];
    // If there exist a suffix which is also prefix AND
    // Length of the remaining substring divides total
    // length, then str[0..n-len-1] is the substring that
    // repeats n/(n-len) times (Readers can print substring
    // and value of n/(n-len) for more clarity.
    return (len > 0 && n % (n - len) == 0) ? true : false;
  }

  // A utility function to fill lps[] or compute prefix funcrion
  // used in KMP string matching algorithm.
  void computeLPSArray(char str[], int M, int lps[]) {
    int len = 0; //length of the previous longest prefix suffix
    int i;
    lps[0] = 0; //lps[0] is always 0
    i = 1;
    // the loop calculates lps[i] for i = 1 to M-1
    while (i < M) {
      if (str[i] == str[len]) {
        len++;
        lps[i] = len;
        i++;
      } else {// (pat[i] != pat[len])
        if (len != 0) {
          // This is tricky. Consider the example AAACAAAA and i = 7.
          len = lps[len - 1];
          // Also, note that we do not increment i here
        } else { // if (len == 0)
          lps[i] = 0;
          i++;
        }
      }
    }
  }

  /*CSV Parser Specifications: Separator: , New Line: \r Quote: " (inside the quote we can have any character)
    Input: hello world,"b,c",Piyush Patel\nfoo,bar,bax
    Output:[[hello world],["b,c"],[Piyush Patel],[foo,bar,bax]]*/
  public List<String> decodeCSV(String s) {
    if (s == null || s.length() == 0) {
      return null;
    }
    List<String> result = new ArrayList<String>();
    boolean inQuotes = false;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      char value = s.charAt(i);
      if (inQuotes) {
        if (value == '"') {
          if (i == s.length() - 1) {
            result.add(sb.toString());
            return result;
          } else if (s.charAt(i + 1) == '"') {
            sb.append('"');
            i++;
          } else {
            result.add(sb.toString());
            sb.setLength(0);
            inQuotes = false;
            i++;
          }
        } else {
          sb.append(value);
        }
      } else if (value == '"') {
        inQuotes = true;
      } else if (value == ',') {
        result.add(sb.toString());
        sb.setLength(0);
      } else {
        sb.append(value);
      }
    }
    result.add(sb.toString());
    return result;
  }

  //Airbnb: Decode String. Given a string try lower/upper case combinations to decode the string.
  //e.g. kljJJ324hjkS_ decodes to 848662 now given Input is : kljjj324hjks_
  //Time complexity is exponential
  private static Integer decodeFindHelper(int start, StringBuffer curr, String badEncString) {
    if (start == badEncString.length()) {
      String testEncStr = curr.toString();
      Integer result = decodeString(testEncStr);
      if (result != null) {
        return result;
      } else {
        return null;
      }
    }
    char c = badEncString.charAt(start);
    if (!Character.isLetter(c)) {
      curr.append(c);
      Integer result = decodeFindHelper(start + 1, curr, badEncString);
      if (result != null) {
        return result;
      }
      curr.deleteCharAt(curr.length() - 1);
    } else {
      // To lower case
      char lower = Character.toLowerCase(c);
      curr.append(lower);
      Integer result = decodeFindHelper(start + 1, curr, badEncString);
      if (result != null) {
        return result;
      }
      curr.deleteCharAt(curr.length() - 1);
      // To upper case
      char upper = Character.toUpperCase(c);
      curr.append(upper);
      result = decodeFindHelper(start + 1, curr, badEncString);
      if (result != null) {
        return result;
      }
      curr.deleteCharAt(curr.length() - 1);
    }
    return null;
  }

  public static Integer decodeString(String testEncStr) {
    String truth = "kljJJ324hijkS_";
    if (testEncStr.equals(truth)) {
      return 848662;
    } else {
      return null;
    }
  }

  //wildcard matching: '?' Matches any single character.
  // '*' Matches any sequence of characters (including the empty sequence).
  static boolean WildCardcomparison(String str, String pattern) {
    int s = 0, p = 0, starIdx = -1;
    while (s < str.length()) {
      // advancing both pointers
      if (p < pattern.length() && (pattern.charAt(p) == '?' || str.charAt(s) == pattern
          .charAt(p))) {
        s++;
        p++;
      }
      // * found, only advancing pattern pointer
      else if (p < pattern.length() && pattern.charAt(p) == '*') {
        starIdx = p;
        p++;
      }
      // last pattern pointer was *, advancing string pointer
      else if (starIdx != -1) {
        //p = starIdx + 1;
        s++;
      }
      //current pattern pointer is not star, last patter pointer was not * characters do not match
      else {
        return false;
      }
    }
    //check for remaining characters in pattern
    while (p < pattern.length() && pattern.charAt(p) == '*') {
      p++;
    }
    return p == pattern.length();
  }


    /* Implement regex regular expression matching with support for '.' and '*'.
    '.' Matches any single character.
    '*' Matches zero or more of the preceding element. */

  /**
   * Dynamic programming technique for regex matching. Time and space = O(M * N) 1, If p.charAt(j)
   * == s.charAt(i) :  dp[i][j] = dp[i-1][j-1]; 2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
   * 3, If p.charAt(j) == '*': here are two sub conditions: 1   if p.charAt(j-1) != s.charAt(i) :
   * dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty 2   if p.charAt(i-1) ==
   * s.charAt(i) or p.charAt(i-1) == '.': dp[i][j] = dp[i-1][j]    //in this case, a* counts as
   * multiple a or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a or dp[i][j] =
   * dp[i][j-2]   // in this case, a* counts as empty
   */
  public static boolean matchRegex(String s, String p) {
    if (s == null || p == null) {
      return false;
    }
    boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
    dp[0][0] = true;
    for (int i = 0; i < p.length(); i++) {
      if (p.charAt(i) == '*' && dp[0][i - 1]) {
        dp[0][i + 1] = true;
      }
    }
    for (int i = 1; i <= s.length(); i++) {
      for (int j = 1; j <= p.length(); j++) {
        if (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        }
        if (p.charAt(j - 1) == '*') {
          if (p.charAt(j - 2) != s.charAt(i - 1) && p.charAt(j - 2) != '.') {
            dp[i][j] = dp[i][j - 2];
          } else {
            dp[i][j] = (dp[i][j - 1] || dp[i][j - 2] || dp[i - 1][j]);
          }
        }
      }
    }
    return dp[s.length()][p.length()];
  }

  /* Implement a regular expression matching. There are three special characters.
        * means zero or more of previous characters
        . means any single character
        + means one or more of previous characters   */
  public static boolean isMatch1(String s, String p) {
    // base case
    if (p.length() == 0) {
      return s.length() == 0;
    }
    // special case
    if (p.length() == 1) {
      // if the length of s is 0, return false
      if (s.length() < 1) {
        return false;
      }
      //if the first does not match, return false
      else if ((p.charAt(0) != s.charAt(0)) && (p.charAt(0) != '.')) {
        return false;
      }
      // otherwise, compare the rest of the string of s and p.
      else {
        return isMatch1(s.substring(1), p.substring(1));
      }
    }
    // case 1: when the second char of p is not '*'
    if (p.charAt(1) != '*' && p.charAt(1) != '+') {
      if (s.length() < 1) {
        return false;
      }
      if ((p.charAt(0) != s.charAt(0)) && (p.charAt(0) != '.')) {
        return false;
      } else {
        return isMatch1(s.substring(1), p.substring(1));
      }
    }
    // case 2: when the second char of p is '*', complex case.
    else if (p.charAt(1) == '*') {
      //case 2.1: a char & '*' can stand for 0 element
      if (isMatch1(s, p.substring(2))) {
        return true;
      }
      //case 2.2: a char & '*' can stand for 1 or more preceding element, so try every sub string
      int i = 0;
      while (i < s.length() && (s.charAt(i) == p.charAt(0) || p.charAt(0) == '.')) {
        if (isMatch1(s.substring(i + 1), p.substring(2))) {
          return true;
        }
        i++;
      }
      return false;
    } else if (p.charAt(1) == '+') {
      int i = 0;
      while (i < s.length() && (s.charAt(i) == p.charAt(0) || p.charAt(0) == '.')) {
        if (isMatch1(s.substring(i + 1), p.substring(2))) {
          return true;
        }
        i++;
      }
      return false;
    }
    return false;
  }

  //Given a character limit and a message, split the message up into annotated chunks without cutting words as,
  //for example when sending the SMS "Hi Sivasrinivas, your Uber is arriving now!" with char limit 25, you should get
  //["Hi Sivasrinivas,(1/3)", "your Uber is arriving(2/3)", "now!(3/3)"]
  static ArrayList<String> splitText(String message, int charLimit) {
    return splitTextAuxUsingSplit(message, charLimit);
  }

  static ArrayList<String> splitTextAuxUsingSplit(String message, int charLimitOriginal) {
    //Decrease the char limit to accomodate chunk number at the end i.e. (1/3). For now assuming, the message chunks won't be more than 9
    int charLimit = charLimitOriginal - 5;
    //New arraylist to store message chunks
    ArrayList<String> result = new ArrayList<String>();
    String[] splitted = message.split(" ");
    String temp;
    for (int i = 0; i < splitted.length - 1; i++) {
      temp = splitted[i];
      //while length of temp and the next element combined is less than charLimit, temp = temp + next element
      //Last element to be taken care of after this loop
      while (i + 1 < splitted.length - 1
          && (temp + 1 + splitted[i + 1]).length() <= charLimit) {  //+1 for space
        temp = temp + " " + splitted[i + 1];
        i++;
      }
      result.add(temp);
    }
    //Take care of the last element
    //Add the last element from splitted to the last element of result if their combined length is less than charLimit
    String lastElement = result.get(result.size() - 1);
    if (lastElement.length() + 1 + splitted[splitted.length - 1].length()
        < charLimit) {  //+1 for space
      result.set(result.size() - 1, lastElement + " " + splitted[splitted.length - 1]);
    } else {
      result.add(splitted[splitted.length - 1]);
    }
    //append message chunk number for ex (1/3)
    int resultSize = result.size();
    for (int i = 0; i < resultSize; i++) {
      result.set(i, result.get(i) + "(" + (i + 1) + "/" + resultSize + ")");
    }
    return result;
  }

  //Remove consecutive duplicate characters e.g AABBCDDAAB -> ABCDAB  ABBBCCD -> ABCD
  public int removeDuplicates(char input[]) {
    int slow = 0;
    int fast = 0;
    int index = 0;
    while (fast < input.length) {
      while (fast < input.length && input[slow] == input[fast]) {
        fast++;
      }
      input[index++] = input[slow];
      slow = fast;
    }
    return index;
  }

  //Given a string, find the rank of the string amongst its permutations sorted lexicographically. Assume that no
  //characters are repeated. Example : Input : 'acb' Output : 2
  public static int findRank(char[] str) {
    int len = str.length;
    long mul = factorial(len);
    int rank = 1, i;
    int[] count = new int[256];  // all elements of count[] are initialized with 0
    // Populate the count array such that count[i] contains count of characters which are present in str and are smaller than i
    populateAndIncreaseCount(count, str);
    for (i = 0; i < len; ++i) {
      mul /= len - i;
      // count number of chars smaller than str[i] from str[i+1] to str[len-1]
      rank += count[str[i] - 1] * mul;
      // Reduce count of characters greater than str[i]
      updatecount(count, str[i]);
    }
    return rank;
  }

  public static long factorial(int n) {
    return n <= 1 ? 1 : (n * factorial(n - 1));
  }

  public static int findSmallerInRight(String A, int low, int high) {
    int countRight = 0;
    for (int i = low + 1; i <= high; i++) {
      if (A.charAt(i) < A.charAt(low)) {
        countRight++;
      }
    }
    return countRight;
  }

  // contains count of smaller characters in whole string
  public static void populateAndIncreaseCount(int[] count, char[] str) {
    int i;
    for (i = 0; str[i] >= 'a' && str[i] <= 'z'; ++i) {
      ++count[str[i]];
    }
    for (i = 1; i < 256; ++i) {
      count[i] += count[i - 1];
    }
  }

  // Removes a character ch from count[] array constructed by populateAndIncreaseCount()
  public static void updatecount(int[] count, char ch) {
    int i;
    for (i = ch; i < 256; ++i) {
      --count[i];
    }
  }

  //Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
  //Only one letter can be changed at a time Each intermediate word must exist in the dictionary
  public int ladderLength(String start, String end, HashSet<String> dict) {
    Map<String, Boolean> visited = new HashMap<String, Boolean>();
    LinkedList<Count> queue = new LinkedList<Count>();
    queue.add(new Count(start, 1));
    visited.put(start, true);
    while (!queue.isEmpty()) {
      Count c = queue.poll();
      // for each character in the string, start new branches
      for (int i = 0; i < start.length(); i++) {
        StringBuilder sb = new StringBuilder(c.string);
        char sc = c.string.charAt(i);
        // for each different character as new node
        for (char cc = 'a'; cc <= 'z'; cc++) {
          if (cc == sc) {
            continue;
          }
          sb.setCharAt(i, cc);
          String tmp = sb.toString();
          // if we haven't visited this node and is in our dictionary we visit this node
          if (visited.get(tmp) == null && dict.contains(tmp)) {
            if (tmp.equals(end)) {
              return c.count + 1;
            }
            visited.put(tmp, true);
            queue.add(new Count(tmp, c.count + 1));
          }
        }
      }
    }
    return 0;
  }

  class Count {

    String string;
    //the counts from start string to current string
    int count;

    public Count(String string, int count) {
      this.string = string;
      this.count = count;
    }
  }

  //Word Ladder: Given a source word, target word and an English dictionary, transform the source word to target by
  //changing/adding/removing 1 character at a time, while all intermediate words being valid English words.
  public static LinkedList<String> transform(String startWord, String stopWord,
      Set<String> dictionary) {
    startWord = startWord.toUpperCase();
    stopWord = stopWord.toUpperCase();
    Queue<String> actionQueue = new LinkedList<>();
    Set<String> visitedSet = new HashSet<>();
    Map<String, String> backtrackMap = new TreeMap<>();
    actionQueue.add(startWord);
    visitedSet.add(startWord);

    while (!actionQueue.isEmpty()) {
      String w = actionQueue.poll();
      // For each possible word v from w with one edit operation
      for (String v : getOneEditWords(w)) {
        if (v.equals(stopWord)) {
          // Found our word!  Now, back track.
          LinkedList<String> list = new LinkedList<>();
          // Append v to list
          list.add(v);
          while (w != null) {
            list.add(0, w);
            w = backtrackMap.get(w);
          }
          return list;
        }
        // If v is a dictionary word
        if (dictionary.contains(v)) {
          if (!visitedSet.contains(v)) {
            actionQueue.add(v);
            visitedSet.add(v); // mark visited
            backtrackMap.put(v, w);
          }
        }
      }
    }
    return null;
  }

  private static Set<String> getOneEditWords(String word) {
    Set<String> words = new TreeSet<String>();
    // for every letter
    for (int i = 0; i < word.length(); i++) {
      char[] wordArray = word.toCharArray();
      // change that letter to something else
      for (char c = 'A'; c <= 'Z'; c++) {
        if (c != word.charAt(i)) {
          wordArray[i] = c;
          words.add(new String(wordArray));
        }
      }
    }
    return words;
  }

  //Remove “b” and “ac” from a given string
  //input = ababaac output = aaa input = abc output =""
  public static String Remove_Pattern_from_String(char[] str) {
    int n = str.length;
    int i = -1;  // previous character
    int j = 0;   // current character
    while (j < n) {
      /* check if current and next character forms ac */
      if (j < n - 1 && str[j] == 'a' && str[j + 1] == 'c') {
        j += 2;
      }
      /* if current character is b */
      else if (str[j] == 'b') {
        j++;
      }
      /* if current char is 'c && previous char is 'a' so delete both */
      else if (i >= 0 && str[j] == 'c' && str[i] == 'a') {
        i--;
        j++;
      }
      /* else copy curr char to output string */
      else {
        str[++i] = str[j++];
      }
    }
    return new String(str).substring(0, ++i);
  }

  //Recursively remove all adjacent duplicates
  //Input:  azxxzy   Output: ay
  public static String removeAdjacentDuplicates(String s) {
    if (s.length() < 2) {
      return s;
    }
    char[] buf = s.toCharArray();
    char lastchar = buf[0];
    // i: index of input char
    // j: index of output char
    int j = 1;
    for (int i = 1; i < buf.length; i++) {
      if (j > 0 && buf[i] == buf[j - 1]) {
        lastchar = buf[j - 1];
        while (j > 0 && buf[j - 1] == lastchar) {
          j--;
        }
      } else if (buf[i] != lastchar) {
        buf[j] = buf[i];
        j++;
      }
    }
    return new String(buf, 0, j);
  }

  //Given a list of words and an abbreviation, I have to write a function which returns true or false about whether
  // the abbreviation maps to exactly one word or not. An abbreviation of a word follows the form <first letter><number><last letter>
  //i|nternationalizatio|n  --> i18n
  private Map<String, String> abbrDict;
  Set<String> uniqueDict;

  public void ValidWordAbbr(String[] dictionary) {
    abbrDict = new HashMap<String, String>();
    uniqueDict = new HashSet<String>();
    for (String word : dictionary) {
      if (!uniqueDict.contains(word)) {
        String abbr = getAbbr(word);
        if (!abbrDict.containsKey(abbr)) {
          abbrDict.put(abbr, word);
        } else {
          abbrDict.put(abbr, "");
        }

        uniqueDict.add(word);
      }
    }
  }

  public boolean isUnique(String word) {
    if (word == null || word.length() == 0) {
      return true;
    }
    String abbr = getAbbr(word);
    if (!abbrDict.containsKey(abbr) || abbrDict.get(abbr).equals(word)) {
      return true;
    } else {
      return false;
    }
  }

  private String getAbbr(String word) {
    if (word == null || word.length() < 3) {
      return word;
    }
    StringBuffer sb = new StringBuffer();
    sb.append(word.charAt(0));
    sb.append(word.length() - 2);
    sb.append(word.charAt(word.length() - 1));
    return sb.toString();
  }

  //Airbnb: display page by host_id. max entry into page is 12
  //There is a trick in this question. When do we need to get to a new page? There are two cases need to consider:
  //1. When the current page has 12 entries.
  //2. When the current page has less than 12 but the iterator has reached to the end. IN this case, we need wrap back and iterator the list again.
  public static void displayPages(List<String> input) {
    if (input == null || input.size() == 0) {
      return;
    }
    Set<String> visited = new HashSet<String>();
    Iterator<String> iterator = input.iterator();
    int pageNum = 1;
    System.out.println("Page " + pageNum);
    while (iterator.hasNext()) {
      String curr = iterator.next();
      String hostId = curr.split(",")[0];
      if (!visited.contains(hostId)) {
        System.out.println(curr);
        visited.add(hostId);
        iterator.remove();
      }
      // New page
      if (visited.size() == 12 || (!iterator.hasNext())) {
        visited.clear();
        iterator = input.iterator();
        if (!input.isEmpty()) {
          pageNum++;
          System.out.println("Page " + pageNum);
        }
      }
    }
  }

  /**
   * Boggle implementation: Given a dictionary, a method to do lookup in dictionary and a M x N
   * board where every cell has one character. Find all possible words that can be formed by a
   * sequence of adjacent charactersNote that we can move to any of 8 adjacent characters, but a
   * word should not have multiple instances of same cell. https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
   * Remove visited[m][n] completely by modifying board[i][j] = '#' directly time complexity: O(m *
   * n * l * wl) where n is board.length, m is board[0].length, l is words.length and wl is the
   * average of length of words in 'words'. Space: O(l * wl) = max(O(wl), O(l * wl)) where O(wl) -
   * The recursive stack can grow at most to wl layers. O(l * wl) - In the worst case when all words
   * start with different characters, the trie has l * wl nodes. Also, since each word is stored in
   * a leaf node, all the leaf nodes require l * wl memory.
   */
  class TrieNode1 {

    TrieNode1[] next = new TrieNode1[26];
    String word;
  }

  class Boggle {

    public TrieNode1 buildTrie(String[] words) {
      TrieNode1 root = new TrieNode1();
      for (String w : words) {
        TrieNode1 p = root;
        for (char c : w.toCharArray()) {
          int i = c - 'a';
          if (p.next[i] == null) {
            p.next[i] = new TrieNode1();
          }
          p = p.next[i];
        }
        p.word = w;
      }
      return root;
    }

    public List<String> findWords(char[][] board, String[] words) {
      List<String> res = new ArrayList<>();
      TrieNode1 root = buildTrie(words);
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
          dfs(board, i, j, root, res);
        }
      }
      return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode1 p, List<String> res) {
      char c = board[i][j];
      if (c == '#' || p.next[c - 'a'] == null) {
        return;
      }
      p = p.next[c - 'a'];
      if (p.word != null) {   // found one
        res.add(p.word);
        p.word = null;     // de-duplicate
      }

      board[i][j] = '#';
      if (i > 0) {
        dfs(board, i - 1, j, p, res);
      }
      if (j > 0) {
        dfs(board, i, j - 1, p, res);
      }
      if (i < board.length - 1) {
        dfs(board, i + 1, j, p, res);
      }
      if (j < board[0].length - 1) {
        dfs(board, i, j + 1, p, res);
      }
      board[i][j] = c;
    }
  }

  /*Word search ii: another version using Trie and Dynamic programming: instead of random constructing word after word in this infinite
  ocean of words why don't I take a word from the dictionary and somehow magically check whether that's available on the board or not?
  DP: For a word of length k to be found (end location) at the [i, j]-th location of the board, the k-1'th letter of that
  word must be located in one of the adjacent cells of [i, j].
  Time = O (W * N * N * MAX_WORD_LENGTH) */
  private static DictNode root;

  private static class DictNode {

    public final char letter;
    public DictNode[] nextNodes = new DictNode[26];
    public boolean wordEnd;

    public DictNode(final char letter) {
      this.letter = letter;
    }

    public void insert(final String word) {
      DictNode node = root;
      char[] letters = word.toCharArray();
      for (int i = 0; i < letters.length; i++) {
        if (node.nextNodes[letters[i] - 'a'] == null) {
          node.nextNodes[letters[i] - 'a'] = new DictNode(letters[i]);

          if (i == letters.length - 1) {
            node.nextNodes[letters[i] - 'a'].wordEnd = true;
          }
        }
        node = node.nextNodes[letters[i] - 'a'];
      }
    }

    public boolean contains(final String word) {
      DictNode node = root;
      char[] letters = word.toCharArray();
      int i = 0;
      while (i < letters.length && node.nextNodes[letters[i] - 'a'] != null) {
        node = node.nextNodes[letters[i] - 'a'];
        i++;
      }
      return (i == letters.length) && node.wordEnd;
    }
  }

  static char board[][];

  public static void boggleTrieDynamic(DictNode node, char[] currentBranch, int currentHeight) {
    if (node == null) {
      return;
    }
    if (node.wordEnd && currentHeight > 3) {
      String word = new String(currentBranch, 0, currentHeight - 1);
      boolean inBoard = isInBoard(board, word);
      if (inBoard) {
        System.out.println(word);
      }
    }
    for (int i = 0; i < node.nextNodes.length; i++) {
      if (node.nextNodes[i] != null) {
        currentBranch[currentHeight] = (char) (i + 'a');
        boggleTrieDynamic(node.nextNodes[i], currentBranch, currentHeight + 1);
      }
    }
  }

  public static boolean isInBoard(char board[][], final String word) {
    int M = board.length;
    int N = board[0].length;
    int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
    boolean[][] visited = new boolean[M][N];
    char[] letters = word.toCharArray();
    boolean[][][] dp = new boolean[letters.length][M][N];
    for (int k = 0; k < letters.length; k++) {
      for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
          if (k == 0) {
            dp[k][i][j] = true;
          } else if (!visited[i][j] && dp[k - 1][i][j]) {
            for (int l = 0; l < 8; l++) {
              int x = i + dx[l];
              int y = j + dy[l];
              if ((x >= 0) && (x < M) && (y >= 0) && (y < N) && (dp[k - 1][x][y]) && (board[i][j]
                  == letters[k])) {
                dp[k][i][j] = true;
                visited[x][y] = true;
                if (k == letters.length - 1) {
                  return true;
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  //tiny URL
  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int BASE = ALPHABET.length();

  public static String encode(int num) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
      sb.append(ALPHABET.charAt(num % BASE));
      num /= BASE;
    }
    return sb.toString();
  }

  public static int decode1(String str) {
    int num = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      num = num * BASE + ALPHABET.indexOf(str.charAt(i));
    }
    return num;
  }

  /*Given a list of words, and the number of rows and columns, return the number of words that can be fit into the rows and columns
     by stringing together each consecutive word. If the next word doesn't fit in the same line, it should move to the next line.  For eg.
     Input: List of words: { "Do";, "Run" } , Number of columns: 9 , Number of rows: 2
     Output = 5 */
  public static int numberCanFit(String[] words, int row, int col) {
    int wordCount = 0;
    int rowIterator = 0;
    int wordIterator = 0;
    int remainingCol;
    while (rowIterator < row) {
      remainingCol = col;
      while (remainingCol > 0 && words[wordIterator].length() <= remainingCol) {
        wordCount++;
        // update remainingCol , takes wordspacing into account
        remainingCol = remainingCol - words[wordIterator].length() - 1;
        wordIterator++;
        if (wordIterator == words.length) {
          wordIterator = 0;
        }
      }
      rowIterator++;
    }
    return wordCount;
  }

  /*List of string that represent class names in CamelCaseNotation. Write a function that given a List and a pattern returns the matching elements.
    Input  = [HelloMars,HelloWorld,HelloWorldMars,HiHo]
    H -> [HelloMars, HelloWorld, HelloWorldMars, HiHo]
    HW -> [HelloWorld, HelloWorldMars]
    Ho -> []
    HeWorM -> [HelloWorldMars] */
  public static ArrayList<String> getCamelCaseMatchingStrings(ArrayList<String> list,
      String pattern) {
    ArrayList<String> patternList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      String s = list.get(i);
      int len = 0;
      int pattern_len = pattern.length() - 1;
      int patIndex = 0;
      while (len != s.length() - 1 && patIndex <= pattern_len) {
        if (s.charAt(len) == pattern.charAt(patIndex)) {
          len++;
          if (patIndex == pattern_len) {
            patternList.add(s);
            break;
          }
          patIndex++;
          continue;
        } else if ((!Character.isUpperCase(pattern.charAt(patIndex)))) {
          break;
        }
        len++;
      }
    }
    return patternList;
  }

  //print non over lapping in order pairs. example:input = [1,2,3,4]
    /*output:   (1234)
                (1)(234)
                (1)(23)(4)
                (1)(2)(34)
                (12)(34)
                (12)(3)(4)
                (123)(4)
                (1)(2)(3)(4)*/
  public static void printNonOverlapping(String number, String prefix) {
    System.out.println(prefix + "(" + number + ")");
    for (int i = 1; i < number.length(); i++) {
      String newPrefix = prefix + "(" + number.substring(0, i) + ")";
      printNonOverlapping(number.substring(i, number.length()), newPrefix);
    }
  }

  /*Define a function that can detect whether the characters of a string can be shuffled without repeating same
    characters as one other's neighbors. E.g. : apple >> alpep, so valid */
  public boolean canShuffle(char[] s) {
    // initial counter array
    int[] counter = new int[300];
    // count the number of character in s
    for (char c : s) {
      counter[c]++;
    }
    // get the maximum from counter
    int maxExistedCharacter = 0;
    for (char c = 'a'; c <= 'z'; c++) {
      maxExistedCharacter = Math.max(counter[c], maxExistedCharacter);
    }
    // s can shuffle when the maxExistedCharacter less than (length of s + 1) / 2
    return maxExistedCharacter <= (s.length + 1) / 2;
  }

  /*Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words
    do not share common letters. You may assume that each word will contain only lower case letters. If no such two words exist, return 0.
    Time = O(26 * n^2) */
  public int maxProduct(String[] words) {
    if (words == null || words.length <= 1) {
      return 0;
    }
    int n = words.length;
    int[] encodedWords = new int[n];
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      for (int j = 0; j < word.length(); j++) {
        char c = word.charAt(j);
        encodedWords[i] |= (1 << (c - 'a'));
      }
    }
    int maxLen = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if ((encodedWords[i] & encodedWords[j]) == 0) {
          maxLen = Math.max(maxLen,
              words[i].length() * words[j].length());
        }
      }
    }
    return maxLen;
  }

  //Given a string consisting of opening and closing parenthesis, find length of the longest valid parenthesis substring.
  public static int findLongestParanthesisLen(String str) {
    int cnt = 0;
    int ans = 0, max_len = 0;
    /// if ( increase cnt
    /// if ) and cnt < 0 means count of ) > ( stop reset cnt and ans
    /// if ) and cnt > 0 means count of ( > ) a pair of () seen increment ans+= 2, do not stop longer valid string can be formed
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        cnt++;
      } else {
        if (cnt <= 0) {
          max_len = Math.max(max_len, ans);
          cnt = 0;
          ans = 0;
        } else {
          cnt--;
          ans += 2;
        }
      }
    }
    if (cnt >= 0) {
      max_len = Math.max(max_len, ans);
    }
    return max_len;
  }

  //Given an input string and ordering string, need to return true if the ordering string is present in Input string.
  //input = "hello world!"  ordering = "hlo!"  result = FALSE (all Ls are not before all Os)
  public static boolean isOrderingStringPresent(String s, String ordering) {
    int[] label = new int[256];
    int order = 1;
    for (int i = 0; i < ordering.length(); i++) {
      label[ordering.charAt(i)] = order;
      order++;
    }
    int last = 0;
    for (int i = 0; i < s.length(); i++) {
      if (label[s.charAt(i)] > 0) {
        if (label[s.charAt(i)] < last) {
          return false;
        }
        last = label[s.charAt(i)];
      }
    }
    return true;
        /*HashMap<Character, Integer> map = new HashMap<>();
        char[] input = s.toCharArray();
        for (int i = 0; i < input.length; i++) {
            map.put(input[i], i);
        }
        char[] _order = ordering.toCharArray();
        int temp = 0;
        for (int i = 0; i < _order.length; i++) {
            if (!map.containsKey(_order[i]) && (map.get(_order[i]) < temp)) {
                return false;
            }
            temp = map.get(_order[i]);
        }
        return true;*/

  }

  /* Ransom Note problem: write code for this function matchstr() given "ab" in a(1)b(1) ---> true
        "z" in a(4)b(4) --> false
        "aaaa" in a(3)b(3) ---> false
        "aab" in a(3)b(3) ---> true
        "aaba" in a(3)b(3) ---> true */
  // Time is O(M) where M is length of magazine. we can do better than this when magazine is too big
  public static boolean ransomNote1(String note, String mag) {
    int[] count = new int[256]; // Assumes only ASCII characters
    // for -- a(1)b(1)
        /*int i =0;
        while(i < mag.length()){
            int c = mag.charAt(i);
            int num = Character.getNumericValue(mag.charAt(i+2));
            count[c] = num;
            i += 4;
        }*/
    for (int i = 0; i < mag.length(); i++) {
      int c = mag.charAt(i);
      count[c]++;
    }
    for (int j = 0; j < note.length(); j++) {
      int c = note.charAt(j);
      count[c]--;
      if (count[c] < 0) {
        return false;
      }
    }
    return true;
  }

  /* We don't scan magazine string and ransom note separately but simultaneously. We scan character from ransom note,
    and check in hash table, if we find good. If not, we scan magazine string till we find the desired character.
    If we reach end of magazine string, return false. If we reach end of ransom note, return true. */
  public static boolean ransomNote2(String str, String pattern) {
    int[] count = new int[256]; // Assumes only ASCII characters
    int n = 0;
    int m = 0;
    while (n < str.length()) {
      int nchar = str.charAt(n);
      if (count[nchar] > 0) {
        count[nchar]--;
        n++;
      } else {
        while (m < pattern.length() && pattern.charAt(m) != nchar) {
          int mchar = pattern.charAt(m);
          count[mchar]++;
          m++;
        }
        if (m >= pattern.length()) {
          return false;
        }
        n++;
        m++;
      }
    }
    return true;
  }

  //evalexpr(-4 - 3 * 2 / 2 + 4) -> result (float or double) without parenthesis
  //[Token(NUM, -4.), Token(SUB), Token(NUM, 3), Token(MUL)…]
  class Token {

    String type;
    double value;

    public Token(String _type, double _value) {
      type = _type;
      value = _value;
    }

    public Token(String _type) {
      type = _type;
    }
  }

  public static double evalExpr(List<Token> tokenList) {
    int i = 0;
    double left = tokenList.get(i++).value;
    while (i < tokenList.size()) {
      String operator = tokenList.get(i++).type;
      double right = Double.valueOf(tokenList.get(i++).value);
      switch (operator) {
        case "*":
          left = left * right;
          break;
        case "/":
          left = left / right;
          break;
        case "+":
        case "-":
          while (i < tokenList.size()) {
            String operator2 = tokenList.get(i++).type;
            if (operator2.equals("+") || operator2.equals("-")) {
              i--;
              break;
            }
            if (operator2.equals("*")) {
              right = right * Double.valueOf(tokenList.get(i++).value);
            }
            if (operator2.equals("/")) {
              right = right / Double.valueOf(tokenList.get(i++).value);
            }
          }
          if (operator.equals("+")) {
            left = left + right;
          } else {
            left = left - right;
          }
          break;
      }
    }
    return left;
  }

  // given list of words dictionary and set of characters find the words you can form from set of characters which ica valid in dictionary
  // {'o','s','o','r','d','w',} output = "word, "words","wood"
  // assuming only a-z characters
  // if find is called many times we can build dictionary with trie as pre-proccessing which is faster
  public void init(String[] words) {
    HashMap<String, int[]> map = new HashMap<>();
    for (int i = 0; i < words.length; i++) {
      int[] count = new int[26];
      char[] ch = words[i].toCharArray();
      for (int j = 0; j < ch.length; j++) {
        int index = ch[j] - 'a';
        count[index]++;
      }
    }
  }

  //O(M + N * 26) M = number of characters and N = number of words in dictionary
  public Set<String> wordFinder(Set<Character> input, HashMap<String, int[]> map) {
    Set<String> out = new HashSet<>();
    int[] charCount = new int[26];
    for (char c : input) {
      int index = c - 'a';
      charCount[index]++;
    }
    for (String key : map.keySet()) {
      boolean isMatch = true;
      int[] temp = map.get(key);
      for (int i = 0; i < temp.length; i++) {
        if (temp[i] != charCount[i]) {
          isMatch = false;
          break;
        }
      }
      if (isMatch) {
        out.add(key);
      }
    }
    return out;
  }

  TrieWordFinder root1;

  class TrieWordFinder {

    char letter;
    TrieWordFinder[] child = new TrieWordFinder[26];
    boolean isWord;

    public TrieWordFinder(char letter) {
      this.letter = letter;
    }

    public void insert(String word) {
      TrieWordFinder node = root1;
      char[] ch = word.toCharArray();
      for (int i = 0; i < ch.length; i++) {
        if (node.child[ch[i] - 'a'] == null) {
          node.child[ch[i] - 'a'] = new TrieWordFinder(ch[i]);
          if (i == ch.length - 1) {
            node.child[ch[i] - 'a'].isWord = true;
          }
        }
        node = node.child[ch[i] - 'a'];
      }
    }

    public Set<String> findUtil(int[] letter) {
      Set<String> out = new HashSet<>();
      TrieWordFinder node = root1;
      for (int i = 0; i < letter.length; i++) {
        String word = "";
        while (letter[i] > 0 && node.child[letter[i] - 'a'] != null) {
          word += node.child[letter[i] - 'a'];
          if (node.child[letter[i] - 'a'].isWord) {
            out.add(word);
          }
          node = node.child[letter[i] - 'a'];

        }
      }
      return out;
    }

    public void init(String[] words) {
      for (String word : words) {
        insert(word);
      }
    }

    public Set<String> find(char[] letters) {
      int[] let = new int[26];
      for (int i = 0; i < letters.length; i++) {
        int index = letters[i] - 'a';
        let[index]++;
      }
      return findUtil(let);
    }
  }

  /*Given two input arrays, return true if the words array is sorted according to the ordering array
    words = ['cc', 'cb', 'bb', 'ac'] ordering = ['c', 'b', 'a'] Output: True
    words = ['cc', 'cb', 'bb', 'ac'] ordering = ['b', 'c', 'a'] Output: False  */
  public static boolean checkIfSortedArray(String strs[], char orderings[]) {
    Map<Character, Integer> map = new HashMap();
    for (int i = 0; i < orderings.length; i++) {
      map.put(orderings[i], i);
    }
    for (int i = 1; i < strs.length; i++) {
      String word1 = strs[i - 1];
      String word2 = strs[i];
      for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
        if (word1.charAt(j) != word2.charAt(j)) {
          char from = word1.charAt(j);
          char to = word2.charAt(j);
          if (map.get(from) > map.get(to)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /* There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you.
    You receive a list of words from the dictionary, where words are sorted lexicographically by the rules of this new language.
    Derive the order of letters in this language. For example, Given the following words in dictionary,
    [ "wrt", "wrf", "er", "ett", "rftt" ] The correct order is: "wertf".*/
  // Time = O( N * M * no of Alphabet] where n is no of words and m is avg word length.
  static class Graph {

    List<Integer>[] adjacencyList;

    Graph(int nVertices) {
      adjacencyList = new ArrayList[nVertices];
      for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
        adjacencyList[vertexIndex] = new ArrayList<>();
      }
    }

    // function to add an edge to graph
    void addEdge(int startVertex, int endVertex) {
      adjacencyList[startVertex].add(endVertex);
    }

    private int getNoOfVertices() {
      return adjacencyList.length;
    }

    public static String alienOrder(String[] words, int noOfAlpha) {
      // Create a graph with 'aplha' edges
      Graph graph = new Graph(noOfAlpha);
      for (int i = 0; i < words.length - 1; i++) {
        // Take the current two words and find the first mismatching character
        String word1 = words[i];
        String word2 = words[i + 1];
        for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
          // If we find a mismatching character, then add an edge from character of word1 to that of word2
          if (word1.charAt(j) != word2.charAt(j)) {
            graph.addEdge(word1.charAt(j) - 'a', word2.charAt(j) - 'a');
            break;
          }
        }
      }

      // Print topological sort of the above created graph
      return graph.topologicalSort();
    }

    String topologicalSort() {
      Stack<Integer> stack = new Stack<>();
      Set visited = new HashSet();

      // Call the recursive helper function to store Topological Sort starting from all vertices one by one
      for (int i = 0; i < getNoOfVertices(); i++) {
        if (!visited.contains(i)) {
          topologicalSortUtil(i, visited, stack);
        }
      }
      StringBuilder output = new StringBuilder();
      while (!stack.isEmpty()) {
        output.append((char) ('a' + stack.pop()) + " ");
      }
      return output.reverse().toString();
    }

    // A recursive function used by topologicalSort
    private void topologicalSortUtil(int currentVertex, Set visited,
        Stack<Integer> stack) {
      // Mark the current node as visited.
      visited.add(currentVertex);

      // Recur for all the vertices adjacent to this vertex
      for (int adjacentVertex : adjacencyList[currentVertex]) {
        if (!visited.contains(adjacentVertex)) {
          topologicalSortUtil(adjacentVertex, visited, stack);
        }
      }
      stack.push(currentVertex);
    }
  }
    /* We are given a list of words that have both 'simple' and 'compound' words in them. Write an algorithm that prints
    out a list of words without the compound words that are made up of the simple words.
    Input: chat, ever, snapchat, snap, salesperson, per, person, sales, son, whatsoever, what so.
    Output should be: chat, ever, snap, per, sales, son, what, so
     */


  // decimal addition: Given two binary strings, return their sum (also a binary string).
  // For example, a = "11", b = "1", the return is "100".
  //Given k = 3, a = "12", b = "1", return "20".
  public static String addBinary(String a, String b, int base) {
    if (a == null || a.isEmpty()) {
      return b;
    }
    if (b == null || b.isEmpty()) {
      return a;
    }
    StringBuilder sb = new StringBuilder();
    int i = a.length() - 1;
    int j = b.length() - 1;
    int carry = 0;
    while (i >= 0 || j >= 0) {
      int sum = 0;
      if (i >= 0) {
        //sum++;
        sum += Character.getNumericValue(a.charAt(i));
        i--;
      }
      if (j >= 0) {
        //sum++;
        sum += Character.getNumericValue(b.charAt(j));
        j--;
      }
      sum += carry;
      if (sum >= base) {
        carry = 1;
      } else {
        carry = 0;
      }
      sb.append((char) ((sum % base) + '0'));
    }
    if (carry == 1) {
      sb.append('1');
    }
    return sb.reverse().toString();
  }

  //Given two big integers represented as strings, Multiplication them and return the production as string.
  //For example, given a=2343324 and b=232232 then return c = a*b = 23433242334323342 * 23223233232434324 = 544195652122144709711313995190808
  //Multiply two big numbers, Add two big numbers
  //O(nm)
  public static String multiply(String str1, String str2) {
    String output = new String("0");
    int count = 0;
    for (int i = str2.length() - 1; i >= 0; i--) {
      int d2 = str2.charAt(i) - '0';
      int carry = 0;
      StringBuffer prod = new StringBuffer();
      for (int j = str1.length() - 1; j >= 0; j--) {
        int d1 = str1.charAt(j) - '0';
        int p = carry + (d1 * d2);
        prod.append(p % 10);
        carry = p / 10;
      }
      if (carry != 0) {
        prod.append(carry);
      }
      prod.reverse();
      for (int k = 0; k < count; k++) {
        prod.append(0);
      }

      output = addBinary(output, prod.toString(), 10);
      count++;
    }

    return output;
  }


  //Letter Combinations of a Phone Number: Given a digit string, return all possible letter combinations that the number could represent.
  //A mapping of digit to letters (just like on the phone buttons) is given below.
  public static List<String> letterPhoneCombinations(String digits) {
    LinkedList<String> ans = new LinkedList<>();
    if (digits.isEmpty()) {
      return ans;
    }
    String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv",
        "wxyz"};
    ans.add("");
    while (ans.peek().length() != digits.length()) {
      String remove = ans.remove();
      String map = mapping[digits.charAt(remove.length()) - '0'];
      for (char c : map.toCharArray()) {
        ans.addLast(remove + c);
      }
    }
    return ans;
  }

  //Given a string with alpha-numeric characters and parentheses, return a string with balanced
  // parentheses by removing the fewest characters possible. You cannot add anything to the string.
  public static String removeUnbalanceParenthesis(String s) {
    StringBuilder str = new StringBuilder(s);
    int left = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        left++;
      } else if (str.charAt(i) == ')') {
        if (left > 0) {
          left--;
        } else {
          str.deleteCharAt(i--);
        }
      }
    }
    int right = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      if (str.charAt(i) == ')') {
        right++;
      } else if (str.charAt(i) == '(') {
        if (right > 0) {
          right--;
        } else {
          str.deleteCharAt(i);
        }
      }
    }
    return str.toString();
  }

  //Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
  //https://leetcode.com/problems/remove-invalid-parentheses/discuss/75027/Easy-Short-Concise-and-Fast-Java-DFS-3-ms-solution
  //Time: O(n), 2 pass
  public static String removeInvalidParentheses1(String s) {
    String r = remove(s, new char[]{'(', ')'});
    String tmp = remove(new StringBuilder(r).reverse().toString(), new char[]{')', '('});
    return new StringBuilder(tmp).reverse().toString();
  }

  private static String remove(String s, char[] p) {
    int stack = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == p[0]) {
        stack++;
      }
      if (s.charAt(i) == p[1]) {
        stack--;
      }
      if (stack < 0) {
        s = s.substring(0, i) + s.substring(i + 1);
        i--;
        stack = 0;
      }
    }
    return s;
  }

  //Integer to English Words: Write a function that takes an integer and prints out the English text of it.
  // if “1234” is given as input, output should be “one thousand two hundred thirty four”.
  private static final String[] one = {"", "one", "two", "three", "four", "five", "six",
      "seven", "eight", "nine", "ten",
      "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
      "nineteen"};
  private static final String[] ten = {"", "Ten", "twenty", "thirty", "forty", "fifty", "sixty",
      "seventy",
      "eighty", "ninety"};
  private static final String[] big = {"", "thousand", "million", "billion"};

  public static String numberToWords(int num) {
    if (num == 0) {
      return "Zero";
    }

    int i = 0;
    String output = "";

    while (num > 0) {
      if (num % 1000 != 0) {
        output = util(num % 1000) + big[i] + " " + output;
      }
      num /= 1000;
      i++;
    }

    return output.trim();
  }

  private static String util(int num) {
    if (num == 0) {
      return "";
    } else if (num < 20) {
      return one[num] + " ";
    } else if (num < 100) {
      return ten[num / 10] + " " + util(num % 10);
    } else {
      return one[num / 100] + " Hundred " + util(num % 100);
    }
  }

  //Given a string containing only digits, restore it by returning all possible valid IP address combinations.
  // Given "25525511135", return ["255.255.11.135", "255.255.111.35"] (Order does not matter)
  public static List<String> restoreIpAddresses(String s) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() == 0) {
      return res;
    }
    int len = s.length();
    if (len < 4 || len > 12) {
      return res;
    }
    for (int i = 1; i < 4; i++) {
      for (int j = i + 1; j < i + 4; j++) {
        for (int k = j + 1; k < j + 4 && k < len; k++) {
          String s1 = s.substring(0, i), s2 = s.substring(i, j), s3 = s.substring(j, k), s4 = s
              .substring(k, len);
          if (isValid1(s1) && isValid1(s2) && isValid1(s3) && isValid1(s4)) {
            res.add(s1 + "." + s2 + "." + s3 + "." + s4);
          }
        }
      }
    }
    return res;
  }

  private static boolean isValid1(String s) {
    if (s.length() > 1 && s.charAt(0) == '0' || Integer.parseInt(s) > 255) {
      return false;
    }
    return true;
  }

  /* Given a string that contains only digits from 0 to 9, and an integer value, target.
    Find out how many expressions are possible which evaluate to target using binary operator
    +, – and * in given string of digits.
    Input : "123",  Target : 6
    Output : {“1+2+3”, “1*2*3”} */
  public static List<String> addOperators(String num, int target) {
    List<String> res = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    dfs(res, sb, num.toCharArray(), 0, target, 0, 0);
    return res;
  }

  private static void dfs(List<String> res, StringBuilder sb, char[] num, int pos, int target,
      long prev, long multi) {
    if (pos == num.length) {
      if (target == prev) {
        res.add(sb.toString());
      }
      return;
    }
    long curr = 0;
    for (int i = pos; i < num.length; i++) {
      if (num[pos] == '0' && i != pos) {
        break;
      }
      curr = 10 * curr + num[i] - '0';
      int len = sb.length();
      if (pos == 0) {
        dfs(res, sb.append(curr), num, i + 1, target, curr, curr);
        sb.setLength(len);
      } else {
        dfs(res, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr);
        sb.setLength(len);
        dfs(res, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr);
        sb.setLength(len);
        dfs(res, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr,
            multi * curr);
        sb.setLength(len);
      }
    }
  }

  /*Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits.
    There is no limit on how many times a digit can be reused. You may assume the given input string
    is always valid. For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.*/
  public static String nextClosestTime(String time) {
    int hour = Integer.parseInt(time.substring(0, 2));
    int min = Integer.parseInt(time.substring(3, 5));
    while (true) {
      if (++min == 60) {
        min = 0;
        ++hour;
        hour %= 24;
      }
      String curr = String.format("%02d:%02d", hour, min);
      Boolean valid = true;
      for (int i = 0; i < curr.length(); ++i) {
        if (time.indexOf(curr.charAt(i)) < 0) {
          valid = false;
          break;
        }
      }
      if (valid) {
        return curr;
      }
    }
  }

  //wordPattern: Given a pattern and a string str, find if str follows the same pattern.
  //Input: pattern = "abba", str = "dog cat cat dog"
  //Output: true
  public static boolean wordPattern(String pattern, String str) {
    String[] words = str.split(" ");
    if (words.length != pattern.length()) {
      return false;
    }
    Map<Character, String> map = new HashMap<>();
    for (int i = 0; i < pattern.length(); i++) {
      char key = pattern.charAt(i);
      String word = words[i];
      if (map.containsKey(key) && !map.get(key).equals(word)) {
        return false;
      }
      if (!map.containsKey(key) && map.containsValue(word)) {
        return false;
      }
      map.put(key, word);
    }
    return true;
  }

  //wordPattern II: Given a pattern and a string str, find if str follows the same pattern.
  //pattern = "abab", str = "redblueredblue" should return true.
  static boolean wordPatternMatch(String pattern, String str) {
    Map<Character, String> map = new HashMap<>();
    Set<String> set = new HashSet<>();
    return isMatch(str, 0, pattern, 0, map, set);
  }

  static boolean isMatch(String str, int strIndex, String pat, int patIndex, Map<Character, String> map,
      Set<String> set) {
    // base case
    if (strIndex == str.length() && patIndex == pat.length()) {
      return true;
    }
    if (strIndex == str.length() || patIndex == pat.length()) {
      return false;
    }
    // get current pattern character
    char patChar = pat.charAt(patIndex);
    // if the pattern character exists
    if (map.containsKey(patChar)) {
      String s = map.get(patChar);
      // then check if we can use it to match str[i...i+s.length()]
      if (!str.startsWith(s, strIndex)) {
        return false;
      }
      // if it can match, great, continue to match the rest
      return isMatch(str, strIndex + s.length(), pat, patIndex + 1, map, set);
    }
    // pattern character does not exist in the map
    for (int k = strIndex; k < str.length(); k++) {
      String strMatch = str.substring(strIndex, k + 1);
      if (set.contains(strMatch)) {
        continue;
      }
      // create or update it
      map.put(patChar, strMatch);
      set.add(strMatch);
      // continue to match the rest
      if (isMatch(str, k + 1, pat, patIndex + 1, map, set)) {
        return true;
      }
      // backtracking
      map.remove(patChar);
      set.remove(strMatch);
    }
    // we've tried our best but still no luck
    return false;
  }

  //Given a non-negative integer num represented as a string, remove k digits from the number so
  //that the new number is the smallest possible.
  //Input: num = "1432219", k = 3  Output: "1219"  "10200", k = 1 Output: "200"
  public String removeKdigits(String num, int k) {
    int digits = num.length() - k;
    char[] stk = new char[num.length()];
    int top = 0;
    // k keeps track of how many characters we can remove
    // if the previous character in stk is larger than the current one
    // then removing it will get a smaller number
    // but we can only do so when k is larger than 0
    for (int i = 0; i < num.length(); ++i) {
      char c = num.charAt(i);
      while (top > 0 && stk[top - 1] > c && k > 0) {
        top -= 1;
        k -= 1;
      }
      stk[top++] = c;
    }
    // find the index of first non-zero digit
    int idx = 0;
    while (idx < digits && stk[idx] == '0') {
      idx++;
    }
    return idx == digits ? "0" : new String(stk, idx, digits - idx);
  }

  /* Given an array with ['a1', 'a2', .....'aN', 'b1', 'b2', ....'bN', 'c1', 'c2', .....'cN'],
  stagger the sub-arrays so it becomes ['a1', 'b1', 'c1', 'a2', 'b2', 'c2', ...'aN', 'bN', 'cN']. The optimal solution requires linear-time
  sorting and a constant space complexity.  */
  private static void sortSpecialArrayUtil(String[] array) {
    int N = getNumberOfChar(array[array.length - 1]);
    int charCount = array.length / N;
    int lastIndex = array.length - 2;
    String element;

    for (int i = 1; i <= lastIndex; i++) {
      element = array[i];
      swap(array, i, getSwapIndex(charCount, element));
    }
  }

  private static int getNumberOfChar(String str) {
    return Integer.parseInt(str.substring(1, str.length()));
  }

  private static int getSwapIndex(int charCount, String element) {
    char c = Character.toLowerCase(element.charAt(0));
    int num = getNumberOfChar(element);
    // the correct index is found by the following formula: index = charDistance + (charCount * (num - 1))
    int charDistance = c - 'a';
    return charDistance + (charCount * (num - 1));
  }

  // JSON Print: pretty print JSON
  static void printJSON(String str) {
    char tokens[] = str.toCharArray();
    String space = "";
    Stack<String> stack = new Stack<>();
    for (int i = 0; i < tokens.length; i++) {
      char temp = tokens[i];
      if (temp == '{') {
        space = getTab(stack.size());
        System.out.print("\n");
        System.out.print(space + "{");
        System.out.print("\n" + space);
        stack.push("" + temp);
      } else if (temp == '}') {
        stack.pop();
        space = getTab(stack.size());
        System.out.print("\n");
        System.out.print(space + "}");
      } else if (temp == '[') {
        space = getTab(stack.size());
        System.out.print("\n");
        System.out.print(space + "[");
        //System.out.print("\n");
        stack.push("" + temp);
      } else if (temp == ']') {
        stack.pop();
        space = getTab(stack.size());
        System.out.print("\n");
        System.out.print(space + "]");
      } else if (temp == ',') {
        System.out.print("\n" + space);
      } else {
        System.out.print(String.valueOf(temp).trim());
      }
    }
  }

  public static String getTab(int N) {
    StringBuffer sbf = new StringBuffer();
    IntStream.range(0, N).forEach(i -> sbf.append("\t"));
    return sbf.toString();
  }

  /* Read N Characters Given Read4 */
  static int read4(char[] buf) {
    return -1;
  }

  static int read(char[] buf, int n) {
    boolean eof = false;      // end of file flag
    int total = 0;            // total bytes have read
    char[] tmp = new char[4]; // temp buffer

    while (!eof && total < n) {
      int count = read4(tmp);

      // check if it's the end of the file
      eof = count < 4;

      // get the actual count
      count = Math.min(count, n - total);

      // copy from temp buffer to buf
      for (int i = 0; i < count; i++) {
        buf[total++] = tmp[i];
      }
    }

    return total;
  }

  // Read N Characters Given Read4 II - Call multiple times
  //http://buttercola.blogspot.com/2014/11/leetcode-read-n-characters-given-read4_23.html
  private char[] buffer = new char[4];
  private int offset = 0;
  private int charactersInBuffer = 0;
  public int readII(char[] buf, int n) {
    int totalCharactersRead = 0;
    boolean eof = false;
    while (!eof && totalCharactersRead < n) {
      if (charactersInBuffer == 0) {
        charactersInBuffer = read4(buffer);
        eof = charactersInBuffer < 4;
      }
      int numCharactersUsed = Math.min(charactersInBuffer, n - totalCharactersRead);
      for (int i = 0; i < numCharactersUsed; i++) {
        buf[totalCharactersRead + i] = buffer[offset + i];
      }
      totalCharactersRead += numCharactersUsed;
      charactersInBuffer -= numCharactersUsed;
      offset = (offset + numCharactersUsed) % 4;
    }
    return totalCharactersRead;
  }


}

