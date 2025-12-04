
-- Roles
INSERT INTO tbl_rol (descripcion) VALUES
    ('ADMINISTRADOR'),
    ('LECTOR'),
    ('BIBLIOTECARIO');

-- Usuarios
INSERT INTO tbl_usuario (nombres, apellidos, correo_electronico, contrasenia, documento_identidad, id_rol) VALUES
    --ADMINISTRADOR
    ('Carlos', 'Acosta Andrade', 'carlosacosta@gmail.com', 'carlos123','78965412', 1),

    --LECTOR
    ('Mery', 'Andia Quiroga', 'meryandia@gmail.com', 'mery123', '67543456', 2),
    ('Andres', 'Gallegos Luque', 'andresgallegos@gmail.com', 'andres123','45612378', 2),

    --BIBLIOTECARIO
    ('Luis', 'Santos Medina', 'luissantos@gmail.com', 'luis123', '12378945', 3);


-- Autores
INSERT INTO tbl_autor (nombre_autor, apellido_autor, biografia_autor) VALUES
    ('Mario', 'Vargas Llosa', 'Escritor peruano, ganador del Premio Nobel de Literatura.'),
    ('Ciro', 'Alegría', 'Escritor peruano, autor de "El mundo es ancho y ajeno".'),
    ('José María', 'Arguedas', 'Escritor peruano, conocido por "Los ríos profundos".'),
    ('Gabriel', 'García Márquez', 'Escritor colombiano, autor de "Cien años de soledad".'),
    ('Isabel', 'Allende', 'Escritora chilena, conocida por "La casa de los espíritus".'),
    ('Jorge Luis', 'Borges', 'Escritor argentino, maestro del cuento y la poesía.'),
    ('Julio', 'Cortázar', 'Escritor argentino, autor de "Rayuela".'),
    ('César', 'Vallejo', 'Poeta peruano, autor de "Los heraldos negros".'),
    ('Ricardo', 'Palma', 'Escritor peruano, conocido por "Tradiciones Peruanas".'),
    ('Clorinda', 'Matto de Turner', 'Escritora peruana, autora de "Aves sin nido".');

-- Libros
-- Libros
INSERT INTO tbl_libro (titulo_libro, anio_publicacion_libro, cantidad_libro, editorial_libro, genero_libro, isbn_libro, nro_paginas_libro, ruta_imagen_libro, sinopsis_libro, id_autor) VALUES
    ('Cien años de soledad', 1967, 10, 'Editorial Sudamericana', 'Realismo mágico', '978-0060883287', 417, '/imagenes/cien_anos_de_soledad.jpg', 'Una obra maestra que narra la historia de la familia Buendía.', 4),
    ('La casa de los espíritus', 1982, 8, 'Editorial Plaza & Janés', 'Realismo mágico', '978-0553383805', 448, '/imagenes/la_casa_de_los_espiritus.jpg', 'Una saga familiar que mezcla lo real y lo sobrenatural.', 5),
    ('Rayuela', 1963, 5, 'Editorial Sudamericana', 'Ficción', '978-8435008880', 736, '/imagenes/rayuela.jpg', 'Una novela experimental que invita al lector a elegir su propio camino.', 7),
    ('El mundo es ancho y ajeno', 1941, 7, 'Editorial Losada', 'Novela social', '978-8420635748', 600, '/imagenes/el_mundo_es_ancho.jpg', 'Una obra que retrata la vida de los campesinos peruanos.', 2),
    ('Los ríos profundos', 1958, 6, 'Editorial Losada', 'Novela social', '978-8437600891', 256, '/imagenes/los_rios_profundos.jpg', 'Una novela que explora la identidad cultural peruana.', 3),
    ('Los heraldos negros', 1919, 4, 'Editorial Emece', 'Poesía', '978-9876123456', 120, '/imagenes/los_heraldos_negros.jpg', 'Una colección de poemas que reflejan el dolor humano.', 8),
    ('Tradiciones Peruanas', 1872, 9, 'Editorial Universo', 'Tradiciones', '978-9876543210', 320, '/imagenes/tradiciones_peruanas.jpg', 'Relatos que narran la historia y cultura del Perú.', 9),
    ('Aves sin nido', 1889, 3, 'Editorial Universo', 'Novela social', '978-9876543227', 240, '/imagenes/aves_sin_nido.jpg', 'Una novela que denuncia las injusticias sociales en el Perú.', 10),
    ('Conversación en La Catedral', 1969, 6, 'Editorial Seix Barral', 'Novela', '978-8432226782', 608, '/imagenes/conversacion_en_la_catedral.jpg', 'Una novela que explora la corrupción y la dictadura en el Perú.', 1),
    ('El zorro de arriba y el zorro de abajo', 1971, 4, 'Editorial Losada', 'Novela', '978-8437600907', 320, '/imagenes/el_zorro.jpg', 'Una obra que refleja los cambios sociales en los Andes.', 3),
    ('Doce cuentos peregrinos', 1992, 7, 'Editorial Mondadori', 'Cuentos', '978-0307957603', 208, '/imagenes/doce_cuentos_peregrinos.jpg', 'Una colección de cuentos que exploran lo mágico y lo real.', 4),
    ('Paula', 1994, 5, 'Editorial Plaza & Janés', 'Memorias', '978-0061564901', 368, '/imagenes/paula.jpg', 'Un relato autobiográfico lleno de emociones.', 5),
    ('Ficciones', 1944, 8, 'Editorial Emecé', 'Cuentos', '978-9875661234', 224, '/imagenes/ficciones.jpg', 'Una colección de cuentos que desafían la realidad.', 6),
    ('Final del juego', 1956, 6, 'Editorial Sudamericana', 'Cuentos', '978-8437600914', 192, '/imagenes/final_del_juego.jpg', 'Una obra que explora la imaginación y la realidad.', 7),
    ('Poemas humanos', 1939, 3, 'Editorial Losada', 'Poesía', '978-9876549876', 160, '/imagenes/poemas_humanos.jpg', 'Una colección de poemas que reflejan la lucha y el dolor humano.', 8),
    ('Piedra y cielo', 1919, 4, 'Editorial Universo', 'Poesía', '978-9876541234', 120, '/imagenes/piedra_y_cielo.jpg', 'Una obra que explora la conexión entre el hombre y la naturaleza.', 8),
    ('Tradiciones en salsa verde', 1899, 5, 'Editorial Universo', 'Tradiciones', '978-9876545678', 300, '/imagenes/tradiciones_salsa_verde.jpg', 'Relatos humorísticos que complementan las Tradiciones Peruanas.', 9),
    ('Índole', 1891, 3, 'Editorial Universo', 'Novela social', '978-9876548765', 280, '/imagenes/indole.jpg', 'Una novela que aborda las problemáticas sociales del Perú.', 10);

--INSERT INTO tbl_prestamo (estado, fecha_devolucion, fecha_prestamo, id_libro, id_usuario) VALUES
--('PENDIENTE', '2024-07-10', '2024-06-10', 1, 2),
--('DEVUELTO', '2024-06-15', '2024-05-15', 3, 3),
--('PENDIENTE', '2024-07-20', '2024-06-20', 5, 2),
--('DEVUELTO', '2024-06-05', '2024-05-05', 2, 3),
--('PENDIENTE', '2024-07-25', '2024-06-25', 4, 2);

