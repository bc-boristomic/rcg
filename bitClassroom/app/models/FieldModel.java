package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Enver on 8.9.2015..
 */

@Entity
public class FieldModel extends Model {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    @Column(name = "name")
    public String name;

    //Constructor
    public FieldModel() {

    }

    //Constructor
    public FieldModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Finder<Long, FieldModel> getFinder(){
        return new Finder<Long, FieldModel>(Long.class,FieldModel.class);
    }

}
