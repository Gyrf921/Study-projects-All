package org.oladushek.utils.impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.oladushek.dto.FileDto;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.FileEntity;
import org.oladushek.utils.ResponseWriteHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class FileResponseWriteHelperImpl implements ResponseWriteHelper<FileEntity> {

    private final Gson gson = new Gson();

    @Override
    public void writeJsonResponseObject(HttpServletResponse resp, FileEntity fileEntity) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(new FileDto(fileEntity)));
        }
    }

    @Override
    public void writeJsonResponseList(HttpServletResponse resp, List<FileEntity> answers) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(Arrays.toString(answers.stream()
                    .map(file -> gson.toJson(new FileDto(file)))
                    .toArray()));
        }
    }

    @Override
    public void writeJsonResponse(HttpServletResponse resp, Object isSuccess) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(isSuccess);
        }
    }

}
