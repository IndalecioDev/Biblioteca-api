# 📚 Biblioteca API — UT6 Actividad Final DAM

API REST desarrollada con Spring Boot para la gestión de una biblioteca. Proyecto de evaluación final de la UT6 — Ficheros y Acceso a BBDD.

## 👥 Autores

| Persona | Responsabilidad |
|--------|----------------|
| **Indalecio Navarro** | Entidad `Libro`, arquitectura completa, CRUD Libros, búsqueda con params, manejo de excepciones, README |
| **[Nombre Compañero]** | Entidad `Autor`, CRUD Autores, búsqueda de autores, documento de diseño |

---

## 🚀 Cómo arrancar el proyecto

### Prerrequisitos
- Java 17 o superior
- Maven (o usar el wrapper incluido `./mvnw`)

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/[usuario]/biblioteca-api.git
cd biblioteca-api

# 2. Arrancar la aplicación
./mvnw spring-boot:run
# O en Windows:
mvnw.cmd spring-boot:run

# 3. La API estará disponible en:
http://localhost:8080
```

> El proyecto usa **H2 en memoria** — no requiere instalar ninguna base de datos. Los datos se inicializan automáticamente con `data.sql`.

---

## 🗄️ Consola H2

Accesible en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:bibliotecadb` |
| User Name | `sa` |
| Password | *(vacío)* |

---

## 📡 Endpoints

### Libros — `/api/v1/libros`

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| GET | `/api/v1/libros` | Listar todos los libros | 200 OK |
| GET | `/api/v1/libros/{id}` | Obtener libro por ID | 200 OK / 404 |
| POST | `/api/v1/libros` | Crear nuevo libro | 201 Created |
| PUT | `/api/v1/libros/{id}` | Actualizar libro | 200 OK / 404 |
| DELETE | `/api/v1/libros/{id}` | Eliminar libro | 204 No Content / 404 |
| GET | `/api/v1/libros/autor/{autorId}` | Libros de un autor | 200 OK |
| GET | `/api/v1/libros/buscar?titulo=xxx` | Buscar por título | 200 OK |
| GET | `/api/v1/libros/buscar?disponible=true` | Filtrar por disponibilidad | 200 OK |
| GET | `/api/v1/libros/buscar?anio=2020` | Filtrar por año | 200 OK |

### Autores — `/api/v1/autores`

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| GET | `/api/v1/autores` | Listar todos los autores | 200 OK |
| GET | `/api/v1/autores/{id}` | Obtener autor por ID | 200 OK / 404 |
| POST | `/api/v1/autores` | Crear nuevo autor | 201 Created |
| PUT | `/api/v1/autores/{id}` | Actualizar autor | 200 OK / 404 |
| DELETE | `/api/v1/autores/{id}` | Eliminar autor | 204 No Content / 404 |
| GET | `/api/v1/autores/buscar?nombre=xxx` | Buscar por nombre | 200 OK |

---

## 🧪 Ejemplos con curl

```bash
# Listar todos los libros
curl http://localhost:8080/api/v1/libros

# Obtener libro con ID 1
curl http://localhost:8080/api/v1/libros/1

# Crear un nuevo libro
curl -X POST http://localhost:8080/api/v1/libros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Nuevo libro","isbn":"1234567890123","anioPublicacion":2024,"disponible":true}'

# Buscar libros por título
curl "http://localhost:8080/api/v1/libros/buscar?titulo=king"

# Libros disponibles
curl "http://localhost:8080/api/v1/libros/buscar?disponible=true"

# Libros de un autor
curl http://localhost:8080/api/v1/libros/autor/1
```

---

## 🏗️ Estructura del proyecto

```
src/main/java/com/dam/biblioteca/
├── BibliotecaApplication.java       # Clase principal
├── model/
│   ├── Libro.java                   # Entidad JPA Libro
│   └── Autor.java                   # Entidad JPA Autor
├── repository/
│   ├── LibroRepository.java         # JpaRepository + métodos derivados
│   └── AutorRepository.java
├── service/
│   ├── LibroService.java            # Lógica de negocio
│   └── AutorService.java
└── controller/
    ├── LibroController.java         # Endpoints REST
    ├── AutorController.java
    └── GlobalExceptionHandler.java  # @ControllerAdvice
```

---

## 📦 Tecnologías

- **Spring Boot 3.2.5**
- **Spring Data JPA / Hibernate**
- **H2 Database** (en memoria)
- **Lombok** (reducción de boilerplate)
- **Bean Validation** (@Valid, @NotBlank, @Size)
- **Java 17**

---

## 📊 Módulos implementados

- [x] **Núcleo** — 2 entidades JPA, CRUD completo, Optional, arquitectura por capas
- [x] **Módulo A** — `@ManyToOne/@OneToMany`, FK en BD, `@JsonIgnore`, endpoint relación
- [x] **Módulo B** — `@RequestParam` opcionales, métodos derivados, `show-sql`
- [x] **Módulo D (parcial)** — `@ControllerAdvice`, `@Valid` con mensajes de error JSON
