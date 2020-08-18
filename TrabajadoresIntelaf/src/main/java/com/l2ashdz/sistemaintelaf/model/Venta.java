package com.l2ashdz.sistemaintelaf.model;

import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class Venta {
    
    private int idVenta;
    private String nitCliente;
    private String codTienda;
    private LocalDate fecha;
    private float porcentCredito;
    private float porcentEfectivo;

    public Venta() {
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(String codTienda) {
        this.codTienda = codTienda;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getPorcentCredito() {
        return porcentCredito;
    }

    public void setPorcentCredito(float porcentCredito) {
        this.porcentCredito = porcentCredito;
    }

    public float getPorcentEfectivo() {
        return porcentEfectivo;
    }

    public void setPorcentEfectivo(float porcentEfectivo) {
        this.porcentEfectivo = porcentEfectivo;
    }
}
