package com.upc.biblioteca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;


@Component
public class DataInitializer implements CommandLineRunner {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        if (datosExistentes()) {
            System.out.println("Los datos ya existen en las tablas. No se ejecutará el script.");
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Ejecutando data.sql en la base de datos...");
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
        }
    }

    private boolean datosExistentes() {

        int roles = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_rol", Integer.class);
        int usuarios = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_usuario", Integer.class);
        int autores = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_autor", Integer.class);
        int libros = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_libro", Integer.class);
        int prestamos = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_prestamo", Integer.class);


        System.out.println("Conteos - Roles: " + roles + ", Usuarios: " + usuarios + ", Autores: " + autores + ", Libros: " + libros + ", Prestamos: " + prestamos);
        return roles > 0 || usuarios > 0 || autores > 0 || libros > 0 || prestamos > 0;
    }
}