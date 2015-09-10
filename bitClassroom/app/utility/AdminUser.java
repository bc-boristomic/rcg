package utility;


import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import views.html.index;

/**
 * Created by boris on 9/8/15.
 */
public class AdminUser extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context) {
        if (!context.session().containsKey("username")) {
            return null;
        }
        String email = context.session().get("username");
        User temp = User.findByEmail(email);
        if (temp != null && temp.role.equals(UserConst.ADMIN)) {
            return temp.email;
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return ok(index.render("Home page"));
    }
}
