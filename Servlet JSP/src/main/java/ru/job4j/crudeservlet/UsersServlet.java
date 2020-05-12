package ru.job4j.crudeservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UsersServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder stringBuilder = new StringBuilder("<table>");
        for (User user: this.logic.findAll()) {
            stringBuilder.append("<tr><td align='center'>" + user.getLogin() + "</td>"
                    + "<td><form action='" + req.getContextPath() + "/edit?id=" + user.getId() + "' method='get'>"
                    + "<input type='submit' value='update'>"
                    + "<input type='hidden' name='id' value='" + user.getId() + "'/>"
                    + "</form></td>"
                    + "<td><form action='"+ req.getContextPath() + "/list"+"' method='post'>"
                    + "<input type='submit' value='delete'>"
                    + "<input type='hidden' name='id' value='" + user.getId() + "'/>"
                    + "</form></td>"
                    + "</tr>");
        }
        stringBuilder.append("</table>");
        writer.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Title</title>" +
                "</head>" +
                "<body>" +
                stringBuilder.toString() +
                "<form action='" + req.getContextPath() + "/create' method='get'>" +
                "<input type='submit' value='Create new user'>" +
                "</body>" +
                "</html>");
        writer.flush();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        this.logic.delete(userId);
        doGet(req, resp);
    }
}
