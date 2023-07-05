package com.example.pruebav500;

import java.io.Serializable;
import java.util.Date;

public class Tarjeta implements Serializable {
    private Integer numero;
    private String titular;
    private Date expira;
    private String tipo;
    private String error;


    public Tarjeta(Integer numero, String titular, Date expira, String tipo) {
        this.numero = numero;
        this.titular = titular.toUpperCase(); // Nombre siempre en mayúsculas
        this.expira = expira;
        this.tipo = tipo;
    }

    public Tarjeta() {
    }

    public Tarjeta(int numeroTarjeta, String titular, int expira, String tipo) {
    }

    // Getters
    public Integer getNumero() {
        return this.numero;
    }

    public String getTitular() {
        return titular;
    }

    public Date getExpira() {
        return expira;
    }

    public String getTipo() {
        return tipo;
    }

    // Setters
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setTitular(String titular) {
        this.titular = titular.toUpperCase();
    }

    public void setExpira(Date expira) {
        this.expira = expira;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    

}
