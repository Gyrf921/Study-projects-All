package org.oladushek.rest;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.service.EventService;
import org.oladushek.service.impl.EventServiceImpl;
import org.oladushek.utils.ResponseWriteHelper;
import org.oladushek.utils.impl.EventResponseWriteHelperImpl;

import java.io.IOException;

public class EventRestControllerV1 extends HttpServlet {

    private static final EventService eventService = new EventServiceImpl();
    private static final ResponseWriteHelper responseWriteHelper  = new EventResponseWriteHelperImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriteHelper.writeJsonResponseList(resp, eventService.getByAll());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long eventId = Long.parseLong(req.getParameter("id"));
        eventService.delete(eventId);
        responseWriteHelper.writeJsonResponseObject(resp, eventId);
    }
}
