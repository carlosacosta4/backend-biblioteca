package com.upc.biblioteca.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public String guardarImagen(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("El archivo de imagen está vacío o no existe");
        }

        String originalName = file.getOriginalFilename() != null
                ? Paths.get(file.getOriginalFilename()).getFileName().toString()
                : "file";

        Path uploadDir = Paths.get("uploads", "libros");
        Files.createDirectories(uploadDir);

        String fileName = System.currentTimeMillis() + "_" + originalName;
        Path filePath = uploadDir.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return filePath.toString().replace(File.separatorChar, '/');
    }
}

