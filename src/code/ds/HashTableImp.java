package code.ds;
/**
 * Created by ppatel2 on 9/11/2014.
 */
public class HashTableImp {
    //Implement hashtable
    public class HashMap<K,V>
    {
        private double loadFactor = 0.75;
        private Entry[] table;
        private int elemCount;;
        public class Entry<key,value>
        {
            K key;
            V value;
            Entry<K,V> next;
            //contructors, getters and setters below
        }
        int hash(int h)
        {
            h ^= (h >>> 20) ^ (h >>> 12);
            return h ^ (h >>> 7) ^ (h >>> 4);
        }
        public void put(K key, V value)
        {
            if (elemCount>table.length*loadFactor) resize();//resize the table, if elements> above len*loadfactor
            int index = hash(key.hashCode())%table.length;
            if (table[index]==null) table[index] = new Entry(key,value);
            else
            {
                Entry cur = table[index];
                while(true)
                {
                    if (cur.getKey().equals(key))
                    {
                        cur.setValue(value);
                        break;
                    }
                    if (cur.next()==null) break;
                    cur = cur.next();// end and the element does not exist in table!
                }
                cur.setNext(new Entry(key,value));//add it to end of table
            }
        }

        public V get(K key)
        {
            int index = hash(key.hashCode())%table.length;
            if (table[index]==null) return null;//if value at index==null, return null
            else
            {
                Entry cur = table[index];
                while(true)
                {
                    if (cur.getKey().equals(key))// check if it exists in table
                    {
                        return cur.getValue();
                    }
                    if (cur.next()==null)
                        break;
                    cur = cur.next();
                }
                return null;// not in map!
            }
        }

        public void resize()// resize the table by creating a new table of length=1.5* old table length
//instantiate new table
//copy all elements from old table to new table and return it
        {
            int newSize = table.length*1.5;
            Entry[] newTable = new Entry[newSize];
            for (int i=0; i<table.length; i++)
                newTable[i]=table[i];
            table = newTable;
        }
    }
}
