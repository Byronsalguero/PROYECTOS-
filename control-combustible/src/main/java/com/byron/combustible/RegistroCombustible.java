package com.byron.combustible;

import java.time.LocalDate;

public class RegistroCombustible {
    private final int id;
    private LocalDate fecha;
    private String nombre;
    private double cantidad;

    public RegistroCombustible(int id, LocalDate fecha, String nombre, double cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getNombre() { return nombre; }
    public double getCantidad() { return cantidad; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return String.format("ID: %d | Fecha: %s | Responsable: %s | Combustible: %.2f galones", id, fecha, nombre, cantidad);
    }
}
