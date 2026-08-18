package com.byron.combustible;

import java.time.LocalDate;

public class RegistroCombustible {
    private LocalDate fecha; private String persona; private String tipoPersona; private String recorrido;
    private String tipoCombustible; private double kms; private double precio; private String motivo;
    public RegistroCombustible(LocalDate fecha,String persona,String tipoPersona,String recorrido,String tipoCombustible,double kms,double precio,String motivo){
        this.fecha=fecha;this.persona=persona;this.tipoPersona=tipoPersona;this.recorrido=recorrido;this.tipoCombustible=tipoCombustible;this.kms=kms;this.precio=precio;this.motivo=motivo;
    }
    public LocalDate getFecha(){return fecha;} public String getPersona(){return persona;} public String getTipoPersona(){return tipoPersona;}
    public String getRecorrido(){return recorrido;} public String getTipoCombustible(){return tipoCombustible;} public double getKms(){return kms;}
    public double getPrecio(){return precio;} public String getMotivo(){return motivo;}
    public void setFecha(LocalDate v){fecha=v;} public void setPersona(String v){persona=v;} public void setTipoPersona(String v){tipoPersona=v;}
    public void setRecorrido(String v){recorrido=v;} public void setTipoCombustible(String v){tipoCombustible=v;} public void setKms(double v){kms=v;}
    public void setPrecio(double v){precio=v;} public void setMotivo(String v){motivo=v;}
}
