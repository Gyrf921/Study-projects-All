package org.oladushek.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.oladushek.config.HibernateConfig;
import org.oladushek.config.PropertiesConfig;
import org.oladushek.dto.FileDto;
import org.oladushek.entity.EventEntity;
import org.oladushek.entity.FileEntity;
import org.oladushek.entity.UserEntity;

import java.io.*;

import static org.oladushek.config.FlywayConfig.applyMigrations;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class FileServlet extends HttpServlet {

    private static final String UPLOAD_DIR = PropertiesConfig.getProperty("upload.dir");

    private Gson gson;

    @Override
    public void init() {
        applyMigrations();
        gson = new Gson();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String fileName = req.getParameter("name");
        try (PrintWriter out = resp.getWriter()) {
            System.out.println("GET /files?name=#");
            FileEntity file = HibernateConfig.getSessionFactory()
                    .fromTransaction(session ->
                            session.createSelectionQuery("from FileEntity where name = ?1", FileEntity.class)
                                    .setParameter(1, fileName)
                                    .getSingleResult());
            out.print(gson.toJson(new FileDto(file.getName(), file.getFilePath())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        File uploadDir = new File(UPLOAD_DIR);

        Long userId = Long.parseLong(req.getParameter("userId"));
        for (Part part : req.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                FileEntity uploadFile = new FileEntity(fileName, uploadDir.getAbsolutePath() + File.separator + fileName);
                part.write(uploadFile.getFilePath());

                HibernateConfig.getSessionFactory().inTransaction(session -> {
                            UserEntity user = session.find(UserEntity.class, userId);
                            session.persist(uploadFile);
                            session.persist(new EventEntity(user, uploadFile));
                        });

                try (PrintWriter out = resp.getWriter()) {
                    out.print(gson.toJson(uploadFile));
                }
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        File uploadDir = new File(UPLOAD_DIR);

        Long fileId = Long.parseLong(req.getParameter("id"));
        for (Part part : req.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                FileEntity updatedFile = HibernateConfig.getSessionFactory().fromTransaction(session -> {
                    FileEntity oldFile = session.find(FileEntity.class, fileId);
                    boolean isDeleted = new File(oldFile.getFilePath()).delete();
                    if (isDeleted) {
                        oldFile.setName(fileName);
                        oldFile.setFilePath(uploadDir.getAbsolutePath() + File.separator + fileName);
                    }
                    return oldFile;
                });

                part.write(updatedFile.getFilePath());

                try (PrintWriter out = resp.getWriter()) {
                    out.print(gson.toJson(updatedFile));
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Long fileId = Long.parseLong(req.getParameter("id"));

        boolean isSuccess = HibernateConfig.getSessionFactory()
                .fromTransaction(session -> {
                    FileEntity fileForDelete = session.find(FileEntity.class, fileId);
                    boolean isDeleted = new File(fileForDelete.getFilePath()).delete();
                    if (isDeleted)
                        session.remove(fileForDelete);

                    return isDeleted;
                });

        try (PrintWriter out = resp.getWriter()) {
            out.print(isSuccess);
        }
    }
}
