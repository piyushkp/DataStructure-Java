package code.ds;

import java.util.*;

/**
 * Created by ppatel2 on 11/9/2016.
 */
public class test {
    public static void main(String[] args) {
        MemDB db = new MemDB();
        db.SET("a", 10);
        db.SET("a", 10);
        db.SET("b", 10);
        db.GET("a"); // 10
        db.COUNT(10); //2
        db.SET("a", 15);
        db.GET("a");
        db.SET("b", 15);
        db.COUNT(15);
        db.SET("b", 20);
        db.SET("c", 15);
        db.DELETE("a");
        db.COUNT(10);
    }
}
class MemDB{
    boolean _isBEGINSet = false;
    boolean _isCOMMITSet = false;
    int prevIndex;
    HashMap<String,Integer> map = new HashMap<>();
    HashMap<Integer, Integer> map1 = new HashMap<>();

    public void SET(String name, Integer value) {
        if(map.containsKey(name)) {
            int oldValue = map.get(name);
            int oldCount = map1.get(oldValue);
            map1.put(oldValue, --oldCount);
        }
        if(!map1.containsKey(value))
            map1.put(value, 1);
        else {
            int count = map1.get(value);
            map1.put(value, ++count);
        }
        map.put(name, value);
    }
    public void GET(String name)
    {
        System.out.println(map.get(name));
    }
    public void DELETE(String name){
        int temp  = map.get(name);
        map.remove(name);
        int count = map1.get(temp);
        map1.put(temp, --count);
    }
    public void COUNT(Integer value){
        System.out.println(map1.get(value));
    }
    public void BEGIN()
    {
        _isBEGINSet = true;
    }
    public void ROLLBACK()
    {
        if(!_isBEGINSet)
            System.out.println("No transaction to COMMIT");
        else if(_isCOMMITSet)
            System.out.println("You can not ROLLBACK, transaction is already committed");
        else
        {

        }
    }
    public void COMMIT()
    {
        if(!_isBEGINSet)
            System.out.println("No transaction to COMMIT");
        _isBEGINSet = true;
    }
}
