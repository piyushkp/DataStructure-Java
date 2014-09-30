package code.ds;
import java.util.*;

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
    //Finds first non repeated character in a String in just one pass.
    private char firstNonRepeatingChar(String word) {
        Set<Character> repeating = new HashSet<Character>();
        List<Character> nonrepeating = new ArrayList<Character>();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (repeating.contains(letter))
                continue;
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
    // Find the first occurrence of number in a Array.
    private int findFirstoccrrence(int[] A, int k) {
        int low = 0;
        int high = A.length;
        int result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (k == A[mid]) {
                result = mid;
                high = mid - 1;
                // for the last occurrence
                // low = mid + 1;
            } else if (k < A[mid]) {
                high = mid - 1;
            } else if (k > A[mid]) {
                low = mid + 1;
            }
        }
        return A[result];
    }
    /*Given a regular expression with characters a-z, ' * ', ' . '
    the task was to find if that string could match another string with characters from: a-z
    where ' * ' can delete the character before it, and ' . ' could match whatever character.
    ' * ' always appear after a a-z character. */
    Boolean isMatch(String sA, String sB, int iALength, int iBLength) {
        if (iALength == 0 && iBLength == 0)
            return true;
        else {
            if (iALength > 0) {
                if (sA.charAt(iALength - 1) == '*')
                    return isMatch(sA, sB, iALength - 1, iBLength) || isMatch(sA, sB, iALength - 2, iBLength);
                if (sA.charAt(iALength - 1) == '.')
                    return isMatch(sA, sB, iALength - 1, iBLength) || isMatch(sA, sB, iALength - 1, iBLength - 1);
                if (iBLength > 0) {
                    if (sA.charAt(iALength - 1) == sB.charAt(iBLength - 1))
                        return true;
                    else
                        return false;
                }
            }
        }
        return false;
    }
    /* Implement regular expression matching with support for '.' and '*'.
    '.' Matches any single character.
    '*' Matches zero or more of the preceding element. */
    public boolean isMatch(String s, String p) {
        if (s == null)
            return p == null;
        if (p == null)
            return s == null;
        int lenS = s.length();
        int lenP = p.length();
        if (lenP == 0)
            return lenS == 0;
        if (lenP == 1) {
            if (p.equals(s) || p.equals(".") && s.length() == 1) {
                return true;
            } else
                return false;
        }
        if (p.charAt(1) != '*') {
            if (s.length() > 0
                    && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                return isMatch(s.substring(1), p.substring(1));
            }
            return false;
        } else {
            while (s.length() > 0
                    && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                if (isMatch(s, p.substring(2)))
                    return true;
                s = s.substring(1);
            }
            return isMatch(s, p.substring(2));
        }
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
                    while (!needFind.containsKey(S.charAt(start))
                            || alreadyFind.get(S.charAt(start)) > needFind.get(S.charAt(start))) {
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
    public static int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        int pre = 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], i);
            } else {
                pre = pre > map.size() ? pre : map.size();
                i = map.get(arr[i]);
                map.clear();
            }
        }
        return Math.max(pre, map.size());
    }
    //Find all the repeating sub-string sequence of specified length in a large string sequence.
    // The sequences returned i.e. the output must be sorted alphabetically
    //Input String: "ABCACBABC" repeated sub-string length: 3 Output: ABC
    //Input String: "ABCABCA" repeated sub-string length: 2 Output: AB, BC, CA
    public static void printRepeatingStrings(String inputString, int sequenceLength) {
        if (inputString.isEmpty() || sequenceLength <= 0 || sequenceLength >= inputString.length()) {
            System.out.println("Invalid input");
        } else {
            int i = 0;
            int j = i + sequenceLength;
            Set<String> tempSet = new HashSet<String>();
            Set<String> repeatingSequences = new TreeSet<String>();
            while (j <= inputString.length()) {
                if (!tempSet.add(inputString.substring(i, j))) {
                    repeatingSequences.add(inputString.substring(i, j));
                }
                i++;
                j = i + sequenceLength;
            }
            for (String str : repeatingSequences) {
                System.out.println(str);
            }
        }
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
                while (i < target.length() - 1 &&
                        (target.charAt(i + 1) == curr || target.charAt(i + 1) == ' ' || target.charAt(i + 1) == '.')) {
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
        if (s == null)
            return null;
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
    //Permutations of the string
    void permute(String str) {
        int length = str.length();
        boolean[] used = new boolean[length];
        StringBuffer out = new StringBuffer();
        char[] in = str.toCharArray();
        doPermute(in, out, used, length, 0);
    }
    void doPermute(char[] in, StringBuffer out,
                   boolean[] used, int length, int level) {
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
    void doCombine(char[] instr, StringBuilder outstr, int length,
                   int level, int start) {
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
        if (s1.length() != s2.length())
            return false;
        else if (s1.length() == 1)
            return true;
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
    //Find minimum distance between two words (order preserved) in a big string
    //eg: For e.g 1. "hello how are you" - distance between "hello" and "you" is 3.
    // e.g 2. "hello how are hello you" - distance is 1
    // e.g 3. "you are hello" - distance is -1. Order of "hello" and "you" should be preserved.
    public static int findDistanceBetweenWords(String inputBody, String pair1, String pair2) {
        if (inputBody.isEmpty() || pair1.isEmpty() || pair2.isEmpty()) {
            return -1;
        }
        if (pair1.equals(pair2)) {
            return 0;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(inputBody, " ");
        int distance = 0, globalDistance = Integer.MAX_VALUE;
        String token;
        while (stringTokenizer.hasMoreTokens()) {
            token = stringTokenizer.nextToken();
            if (token.equals(pair1)) {
                distance = 0;
            } else if (token.equals(pair2)) {
                globalDistance = Math.min(distance, globalDistance);
            }
            distance++;
        }
        if (globalDistance == Integer.MAX_VALUE || globalDistance == 0) {
            return -1;
        } else {
            return globalDistance;
        }
    }
    /* Input pair can be considered in any order. For e.g. "A B C D A" - Min distance between A and D is 1. With order
     * preserved it would have been 3.*/
    public static int findDistanceBetweenWordsUnOrdered(String inputBody, String pair1, String pair2) {
        if (inputBody.isEmpty() || pair1.isEmpty() || pair2.isEmpty()) {
            return -1;
        }
        if (pair1.equals(pair2)) {
            return 0;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(inputBody, " ");
        int distance = 0, globalDistance = Integer.MAX_VALUE;
        String previous = "";
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (previous.isEmpty())
            {
                if (token.equalsIgnoreCase(pair1) || token.equalsIgnoreCase(pair2)) {
                    previous = token;
                }
            }
            else if (token.equalsIgnoreCase(pair1) || token.equalsIgnoreCase(pair2))
            {
                if (!token.equalsIgnoreCase(previous)) {
                    globalDistance = Math.min(globalDistance, distance);
                    previous = token;
                }
                distance = 0;
            }
            distance++;
        }
        // None of the pairs were found in inputBody.
        if (previous.isEmpty() || globalDistance == Integer.MAX_VALUE) {
            return -1;
        }
        return globalDistance;
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
            if (S1.indexOf(c) != -1 && S2.indexOf(c) != -1)
                _set.add(String.valueOf(c));
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
    public static char findNextChar(char[] list, char c) {
        assert list.length > 0;
        int left = 0, right = list.length - 1;
        char result = list[0];
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list[mid] == c) {
                if (mid < list.length - 1) return list[mid + 1];
                else return result;
            } else if (list[mid] < c) {
                left = mid + 1;
            } else {//list[mid] > c
                result = list[mid];
                right = mid - 1;
            }
        }
        return result;
    }
    // Check whether two strings are Anagram or not
    private boolean areAnagram(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;
        HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
        for (int i = 0; i < s1.length(); i++) {
            Character ch = s1.charAt(i);
            Integer count = (Integer) counter.get(ch);
            if (count == null)
                counter.put(ch, 1);
            else {
                counter.put(ch, count + 1);
            }
        }
        for (int i = 0; i < s2.length(); i++) {
            Character ch = s2.charAt(i);
            Integer count = (Integer) counter.get(ch);
            if (count == null || count == 0)
                return false;
            else {
                counter.put(ch, count - 1);
            }
        }
        return true;
    }
    //Given a string S, find the longest palindromic substring in S. O(N2) time and O(1) space
    String expandAroundCenter(String s, int c1, int c2) {
        int l = c1, r = c2;
        int n = s.length();
        while (l >= 0 && r <= n - 1 && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r - l - 1);
    }
    String longestPalindromeSimple(String s) {
        int n = s.length();
        if (n == 0) return "";
        String longest = s.substring(0, 1);  // a single char itself is a palindrome
        for (int i = 0; i < n - 1; i++) {
            String p1 = expandAroundCenter(s, i, i);
            if (p1.length() > longest.length())
                longest = p1;

            String p2 = expandAroundCenter(s, i, i + 1);
            if (p2.length() > longest.length())
                longest = p2;
        }
        return longest;
    }
    //A Program to check if strings are rotations of each other or not
    //given s1 = ABCD and s2 = CDAB, return true
    boolean areRotations(String s1, String s2)
    {
        String temp = s1+s1;
        if(temp.contains(s2))
            return true;
        return false;
    }
}

