package code.ds;

/**
 * Created by ppatel2 on 3/16/2017.
 */
import java.util.BitSet;
public class BloomFilter {
    private BitSet filter_;
    int hashCount;
    BloomFilter(int hashes, BitSet filter){
        hashCount = hashes;
        filter_ = filter;
    }
    public void add(String key){
        for (int bucketIndex : getHashBuckets(key, hashCount))
        {
            filter_.set(bucketIndex);
        }
    }

    public boolean isPresent(String key)
    {
        for (int bucketIndex : getHashBuckets( key, hashCount))
        {
            if (!filter_.get(bucketIndex))
            {
                return false;
            }
        }
        return true;
    }

    static int[] getHashBuckets(String key, int hashCount) {
        int[] result = new int[hashCount];
        
        return result;
    }
}
