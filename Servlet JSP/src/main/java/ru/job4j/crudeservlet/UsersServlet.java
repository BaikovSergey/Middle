package ru.job4j.crudeservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    private final UserUpdateServlet update = new UserUpdateServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder stringBuilder = new StringBuilder("<table>");
        for (User user: this.logic.findAll()) {
            stringBuilder.append("<tr><td align='center'>" + user.getLogin() + "</td>"
                    + "<td><form action='" + req.getContextPath() + "/edit?id=" + user.getId() + "' method='post'>"
                    + " <input type='submit' value='update'>"
                    + "<input type='hidden' name='id' value='<%=id%>'/>"
                    + "</form></td>"
                    + "<td><form action='/' method='post'>"
                    + " <input type='button' value='delete'>"
                    + "<input type='hidden' name='id' value='<%=id%>'/>"
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
                "</body>" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.update.doPost(req, resp);
    }
}
