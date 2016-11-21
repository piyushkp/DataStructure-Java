package code.ds;

import java.util.*;

/**
 * Created by ppatel2 on 11/9/2016.
 */
public class test {
    public static void main(String[] args) {
        MemDB db = new MemDB();
        db.SET("a", 10);
        //db.SET("a", 10);
        db.SET("b", 10);
        db.GET("a");
        System.out.println(db.COUNT(10));
        db.SET("a", 15);
        db.GET("a");
        db.SET("b", 15);
        System.out.println(db.COUNT(15));
        db.SET("b", 100000);
        db.SET("c", 15);
        db.DELETE("a");
        System.out.println(db.COUNT(10000));

    }
}
class MemDB {
    private Map<String, List<Integer>> map = new HashMap<>();
    private Map<Integer, List<String>> map1 = new HashMap<>();
    int index;
    int size;
    boolean _isBEGINSet = false;
    boolean _isCOMMITSet = false;
    int prevIndex;
    public void SET(String name, int value) {
        if(!map.containsKey(name)) {
            index = size;
            List<Integer> temp = new ArrayList<>();
            temp.add(value);
            temp.add(index);
            map.put(name, temp);
            List<String> list = map1.get(value);
            if (list == null) {
                list = new ArrayList<>();
            }
            if (!list.contains(name))
                list.add(list.size(), name);
            map1.put(value, list);
            size++;
        }
        else
        {
            List<String> list = map1.get(value);
            int _newIndex = 0;
            if (list == null) {
                list = new ArrayList<>();
            }
            else if(list.size() > 0)
                _newIndex = list.size();

            List<Integer> _list = map.get(name);
            int oldValue = _list.get(0);
            int _index = map.get(name).get(1);
            _list.set(0, value);
            _list.set(1,_newIndex);
            map.put(name,_list);

            List<String> _nameList = map1.get(oldValue);
            _nameList.set(_index,_nameList.get(_nameList.size()-1));
            _nameList.remove(_nameList.size()-1);
            map1.put(oldValue, _nameList);

            if(_nameList.size() > 0) {
                List<Integer> _list1 = map.get(_nameList.get(_newIndex));
                _list1.set(1, _newIndex);
                map.put(_nameList.get(_newIndex), _list1);
            }
            list.add(_newIndex, name);
            map1.put(value, list);
        }
    }
    public void GET(String name)
    {
        List<Integer> list = map.get(name);
        if(list == null)
            System.out.println("NULL");
        else
            System.out.println(map.get(name).get(0));

    }
    public void DELETE(String name)
    {
        int index = map.get(name).get(1);
        int value = map.get(name).get(0);
        List<String> arr = map1.get(value);
        arr.set(index, arr.get(arr.size() - 1));
        arr.remove(arr.size() - 1 );
        size--;

        map1.put(value, arr);
        map.remove(name);

    }
    public int COUNT(int value)
    {
        if(map1.get(value) == null)
            return 0;
        return map1.get(value).size();
    }

    public void BEGIN()
    {
        _isBEGINSet = true;
        prevIndex = index;
    }
    public void ROLLBACK()
    {
        if(!_isBEGINSet)
            System.out.println("No transaction to COMMIT");
        else if(_isCOMMITSet)
            System.out.println("You can not ROLLBACK, transaction is already committed");
        else
        {
            size -= prevIndex;
        }
    }
    public void COMMIT()
    {
        if(!_isBEGINSet)
            System.out.println("No transaction to COMMIT");
        _isBEGINSet = true;
    }
}
