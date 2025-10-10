package org.oladushek.utils.impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.UserEntity;
import org.oladushek.utils.ResponseWriteHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class UserResponseWriteHelperImpl implements ResponseWriteHelper<UserEntity> {

    private final Gson gson = new Gson();


    @Override
    public void writeJsonResponseObject(HttpServletResponse resp, UserEntity answer) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(new UserDto(answer)));
        }
    }

    @Override
    public void writeJsonResponseList(HttpServletResponse resp, List<UserEntity> answers) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            out.print(Arrays.toString(answers.stream()
                    .map(user -> gson.toJson(new UserDto(user)))
                    .toArray()));
        }

    }

    @Override
    public void writeJsonResponse(HttpServletResponse resp, Object id) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(id);
        }
    }

}
