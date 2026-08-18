# Sistema de Control de Combustible

Proyecto en Java 17 para registrar y controlar el consumo de combustible.

## Funciones
- Registrar fecha, responsable y cantidad de combustible.
- Listar registros ordenados por fecha.
- Calcular el total utilizado.
- Modificar registros.
- Eliminar registros.
- Generar reporte semanal.

## Requisitos
- Java 17 o superior.
- Maven.

## Ejecutar
```bash
mvn compile exec:java
```

## Estructura
```text
control-combustible/
├── pom.xml
├── README.md
└── src/main/java/com/byron/combustible/
    ├── Main.java
    └── RegistroCombustible.java
```

## Próximas mejoras
Se puede ampliar con MySQL, interfaz gráfica/web, autenticación, vehículos, kilometraje, precio por galón y reportes exportables a Excel/PDF.
