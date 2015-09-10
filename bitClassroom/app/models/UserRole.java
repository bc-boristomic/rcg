package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

import org.joda.time.DateTime;


/**
 * Created by Senadin on 8.9.2015.
 */

@Entity
@Table(name="user_role")
public class UserRole extends Model {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "role_code", length = 45)
    public String roleCode;

    @Column(name = "status", length = 1)
    public String status;

    @Column(name = "description", length = 4000)
    public String description;

    @Column(name = "created")
    public DateTime created = new DateTime();

    @Column(name = "created_by", length = 100)
    public String createdBy;

    @Column(name = "modified")
    public DateTime modified;

    @Column(name = "modified_by")
    public String modifiedBy;

    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "id")
    public User user;

    @ManyToOne()
    @JoinColumn(name="role_id", referencedColumnName = "id")
    public Role role;

    public UserRole(){

    }

    public UserRole(String description, DateTime created, String createdBy, DateTime modified, String modifiedBy){
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }


    public Finder<Long, UserRole> finder = new Finder<>(UserRole.class);

    public String toString(){
        return String.format("%s", id.toString());
    }

}
