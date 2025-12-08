package service.impl;

import com.upc.biblioteca.entity.Rol;
import com.upc.biblioteca.service.impl.RolNegocio;
import com.upc.biblioteca.repository.IRolRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolNegocioTest {

    @InjectMocks
    private RolNegocio rolNegocio;

    @Mock
    private IRolRepositorio repositorio;

    private Rol rolAdmin;

    @BeforeEach
    void setUp() {
        rolAdmin = Rol.builder()
                .idRol(1L)
                .descripcion("ADMIN")
                .build();
    }

    @Test
    void listar_RetornaListaRoles() {
        when(repositorio.findAll()).thenReturn(List.of(rolAdmin));

        List<Rol> resultados = rolNegocio.listar();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("ADMIN", resultados.get(0).getDescripcion());
        verify(repositorio, times(1)).findAll();
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void listar_RetornaListaVacia() {
        when(repositorio.findAll()).thenReturn(List.of());

        List<Rol> resultados = rolNegocio.listar();

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty(), "La lista debe estar vacía cuando no hay roles");
        verify(repositorio, times(1)).findAll();
        verifyNoMoreInteractions(repositorio);
    }
}
