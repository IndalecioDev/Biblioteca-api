-- =========================================================
--  data.sql — Datos de prueba para la Biblioteca API
--  Spring Boot carga este archivo automáticamente al arrancar
--  cuando spring.jpa.hibernate.ddl-auto=update y H2 en memoria
-- =========================================================

-- Insertar autores de prueba
INSERT INTO autores (nombre, nacionalidad, anio_nacimiento)
VALUES ('Stephen King', 'Estadounidense', 1947);

INSERT INTO autores (nombre, nacionalidad, anio_nacimiento)
VALUES ('Mariana Enriquez', 'Argentina', 1973);

INSERT INTO autores (nombre, nacionalidad, anio_nacimiento)
VALUES ('Jeff VanderMeer', 'Estadounidense', 1968);

-- Insertar libros de prueba (con FK al autor)
INSERT INTO libros (titulo, isbn, anio_publicacion, descripcion, disponible, autor_id)
VALUES ('El resplandor', '9788466329996', 1977, 'Un escritor y su familia pasan el invierno como cuidadores de un hotel aislado.', true, 1);

INSERT INTO libros (titulo, isbn, anio_publicacion, descripcion, disponible, autor_id)
VALUES ('It', '9788466337410', 1986, 'Un grupo de niños se enfrenta a una entidad maligna en Derry, Maine.', true, 1);

INSERT INTO libros (titulo, isbn, anio_publicacion, descripcion, disponible, autor_id)
VALUES ('Las cosas que perdimos en el fuego', '9788433998347', 2016, 'Colección de relatos de terror urbano latinoamericano.', true, 2);

INSERT INTO libros (titulo, isbn, anio_publicacion, descripcion, disponible, autor_id)
VALUES ('Aniquilación', '9788466661812', 2014, 'Una expedición entra en la Zona X, un territorio inexplicable y perturbador.', false, 3);
