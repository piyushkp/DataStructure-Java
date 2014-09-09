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
    // Find the first occurrence of number.
    private int findFirstoccrrence(int[] A, int k)
    {
        int low = 0;
        int high = A.length;
        int result = -1;
        while (low <= high)
        {
            int mid = (low + high) /2;
            if(k == A[mid])
            {
                result = mid;
                high = mid -1;
                // for the last occurrence
                // low = mid + 1;
            }
            else if (k < A[mid])
            {
                high = mid -1;
            }
            else if (k > A[mid])
            {
                low = mid + 1;
            }
         }
        return result;
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

    /* Regular Expression Matching
    Implement regular expression matching with support for '.' and '*'.
    '.' Matches any single character.
    '*' Matches zero or more of the preceding element. */
    private static boolean match(String s, int ss, String p, int sp) {
        // if (sp > p.length()) return false; // works both with and without this line
        if (sp == p.length()) return ss == s.length();
        if (sp + 1 < p.length() && p.charAt(sp + 1) == '*') {
            if (match(s, ss, p, sp + 2)) return true; // **important**
            while (ss < s.length()) {
                if ((s.charAt(ss) == p.charAt(sp) || p.charAt(sp) == '.')) { // **important**
                    ss++; // **important**
                    if (match(s, ss, p, sp + 2)) return true; // **important**
                } // **important**
                else return false; // **important**
            }
            return false;
        } else {
            if (ss < s.length()) {
                return (p.charAt(sp) == s.charAt(ss) || p.charAt(sp) == '.') && match(s, ss + 1, p, sp + 1);
            } else {
                return false;
            }
        }
    }

    /* Given a string, find the length of the longest substring without repeating characters.
       For example, the longest substring without repeating letters for “abcabcbb” is “abc” */
    public int lengthOfLongestSubstring(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int i = 0;
        int maxLength = 0;
        while (i < s.length()) {
            int len = 0;
            Hashtable<Character, Integer> h = new Hashtable<Character, Integer>();
            while (i < s.length() && h.get((Character) s.charAt(i)) == null) {
                h.put(s.charAt(i), i);
                i++;
                len++;
                maxLength = Math.max(maxLength, len);
            }
            if (i == s.length()) {
                break;
            } else {
                i = h.get(s.charAt(i)) + 1;
            }
        }
        return maxLength;
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
    private String removeDuplicate(String s)
    {
        if(s == null)
            return null;
        HashSet<Character> _set = new HashSet<Character>();
        StringBuffer result = new StringBuffer();
        char[] _ch = s.toCharArray();
        for (int i = 0; i < _ch.length; i++)
        {
            if(!_set.contains(_ch[i])) {
                _set.add(_ch[i]);
                result.append(_ch[i]);
            }
        }
        return  result.toString();
    }
    //Permutations of the string
    void permute( String str ){
        int length = str.length();
        boolean[] used = new boolean[ length ];
        StringBuffer out = new StringBuffer();
        char[] in = str.toCharArray();
        doPermute( in, out, used, length, 0 );
    }
    void doPermute( char[] in, StringBuffer out,
                    boolean[] used, int length, int level ){
        if( level == length ){
            System.out.println( out.toString() );
            return;
        }
        for( int i = 0; i < length; ++i ){
            if( used[i] ) continue;
            out.append( in[i] );
            used[i] = true;
            doPermute( in, out, used, length, level + 1 );
            used[i] = false;
            out.setLength( out.length() - 1 );
        }
    }
    //combination of the string
    void combine( String str ){
        int length = str.length();
        char[] instr = str.toCharArray();
        StringBuilder outstr = new StringBuilder();
        doCombine( instr, outstr, length, 0, 0 );
    }
    void doCombine( char[] instr, StringBuilder outstr, int length,
                    int level, int start ){
        for( int i = start; i < length; i++ ){
            outstr.append( instr[i] );
            System.out.println( outstr );
            if( i < length - 1 ){
                doCombine( instr, outstr, length, level + 1, i + 1 );
            }
            outstr.setLength(outstr.length() - 1);
        }
    }

    //Given set of characters and a string, find smallest substring which contains all characters

    //Reverse words in a string



}

