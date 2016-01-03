package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file/view")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = null;

        try {
            path = Paths.get(req.getParameter("path"));
            req.setAttribute("backLink", path.getParent());
            req.setAttribute("content", readFile(path));
            req.getRequestDispatcher("/WEB-INF/pages/fileContent.jsp").forward(req, resp);
        } catch (IOException e) {
            req.setAttribute("message", "Error read file ");
            req.getRequestDispatcher("/dir/view?path=" + path.getParent()).forward(req, resp);
        } catch (NullPointerException e) {
            req.setAttribute("message", "Not selected file");
            req.getRequestDispatcher("/dir/view").forward(req, resp);
        }
    }

    private static String readFile(Path path) throws IOException {
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }
}
