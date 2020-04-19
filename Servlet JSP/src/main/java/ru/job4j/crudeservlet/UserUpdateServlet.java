package ru.job4j.crudeservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class UserUpdateServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    private final UsersServlet usersList = new UsersServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        int userId = Integer.parseInt(req.getParameter("id"));
        String loginToUpdate = this.logic.findById(userId).getLogin();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Title</title>" +
                "</head>" +
                "<body>" +
                "<form action='" + req.getContextPath() + "/edit' method='post'>" +
                "Login : <input type='text' name='login' value='" + loginToUpdate + "'/>" +
                "<input type='hidden' name='id' value='" + userId + "'/>" +
                "<input type='submit' value='Update user'>" +
                "</form>" +
                "</body>" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login");
        User user = this.logic.findById(userId);
        user.setLogin(login);
        this.usersList.doGet(req, resp);
    }

}
