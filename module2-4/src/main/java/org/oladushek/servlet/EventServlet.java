package org.oladushek.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.config.HibernateConfig;
import org.oladushek.dto.EventDto;
import org.oladushek.dto.FileDto;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.EventEntity;
import org.oladushek.entity.FileEntity;
import org.oladushek.entity.UserEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.oladushek.config.FlywayConfig.applyMigrations;

public class EventServlet extends HttpServlet {

    private Gson gson;

    @Override
    public void init() {
        applyMigrations();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            System.out.println("GET /events");
            List<EventDto> events = HibernateConfig.getSessionFactory()
                    .fromTransaction(session ->
                            session.createSelectionQuery("from EventEntity", EventEntity.class)
                                    .list().stream()
                                    .map(entity -> new EventDto(new UserDto(entity.getUser()), new FileDto(entity.getFile())))
                                    .toList()
                    );

            out.print(gson.toJson(events));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Long eventId = Long.parseLong(req.getParameter("id"));

        HibernateConfig.getSessionFactory().inTransaction(
                session -> session.remove(session.getReference(EventDto.class, eventId)));

        try (PrintWriter out = resp.getWriter()) {
            out.print(eventId);
        }
    }


}
