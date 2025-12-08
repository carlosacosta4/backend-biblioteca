package service.impl;

import com.upc.biblioteca.dto.LoginResponseDto;
import com.upc.biblioteca.entity.Rol;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.impl.UsuarioNegocio;
import com.upc.biblioteca.service.impl.JwtUtil;
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
public class UsuarioNegocioTest {

    @InjectMocks
    private UsuarioNegocio usuarioNegocio;

    @Mock
    private IUsuarioRepositorio repositorio;

    @Mock
    private JwtUtil jwtUtil;

    private Usuario usuario;

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
    }

    @Test
    void listar_RetornaListaUsuarios() {
        when(repositorio.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultados = usuarioNegocio.listar();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("12345678", resultados.get(0).getDocumentoIdentidad());
        verify(repositorio, times(1)).findAll();
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void registrar_GuardaYRetornaUsuario() {
        when(repositorio.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setIdUsuario(10L);
            return u;
        });

        Usuario creado = usuarioNegocio.registrar(usuario);

        assertNotNull(creado);
        assertEquals(10L, creado.getIdUsuario());
        verify(repositorio, times(1)).save(any(Usuario.class));
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void buscarPorDocumento_Existe_RetornaUsuario() {
        when(repositorio.findByDocumentoIdentidad("12345678")).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioNegocio.buscarPorDocumentoIdentidad("12345678");

        assertNotNull(encontrado);
        assertEquals("Carlos", encontrado.getNombres());
        verify(repositorio, times(1)).findByDocumentoIdentidad("12345678");
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void buscarPorDocumento_NoExiste_RetornaNull() {
        when(repositorio.findByDocumentoIdentidad("9999")).thenReturn(Optional.empty());

        Usuario resultado = usuarioNegocio.buscarPorDocumentoIdentidad("9999");

        assertNull(resultado);
        verify(repositorio, times(1)).findByDocumentoIdentidad("9999");
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void buscarUsuarios_RetornaCoincidencias() {
        String termino = "carlos";
        when(repositorio.findByNombresContainingIgnoreCaseOrCorreoElectronicoContainingIgnoreCase(termino, termino))
                .thenReturn(List.of(usuario));

        List<Usuario> encontrados = usuarioNegocio.buscarUsuarios(termino);

        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals("carlos@example.com", encontrados.get(0).getCorreoElectronico());
        verify(repositorio, times(1))
                .findByNombresContainingIgnoreCaseOrCorreoElectronicoContainingIgnoreCase(termino, termino);
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void login_CredencialesValidas_RetornaTokenYDatos() {
        when(repositorio.findByCorreoElectronico("carlos@example.com")).thenReturn(usuario);
        when(jwtUtil.generateToken("carlos@example.com")).thenReturn("token-123");

        LoginResponseDto resp = usuarioNegocio.login("carlos@example.com", "pwd");

        assertNotNull(resp);
        assertNotNull(resp.getEncabezado());
        assertTrue(resp.getEncabezado().isResultado());
        assertEquals("token-123", resp.getToken());
        assertEquals("Carlos", resp.getNombres());
        assertEquals("Acosta", resp.getApellidos());
        assertEquals(usuario.getRol(), resp.getRol());

        verify(repositorio, times(1)).findByCorreoElectronico("carlos@example.com");
        verify(jwtUtil, times(1)).generateToken("carlos@example.com");
        verifyNoMoreInteractions(repositorio, jwtUtil);
    }

    @Test
    void login_CredencialesInvalidas_RetornaErrorEnEncabezado() {
        when(repositorio.findByCorreoElectronico("carlos@example.com")).thenReturn(usuario);

        LoginResponseDto resp = usuarioNegocio.login("carlos@example.com", "wrongpwd");

        assertNotNull(resp);
        assertNotNull(resp.getEncabezado());
        assertFalse(resp.getEncabezado().isResultado());
        assertEquals("Credenciales no coinciden", resp.getEncabezado().getMensaje());

        verify(repositorio, times(1)).findByCorreoElectronico("carlos@example.com");
        verifyNoMoreInteractions(repositorio);
    }
}
