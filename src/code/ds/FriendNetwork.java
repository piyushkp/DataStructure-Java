package code.ds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendNetwork<T> {

  private List<Friendship<T>> allFriendships;
  private Map<String, User<T>> allUser;
  private HashSet<String> allIndirectFriends;

  public FriendNetwork() {
    allFriendships = new ArrayList<>();
    allUser = new HashMap<>();
  }

  public User<T> addUser(String userName) {
    if (allUser.containsKey(userName)) {
      return allUser.get(userName);
    }
    User<T> v = new User<T>(userName);
    allUser.put(userName, v);
    return v;
  }

  public User<T> getUser(String userName) {
    return allUser.get(userName);
  }

  public void addFriendship(String user1, String user2) {
    User<T> User1;
    if (allUser.containsKey(user1)) {
      User1 = allUser.get(user1);
    } else {
      User1 = new User<>(user1);
      allUser.put(user1, User1);
    }
    User<T> User2;
    if (allUser.containsKey(user2)) {
      User2 = allUser.get(user2);
    } else {
      User2 = new User<>(user2);
      allUser.put(user2, User2);
    }

    Friendship<T> Friendship = new Friendship<>(User1, User2);
    allFriendships.add(Friendship);
    User1.addAdjacentUser(Friendship, User2);
    User2.addAdjacentUser(Friendship, User1);

  }

  public void removeFriendship(String user1, String user2) {
    User<T> User1 = null;
    if (allUser.containsKey(user1)) {
      User1 = allUser.get(user1);
    } else {
      // no User found exception
    }
    User<T> User2 = null;
    if (allUser.containsKey(user2)) {
      User2 = allUser.get(user2);
    } else {
      // no User found exception
    }

    Friendship<T> Friendship = new Friendship<>(User1, User2);
    allFriendships.remove(Friendship);
    User1.removeAdjacentUser(Friendship, User2);
    User2.removeAdjacentUser(Friendship, User1);

  }

  public Set<String> getDirectFriends(String userName) {
    HashSet<String> allDirectFriends = new HashSet<>();
    User<T> User = getUser(userName);
    for (Friendship friend : User.getFriendships()) {
      allDirectFriends.add(friend.getUser2().userName);
    }
    return allDirectFriends;
  }

  public Set<String> getIndirectFriends(String userName) {
    allIndirectFriends = new HashSet<>();
    User<T> user = getUser(userName);
    List<User<T>> friends = user.getAdjacentUseres();
    Set<String> visited = new HashSet<>();
    visited.add(userName);
    for (Friendship frd : user.getFriendships()) {
      User<T> friend = frd.getUser2();
      if(!visited.contains(friend)){
        getIndirectFriends(friend, visited, friends);
      }
    }
    return allIndirectFriends;
  }

  public void getIndirectFriends(User<T> friend, Set<String> visited, List<User<T>> directFriends){
    visited.add(friend.userName);
    for (User<T> frd : friend.getAdjacentUseres()){
      if (!visited.contains(frd.userName)) {
        if(!directFriends.contains(frd)) {
          allIndirectFriends.add(frd.userName);
        }
        getIndirectFriends(frd, visited, directFriends);
      }
    }
  }

  public List<Friendship<T>> getAllFriendships() {
    return allFriendships;
  }

  public Collection<User<T>> getAllUser() {
    return allUser.values();
  }


  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    for (Friendship<T> Friendship : getAllFriendships()) {
      buffer.append(
          Friendship.getUser1() + " " + Friendship.getUser2());
      buffer.append("\n");
    }
    return buffer.toString();
  }
}


class User<T> {

  String userName;
  private T data;
  private List<Friendship<T>> Friendships = new ArrayList<>();
  private List<User<T>> adjacentUser = new ArrayList<>();

  User(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void addAdjacentUser(Friendship<T> e, User<T> v) {
    Friendships.add(e);
    adjacentUser.add(v);
  }

  public void removeAdjacentUser(Friendship<T> e, User<T> v) {
    Friendships.remove(e);
    adjacentUser.remove(v);
  }

  public List<User<T>> getAdjacentUseres() {
    return adjacentUser;
  }

  public List<Friendship<T>> getFriendships() {
    return Friendships;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + (int) (Integer.valueOf(userName) ^ (Integer.valueOf(userName) >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (userName != other.userName) {
      return false;
    }
    return true;
  }
}

class Friendship<T> {

  private User<T> User1;
  private User<T> User2;

  Friendship(User<T> User1, User<T> User2) {
    this.User1 = User1;
    this.User2 = User2;
  }

  User<T> getUser1() {
    return User1;
  }

  User<T> getUser2() {
    return User2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((User1 == null) ? 0 : User1.hashCode());
    result = prime * result + ((User2 == null) ? 0 : User2.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Friendship other = (Friendship) obj;
    if (User1 == null) {
      if (other.User1 != null) {
        return false;
      }
    } else if (!User1.equals(other.User1)) {
      return false;
    }
    if (User2 == null) {
      if (other.User2 != null) {
        return false;
      }
    } else if (!User2.equals(other.User2)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Friendship [User1=" + User1 + ", User2=" + User2 + "]";
  }
}
