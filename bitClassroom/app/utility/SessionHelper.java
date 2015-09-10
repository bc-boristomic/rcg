package utility;

import models.User;
import play.mvc.Http;

/**
 * Created by banjich on 9/4/15.
 */
public class SessionHelper {



    public static User currentUser(Http.Context context){
        String username = context.session().get("username");
        if(username == null)
            return null;
        return User.findByEmail(username);
    }
}
