# Inventario Municipal

Sistema de inventario para municipalidades desarrollado con Java 17, Spring Boot y MySQL.

## Funciones
- Encargados y dependencias.
- Bienes y existencias.
- Movimientos DEBE/HABER.
- Hojas de responsabilidad por encargado.
- Consulta de saldo y valor total.
- Edición y eliminación de registros.

## Requisitos
- Java 17+
- Maven 3.9+
- MySQL 8+

## Base de datos
Crear una base llamada `inventario_municipal`. Luego ajustar `application.properties` con usuario y contraseña de MySQL.

## Ejecución
`mvn spring-boot:run`

API base: `/api`
