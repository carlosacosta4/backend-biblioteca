package com.upc.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.biblioteca.dto.ErrorDto;
import com.upc.biblioteca.dto.LibroDetalleDto;
import com.upc.biblioteca.dto.LibroListDto;
import com.upc.biblioteca.dto.PrestamoRequestDto;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.service.impl.FileService;
import com.upc.biblioteca.service.ILibroNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> crearLibro( @RequestPart("file") MultipartFile file,
                            @ModelAttribute Libro libro) {
        Libro lb;
        try{

            String rutaImagen = lbFileService.guardarImagen(file);

            libro.setRutaImagenLibro(rutaImagen);

            Libro saved = lbLibroNegocio.registrar(libro);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (RuntimeException e) {
            // Error de negocio (ISBN duplicado)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorDto("Error de validación", e.getMessage()));
        } catch (Exception e){
            ErrorDto error = new ErrorDto("Error al crear el libro", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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
