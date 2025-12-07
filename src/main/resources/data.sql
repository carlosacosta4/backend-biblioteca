
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
    ('Ciro', 'Alegría', 'Escritor peruano, autor de El mundo es ancho y ajeno.'),
    ('José María', 'Arguedas', 'Escritor peruano, conocido por Los ríos profundos.'),
    ('Gabriel', 'García Márquez', 'Escritor colombiano, autor de Cien años de soledad.'),
    ('Isabel', 'Allende', 'Escritora chilena, conocida por La casa de los espíritus.'),
    ('Jorge Luis', 'Borges', 'Escritor argentino, maestro del cuento y la poesía.'),
    ('Julio', 'Cortázar', 'Escritor argentino, autor de "Rayuela".'),
    ('César', 'Vallejo', 'Poeta peruano, autor de Los heraldos negros.'),
    ('Ricardo', 'Palma', 'Escritor peruano, conocido por Tradiciones Peruanas.'),
    ('Clorinda', 'Matto de Turner', 'Escritora peruana, autora de Aves sin nido.');

-- Libros
INSERT INTO tbl_libro (titulo_libro, anio_publicacion_libro, cantidad_libro, editorial_libro, genero_libro, isbn_libro, nro_paginas_libro, ruta_imagen_libro, sinopsis_libro, id_autor) VALUES
    ('Cien años de soledad', 1967, 10, 'Editorial Sudamericana', 'Realismo mágico', '978-0060883287', 417, 'uploads/libros/cien_anios_soledad.webp', 'La magistral crónica de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo. Entre guerras, amores prohibidos y sucesos mágicos, la novela explora la soledad inherente al ser humano y el destino ineludible de una estirpe condenada a no tener una segunda oportunidad sobre la tierra.', 4),
    ('La casa de los espíritus', 1982, 8, 'Editorial Plaza & Janés', 'Realismo mágico', '978-0553383805', 448, 'uploads/libros/la_casa_de_los_espiritus.webp', 'Una inolvidable saga familiar que sigue las vivencias de cuatro generaciones de la familia Trueba. A través de la clarividencia de Clara y el temperamento de Esteban, se teje una trama de amor, lucha de clases y agitación política que refleja las transformaciones sociales de un país sudamericano.', 5),
    ('Rayuela', 1963, 5, 'Editorial Sudamericana', 'Ficción', '978-8435008880', 736, 'uploads/libros/rayuela.webp', 'Una antinovela revolucionaria que rompe las estructuras tradicionales. Relata la historia de Horacio Oliveira y su relación con la Maga en París, invitando al lector a ser cómplice y jugar con el orden de los capítulos para explorar múltiples finales y laberintos existenciales.', 7),
    ('El mundo es ancho y ajeno', 1941, 7, 'Editorial Losada', 'Novela social', '978-8420635748', 600, 'uploads/libros/el_mundo_es_ancho_ajeno.webp', 'La épica lucha de la comunidad indígena de Rumi, liderada por el sabio alcalde Rosendo Maqui, contra la codicia de los terratenientes y la corrupción del sistema judicial. Una obra cumbre que retrata el despojo de tierras y la tragedia del campesinado en los Andes peruanos.', 2),
    ('Los ríos profundos', 1958, 6, 'Editorial Losada', 'Novela social', '978-8437600891', 256, 'uploads/libros/los_rios_profundos.webp', 'Ernesto, un adolescente sensible, se enfrenta al brutal mundo de un internado en Abancay. La obra narra su conflicto interno entre la cultura andina que ama y el mundo occidental al que pertenece por sangre, encontrando refugio en la naturaleza y la magia del zumbayllu.', 3),
    ('Los heraldos negros', 1919, 4, 'Editorial Emece', 'Poesía', '978-9876123456', 120, 'uploads/libros/los_heraldos_negros.webp', 'El primer poemario de Vallejo, donde explora con intensidad el sufrimiento existencial, la duda religiosa, la culpa y la orfandad. Incluye versos inmortales que capturan el "golpe" del dolor intrínseco a la condición humana y la melancolía de la vida andina.', 8),
    ('Tradiciones Peruanas', 1872, 9, 'Editorial Universo', 'Tradiciones', '978-9876543210', 320, 'uploads/libros/tradiciones_peruanas.webp', 'Una célebre colección de relatos cortos que mezclan historia, ficción y sátira. Palma reconstruye con humor, ironía y un lenguaje castizo anécdotas de la época incaica, virreinal y republicana, definiendo la picardía y el carácter de la identidad peruana.', 9),
    ('Aves sin nido', 1889, 3, 'Editorial Universo', 'Novela social', '978-9876543227', 240, 'uploads/libros/aves_sin_nido.webp', 'Considerada la precursora del indigenismo, esta novela denuncia los abusos del clero y las autoridades políticas contra los indígenas en el pueblo de Killac. A través de una historia de amor imposible, expone la corrupción moral y la injusticia social de la época.', 10),
    ('Conversación en La Catedral', 1969, 6, 'Editorial Seix Barral', 'Novela', '978-8432226782', 608, 'uploads/libros/conversacion_en_la_catedral.webp', 'A través de una charla en un bar de Lima entre Santiago Zavala y el exchófer de su padre, se reconstruye la oscura época de la dictadura de Odría. Una compleja radiografía del poder, la corrupción y el fracaso moral de la sociedad peruana ante la pregunta: ¿En qué momento se jodió el Perú?', 1),
    ('El zorro de arriba y el zorro de abajo', 1971, 4, 'Editorial Losada', 'Novela', '978-8437600907', 320, 'uploads/libros/el_zorro.webp', 'Obra póstuma e incompleta que alterna diarios personales y desgarradores del autor con una narrativa sobre el auge pesquero en Chimbote. Retrata la caótica modernización, la migración, la explotación y la pérdida de identidad cultural en un mundo andino en crisis.', 5);

INSERT INTO tbl_prestamo (estado, fecha_devolucion, fecha_prestamo, id_libro, id_usuario) VALUES
    ('ACTIVO', '2025-12-15', '2025-12-10', 1, 2),
    ('ACTIVO', '2025-12-20', '2025-11-28', 2, 3),
    ('VENCIDO', '2025-12-3', '2025-11-20', 3, 2),
    ('DEVUELTO', '2025-11-20', '2025-11-10', 4, 3),
    ('DEVUELTO', '2025-11-10', '2025-10-28', 5, 2),
    ('ACTIVO', '2025-12-2', '2025-11-28', 2, 3);



