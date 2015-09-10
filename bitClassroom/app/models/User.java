package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Class User extends ebean Model, represents table User in database.
 * Finder is created alon with CRUD methods for easier manipulation of data.
 */
@Entity
@Table(name = "user")
public class User extends Model {

    public static Finder<Long, User> finder = new Finder<>(User.class);

    private static List<User> users = new ArrayList<User>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "email", unique = true, updatable = false, length = 100)
    public String email;

    @Column(name = "password", length = 100)
    public String password;

    @Column(name = "status")
    public Boolean status = false;

    @Column(name = "first_name", length = 100)
    public String firstName;

    @Column(name = "last_name", length = 100)
    public String lastName;

    @Column(name = "role", length = 1)
    public Integer role;

    @Column(name = "birth_date")
    public DateTime birthDate;

    @Column(name = "gender", length = 1)
    public Integer gender;

    @Column(name = "skype_name", length = 100)
    public String skypeName;

    @Column(name = "facebook_profile", length = 300)
    public String facebookProfile;

    @Column(name = "create_date", updatable = false, columnDefinition = "datetime")
    public DateTime creationDate = new DateTime().toDateTime();

    @Column(name = "created_by", updatable = false, length = 100)
    public String createdBy;

    @Column(name = "update_date", columnDefinition = "datetime")
    public DateTime updateDate;

    @Column(name = "updated_by", length = 100)
    public String updatedBy;

    @OneToMany()
    @JoinColumn(name="user_role_id", referencedColumnName = "id")
    public List<UserRole> userRoles;

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
    public User(String email, String password, Integer role) {

        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor for creating object with all attributes, used for update table for edit your personal info
     *
     * @param facebookProfile String of faceboook profile link
     * @param skypeName       String skype name (nickname)
     * @param gender          Integer value: 1 is M 0 is F
     * @param birthDate       Date of birth
     * @param role            Boolean
     * @param lastName        String value of last name
     * @param firstName       String value of first name
     * @param password        String of password
     * @param email           String of email address
     */
    public User(String facebookProfile, String skypeName, Integer gender, DateTime birthDate, Integer role, String lastName, String firstName, String password, String email) {

        this.facebookProfile = facebookProfile;
        this.skypeName = skypeName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
    }

    /**
     * Constructor used to register new User to database. User is added with email,
     * hasshed password, status, role, creation date and creator.
     *
     * @param email        <code>String</code> type value of user email
     * @param password     <code>String</code> type value of user has
     * @param status       <code>Integer</code> type value of user status
     * @param role         <code>Integer</code> type value of user role
     * @param creationDate <code>DateTime</code> value of creation date
     * @param createdBy    <code>String</code> type value of creator
     */
    public User(String email, String password, Boolean status, Integer role, DateTime creationDate, String createdBy) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }

    public User(String email, String password, Boolean status, String firstName, String lastName,
                Integer role, DateTime birthDate, Integer gender, String skypeName, String facebookProfile,
                DateTime creationDate, String createdBy, DateTime updateDate, String updatedBy, List<UserRole> userRoles) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.birthDate = birthDate;
        this.gender = gender;
        this.skypeName = skypeName;
        this.facebookProfile = facebookProfile;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.updateDate = updateDate;
        this.updatedBy = updatedBy;
        this.userRoles = userRoles;
    }


    /**
     * Change status of user, are they already register- Default value is false
     * @param status - Boolean
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public static User getUserByRole(Integer role) {
        return finder.where().eq("role_id", role).findUnique();
    }

    /**
     * Goes thru entire User table and finds every entry. Returns as ArrayList of UserController.
     *
     * @return <code>ArrayList</code> of all UserController from User table
     */
    public static List<User> findAll() {
        return finder.all();
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
        List<User> user = finder.where().eq("email", email.toLowerCase()).eq("password", password).findList();
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
     * Method for check user role
     *
     * @param role
     * @return
     */
    public static User findUserByRole(Integer role) {

        List<User> user = finder.where().eq("role", role).findList();
        if (user.size() == 0) {

            return null;
        }
        return (User) user.get(0);
    }

    /**
     * Returns user by inputed String
     *
     * @param email
     * @return
     */
    public static User findByEmail(String email) {
        return finder.where().eq("email", email.toLowerCase()).findUnique();
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


    public String getEmail(){
        return email;
    }
}

