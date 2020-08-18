package com.l2ashdz.sistemaintelaf.model;

public class Empleado extends Persona{
    private String codigo;
    private int estado;

    public Empleado() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
