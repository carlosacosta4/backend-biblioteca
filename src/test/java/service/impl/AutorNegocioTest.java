package service.impl;

import com.upc.biblioteca.entity.Autor;
import com.upc.biblioteca.service.impl.AutorNegocio;
import com.upc.biblioteca.repository.IAutorRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorNegocioTest {

    @InjectMocks
    private AutorNegocio autorNegocio;

    @Mock
    private IAutorRepositorio repositorio;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .idAutor(1L)
                .nombreAutor("Gabriel")
                .apellidoAutor("García")
                .biografiaAutor("Autor colombiano")
                .build();
    }

    @Test
    void listar_RetornaListaAutores() {
        when(repositorio.findAll()).thenReturn(List.of(autor));

        List<Autor> resultados = autorNegocio.listar();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Gabriel", resultados.get(0).getNombreAutor());
        verify(repositorio, times(1)).findAll();
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void listar_RetornaListaVacia() {
        when(repositorio.findAll()).thenReturn(List.of());

        List<Autor> resultados = autorNegocio.listar();

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty(), "La lista debe estar vacía cuando no hay autores");
        verify(repositorio, times(1)).findAll();
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void registrar_GuardaYRetornaAutor() {
        when(repositorio.save(any(Autor.class))).thenAnswer(inv -> {
            Autor a = inv.getArgument(0);
            a.setIdAutor(10L);
            return a;
        });

        Autor creado = autorNegocio.registrar(autor);

        assertNotNull(creado);
        assertEquals(10L, creado.getIdAutor());
        verify(repositorio, times(1)).save(any(Autor.class));
        verifyNoMoreInteractions(repositorio);
    }
}

