package code.ds;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by ppatel2 on 11/22/2016.
 */
//In memory DB with add, delete, get, and count with transactions
public class InMemDB {
    public static void main(String[] args) {
        MemDB db = new MemDB();
        db.SET("a", 10);
        db.BEGIN();
        db.SET("a", 15);
            db.BEGIN();
            db.DELETE("a");
            db.SET("a", 10);
            db.ROLLBACK();
        db.COMMIT();
        db.SET("b", 10);
        db.GET("a"); // 10
        db.COUNT(10); //2
        db.SET("a", 10);
        db.GET("a");
        db.SET("b", 15);
        db.COUNT(15);
        db.SET("b", 20);
        db.SET("c", 15);
        db.COUNT(10);
    }
}
class MemDB{
    HashMap<String,Integer> map = new HashMap<>();
    HashMap<Integer, Integer> map1 = new HashMap<>();
    Stack<Transaction> _actions = new Stack<>();
    public void SET(String name, Integer value) {
        if(map.containsKey(name)) {
            int oldValue = map.get(name);
            int oldCount = map1.get(oldValue);
            map1.put(oldValue, --oldCount);
            if (!_actions.isEmpty())
                _actions.peek().addSetCommand(new String[]{name, Integer.toString(oldValue)});
        }
        else {
            if (!_actions.isEmpty())
                _actions.peek().addSetCommand(new String[]{name});
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
        int oldValue  = map.get(name);
        map.remove(name);
        int count = map1.get(oldValue);
        map1.put(oldValue, --count);
        if (!_actions.isEmpty())
            _actions.peek().addSetCommand(new String[]{name, Integer.toString(oldValue)});
    }
    public void COUNT(Integer value){
        System.out.println(map1.get(value));
    }
    public void BEGIN()
    {
        _actions.push(new Transaction(this));
    }
    public void ROLLBACK()
    {
        if (_actions.isEmpty()){
            System.out.println("NO TRANSACTIONS IN PROGRESS");
            return;
        }
        _actions.pop().rollback();
    }
    public void COMMIT()
    {
        if (_actions.isEmpty()){
            System.out.println("NO TRANSACTIONS IN PROGRESS");
            return;
        }
        _actions.clear();
    }
}
class Transaction {
    private Stack<String> _commands;
    private MemDB _db;
    public Transaction(MemDB db){
        _db = db;
        _commands = new Stack<>();
    }
    public void addSetCommand(String[] command) {
        if (command.length == 1){
            _commands.push(command[0]);
        }
        else
        {
            _commands.push(command[0] + " " + command[1]);
        }
    }
    public void addDeleteCommand(String[] command) {
        _commands.push(command[0] + " " + command[1]);
    }
    public void rollback(){
        while (!_commands.isEmpty()){
            String command = _commands.pop();
            String[] parsed = command.split(" ");
            if (parsed.length==1){
                _db.DELETE(parsed[0]);
            }else {
                _db.SET(parsed[0], Integer.parseInt(parsed[1]));
            }
        }
    }
}
