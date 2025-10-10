package org.oladushek.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.oladushek.entity.FileEntity;
import org.oladushek.service.FileService;
import org.oladushek.service.impl.FileServiceImpl;
import org.oladushek.utils.ResponseWriteHelper;
import org.oladushek.utils.impl.FileResponseWriteHelperImpl;

import java.io.*;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class FileRestControllerV1 extends HttpServlet {

    private static final FileService fileService = new FileServiceImpl();
    private static final ResponseWriteHelper responseWriteHelper  = new FileResponseWriteHelperImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriteHelper.writeJsonResponseObject(resp, fileService.getByName(req.getParameter("name")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        for (Part part : req.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                FileEntity uploadFile = fileService.create(fileName, Long.parseLong(req.getParameter("userId")));

                if (uploadFile != null) {
                    part.write(uploadFile.getFilePath());
                    responseWriteHelper.writeJsonResponseObject(resp, uploadFile);
                }
                responseWriteHelper.writeJsonResponse(resp, "User not found");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        for (Part part : req.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                FileEntity updatedFile = fileService.update(Long.parseLong(req.getParameter("id")), fileName);

                if (updatedFile != null) {
                    part.write(updatedFile.getFilePath());
                    responseWriteHelper.writeJsonResponseObject(resp, updatedFile);
                }
                responseWriteHelper.writeJsonResponse(resp, "File not found");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriteHelper.writeJsonResponse(resp, fileService.delete(Long.parseLong(req.getParameter("id"))));
    }
}
