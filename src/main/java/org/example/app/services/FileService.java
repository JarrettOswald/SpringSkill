package org.example.app.services;

import org.example.web.dto.FileInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final Logger logger = Logger.getLogger(FileService.class);
    private final ProjectRepository<FileInRepo> fileRepo;


    @Autowired
    public FileService(ProjectRepository<FileInRepo> fileRepo) {
        logger.info("constructor: FileService");
        this.fileRepo = fileRepo;
    }

    public List<FileInRepo> getListFile(){
        return fileRepo.retrieveAll();
    }

    public Optional<FileInRepo> getFile(int id){
        return fileRepo.retrieveAll()
                .stream()
                .filter((file)-> file.getId() == id)
                .findFirst();
    }

}
