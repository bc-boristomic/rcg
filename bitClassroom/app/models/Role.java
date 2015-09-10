package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Class User extends ebean Model, represents table role in database.
 * Finder is created along with CRUD methods for easier manipulation of data.
 */
@Entity
@Table(name = "role")
public class Role extends Model{

    public static Finder<Long, Role> finder = new Finder<Long, Role>(Long.class, Role.class);

    private static List<Role> roles = new ArrayList<Role>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "name", length = 120)
    public String name;

    @Column(name = "status", length = 1)
    public Integer status;

    @Column(name = "description", length = 4000)
    public String description;

    @Column(name = "created")
    public DateTime created = new DateTime();

    @Column(name = "created_by", length = 45)
    public String createdBy;

    @Column(name = "modified")
    public DateTime modified;

    @Column(name = "modified_by", length = 45)
    public String modifiedBy;

    @OneToMany(mappedBy = "role")
    @JoinColumn(name="user_role_id", referencedColumnName = "id")
    public List<UserRole> userRoles;

    /**
     * Default empty constructor
     */
    public Role() {

    }

    /**
     * Goes thru entire Role table and finds every entry. Returns as ArrayList of RoleController.
     *
     * @return <code>ArrayList</code> of all RoleController from Role table
     */
    public static List<Role> findAll() {
        return new ArrayList<Role>(finder.all());
    }

    /**
     * Searches for role code in Role table, if code is found, returns Role object, otherwise returns null.
     *
     * @param code <code>String</code> type value of role code to look in database
     * @return <code>Role</code> object if code is found in database
     */
    public static Role findRoleByCode(String code) {
        List<Role> role = finder.where().eq("code", code).findList();
        if (role.size() == 0) {
            return null;
        }
        return (Role) role.get(0);
    }

}