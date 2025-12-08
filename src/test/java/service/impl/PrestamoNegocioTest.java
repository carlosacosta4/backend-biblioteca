package service.impl;

import com.upc.biblioteca.dto.PrestamoDto;
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
import com.upc.biblioteca.service.impl.PrestamoNegocio;
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
public class PrestamoNegocioTest {

    @InjectMocks
    private PrestamoNegocio prestamoNegocio;

    @Mock
    private IPrestamoRepositorio prestamoRepositorio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private IReservaRepositorio reservaRepositorio;

    @Mock
    private ILibroRepositorio libroRepositorio;

    private Usuario usuario;
    private Libro libro;
    private Prestamo prestamoExistente;
    private Reserva reservaExistente;

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

        prestamoExistente = Prestamo.builder()
                .idPrestamo(20L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now().minusDays(5))
                .fechaDevolucion(LocalDate.now().plusDays(5))
                .estado(LibroEstado.ACTIVO)
                .build();

        reservaExistente = Reserva.builder()
                .idReserva(10L)
                .usuario(usuario)
                .libro(libro)
                .estado(LibroEstado.RESERVADO)
                .build();
    }

    @Test
    void registrarPorDocumentoEIsbn_ErrorUsuarioNoEncontrado() {
        when(usuarioRepositorio.findByDocumentoIdentidad("9999")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () ->
                prestamoNegocio.registrarPorDocumentoEIsbn("9999", "ISBN-001", LocalDate.now(), LocalDate.now().plusDays(7)));

        assertEquals("Usuario no encontrado", ex.getMessage());
        verify(usuarioRepositorio, times(1)).findByDocumentoIdentidad("9999");
        verifyNoMoreInteractions(usuarioRepositorio);
    }

    @Test
    void registrarPorDocumentoEIsbn_ErrorLibroNoEncontrado() {
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("NO-ISBN")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () ->
                prestamoNegocio.registrarPorDocumentoEIsbn("12345678", "NO-ISBN", LocalDate.now(), LocalDate.now().plusDays(7)));

        assertEquals("Libro no encontrado", ex.getMessage());
        verify(usuarioRepositorio, times(1)).findByDocumentoIdentidad("12345678");
        verify(libroRepositorio, times(1)).findByIsbnLibro("NO-ISBN");
    }

    @Test
    void registrarPorDocumentoEIsbn_ErrorSinEjemplaresDisponibles() {
        libro.setCantidadLibro(0L);
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("ISBN-001")).thenReturn(Optional.of(libro));

        Exception ex = assertThrows(Exception.class, () ->
                prestamoNegocio.registrarPorDocumentoEIsbn("12345678", "ISBN-001", LocalDate.now(), LocalDate.now().plusDays(7)));

        assertEquals("No hay ejemplares disponibles de este libro", ex.getMessage());
        verify(libroRepositorio, times(1)).findByIsbnLibro("ISBN-001");
    }

    @Test
    void registrarPorDocumentoEIsbn_ErrorYaExisteReserva() {
        when(usuarioRepositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));
        when(libroRepositorio.findByIsbnLibro("ISBN-001")).thenReturn(Optional.of(libro));
        when(prestamoRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of());
        when(reservaRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of(reservaExistente));

        Exception ex = assertThrows(Exception.class, () ->
                prestamoNegocio.registrarPorDocumentoEIsbn("12345678", "ISBN-001", LocalDate.now(), LocalDate.now().plusDays(7)));

        assertEquals("Ya existe una reserva para este libro y usuario", ex.getMessage());
        verify(reservaRepositorio, times(1)).findByUsuarioDocumento("12345678");
    }

    @Test
    void obtenerPrestamosPorDocumento_MapeaAPrestamoDto() throws Exception {
        Prestamo p = Prestamo.builder()
                .idPrestamo(55L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now().minusDays(3))
                .fechaDevolucion(LocalDate.now().plusDays(4))
                .estado(LibroEstado.ACTIVO)
                .build();

        when(prestamoRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of(p));

        List<PrestamoDto> dtos = prestamoNegocio.obtenerPrestamosPorDocumento("12345678");

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        PrestamoDto dto = dtos.get(0);
        assertEquals(55L, dto.idPrestamo());
        assertEquals("12345678", dto.documentoUsuario());
        assertEquals("Cien años de soledad", dto.tituloLibro());
        assertEquals(LibroEstado.ACTIVO, dto.estado());

        verify(prestamoRepositorio, times(1)).findByUsuarioDocumento("12345678");
    }

    @Test
    void actualizarEstadoPrestamos_CambiaActivoAVencido() {
        Prestamo activoVencido = Prestamo.builder()
                .idPrestamo(1L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now().minusDays(20))
                .fechaDevolucion(LocalDate.now().minusDays(1))
                .estado(LibroEstado.ACTIVO)
                .build();

        Prestamo activoNoVencido = Prestamo.builder()
                .idPrestamo(2L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucion(LocalDate.now().plusDays(5))
                .estado(LibroEstado.ACTIVO)
                .build();

        when(prestamoRepositorio.findAll()).thenReturn(List.of(activoVencido, activoNoVencido));
        when(prestamoRepositorio.save(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        prestamoNegocio.actualizarEstadoPrestamos();

        ArgumentCaptor<Prestamo> captor = ArgumentCaptor.forClass(Prestamo.class);
        verify(prestamoRepositorio, atLeastOnce()).save(captor.capture());
        List<Prestamo> guardados = captor.getAllValues();

        boolean encontroVencido = guardados.stream().anyMatch(p -> p.getIdPrestamo().equals(1L) && p.getEstado() == LibroEstado.VENCIDO);
        assertTrue(encontroVencido, "Se debe actualizar el préstamo 1 a VENCIDO");

        // No debe actualizar el que aún no vence a VENCIDO
        boolean encuentroNoVencido = guardados.stream().anyMatch(p -> p.getIdPrestamo().equals(2L) && p.getEstado() == LibroEstado.VENCIDO);
        assertFalse(encuentroNoVencido, "El préstamo 2 no debe convertirse a VENCIDO");
    }

    @Test
    void devolverPrestamo_Exitoso_AumentaCantidadYMarcaDevuelto() throws Exception {
        Prestamo prestamo = Prestamo.builder()
                .idPrestamo(30L)
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now().minusDays(10))
                .fechaDevolucion(LocalDate.now().plusDays(1))
                .estado(LibroEstado.ACTIVO)
                .build();

        when(prestamoRepositorio.findByUsuarioDocumento("12345678")).thenReturn(List.of(prestamo));
        when(libroRepositorio.save(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));
        when(prestamoRepositorio.save(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        prestamoNegocio.devolverPrestamo("12345678", "ISBN-001");

        ArgumentCaptor<Libro> captorLibro = ArgumentCaptor.forClass(Libro.class);
        verify(libroRepositorio, times(1)).save(captorLibro.capture());
        assertEquals(4L, captorLibro.getValue().getCantidadLibro());

        ArgumentCaptor<Prestamo> captorPrestamo = ArgumentCaptor.forClass(Prestamo.class);
        verify(prestamoRepositorio, times(1)).save(captorPrestamo.capture());
        assertEquals(LibroEstado.DEVUELTO, captorPrestamo.getValue().getEstado());
    }

}
