package ru.job4j.crudeservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    private final Map<String, Consumer<User>> dispatch = new HashMap<>();

    @Override
    public void init() throws ServletException {
        this.dispatch.put("add", this::add);
        this.dispatch.put("update", this::update);
        this.dispatch.put("delete", this::delete);
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<User> users = this.logic.findAll();
        if (users.size() > 0) {
            for (User user: users) {
                writer.append(user.toString());
            }
        } else {
            writer.append("Users list is empty");
        }
        writer.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        User user = new User(name, login, email);
        this.dispatch.get(action).accept(user);
    }

    private void add(User user) {
        this.logic.add(user.getId(), user);
    }

    private void update(User user) {
        this.logic.update(user.getId(), user);
    }

    private void delete(User user) {
        this.logic.delete(user.getId());
    }
}
