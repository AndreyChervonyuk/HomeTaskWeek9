package com.geekhub.hw8.file;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileInformation {
    private String name;
    private String path;
    private Type type;

    public FileInformation(Path path) {
        this.name = path.getFileName().toString();
        this.path = path.toString();
        if (Files.isDirectory(path)) {
            this.type = Type.DIRECTORY;
        } else {
            this.type = Type.FILE;
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }
}
