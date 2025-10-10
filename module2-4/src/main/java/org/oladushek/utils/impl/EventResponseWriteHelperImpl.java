package org.oladushek.utils.impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.dto.EventDto;
import org.oladushek.dto.FileDto;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.EventEntity;
import org.oladushek.entity.UserEntity;
import org.oladushek.utils.ResponseWriteHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class EventResponseWriteHelperImpl implements ResponseWriteHelper<EventDto> {

    private final Gson gson = new Gson();


    @Override
    public void writeJsonResponseObject(HttpServletResponse resp, EventDto answer) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(answer));
        }
    }

    @Override
    public void writeJsonResponseList(HttpServletResponse resp, List<EventDto> answers) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print(Arrays.toString(answers.stream()
                    .map(event -> gson.toJson(event))
                    .toArray()));
        }

    }

    @Override
    public void writeJsonResponse(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(obj);
        }
    }

}
