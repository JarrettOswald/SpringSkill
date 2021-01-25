package org.example.web.dto;

import java.nio.file.Path;
import java.util.Objects;

public class FileInRepo {
    private int id;
    private String name;
    private Path path;
    private long length;

    public FileInRepo(int id, String name, Path path, long length) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInRepo that = (FileInRepo) o;
        return length == that.length &&
                name.equals(that.name) &&
                path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, length);
    }
}
