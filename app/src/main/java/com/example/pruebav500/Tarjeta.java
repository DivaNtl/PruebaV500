package com.example.pruebav500;

import java.io.Serializable;
import java.util.Date;

public class Tarjeta implements Serializable {
    private Integer numero;
    private String titular;
    private Integer expira;
    private String tipo;


    public Tarjeta(Integer numero, String titular, Integer expira, String tipo) {
        this.numero = numero;
        this.titular = titular.toUpperCase(); // Nombre siempre en mayúsculas
        this.expira = expira;
        this.tipo = tipo;
    }

    public Tarjeta() {
    }

    // Getters
    public Integer getNumero() {
        return this.numero;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getExpira() {
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

    public void setExpira(Integer expira) {
        this.expira = expira;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
