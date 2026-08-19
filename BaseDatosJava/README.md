# BaseDatosJava

Programa en Java 17 + MySQL que crea automáticamente la base de datos `sistema_control` y la tabla `registros`.

## Funciones

- Crear base de datos automáticamente.
- Crear tabla automáticamente.
- Agregar registros.
- Mostrar registros ordenados por fecha.
- Editar registros.
- Eliminar registros.
- Buscar registros.

## Requisitos

- Java 17 o superior.
- MySQL Server.
- Maven.

## Configuración

En `Main.java`, modifica estas constantes si tu instalación de MySQL utiliza otro usuario o contraseña:

```java
private static final String USER = "root";
private static final String PASSWORD = "";
```

Luego ejecuta:

```bash
mvn compile
mvn exec:java
```

La base de datos se crea automáticamente al iniciar el programa.
