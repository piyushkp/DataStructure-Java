package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Search
{
    //BinarySearch
    public static int binarySearch(String key, String[] a, int lo, int hi) {
        // possible key indices in [lo, hi)
        if (hi <= lo) return -1;
        int mid = lo + (hi - lo) / 2;
        int cmp = a[mid].compareTo(key);
        if (cmp > 0)
            return binarySearch(key, a, lo, mid);
        else if (cmp < 0)
            return binarySearch(key, a, mid + 1, hi);
        else
            return mid;
    }
}
