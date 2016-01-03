package com.geekhub.hw8;

import com.geekhub.hw8.file.FileInformation;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/dir/view", initParams = {
    @WebInitParam(name = "root", value = "D:\\")
})
public class ViewDirectoryServlet extends HttpServlet {

    private static Path ROOT_PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ROOT_PATH = Paths.get(config.getInitParameter("root"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path currentPath;
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String path = req.getParameter("path");

        if (path == null || path.equals("null")) {
            currentPath = ROOT_PATH;
        } else {
            currentPath = Paths.get(path);
        }

        if (Files.exists(currentPath)) {
            req.setAttribute("fileList", createFileList(currentPath));
            req.setAttribute("currentPath", currentPath);
            req.getRequestDispatcher("/WEB-INF/pages/fileList.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "File: " + currentPath.toString().replace("\\", "/") + " not found");
            req.getRequestDispatcher("/dir/view?path=" + getLastExistDirectory(currentPath)).forward(req, resp);
        }
    }

    private List<FileInformation> createFileList(Path path) {
        List<FileInformation> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path filePath : stream) {
                fileList.add(new FileInformation(filePath));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  fileList;
    }

    private Path getLastExistDirectory(Path path) {
        while(path != null) {
            path = path.getParent();
            if (Files.exists(path) & Files.isDirectory(path)) {
                break;
            }
        }
        return path;
    }


}
