package models;

import com.avaje.ebean.Model;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Enver on 8.9.2015..
 */

@Entity
public class RaportRelationModel extends Model {


    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    @ManyToOne
    @JoinColumn(name = "daily_id", referencedColumnName = "id")
    public DailyRaportModel dailyRaportModel;


    @ManyToOne
    public FieldModel fieldModel;

    @Column
    public String value;

    //Constructor
    public RaportRelationModel() {

    }

    //Constructor
    public RaportRelationModel(Long id, DailyRaportModel dailyRaportModel, FieldModel fieldModel){
        this.id = id;
        this.dailyRaportModel = dailyRaportModel;
        this.fieldModel = fieldModel;

    }
}
