package com.upc.biblioteca.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.biblioteca.entidades.Autor;
import com.upc.biblioteca.entidades.Libro;
import com.upc.biblioteca.negocio.FileService;
import com.upc.biblioteca.negocio.IAutorNegocio;
import com.upc.biblioteca.negocio.ILibroNegocio;
import com.upc.biblioteca.repositorio.ILibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

}
