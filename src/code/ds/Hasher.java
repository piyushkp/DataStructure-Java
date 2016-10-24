package code.ds;
/**
 * Created by Piyush Patel.
 */
import java.util.ArrayList;
import java.util.*;

public class Hasher<K, V> {
    public static void main(String [] args) {
        System.out.print("Hasher");
    }
    private static class LinkedListNode<K, V> {
        public LinkedListNode<K, V> next;
        public LinkedListNode<K, V> prev;
        public K key;
        public V value;
        public LinkedListNode(K k, V v) {
            key = k;
            value = v;
        }

        public String printForward() {
            String data = "(" + key + "," + value + ")";
            if (next != null) {
                return data + "->" + next.printForward();
            } else {
                return data;
            }
        }
    }

    private ArrayList<LinkedListNode<K, V>> arr;
    public Hasher(int capacity) {
		/* Create list of linked lists. */
        arr = new ArrayList<LinkedListNode<K, V>>();
        arr.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }

    /* Insert key and value into hash table. */
    public V put(K key, V value) {
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node != null) {
            V oldValue = node.value;
            node.value = value; // just update the value.
            return oldValue;
        }

        node = new LinkedListNode<K, V>(key, value);
        int index = getIndexForKey(key);
        if (arr.get(index) != null) {
            node.next = arr.get(index);
            node.next.prev = node;
        }
        arr.set(index, node);
        return null;
    }

    /* Remove node for key. */
    public V remove(K key) {
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node == null) {
            return null;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
			/* Removing head - update. */
            int hashKey = getIndexForKey(key);
            arr.set(hashKey, node.next);
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
        return node.value;
    }

    /* Get value for key. */
    public V get(K key) {
        if (key == null) return null;
        LinkedListNode<K, V> node = getNodeForKey(key);
        return node == null ? null : node.value;
    }

    /* Get linked list node associated with a given key. */
    private LinkedListNode<K, V> getNodeForKey(K key) {
        int index = getIndexForKey(key);
        LinkedListNode<K, V> current = arr.get(index);
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /* Really stupid function to map a key to an index. */
    public int getIndexForKey(K key) {
        return Math.abs(key.hashCode() % arr.size());
    }

    public void printTable() {
        for (int i = 0; i < arr.size(); i++) {
            String s = arr.get(i) == null ? "" : arr.get(i).printForward();
            System.out.println(i + ": " + s);
        }
    }
}
/*Implement Mini Cassandra is a NoSQL storage. The structure has two-level keys. hashMap with Multiple keys
    Level 1: raw_key. The same as hash_key or shard_key.
    Level 2: column_key.
    Level 3: column_value
    1. insert(raw_key, column_key, column_value)
    2. query(raw_key, column_start, column_end) // return a list of entries */
class Column {
    public int key;
    public String value;

    public Column(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
class MiniCassandra {
    private Map<String, TreeMap<Integer, String>> map = new HashMap<>();
    public MiniCassandra() {
        // initialize your data structure here.
    }
    public void insert(String raw_key, int column_key, String column_value) {
        TreeMap<Integer, String> tm = map.get(raw_key);
        if (tm == null) {
            tm = new TreeMap<>();
            map.put(raw_key, tm);
        }
        tm.put(column_key, column_value);
    }
    public List<Column> query(String raw_key, int column_start, int column_end) {
        List<Column> results = new ArrayList<>();
        TreeMap<Integer, String> tm = map.get(raw_key);
        if (tm == null) return results;
        Map<Integer, String> queried = tm.subMap(column_start, true, column_end, true);
        for (int key : queried.keySet()) {
            results.add(new Column(key, queried.get(key)));
        }
        return results;
    }
}