package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/file/create")
public class CreateFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = req.getParameter("path") + "\\" + req.getParameter("fileName");
        String type = req.getParameter("type");

        try {
            switch (type) {
                case  "file" :
                    Files.createFile(Paths.get(filePath));
                    break;
                case "directory" :
                    Files.createDirectories(Paths.get(filePath));
                    break;
            }
        } catch (Exception e) {
            req.setAttribute("message", "Error create new file " + e.getClass().getSimpleName());
        }

        req.getRequestDispatcher("/dir/view?path=" + req.getParameter("path")).forward(req, resp);
    }
}
