package org.oladushek.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.config.HibernateConfig;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.UserEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.oladushek.config.FlywayConfig.applyMigrations;

public class UserServlet extends HttpServlet {

    private Gson gson;
    @Override
    public void init()  {
        applyMigrations();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idFromPath = req.getPathInfo();
        try (PrintWriter out = resp.getWriter()) {
            if (idFromPath == null || idFromPath.equals("/")) {
                System.out.println("GET /users");
                List<UserEntity> users = HibernateConfig.getSessionFactory()
                        .fromTransaction(session ->  session.createSelectionQuery("from UserEntity", UserEntity.class).list());
                out.print(Arrays.toString(users.stream()
                        .map(user -> gson.toJson(new UserDto(user.getId(), user.getName())))
                        .toArray()));
            }
            else {
                System.out.println("GET /users/{id}");
                Long userId = Long.parseLong(idFromPath.replace("/", ""));
                UserEntity user = HibernateConfig.getSessionFactory()
                        .fromTransaction(session ->  session.find(UserEntity.class, userId));
                out.print(gson.toJson(new UserDto(user.getId(), user.getName())));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("name");

        UserEntity user = HibernateConfig.getSessionFactory().fromTransaction(session -> {
            UserEntity userForSave = new UserEntity(userName);
            session.persist(userForSave);
            return userForSave;
        });

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(new UserDto(user.getId(), user.getName())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Long userId = Long.parseLong(req.getParameter("id"));
        String userName = req.getParameter("name");

        UserEntity user = HibernateConfig.getSessionFactory().fromTransaction(session -> {
            UserEntity userForUpdate = session.find(UserEntity.class, userId);
            userForUpdate.setName(userName);
            session.persist(userForUpdate);
            return userForUpdate;
        });

        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(new UserDto(user.getId(), user.getName())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Long userId = Long.parseLong(req.getParameter("id"));

        HibernateConfig.getSessionFactory().inTransaction(session -> session.remove(session.getReference(UserEntity.class, userId)));

        try (PrintWriter out = resp.getWriter()) {
            out.print(userId);
        }
    }
}
