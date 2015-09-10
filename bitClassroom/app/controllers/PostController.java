package controllers;

import utility.CurrentUser;
import utility.SessionHelper;
import models.Post;
import models.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import play.cache.Cached;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.post.addNewPost;
import views.html.index;
import views.html.post.allPosts;

import play.Logger;


import java.util.List;

/**
 * Created by banjich on 9/4/15.
 */
public class PostController extends Controller {

    private static final Form<Post> postForm = Form.form(Post.class);


    //final static Logger logger = LoggerFactory.getLogger(PostController.class);

    public Result addPost() {
        return ok(addNewPost.render(postForm));
    }

    public Result savePost() {
        Form<Post> boundForm = postForm.bindFromRequest();

        String email = session().get("username");
        User user = User.findByEmail(email);

        String title = boundForm.bindFromRequest().field("name").value();
        String message = boundForm.bindFromRequest().field("message").value();
        String mentor = boundForm.bindFromRequest().field("mentor").value();

        Integer visible = 0;
        if ("on".equals(mentor)) {
            visible = 1;
        }

        Logger.info(title + " " + message + " " + visible);

        Post post = new Post(message, title, visible, user);
        post.save();

        flash("success", "You successfully added new post.");
        return redirect("post/list");
    }

    public Result listPosts() {
        List<Post> posts = Post.findAllPosts();
        return ok(allPosts.render(Post.descOrder(posts)));
    }


}
