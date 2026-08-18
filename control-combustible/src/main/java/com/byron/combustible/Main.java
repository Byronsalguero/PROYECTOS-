package com.byron.combustible;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/** Sistema de consola para registrar y controlar combustible. */
public class Main {
    static final Scanner sc = new Scanner(System.in);
    static final List<RegistroCombustible> registros = new ArrayList<>();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== CONTROL DE COMBUSTIBLE ===");
            System.out.println("1. Registrar combustible");
            System.out.println("2. Listar registros");
            System.out.println("3. Modificar registro");
            System.out.println("4. Eliminar registro");
            System.out.println("5. Reporte semanal");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> modificar();
                case 4 -> eliminar();
                case 5 -> reporteSemanal();
                case 0 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    static void registrar() {
        LocalDate fecha = leerFecha("Fecha (AAAA-MM-DD): ");
        String nombre = leerTexto("Nombre del responsable: ");
        double cantidad = leerDouble("Cantidad de combustible (galones): ");
        int id = registros.stream().mapToInt(RegistroCombustible::getId).max().orElse(0) + 1;
        registros.add(new RegistroCombustible(id, fecha, nombre, cantidad));
        System.out.println("Registro guardado con ID " + id + ".");
    }

    static void listar() {
        if (registros.isEmpty()) { System.out.println("No hay registros."); return; }
        registros.stream().sorted(Comparator.comparing(RegistroCombustible::getFecha)).forEach(System.out::println);
        System.out.printf("TOTAL: %.2f galones%n", registros.stream().mapToDouble(RegistroCombustible::getCantidad).sum());
    }

    static void modificar() {
        int id = leerEntero("ID a modificar: ");
        RegistroCombustible r = buscar(id);
        if (r == null) { System.out.println("Registro no encontrado."); return; }
        r.setFecha(leerFecha("Nueva fecha (AAAA-MM-DD): "));
        r.setNombre(leerTexto("Nuevo responsable: "));
        r.setCantidad(leerDouble("Nueva cantidad: "));
        System.out.println("Registro actualizado.");
    }

    static void eliminar() {
        int id = leerEntero("ID a eliminar: ");
        RegistroCombustible r = buscar(id);
        if (r == null) { System.out.println("Registro no encontrado."); return; }
        registros.remove(r);
        System.out.println("Registro eliminado.");
    }

    static void reporteSemanal() {
        LocalDate fin = leerFecha("Fecha final de la semana (AAAA-MM-DD): ");
        LocalDate inicio = fin.minusDays(6);
        double total = registros.stream().filter(r -> !r.getFecha().isBefore(inicio) && !r.getFecha().isAfter(fin)).mapToDouble(RegistroCombustible::getCantidad).sum();
        long cantidad = registros.stream().filter(r -> !r.getFecha().isBefore(inicio) && !r.getFecha().isAfter(fin)).count();
        System.out.println("\nREPORTE SEMANAL: " + inicio + " a " + fin);
        System.out.println("Registros: " + cantidad);
        System.out.printf("Combustible utilizado: %.2f galones%n", total);
    }

    static RegistroCombustible buscar(int id) { return registros.stream().filter(r -> r.getId() == id).findFirst().orElse(null); }
    static String leerTexto(String m) { System.out.print(m); return sc.nextLine().trim(); }
    static int leerEntero(String m) { while (true) try { System.out.print(m); return Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { System.out.println("Ingrese un numero entero valido."); } }
    static double leerDouble(String m) { while (true) try { System.out.print(m); double v=Double.parseDouble(sc.nextLine().trim()); if(v>=0)return v; } catch(Exception e){} System.out.println("Ingrese una cantidad valida."); }
    static LocalDate leerFecha(String m) { while(true) try { return LocalDate.parse(leerTexto(m)); } catch(Exception e){ System.out.println("Formato invalido. Use AAAA-MM-DD."); } }
}
