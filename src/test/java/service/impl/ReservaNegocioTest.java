package service.impl;

import com.upc.biblioteca.dto.ReservaDto;
import com.upc.biblioteca.entity.Autor;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.entity.Reserva;
import com.upc.biblioteca.entity.Rol;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.enums.LibroEstado;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.repository.IPrestamoRepositorio;
import com.upc.biblioteca.repository.IReservaRepositorio;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.impl.ReservaNegocio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservaNegocioTest {

    @InjectMocks
    private ReservaNegocio reservaNegocio;

    @Mock
    private IReservaRepositorio reservaRepositorio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private ILibroRepositorio libroRepositorio;

    @Mock
    private IPrestamoRepositorio prestamoRepositorio;

    private Usuario usuario;
    private Libro libro;
    private Reserva reservaExistente;
    private Prestamo prestamoExistente;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .idUsuario(1L)
                .nombres("Carlos")
                .apellidos("Acosta")
                .documentoIdentidad("12345678")
                .correoElectronico("carlos@example.com")
                .contrasenia("pwd")
                .rol(Rol.builder().idRol(1L).descripcion("USER").build())
                .build();

        Autor autor = Autor.builder().idAutor(1L).nombreAutor("Gabriel").apellidoAutor("García").build();

        libro = Libro.builder()
                .idLibro(1L)
                .tituloLibro("Cien años de soledad")
                .autor(autor)
                .isbnLibro("ISBN-001")
                .editorialLibro("Editorial X")
                .anioPublicacionLibro("1967")
                .nroPaginasLibro(417L)
                .sinopsisLibro("Sinopsis")
                .generoLibro("Novela")
                .rutaImagenLibro(null)
                .cantidadLibro(3L)
                .build();

        reservaExistente = Reserva.builder()
                .idReserva(10L)
                .usuario(usuario)
                .libro(libro)
                .estado(LibroEstado.RESERVADO)
                .build();

        prestamoExistente = Prestamo.builder()
                .idPrestamo(20L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now().minusDays(5))
                .fechaDevolucion(LocalDate.now().plusDays(5))
                .estado(LibroEstado.ACTIVO)
                .build();
    }

    @Test
    void registrarReserva_Exitoso_DecrementaCantidadYGuardaReserva() throws Exception {
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("ISBN-001")).thenReturn(Optional.of(libro));
        when(reservaRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of());
        when(prestamoRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of());
        when(libroRepositorio.save(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));
        when(reservaRepositorio.save(any(Reserva.class))).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setIdReserva(100L);
            return r;
        });

        Reserva resultado = reservaNegocio.registrarReserva("12345678", "ISBN-001");

        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdReserva());
        assertEquals(LibroEstado.RESERVADO, resultado.getEstado());
        ArgumentCaptor<Libro> captorLibro = ArgumentCaptor.forClass(Libro.class);
        verify(libroRepositorio, times(1)).save(captorLibro.capture());
        assertEquals(2L, captorLibro.getValue().getCantidadLibro());

        ArgumentCaptor<Reserva> captorReserva = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepositorio, times(1)).save(captorReserva.capture());
        assertEquals("ISBN-001", captorReserva.getValue().getLibro().getIsbnLibro());

        verifyNoMoreInteractions(usuarioRepositorio, libroRepositorio, reservaRepositorio, prestamoRepositorio);
    }

    @Test
    void registrarReserva_ErrorUsuarioNoEncontrado() {
        when(usuarioRepositorio.findByDocumentoIdentidad("9999")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () ->
                reservaNegocio.registrarReserva("9999", "ISBN-001"));

        assertEquals("Usuario no encontrado", ex.getMessage());
        verify(usuarioRepositorio, times(1)).findByDocumentoIdentidad("9999");
        verifyNoMoreInteractions(usuarioRepositorio);
    }

    @Test
    void registrarReserva_ErrorLibroNoEncontrado() {
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("NO-ISBN")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () ->
                reservaNegocio.registrarReserva("12345678", "NO-ISBN"));

        assertEquals("Libro no encontrado", ex.getMessage());
        verify(usuarioRepositorio, times(1)).findByDocumentoIdentidad("12345678");
        verify(libroRepositorio, times(1)).findByIsbnLibro("NO-ISBN");
    }

    @Test
    void registrarReserva_ErrorSinEjemplaresDisponibles() {
        libro.setCantidadLibro(0L);
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("ISBN-001")).thenReturn(Optional.of(libro));

        Exception ex = assertThrows(Exception.class, () ->
                reservaNegocio.registrarReserva("12345678", "ISBN-001"));

        assertEquals("No hay ejemplares disponibles de este libro", ex.getMessage());
        verify(libroRepositorio, times(1)).findByIsbnLibro("ISBN-001");
    }

    @Test
    void registrarReserva_ErrorYaExisteReservaParaMismoUsuarioYLibro() {
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("ISBN-001")).thenReturn(Optional.of(libro));
        when(reservaRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of(reservaExistente));

        Exception ex = assertThrows(Exception.class, () ->
                reservaNegocio.registrarReserva("12345678", "ISBN-001"));

        assertEquals("Ya existe una reserva para este libro y usuario", ex.getMessage());
        verify(reservaRepositorio, times(1)).findByUsuarioDocumento("12345678");
    }

    @Test
    void obtenerReservasPorDocumento_MapeaAReservaDto() throws Exception {
        Reserva r = Reserva.builder()
                .idReserva(55L)
                .usuario(usuario)
                .libro(libro)
                .estado(LibroEstado.RESERVADO)
                .build();

        when(reservaRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of(r));

        List<ReservaDto> dtos = reservaNegocio.obtenerReservasPorDocumento("12345678");

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        ReservaDto dto = dtos.get(0);
        assertEquals(55L, dto.getIdReserva());
        assertEquals("12345678", dto.getDocumentoUsuario());
        assertEquals("ISBN-001", dto.getIsbnLibro());
        assertEquals(LibroEstado.RESERVADO, dto.getEstado());

        verify(reservaRepositorio, times(1)).findByUsuarioDocumento("12345678");
    }
}
