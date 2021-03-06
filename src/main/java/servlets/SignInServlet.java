package servlets;

import accounts.AccountService;
import database.DBException;
import database.DBService;
import dataset.UserDataSet;
import main.Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }
    // authorize
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (login == null || pass == null){
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }
//        UserDataSet userProfile = accountService.getUserByLogin(login);

        UserDataSet userProfile = null;
        try {
            userProfile = Main.dbService.signIn(login, pass);
        } catch (DBException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        System.out.println(userProfile);
        if (userProfile == null || !userProfile.getPassword().equals(pass)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().print("Unauthorized");
            return;
        }
        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print("Authorized: " + login);


    }
}
