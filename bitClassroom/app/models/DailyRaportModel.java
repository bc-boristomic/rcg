package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enver.memic on 07/09/15.
 */


@Entity
public class DailyRaportModel extends Model {

    public static Finder<String, DailyRaportModel> finderRaport = new Finder<>(String.class, DailyRaportModel.class);

    private static List<DailyRaportModel> raport = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "name", length = 100)
    public String name;

    @Column(name = "date")
    public DateTime date = new DateTime();

    @Column(name = "fieldmodel")
    public List<FieldModel> list;


    //Constructor
    public DailyRaportModel() {

    }

    //Constructor
    public DailyRaportModel(Long id, String name, DateTime date, List<FieldModel> list) {
        this.id = id;
        this.name = name;
        this. date = date;
        this.list = list;
    }
}
