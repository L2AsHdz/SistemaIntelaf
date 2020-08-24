package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class Persona {

    private String nit;
    private String nombre;
    private String telefono;
    private String cui;
    private String correo;
    private String direccion;

    public Persona() {
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        if (direccion == null) direccion ="";
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
