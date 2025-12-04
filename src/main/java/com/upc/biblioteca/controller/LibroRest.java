package com.upc.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.biblioteca.dto.ErrorDto;
import com.upc.biblioteca.dto.LibroDetalleDto;
import com.upc.biblioteca.dto.LibroListDto;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.service.impl.FileService;
import com.upc.biblioteca.service.ILibroNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/libros/buscar")
    public ResponseEntity<?> buscarLibros(@RequestParam String titulo) {
        List<Libro> libros = lbLibroNegocio.buscarPorTitulo(titulo);
        if (libros.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorDto("Libro no encontrado", "No se encontraron libros con el término proporcionado."));
        }
        return ResponseEntity.ok(libros);
    }


    @GetMapping("/libros/catalogo")
    public ResponseEntity<List<LibroListDto>> listarLibros() {
        List<LibroListDto> lista = lbLibroNegocio.listar().stream()
                .map(l -> new LibroListDto(
                        l.getRutaImagenLibro(),
                        l.getTituloLibro(),
                        l.getAutor() != null
                                ? (safeTrim(l.getAutor().getNombreAutor()) + " " + safeTrim(l.getAutor().getApellidoAutor())).trim()
                                : null,
                        l.getIsbnLibro()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    @GetMapping("/libro/detalle/{isbnLibro}")
    public ResponseEntity<LibroDetalleDto> obtenerDetallePorIsbn(@PathVariable String isbnLibro) {
        Libro libro = lbLibroNegocio.buscarPorIsbn(isbnLibro);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        LibroDetalleDto detalleDto = new LibroDetalleDto(
                libro.getIsbnLibro(),
                libro.getTituloLibro(),
                libro.getAutor() != null
                        ? libro.getAutor().getNombreAutor() + " " + libro.getAutor().getApellidoAutor()
                        : null,
                libro.getRutaImagenLibro(),
                libro.getEditorialLibro(),
                libro.getAnioPublicacionLibro(),
                libro.getNroPaginasLibro(),
                libro.getSinopsisLibro(),
                libro.getGeneroLibro(),
                libro.getCantidadLibro()
        );
        return ResponseEntity.ok(detalleDto);
    }

}
