package controllers;

import models.DailyRaportModel;
import models.FieldModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by enver.memic on 07/09/15.
 */
public class DailyRaportController extends Controller {

    private static final Form<DailyRaportModel> raportForm = Form.form(DailyRaportModel.class);


    public Result dailyRaport() {

        List<FieldModel> fields  = FieldModel.getFinder().findList();
        return ok(views.html.dailyraports.dailyraport.render(raportForm,fields));
    }

    public Result saveRaport() {
        return TODO;
    }

    public Result listRaport() {
        return TODO;
    }

    public Result setRaport() {
        return TODO;
    }

    public Result deleteRaport() {
        return TODO;
    }

    public Result saveField() {
        Form<FieldModel> fieldModelForm = Form.form(FieldModel.class);

        FieldModel model = new FieldModel();
        model.name = fieldModelForm.bindFromRequest().field("name").value();
        model.save();
        return ok(views.html.dailyraports.setingsdailyraport.render()) ;
    }

    public Result raportModel() {
        return ok(views.html.dailyraports.setingsdailyraport.render());
    }


}
