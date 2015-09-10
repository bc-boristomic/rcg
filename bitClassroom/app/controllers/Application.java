package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Controller for Application that represents main application.
 */
public class Application extends Controller {

    /**
     * Index method renders index page and send string to main with message Home
     *
     * @return
     */
    public Result index() {
        return ok(index.render("Home"));
    }

}
