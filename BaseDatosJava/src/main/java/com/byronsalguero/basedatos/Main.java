package com.byronsalguero.basedatos;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String SERVER = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    private static final String DB = "jdbc:mysql://localhost:3306/sistema_control?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            crearBaseDatos();
            crearTabla();
            menu();
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    private static Connection conectar(String url) throws SQLException {
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    private static void crearBaseDatos() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS sistema_control CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        try (Connection cn = conectar(SERVER); Statement st = cn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Base de datos 'sistema_control' lista.");
        }
    }

    private static void crearTabla() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS registros (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "fecha DATE NOT NULL," +
                "nombre VARCHAR(150) NOT NULL," +
                "descripcion VARCHAR(255)," +
                "cantidad DECIMAL(10,2) DEFAULT 0," +
                "observaciones TEXT)";
        try (Connection cn = conectar(DB); Statement st = cn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Tabla 'registros' lista.");
        }
    }

    private static void menu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== SISTEMA DE BASE DE DATOS ===");
            System.out.println("1. Agregar registro");
            System.out.println("2. Mostrar registros");
            System.out.println("3. Editar registro");
            System.out.println("4. Eliminar registro");
            System.out.println("5. Buscar registro");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1" -> agregar(sc);
                case "2" -> mostrar();
                case "3" -> editar(sc);
                case "4" -> eliminar(sc);
                case "5" -> buscar(sc);
                case "0" -> { System.out.println("Programa finalizado."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void agregar(Scanner sc) throws SQLException {
        System.out.print("Fecha (AAAA-MM-DD): "); String fecha = sc.nextLine();
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Descripción: "); String descripcion = sc.nextLine();
        System.out.print("Cantidad: "); double cantidad = Double.parseDouble(sc.nextLine());
        System.out.print("Observaciones: "); String obs = sc.nextLine();
        String sql = "INSERT INTO registros(fecha,nombre,descripcion,cantidad,observaciones) VALUES(?,?,?,?,?)";
        try (Connection cn = conectar(DB); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha)); ps.setString(2,nombre); ps.setString(3,descripcion);
            ps.setDouble(4,cantidad); ps.setString(5,obs); ps.executeUpdate();
            System.out.println("Registro guardado correctamente.");
        }
    }

    private static void mostrar() throws SQLException {
        String sql = "SELECT * FROM registros ORDER BY fecha DESC, id DESC";
        try (Connection cn = conectar(DB); Statement st = cn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nID | FECHA | NOMBRE | DESCRIPCIÓN | CANTIDAD | OBSERVACIONES");
            while (rs.next()) System.out.printf("%d | %s | %s | %s | %.2f | %s%n", rs.getInt("id"), rs.getDate("fecha"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDouble("cantidad"), rs.getString("observaciones"));
        }
    }

    private static void editar(Scanner sc) throws SQLException {
        System.out.print("ID a editar: "); int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo nombre: "); String nombre = sc.nextLine();
        System.out.print("Nueva descripción: "); String descripcion = sc.nextLine();
        System.out.print("Nueva cantidad: "); double cantidad = Double.parseDouble(sc.nextLine());
        System.out.print("Nuevas observaciones: "); String obs = sc.nextLine();
        String sql = "UPDATE registros SET nombre=?,descripcion=?,cantidad=?,observaciones=? WHERE id=?";
        try (Connection cn = conectar(DB); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1,nombre); ps.setString(2,descripcion); ps.setDouble(3,cantidad); ps.setString(4,obs); ps.setInt(5,id);
            System.out.println(ps.executeUpdate() > 0 ? "Registro actualizado." : "ID no encontrado.");
        }
    }

    private static void eliminar(Scanner sc) throws SQLException {
        System.out.print("ID a eliminar: "); int id = Integer.parseInt(sc.nextLine());
        try (Connection cn = conectar(DB); PreparedStatement ps = cn.prepareStatement("DELETE FROM registros WHERE id=?")) {
            ps.setInt(1,id); System.out.println(ps.executeUpdate() > 0 ? "Registro eliminado." : "ID no encontrado.");
        }
    }

    private static void buscar(Scanner sc) throws SQLException {
        System.out.print("Nombre a buscar: "); String texto = sc.nextLine();
        String sql = "SELECT * FROM registros WHERE nombre LIKE ? ORDER BY fecha DESC";
        try (Connection cn = conectar(DB); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1,"%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) System.out.printf("%d | %s | %s | %.2f%n", rs.getInt("id"), rs.getDate("fecha"), rs.getString("nombre"), rs.getDouble("cantidad"));
            }
        }
    }
}
