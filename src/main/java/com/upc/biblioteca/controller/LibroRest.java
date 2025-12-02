package com.upc.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.biblioteca.dto.LibroDto;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.service.impl.FileService;
import com.upc.biblioteca.service.ILibroNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibroRest {

    @Autowired
    public ILibroNegocio lbLibroNegocio;

    @Autowired
    private FileService lbFileService;

    @GetMapping("/libros")
    public List<Libro> obtenerLibros() {
        return lbLibroNegocio.listar();
    }

    @PostMapping(value = "/libro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void crearLibro( @RequestPart("file") MultipartFile file,
                            @RequestPart("libro") String libroRequest) {
        Libro lb;
        try{

            ObjectMapper mapper = new ObjectMapper();
            Libro libro = mapper.readValue(libroRequest, Libro.class);

            String rutaImagen = lbFileService.guardarImagen(file);

            libro.setRutaImagenLibro(rutaImagen);

            lb = lbLibroNegocio.registrar(libro);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/libro/isbn/{isbnLibro}")
    public ResponseEntity<LibroDto> obtenerPorIsbn(@PathVariable String isbnLibro) {
        Libro libro = lbLibroNegocio.buscarPorIsbn(isbnLibro);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        LibroDto dto = new LibroDto(libro.getIsbnLibro(), libro.getTituloLibro(), libro.getAutor());
        return ResponseEntity.ok(dto);
    }
}
