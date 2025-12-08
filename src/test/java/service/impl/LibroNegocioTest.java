package service.impl;

import com.upc.biblioteca.entity.Autor;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.service.impl.LibroNegocio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroNegocioTest {

    @Mock
    private ILibroRepositorio repositorio;

    @InjectMocks
    private LibroNegocio libroNegocio;

    private Libro libro1;
    private Libro libro2;
    private Autor autor1;
    private Autor autor2;

    @BeforeEach
    void setUp() {

        autor1 = new Autor(1L, "Gabriel", "García Márquez", "Escritor colombiano, autor de Cien años de soledad.");
        autor2 = new Autor(2L, "Miguel", "de Cervantes", "Escritor español, autor de Don Quijote de la Mancha.");

        libro1 = new Libro(1L,"Cien años de soledad", autor1, "978-6124743231",
                "Editorial Sudamericana", "1967",105L
                , "La magistral crónica de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.",
                "Realismo","uploads/libros/cien_anios_soledad.webp", 10L);

        libro2 = new Libro(2L,"Don Quijote de la Mancha", autor2, "978-8491050293",
                "Editorial Planeta", "1605", 863L,
                "La aventura del ingenioso hidalgo Don Quijote y su fiel escudero Sancho Panza en su lucha contra la injusticia.",
                "Aventura","uploads/libros/don_quijote_mancha.webp", 8L);
    }

    @Test
    void debeListarTodosLosLibros() {

        List<Libro> listaEsperada = Arrays.asList(libro1, libro2);

        when(repositorio.findAll()).thenReturn(listaEsperada);

        List<Libro> listaActual = libroNegocio.listar();

        assertNotNull(listaActual, "La lista no debe ser nula");
        assertEquals(2, listaActual.size(), "Debe retornar dos libros");
        assertEquals("Cien años de soledad", listaActual.get(0).getTituloLibro());

        verify(repositorio, times(1)).findAll();
    }

    @Test
    void debeBuscarLibroPorIsbnExistente() {

        String isbn = "978-6124743231";

        when(repositorio.findByIsbnLibro(isbn)).thenReturn(Optional.of(libro1));

        Libro encontrado = libroNegocio.buscarPorIsbn(isbn);

        assertNotNull(encontrado, "Debe encontrar el libro");
        assertEquals("Gabriel", encontrado.getAutor().getNombreAutor());
        verify(repositorio, times(1)).findByIsbnLibro(isbn);
    }

    @Test
    void debeRetornarNullSiIsbnNoExiste() {

        String isbnInexistente = "123-456";

        when(repositorio.findByIsbnLibro(isbnInexistente)).thenReturn(Optional.empty());

        Libro encontrado = libroNegocio.buscarPorIsbn(isbnInexistente);

        assertNull(encontrado, "Debe retornar null si no se encuentra el libro");
        verify(repositorio, times(1)).findByIsbnLibro(isbnInexistente);
    }

    @Test
    void debeBuscarLibrosPorTituloContiene() {

        String tituloBusqueda = "años";
        List<Libro> listaEsperada = List.of(libro1);

        when(repositorio.findByTituloLibroContainingIgnoreCase(tituloBusqueda))
                .thenReturn(listaEsperada);

        List<Libro> encontrados = libroNegocio.buscarPorTitulo(tituloBusqueda);

        assertNotNull(encontrados);
        assertEquals(1, encontrados.size(), "Debe encontrar solo un libro con 'años'");
        assertEquals("Cien años de soledad", encontrados.get(0).getTituloLibro());
        verify(repositorio, times(1)).findByTituloLibroContainingIgnoreCase(tituloBusqueda);
    }
}