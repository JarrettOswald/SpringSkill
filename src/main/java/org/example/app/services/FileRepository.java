package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.FileInRepo;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileRepository implements ProjectRepository<FileInRepo> {

    private final Logger logger = Logger.getLogger(FileRepository.class);


    @Override
    public List<FileInRepo> retrieveAll() {
        String rootPath = System.getProperty("catalina.home");
        List<FileInRepo> result = new ArrayList<>();


        try {
            try (Stream<Path> walk = Files.walk(Paths.get(rootPath + File.separator + "external_uploads"))) {
                result = walk
                        .filter(Files::isRegularFile)
                        .map((path) -> new FileInRepo(path.hashCode(), String.valueOf(path.getFileName()), path, new File(String.valueOf(path)).length()))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void store(FileInRepo item) {

    }

    @Override
    public boolean removeItemById(int id) {
        return false;
    }
}
