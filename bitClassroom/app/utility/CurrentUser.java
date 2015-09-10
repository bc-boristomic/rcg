package utility;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import models.User;
import views.html.index;


/**
 * Created by boris.tomic on 08/09/15.
 */
public class CurrentUser extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context) {
        if (!context.session().containsKey("username")) {
            return null;
        }
        String email = context.session().get("username");
        User temp = User.findByEmail(email);

        if (temp != null) {
            return temp.email;
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return ok(index.render("Home page"));
    }
}
