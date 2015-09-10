package controllers;

import models.ErrorLog;
import models.User;

import org.joda.time.DateTime;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import com.avaje.ebean.Ebean;

import play.mvc.Security;
import utility.*;

import views.html.admin.addUser;
import views.html.user.user;
import views.html.users.email;
import views.html.users.login;
import views.html.users.register;
import views.html.admin.admin;
import views.html.admin.listUser;
import views.html.admin.errorLogs;
import views.html.user.editUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by boris.tomic on 01/09/15.
 */
public class UserController extends Controller {

    private static final Form<User> userForm = Form.form(User.class);

    /**
     * Renders login page with userForm. login form contains field for email
     * and password only and one log in button that send request to checkLogin
     * method.
     *
     * @return rendered login.scala.html view with userForm
     */
    public Result login() {
        return ok(login.render(userForm));
    }

    @Security.Authenticated(CurrentUser.class)
    public Result signOut() {
        session().clear();
        flash("success", "You successfully signed out");
        return redirect(routes.UserController.login());
    }

    /**
     * Popup window with one field for inputing email and one send button. If send
     * is pressed password reset link is sent to inputed email.
     *
     * @return rendered mail.scala.html
     */
    public Result sendEmail() {
        return ok(email.render());
    }

    /**
     * Checks login, reads from userForm all the values eg. email and password,
     * sends both to database for validation. If everything goes well user is
     * redirected to new page, otherwise user is redirected to login.
     * TODO validationz
     *
     * @return
     */
    public Result checkLogin() {


        Form<User> boundForm = userForm.bindFromRequest();

        if (boundForm.hasErrors()) {
            flash("warning", "Please correct the form.");
            return redirect("login");
        }

        String email = boundForm.bindFromRequest().field("email").value();
        String password = boundForm.bindFromRequest().field("password").value();

        String passwordHashed = MD5PasswordHash.getEncriptedPasswordMD5(password);

        User user = User.findUserByEmailAndPassword(email, passwordHashed);
        session("username", user.email);

        Logger.info(email + " " + password + " " + passwordHashed);


        if (user != null) {

            if (!user.status) {

                flash("success", String.format("User %s successfully logged in", user));
                return ok(register.render("Successful login"));

            } else if (user.role == 1) {

                flash("warning", "User not found under provided email and password.");
                return ok(admin.render());
            } else {

                return ok(addUser.render(userForm));
            }
        }

            flash("warning", "User not found under provided email and password.");
            return ok(login.render(userForm));

    }

    /**
     * Renders regiser page with userForm, page is used to add new user to database
     *
     * @return
     */
    public Result register() {
        return ok(addUser.render(userForm));
    }

    /**
     * Reads email and password from userForm, and sends data to database.
     * TODO validation
     *
     * @return
     */
    @Security.Authenticated(CurrentUser.class)
    public Result saveUser() {
        try {
            Form<User> boundForm = userForm.bindFromRequest();

            if (boundForm.hasErrors()) {

                flash("warning", "Please correct the form.");
                return redirect("register");
            }

            String email = boundForm.bindFromRequest().field("email").value();
            String password = boundForm.bindFromRequest().field("password").value();
            String tmpRole = boundForm.bindFromRequest().field("type").value();
            String passwordHashed = MD5PasswordHash.getEncriptedPasswordMD5(password);
            Integer role = Integer.parseInt(tmpRole);

            if (isValid(email) && isValidPassword(password)) {
                User user = new User(email, passwordHashed, role);

                if (user != null) {
                    user.save();

                    Logger.info(user.toString());

                    flash("success", String.format("User %s successfully added to database", user));
                    return ok(addUser.render(userForm));
                } else {
                    flash("warning", "Can not add user with provided email and password to database.");
                    return redirect("/");

                }
            } else {

                flash("warning", "Entered e-mail address is incorrect!");
                return ok(addUser.render(userForm));

            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
            ErrorLog el = new ErrorLog(e.getMessage());
            if (el != null) {
                el.save();
            }
            flash("warning", "Entered e-mail address is incorrect!");
            return ok(addUser.render(userForm));
        }
    }

    @Security.Authenticated(CurrentUser.class)
    public Result mainUser(){

        String email = session().get("username");

        return ok(user.render(email));
    }

    @Security.Authenticated(CurrentUser.class)
    public Result editUser() {
        try {
            String email = session().get("username");
            User user = User.findByEmail(email);
            return ok(editUser.render(user));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ok();
    }

    @Security.Authenticated(CurrentUser.class)
    public Result updateUser(){

        Logger.info("adsdadasdasdasd");
        return TODO;
    }

    @Security.Authenticated(AdminUser.class)
    public Result admin(){
        return ok(admin.render());
    }

    /**
     * Method for get list of user from Date Base
     * @return - view of user list
     */
    public Result list() {
        List<User> Users = User.findAll();
        return ok(listUser.render(Users));
    }

    public Result allErrors() {
        List<ErrorLog> errors = ErrorLog.findAllErrors();
        return ok(errorLogs.render(errors));
    }

    /**
     * Method for delete user , used only when user login like admin
     * @param id -Long id of selected user
     * @return - Information od method result
     */
    @Security.Authenticated(AdminUser.class)
    public Result deleteUser(Long id) {
        final User user = User.findUserById(id);
        if (user == null) {
            return notFound(String.format("User %s does not exists.", id));
        }
        User.remove(user);
        Ebean.delete(user);
        return redirect(routes.UserController.list());
    }

    /**
     * Checks if email is valid
     * TODO add more comments
     * TODO changename ofmethod
     * @param email
     * @return
     */
    public Boolean isValid(String email) {

        if (email.contains("@") && email.contains(".") && email.substring(email.indexOf("@"), email.length()).equals("@bitcamp.ba")) {

            return true;

        } else {

            return false;
        }

    }

    /**
     * TODO fix return
     * @param password
     * @return
     */
    public Boolean isValidPassword(String password) {
        char passOne[] = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            passOne[i] = password.charAt(i);
        }
        Logger.info(Arrays.toString(passOne));

        for (int i = 0; i < passOne.length; i++) {
            if (passOne[i] >= 33 && passOne[i] <= 126) {
                return true;
            }
        }
        return false;
    }

    public Result checkAdmin(){
        try {

            Form<User> boundForm = userForm.bindFromRequest();

            String email = session("username");
            User user = User.findByEmail(email);
            user.setFirstName(boundForm.bindFromRequest().field("firstName").value());
            user.lastName.equals(boundForm.bindFromRequest().field("lastName").value());
            user.setStatus(true);
            user.update();
            Ebean.update(user);
            Logger.info(user.toString());

            if (user != null) {
                if (user.role == 1) {

                    return ok(admin.render());

                } else {

                    return ok(addUser.render(userForm));
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
            ErrorLog el = new ErrorLog(e.getMessage());
            if (el != null) {
                el.save();
            }


            return ok(addUser.render(userForm));

        }

        return ok(addUser.render(userForm));

    }

    @Security.Authenticated(AdminUser.class)
    public Result deleteError(Long id) {
        final ErrorLog error = ErrorLog.findErrorById(id);
        if (error == null) {
            return notFound(String.format("Error %s does not exists.", id));
        }
        Ebean.delete(error);
        return redirect(routes.UserController.allErrors());
    }

}
