package org.oladushek.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ResponseWriteHelper<T> {
    void writeJsonResponseObject(HttpServletResponse resp, T answer) throws IOException;

    void writeJsonResponseList(HttpServletResponse resp, List<T> answers) throws IOException;

    void writeJsonResponse(HttpServletResponse resp, Object obj) throws IOException;
}
