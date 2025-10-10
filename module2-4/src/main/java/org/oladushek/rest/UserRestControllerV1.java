package org.oladushek.rest;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.service.UserService;
import org.oladushek.service.impl.UserServiceImpl;
import org.oladushek.utils.ResponseWriteHelper;
import org.oladushek.utils.impl.UserResponseWriteHelperImpl;

import java.io.IOException;

public class UserRestControllerV1 extends HttpServlet {

    private static final UserService userService = new UserServiceImpl();
    private static final ResponseWriteHelper responseWriteHelper  = new UserResponseWriteHelperImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idFromPath = req.getPathInfo();
        if (idFromPath == null || idFromPath.equals("/")) {
            System.out.println("GET /users");
            responseWriteHelper.writeJsonResponseList(resp, userService.getByAll());
        }
        else {
            System.out.println("GET /users/{id}");
            responseWriteHelper.writeJsonResponseObject(resp,
                    userService.getById(Long.parseLong(idFromPath.replace("/", ""))));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriteHelper.writeJsonResponseObject(resp, userService.create(req.getParameter("name")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriteHelper.writeJsonResponseObject(resp, userService.update(Long.parseLong(req.getParameter("id")), req.getParameter("name")));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        userService.delete(userId);
        responseWriteHelper.writeJsonResponse(resp, userId);
    }
}
