package code.ds;
import java.util.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Piyush Patel.
 */
public class StringImp {
   /* Compress a given string. Input: aaaaabbccc  Output: a5b2c3    */
    static void compressString(String s) {
        char[] string = s.toCharArray();
        if (string.length == 0) return;
        char first = string[0];
        int count = 0;
        for (int i = 0; i < string.length; i++) {
            if (first != string[i]) {
                System.out.printf("%c%d", first, count);
                count = 1;
                first = string[i];
            } else {
                count++;
            }
        }
        System.out.printf("%c%d", first, count);
    }
    public static String encode(String source) {
        StringBuffer dest = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            int runLength = 1;
            while (i+1 < source.length() && source.charAt(i) == source.charAt(i+1)) {
                runLength++;
                i++;
            }
            if(runLength >=3)
                dest.append(runLength);
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
    //Finds first non repeated character in a String in just one pass. more space but in one pass
    private char firstNonRepeatingChar(String word) {
        Set<Character> repeating = new HashSet<Character>();
        List<Character> nonrepeating = new ArrayList<Character>();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (repeating.contains(letter)) continue;
            if (nonrepeating.contains(letter)) {
                nonrepeating.remove(letter);
                repeating.add(letter);
            } else {
                nonrepeating.add(letter);
            }
        }
        return nonrepeating.get(0);
    }
    //Return maximum occurring character in the input string
    public static String getMaxRepeatedChar(String txt) {
        if ((txt != null)) {
            HashMap<Character, Integer> hash = new HashMap<Character, Integer>();
            char maxCh = 1;
            int maxCnt = 0;
            for (char ch : txt.toCharArray()) {
                if (hash.containsKey(ch)) {
                    int i = hash.get(ch);
                    hash.put(ch, i + 1);
                    if (maxCnt < (i + 1)) {
                        maxCh = ch;
                        maxCnt = 1 + i;
                    }
                } else {
                    hash.put(ch, 1);
                    if (maxCnt < 1) {
                        maxCh = ch;
                        maxCnt = 1;
                    }
                }
            }
            return "Most Repeated character : " + maxCh + " and Count : " + maxCnt;
        }
        return null;
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
            case '(': return closing == ')';
            case '{': return closing == '}';
            case '[': return closing == ']';
            default: return false;
        }
    }
    //Given two strings str1 and str2 and below operations that can performed on str1. Find minimum number of edits
    //(operations) required to convert ‘str1′ into ‘str2′. Edit Distance problem/Levenshtein distance
    //Input:   str1 = "geek", str2 = "gesek"     Output:  1
    //We can convert str1 into str2 by inserting a 's'.
    //Time Complexity: O(m x n)     Auxiliary Space: O(m x n)
    int editDistDP(String str1, String str2, int m, int n) {
        // Create a table to store results of subproblems
        int dp[][] = new int[m+1][n+1];
        if (n == 0){
            return m;
        }
        if (m == 0){
            return n;
        }
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;
        // d[i][j] = edit distance between word1[0..(i-1)] and word2[0..(j-1)]
        for (int i=1; i<=m; i++){
            for (int j=1; j<=n; j++){
                // If last characters are same, ignore last char and recur for remaining string
                if (str1.charAt(i-1) == str2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1];
                    //If last characters are not same, consider all three operations on last character of first string,
                    //recursively compute minimum cost for all three operations and take minimum of three values.
                else
                    dp[i][j] = 1 + Math.min(Math.min(dp[i][j-1],  // Insert
                                    dp[i-1][j]),  // Remove
                            dp[i-1][j-1]); // Replace
            }
        }
        return dp[m][n];
    }
    //Given two string s1 and s2, find if s1 can be converted to s2 with exactly one edit.
    //Time = O(m + n) space =O(1)
    boolean isEditDistanceOne(String s1, String s2){
        // Find lengths of given strings
        int m = s1.length(), n = s2.length();
        // If difference between lengths is more than 1, then strings can't be at one distance
        if (Math.abs(m - n) > 1)
            return false;
        int count = 0; // Count of edits
        int i = 0, j = 0;
        while (i < m && j < n){
            // If current characters don't match
            if (s1.charAt(i) != s2.charAt(j)){
                if (count == 1)
                    return false;
                // If length of one string is more, then only possible edit is to remove a character
                if (m > n)
                    i++;
                else if (m< n)
                    j++;
                else {//If lengths of both strings is same
                    i++;
                    j++;
                }
                // Increment count of edits
                count++;
            }
            else {// If current characters match
                i++;
                j++;
            }
        }
        // If last character is extra in any string
        if (i < m || j < n)
            count++;
        return count == 1;
    }
    //Given a dictionary as a hashtable and a word. Find the minimum # of deletions needed on the word in order to make it a valid word in the dictionary.
    static int numberofMinDeletion(String word, HashSet<String> dic){
        int mindelete = word.length();
        for (String item : dic){
            if (word == item)
                return 0;
            else if (item.length() >= word.length())
                continue;
            else{
                mindelete = Math.min(mindelete, minDeletionToTransformWord(word, item));
            }
        }
        if (mindelete == word.length())
            return -1;
        return mindelete;
    }
    static int minDeletionToTransformWord(String s1, String s2){
        // Find lengths of given strings
        int m = s1.length(), n = s2.length();
        int count = 0; // Count of edits
        int i = 0, j = 0;
        while (i < m && j < n){
            // If current characters don't match
            if (s1.charAt(i) != s2.charAt(j)){
                // If length of one string is more, then only possible edit is to remove a character
                if (m > n)
                    i++;
                count++;
            }
            else {// If current characters match
                i++;
                j++;
            }
        }
        // If last character is extra in word string
        while (i < m){
            count++;
            i++;
        }
        if (j < n)
            return m;
        return count;
    }

    //Given an input string and a dictionary of words, find out if the input string can be segmented into a
    //space-separated sequence of dictionary words.
    //Consider the following dictionary { i, like, sam, sung, samsung, mobile, ice,cream, icecream, man, go, mango}
    //Input:  ilike  Output: Yes The string can be segmented as "i like".
    Boolean wordBreak(String str){
        int size = str.length();
        if (size == 0)   return true;
        // Create the DP table to store results of subroblems. The value wb[i] will be true if str[0..i-1]
        // can be segmented into dictionary words, otherwise false.
        boolean[] wb = new boolean[size+1];
        wb[0] = false; // Initialize all values as false.
        for (int i=1; i<=size; i++){
            // if wb[i] is false, then check if current prefix can make it true.
            // Current prefix is "str.substr(0, i)"
            if (wb[i] == false && dictionaryContains( str.substring(0, i) ))
                wb[i] = true;
            // wb[i] is true, then check for all substrings starting from (i+1)th character and store their results.
            if (wb[i] == true){
                // If we reached the last prefix
                if (i == size)
                    return true;
                for (int j = i+1; j <= size; j++){
                    //Update wb[j] if it is false and can be updated Note the parameter passed to dictionaryContains() is
                    //substring starting from index 'i' and length 'j-i'
                    if (wb[j] == false && dictionaryContains( str.substring(i, j-i) ))
                        wb[j] = true;
                    //If we reached the last character
                    if (j == size && wb[j] == true)
                        return true;
                }
            }
        }
        // If we have tried all prefixes and none of them worked
        return false;
    }
    /*A utility function to check whether a word is present in dictionary or not.An array of strings is used for
     dictionary.Using array of strings for dictionary is definitely not a good idea. We have used for simplicity of
    the program*/
    Boolean dictionaryContains(String word){
        String dictionary[] = {"mobile","samsung","sam","sung","man","mango","icecream","and","go","i","like","ice","cream"};
        for (int i = 0; i < dictionary.length; i++)
            if (dictionary[i].compareTo(word) == 0)
                return true;
        return false;
    }
    //Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a
    //valid dictionary word. Return all such possible sentences.
    //s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"], the solution is ["cats and dog", "cat sand dog"].
    void wordBreakUtil(String str, int size, String result){
        //Process all prefixes one by one
        for (int i=1; i<=size; i++){
            //extract substring from 0 to i in prefix
            String prefix = str.substring(0, i);
            // if dictionary conatins this prefix, then we check for remaining string. Otherwise we ignore this prefix
            // (there is no else for this if) and try next
            if (dictionaryContains(prefix)){
                // if no more elements are there, print it
                if (i == size){
                    // add this element to previous prefix
                    result += prefix;
                    System.out.println(result);
                    return;
                }
                wordBreakUtil(str.substring(i, size-i), size-i, result+prefix+" ");
            }
        }
    }
    //Given two sequences, find the length of longest common subsequence present in both of them.
    // Time  O(mn)
    int lcs( char []X, char []Y, int m, int n ){
        int L[][] = new int [m+1][n+1];
        int i, j;
   /* Following steps build L[m+1][n+1] in bottom up fashion. Note
      that L[i][j] contains length of LCS of X[0..i-1] and Y[0..j-1] */
        for (i=0; i<=m; i++){
            for (j=0; j<=n; j++){
                if (i == 0 || j == 0)
                    L[i][j] = 0;
                else if (X[i-1] == Y[j-1])
                    L[i][j] = L[i-1][j-1] + 1;
                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]);
            }
        }
   /* L[m][n] contains length of LCS for X[0..n-1] and Y[0..m-1] */
        return L[m][n];
    }
    //Given set of characters and a string, find smallest substring which contains all characters
    //Input string1: “this is a test string” string2: “tist” Output string: “t stri”
    public String minSubString(String S, String T) {
        if (S == null || T == null) {
            return null;
        }
        if (S.length() == 0 && T.length() == 0) {
            return "";
        }
        if (S.length() < T.length()) {
            return "";
        }
        HashMap<Character, Integer> needFind = new HashMap<Character, Integer>();
        HashMap<Character, Integer> alreadyFind = new HashMap<Character, Integer>();
        for (int i = 0; i < T.length(); i++) {
            alreadyFind.put(T.charAt(i), 0);
            if (needFind.containsKey(T.charAt(i))) {
                needFind.put(T.charAt(i), needFind.get(T.charAt(i)) + 1);
            } else {
                needFind.put(T.charAt(i), 1);
            }
        }
        int minStart = -1;
        int minEnd = S.length();
        int start = 0;
        int len = 0;
        for (int i = 0; i < S.length(); i++) {
            if (alreadyFind.containsKey(S.charAt(i))) {
                alreadyFind.put(S.charAt(i), alreadyFind.get(S.charAt(i)) + 1);
                if (alreadyFind.get(S.charAt(i)) <= needFind.get(S.charAt(i))) {
                    len++;
                }
                if (len == T.length()) {
                    while (!needFind.containsKey(S.charAt(start)) || alreadyFind.get(S.charAt(start)) > needFind.get(S.charAt(start))) {
                        if (needFind.containsKey(S.charAt(start))) {
                            alreadyFind.put(S.charAt(start), alreadyFind.get(S.charAt(start)) - 1);
                        }
                        start++;
                    }
                    if (i - start < minEnd - minStart) {
                        minStart = start;
                        minEnd = i;
                    }
                }
            }
        }
        if (minStart == -1) {
            return "";
        }
        return S.substring(minStart, minEnd + 1);
    }
    /* Given a string, find the length of the longest substring without repeating characters.
       For example, the longest substring without repeating letters for “abcabcbb” is “abc” */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> uniqueSet = new HashSet<Character>();
        int maxSize = 0;
        int start = 0;
        for(int i = 0; i < s.length(); i++) {
            if(!uniqueSet.contains(s.charAt(i))) {
                uniqueSet.add(s.charAt(i));
                if(uniqueSet.size() > maxSize) {
                    maxSize = uniqueSet.size();
                }
            } else {
                while (s.charAt(start) != s.charAt(i)) {
                    uniqueSet.remove(s.charAt(start));
                    start++;
                }
                start++;
            }
        }
        return maxSize;
    }
    //Given a string, find the length of the longest substring T that contains at most k distinct characters.
    //For example, Given s = “eceba” and k = 2,
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0 || s.length() == 0) {
            return 0;
        }
        int[] ascii = new int[256];
        int count = 0;
        int start = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            if (count < k) {
                if (ascii[ch] == 0) {
                    count++;
                }
            } else if (ascii[ch] == 0){
                while (start < i) {
                    char ch1 = s.charAt(start);
                    ascii[ch1]--;
                    if (ascii[ch1] == 0) {
                        break;
                    }
                    start++;
                }
            }
            ascii[ch]++;
            max = Math.max(max, i - start + 1);
        }
        return max;
    }

    //Find all the repeating sub-string sequence of specified length in a large string sequence.
    //The sequences returned i.e. the output must be sorted alphabetically
    //Input String: "ABCACBABC" repeated sub-string length: 3 Output: ABC
    //Input String: "ABCABCA" repeated sub-string length: 2 Output: AB, BC, CA
    public static void lrs(String s, int sequenceLength) {
        // form the N suffixes
        int N  = s.length();
        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++) {
            suffixes[i] = s.substring(i, N);
        }
        // sort them
        Arrays.sort(suffixes);
        // find longest repeated substring by comparing adjacent sorted suffixes
        String lrs = "";
        for (int i = 0; i < N - 1; i++) {
            String x = lrsUtil(suffixes[i], suffixes[i+1], sequenceLength);
            if (x.length() == sequenceLength)
                System.out.println(x);
        }
    }
    public static String lrsUtil(String s, String t, int sequenceLength) {
        int n = Math.min(s.length(), t.length());
        if(n >= sequenceLength) {
            if (s.substring(0, sequenceLength) == t.substring(0, sequenceLength))
                return s.substring(0, sequenceLength);
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
                while (i < target.length() - 1 && (target.charAt(i + 1) == curr || target.charAt(i + 1) == ' ' || target.charAt(i + 1) == '.')) {
                    i++;
                }
            } else {
                prev = curr;
            }
        }
        return rpt;
    }
    //Remove duplicate characters in a given string keeping only the first occurrences.
    private String removeDuplicate(String s) {
        if (s == null) return null;
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
    public static void RemoveDuplicates(char[] str){
        int check = 0;
        for(int i = 0; i < str.length; i++){
            int val = str[i] - 'a';
            if((check & (1 << val)) > 0){
                str[i] = '\0';
                continue;
            }
            check = check | (1 << val);
        }
        for(int j = 0; j < str.length; j++){
            if(str[j] == '\0')
                continue;
            else
                System.out.print(str[j]);
        }
    }
    //Permutations of the string
    void permute(String str) {
        int length = str.length();
        boolean[] used = new boolean[length];
        StringBuffer out = new StringBuffer();
        char[] in = str.toCharArray();
        doPermute(in, out, used, length, 0);
    }
    void doPermute(char[] in, StringBuffer out, boolean[] used, int length, int level) {
        if (level == length) {
            System.out.println(out.toString());
            return;
        }
        for (int i = 0; i < length; ++i) {
            if (used[i]) continue;
            out.append(in[i]);
            used[i] = true;
            doPermute(in, out, used, length, level + 1);
            used[i] = false;
            out.setLength(out.length() - 1);
        }
    }
    //combination of the string
    void combine(String str) {
        int length = str.length();
        char[] instr = str.toCharArray();
        StringBuilder outstr = new StringBuilder();
        doCombine(instr, outstr, length, 0, 0);
    }
    void doCombine(char[] instr, StringBuilder outstr, int length, int level, int start) {
        for (int i = start; i < length; i++) {
            outstr.append(instr[i]);
            System.out.println(outstr);
            if (i < length - 1) {
                doCombine(instr, outstr, length, level + 1, i + 1);
            }
            outstr.setLength(outstr.length() - 1);
        }
    }
    //Given two (dictionary) words as Strings, determine if they are isomorphic. given "foo", "app"; returns true
    //given "turtle", "tletur"; returns true
    //Ordered only i.e. ofo won't map to app encoding would be 010 and 011
    public static boolean isIsomorphic(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        else if (s1.length() == 1) return true;
        else {
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
    public void WordDistanceFinder (List<String> words) {
        for (int i = 0; i <words.size() ; i++) {
            if(!_map.containsKey(words.get(i))) {
                _map.put(words.get(i), new LinkedList<Integer>());
            }
            _map.get(words.get(i)).add(i);
        }
    }
    public int distance (String wordOne, String wordTwo) {
        if(!_map.containsKey(wordOne) || !_map.containsKey(wordTwo))
            return -1;
        if(wordOne.equals(wordTwo))
            return 0;
        int _minDistance = Integer.MAX_VALUE;
        for(int i : _map.get(wordOne)){
            for(int j : _map.get(wordTwo)){
                _minDistance = Math.min(_minDistance,Math.abs(i-j));
            }
        }
        return _minDistance;
    }
    // Check whether two strings are anagram  or not
    // For example, “abcd” and “dabc” are anagram of each other.
    private boolean areAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
        for (int i = 0; i < s1.length(); i++) {
            Character ch = s1.charAt(i);
            Integer count = (Integer) counter.get(ch);
            if (count == null) counter.put(ch, 1);
            else {
                counter.put(ch, count + 1);
            }
        }
        for (int i = 0; i < s2.length(); i++) {
            Character ch = s2.charAt(i);
            Integer count = (Integer) counter.get(ch);
            if (count == null || count == 0) return false;
            else {
                counter.put(ch, count - 1);
            }
        }
        return true;
    }
    //Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function search(char pat[], char txt[]) that prints
    //all occurrences of pat[] and its permutations (or anagrams) in txt[].
    //Find if the given string contains an anagram of another smaller string.
    public static List<Integer> anagramsMatch(String s, String p) {
        List<Integer> list = new ArrayList<Integer>();
        int[] count = new int[256];
        for (char c : p.toCharArray())
            count[c]++;
        int[] tc = new int[256];
        for (int i = 0; i < p.length(); i++) {
            tc[s.charAt(i)]++;
        }
        if (matchCount(count, tc))
            list.add(0);
        for (int i = p.length(); i < s.length(); i++) {
            tc[s.charAt(i - p.length())]--;
            tc[s.charAt(i)]++;
            if (matchCount(count, tc))
                list.add(i - p.length() + 1);
        }
        for (int num : list)
            System.out.print("Found at Index " + num);
        return list;
    }
    private static boolean matchCount(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                return false;
        return true;
    }
    //Given an array of words, print all anagrams together. For example, if the given array is
    // {“cat”, “dog”, “tac”, “god”, “act”}, then output may be “cat tac act dog god”
    void printAnagramsUtil(List<String> input){
        HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for (int i = 0; i <input.size() ; i++) {
            char[] content = input.get(i).toCharArray();
            Arrays.sort(content);
            String key = new String(content);
            if (!map.containsKey(key)) {
                map.put(key, new LinkedList<Integer>());
            }
            map.get(key).add(i);
        }
        for (String s : map.keySet())
            for (Integer i: map.get(s))
                System.out.println(input.get(i));
    }
    //reverse the string
    private static String ReverseString(String str){
        char[] inputstream = str.toCharArray();
        int length = str.length() - 1;
        for (int i =0; i < length; i++, length--)
        {
            inputstream[i] ^= inputstream[length];
            inputstream[length] ^= inputstream[i];
            inputstream[i] ^= inputstream[length];
        }
        return new String(inputstream);
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
    //Revesre words in a string
    public static char[] reverseWords() {
        // reverse the string
        char[] arr = "welcome to coding algorithms".toCharArray();
        reverse(arr, 0, arr.length / 2, arr.length);
        // reverse words of a string
        int wordIdx = 0;
        int wordMidIdx = 0;
        int prevWordLastIdx = 0;
        // outer loop to track spaces
        for (int sentenceIdx = 0; sentenceIdx < arr.length; sentenceIdx++) {
            if (arr[sentenceIdx] != ' ')
                continue;
            wordIdx = prevWordLastIdx;
            int wordLastIdx = sentenceIdx;
            wordMidIdx = (sentenceIdx + wordIdx) / 2;
            // reverse each word in a string
            reverse(arr, wordIdx, wordMidIdx, wordLastIdx);
            prevWordLastIdx = sentenceIdx + 1;
        }
        // reverse last word
        wordIdx = prevWordLastIdx;
        wordMidIdx = (arr.length + wordIdx) / 2;
        reverse(arr, wordIdx, wordMidIdx, arr.length);
        return arr;
    }
    private static void reverse(char[] arr, int wordIdx, int wordMidIdx,
                                int wordLastIdx) {
        for (; wordIdx < wordMidIdx; wordIdx++) {
            // swap first letter with the last letter in the
            char tmp = arr[wordIdx];
            arr[wordIdx] = arr[wordLastIdx - 1];
            arr[wordLastIdx - 1] = tmp;
            wordLastIdx--;
        }
    }
    //find count of common characters presented in an array of strings or array of character arrays
    private void CountOfCommonCharacters(String s, String S1, String S2) {
        char[] _chars = s.toCharArray();
        Set<String> _set = new HashSet<String>();
        for (int i = 0; i < _chars.length; i++) {
            char c = _chars[i];
            if (S1.indexOf(c) != -1 && S2.indexOf(c) != -1) _set.add(String.valueOf(c));
        }
        for (String string : _set)
            System.out.println(string);
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
        while(l <= r) {
            int m = l + (r-l) / 2;
            if(str.charAt(m) > c) {
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
    //implement a function to find if a given string is a palindrome
    boolean isPalindrome(String s) {
        int n = s.length();
        for (int i=0;i<(n / 2) + 1;++i) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }
    //Given a string S, find the longest palindromic substring in S. O(NlogN) time and O(N) space
    //Better algorithm in linear time Manacher’s Algorithm
    public static String LongestPalindromeImprove(String s) {
        s +="^" + ReverseString(s);
        int N  = s.length();
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
            int x = lcp(suffixes[i], suffixes[i+1]);
            String key = suffixes[i].substring(0,x);
            if (!map.containsKey(key))
                map.put(key,x);
        }
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
    // longest common prefix of s and t
    private static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return N;
    }
    //A Program to check if strings are rotations of each other or not
    //given s1 = ABCD and s2 = CDAB, return true
    boolean areRotations(String s1, String s2) {
        String temp = s1 + s1;
        if (temp.contains(s2)) return true;
        return false;
    }
    //Output top N positive integer in string comparison order.
    // For example, let's say N=1000, output should be 1, 10, 100, 1000, 101, 102, ... 109, 11, 110,
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++)
            printRec("" + i, 1000);
    }
    static void printRec(String str, int n) {
        if (Integer.parseInt(str) > n) return;
        System.out.println(str);
        for (int i = 0; i < 10; i++)
            printRec(str + i, n);
    }
    //Find the longest sequence of prefix shared by all the words in a string.
    //"abcdef abcdxxx abcdabcdef abcyy" => "abc"
    public String longPrefix(String str){
        String arr[] = str.split(" ");
        int len = arr[0].length();
        int p;
        for(int i =1; i < arr.length;i++){
            p=0;
            while(p < len && p < arr[i].length()
                    && arr[0].charAt(p) == arr[i].charAt(p))
                p++;
            len = p;
        }
        return arr[0].substring(0,len);
    }
    /* Write a program to determine whether an input string str1 is a substring of another input string str2.*/
    //Given two strings, find if first string is a subsequence of second
    //Input: str1 = "AXY", str2 = "ADXCPY"  Output: True (str1 is a subsequence of str2)
    // Returns true if str1[] is a subsequence of str2[]. m is length of str1 and n is length of str2
    boolean isSubSequence(char str1[], char str2[], int m, int n){
        int j = 0; // For index of str1 (or subsequence
        // Traverse str2 and str1, and compare current character
        // of str2 with first unmatched char of str1, if matched
        // then move ahead in str1
        for (int i=0; i<n&&j<m; i++)
            if (str1[j] == str2[i])
                j++;
        // If all characters of str1 were found in str2
        return (j==m);
    }
    //Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function to search the index of subString
    //Naive Pattern Searching. Best case O(n) worst case  O(m*(n-m+1))
    //Better approach is KMP (Knuth Morris Pratt) Pattern Searching
    void search(String pat, String txt){
        int M = pat.length();
        int N = txt.length();
        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++){
            int j;
        /* For current index i, check for pattern match */
            for (j = 0; j < M; j++){
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == M)  // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
               System.out.print("Pattern found at index " + i);
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
    void find10FrequentWords(String[] s, int k) {
        HashMap<String, Integer> _hash = new HashMap<String, Integer>();
        PriorityQueue<Integer> _minHeap = new PriorityQueue<Integer>();
        for (int i = 0; i < s.length; i++) {
            if (_hash.containsKey(s[i])) {
                int count = _hash.get(s[i]);
                _hash.put(s[i], count + 1);
            } else _hash.put(s[i], 1);
        }
        int count = 0;
        for (Map.Entry<String, Integer> entry : _hash.entrySet()) {
            if(count < k) {
                _minHeap.add(entry.getValue());
                count++;
            }
            else if(entry.getValue() > _minHeap.peek()){
                _minHeap.poll();
                _minHeap.add(entry.getValue());
            }
        }
        while (!_minHeap.isEmpty()) {
            System.out.println(_hash.get(_minHeap.poll()));
        }
    }
    //Find if a given string can be represented from a substring by iterating the substring “n” times
    //Input: str = "abcabcabc"  Output: true The given string is 3 times repetition of "abc"
    // Returns true if str is repetition of one of its sub Strings else return false.
    Boolean isRepeat(char str[]){
        // Find length of string and create an array to store lps values used in KMP
        int n = str.length;
        int [] lps = new int[n];
        // Pre-process the pattern (calculate lps[] array)
        computeLPSArray(str, n, lps);
        // Find length of longest suffix which is also prefix of str.
        int len = lps[n-1];
        // If there exist a suffix which is also prefix AND
        // Length of the remaining substring divides total
        // length, then str[0..n-len-1] is the substring that
        // repeats n/(n-len) times (Readers can print substring
        // and value of n/(n-len) for more clarity.
        return (len > 0 && n%(n-len) == 0)? true: false;
    }
    // A utility function to fill lps[] or compute prefix funcrion
    // used in KMP string matching algorithm.
    void computeLPSArray(char str[], int M, int lps[]){
        int len = 0; //length of the previous longest prefix suffix
        int i;
        lps[0] = 0; //lps[0] is always 0
        i = 1;
        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M){
            if (str[i] == str[len]){
                len++;
                lps[i] = len;
                i++;
            }
            else {// (pat[i] != pat[len])
                if (len != 0){
                    // This is tricky. Consider the example AAACAAAA and i = 7.
                    len = lps[len-1];
                    // Also, note that we do not increment i here
                }
                else{ // if (len == 0)
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }
    /*CSV Parser Specifications: Separator: , New Line: \r Quote: " (inside the quote we can have any charater)
    Input: hello world,"b,c\n",Piyush Patel\nfoo,bar,bax
    Output:[[hello world],["b,c\n"],[Piyush Patel],[foo,bar,bax]]*/
    public List<String> decodeCSV(String csvString) {
        List<String> individualValues = new ArrayList<String>();
        char[] arr = csvString.toCharArray();
        boolean inQuotes = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            char value = arr[i];
            if (inQuotes) {
                if (value == '"') {
                    if (i == arr.length - 1) {
                        individualValues.add(sb.toString());
                        return individualValues;
                    } else if (arr[i + 1] == '"') {
                        sb.append('"');
                        i++;
                    } else {
                        individualValues.add(sb.toString());
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
                individualValues.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(value);
            }
        }
        individualValues.add(sb.toString());
        return individualValues;
    }

    /*Given a regular expression with characters a-z, ' * ', ' . '
   the task was to find if that string could match another string with characters from: a-z
   where ' * ' can delete the character before it, and ' . ' could match whatever character.
   ' * ' always appear after a a-z character. */
    Boolean isMatch(String sA, String sB, int iALength, int iBLength) {
        if (iALength == 0 && iBLength == 0) return true;
        else {
            if (iALength > 0) {
                if (sA.charAt(iALength - 1) == '*')
                    return isMatch(sA, sB, iALength - 1, iBLength) || isMatch(sA, sB, iALength - 2, iBLength);
                if (sA.charAt(iALength - 1) == '.')
                    return isMatch(sA, sB, iALength - 1, iBLength) || isMatch(sA, sB, iALength - 1, iBLength - 1);
                if (iBLength > 0) {
                    if (sA.charAt(iALength - 1) == sB.charAt(iBLength - 1)) return true;
                    else return false;
                }
            }
        }
        return false;
    }
    /* Implement regular expression matching with support for '.' and '*'.
    '.' Matches any single character.
    '*' Matches zero or more of the preceding element. */
    public boolean isMatch(String s, String p) {
        if (s == null) return p == null;
        if (p == null) return s == null;
        int lenS = s.length();
        int lenP = p.length();
        if (lenP == 0) return lenS == 0;
        if (lenP == 1) {
            if (p.equals(s) || p.equals(".") && s.length() == 1) {
                return true;
            } else return false;
        }
        if (p.charAt(1) != '*') {
            if (s.length() > 0 && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                return isMatch(s.substring(1), p.substring(1));
            }
            return false;
        } else {
            while (s.length() > 0 && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                if (isMatch(s, p.substring(2))) return true;
                s = s.substring(1);
            }
            return isMatch(s, p.substring(2));
        }
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
            while (i + 1 < splitted.length - 1 && (temp + 1 + splitted[i + 1]).length() <= charLimit) {  //+1 for space
                temp = temp + " " + splitted[i + 1];
                i++;
            }
            result.add(temp);
        }
        //Take care of the last element
        //Add the last element from splitted to the last element of result if their combined length is less than charLimit
        String lastElement = result.get(result.size() - 1);
        if (lastElement.length() + 1 + splitted[splitted.length - 1].length() < charLimit) {  //+1 for space
            result.set(result.size() - 1, lastElement + " " + splitted[splitted.length - 1]);
        } else {
            result.add(splitted[splitted.length - 1]);
        }
        //append message chunk number for ex (1/3)
        int resultSize = result.size();
        for(int i = 0; i < resultSize; i++) {
            result.set(i, result.get(i) +"("+ (i+1) + "/" + resultSize + ")" );
        }
        return result;
    }
    //Remove consecutive duplicate characters e.g AABBCDDAAB -> ABCDAB  ABBBCCD -> ABCD
    public int removeDuplicates(char input[]){
        int slow = 0;
        int fast = 0;
        int index = 0;
        while(fast < input.length){
            while(fast < input.length && input[slow] == input[fast]){
                fast++;
            }
            input[index++] = input[slow];
            slow = fast;
        }
        return index;
    }
    //Given a string, find the rank of the string amongst its permutations sorted lexicographically. Assume that no
    //characters are repeated. Example : Input : 'acb' Output : 2
    public static int findRank (char[] str){
        int len = str.length;
        long mul = factorial(len);
        int rank = 1, i;
        int[] count = new int[256];  // all elements of count[] are initialized with 0
        // Populate the count array such that count[i] contains count of characters which are present in str and are smaller than i
        populateAndIncreaseCount( count, str );
        for (i = 0; i < len; ++i){
            mul /= len - i;
            // count number of chars smaller than str[i] from str[i+1] to str[len-1]
            rank += count[ str[i] - 1] * mul;
            // Reduce count of characters greater than str[i]
            updatecount (count, str[i]);
        }
        return rank;
    }
    public static long factorial(int n){
        return n <= 1? 1: (n * factorial(n - 1));
    }
    public static int findSmallerInRight(String A, int low, int high){
        int countRight = 0;
        for(int i = low + 1; i <= high; i++){
            if(A.charAt(i) < A.charAt(low))
                countRight++;
        }
        return countRight;
    }
    // contains count of smaller characters in whole string
    public static void populateAndIncreaseCount (int[] count, char[] str){
        int i;
        for( i = 0; str[i] >= 'a' && str[i] <= 'z' ; ++i )
            ++count[ str[i] ];
        for( i = 1; i < 256; ++i )
            count[i] += count[i-1];
    }
    // Removes a character ch from count[] array constructed by populateAndIncreaseCount()
    public static void updatecount (int[] count, char ch){
        int i;
        for( i = ch; i < 256; ++i )
            --count[i];
    }
    //Given a source word, target word and an English dictionary, transform the source word to target by
    //changing/adding/removing 1 character at a time, while all intermediate words being valid English words.
    public static LinkedList<String> transform(String startWord, String stopWord, Set<String> dictionary) {
        startWord = startWord.toUpperCase();
        stopWord = stopWord.toUpperCase();
        Queue<String> actionQueue = new LinkedList<String>();
        Set<String> visitedSet = new HashSet<String>();
        Map<String, String> backtrackMap = new TreeMap<String, String>();
        actionQueue.add(startWord);
        visitedSet.add(startWord);
        while (!actionQueue.isEmpty()) {
            String w = actionQueue.poll();
            // For each possible word v from w with one edit operation
            for (String v : getOneEditWords(w)) {
                if (v.equals(stopWord)) {
                    // Found our word!  Now, back track.
                    LinkedList<String> list = new LinkedList<String>();
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
}

