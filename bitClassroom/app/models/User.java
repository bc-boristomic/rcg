package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class User extends ebean Model, represents table User in database.
 * Finder is created alon with CRUD methods for easier manipulation of data.
 */
@Entity
public class User extends Model {

    public static Finder<String, User> finder = new Finder<String, User>(String.class, User.class);

    private static List<User> users = new ArrayList<User>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "email", unique = true, updatable = false, length = 100)
    public String email;

    @Column(name = "password", length = 100)
    public String password;

    //public String firstName; // TODO not null
    //public String lastName; // TODO not null
    //public Date firstEntry;// TODO timedate
    //public Date lastEntry; // TODO timedate

    /**
     * Default empty constructor
     */
    public User() {

    }

    /**
     * Constructor for creating object with email and password only. Other values are not included.
     *
     * @param email    <code>String</code> type value of User email
     * @param password <code>String</code> type value of User password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Goes thru entire User table and finds every entry. Returns as ArrayList of Users.
     *
     * @return <code>ArrayList</code> of all Users from User table
     */
    public static List<User> findAll() {
        return new ArrayList<User>(finder.all());
    }

    /**
     * Searches for user email and password, both must match to get result.
     * If user and password don't match return is null, otherwise you get User object
     *
     * @param email    <code>String</code> type value of User email to look in database
     * @param password <code>String</code> type value of User password to look in database
     * @return <code>User</code> object if email and password match in table User, null if nothing is found.
     */
    public static User findUserByEmailAndPassword(String email, String password) {
        List<User> user = finder.where().eq("email", email).eq("password", password).findList();
        if (user.size() == 0) {
            return null;
        }
        return (User) user.get(0);
    }

    /**
     * Searches for user id in User table, if id is found, returns User object, otherwise returns null.
     *
     * @param id <code>Long</code> type value of user id to look in database
     * @return <code>User</code> object if id is found in database
     */
    public static User findUserById(Long id) {
        List<User> user = finder.where().eq("id", id).findList();
        if (user.size() == 0) {
            return null;
        }
        return (User) user.get(0);
    }

    /**
     * Removes user from ArralList of users.
     *
     * @param user <code>User</code> type value of User object
     * @return <code>boolean</code> type value true if user is removed, false if not
     */
    public static boolean remove(User user) {
        return users.remove(user);
    }

    /**
     * toString method returns user email.
     *
     * @return <code>String</code> type value of user email
     */
    public String toString() {
        return String.format("%s", email);
    }


}

