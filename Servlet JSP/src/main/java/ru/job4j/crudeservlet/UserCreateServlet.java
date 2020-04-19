package ru.job4j.crudeservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class UserCreateServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Title</title>" +
                "</head>" +
                "<body>" +
                "<form action='" + req.getContextPath() + "/create' method='post'>" +
                "Login : <input type='text' name='login'/>" +
                "<input type='submit' value='Add user'>" +
                "</form>" +
                "<form action='" + req.getContextPath() + "/list' method='get'>" +
                "<input type='submit' value='List of users'>" +
                "</form>" +
                "</body>" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String name = "John Doe";
        String login = req.getParameter("login");
        String email = "johndoe@gmail.com";
        User user = new User(name, login, email);
        this.logic.add(user.getId(), user);
        if (!login.isEmpty()) {
            writer.append("User added!");
        }
        writer.flush();
        doGet(req, resp);
    }
}
