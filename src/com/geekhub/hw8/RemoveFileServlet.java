package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@WebServlet("/file/remove")
public class RemoveFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = null;
        String message;

        try {
            path = Paths.get(req.getParameter("path"));
            if(Files.isDirectory(path)) {
                deleteDirectory(path);
            } else {
                Files.delete(path);
            }
            message = path.toString() + " deleted";
        } catch (FileSystemException e) {
            message = "Error delete file";
        } catch (NullPointerException e) {
            message = "Error delete file";
            path = Paths.get("/");
        }

        req.setAttribute("message", message.replace("\\", "/"));
        req.getRequestDispatcher("/dir/view?path=" + path.getParent()).forward(req, resp);
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if(exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;
                }
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });

    }
}
