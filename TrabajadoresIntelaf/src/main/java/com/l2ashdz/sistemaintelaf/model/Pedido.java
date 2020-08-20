package com.l2ashdz.sistemaintelaf.model;

import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class Pedido {

    private int codigo;
    private String nitCliente;
    private String codigoTiendaOrigen;
    private String codigoTiendaDestino;
    private LocalDate fecha;
    private LocalDate fechaVerificacion;
    private LocalDate fechaRetiro;
    private float porcentajeEfectivo;
    private float porcentajeCredito;
    private float porcentajePagado;
    private int estado;

    public Pedido() {
    }

    public Pedido(String codigo, String nitC, String codTO, String codTD, String fecha,
            String porcentajeC, String porcentajeE, String porcentajeP, int estado) {
        this.codigo = Integer.parseInt(codigo);
        this.nitCliente = nitC;
        this.codigoTiendaOrigen = codTO;
        this.codigoTiendaDestino = codTD;
        this.fecha = LocalDate.parse(fecha);
        this.porcentajeCredito = Float.parseFloat(porcentajeC);
        this.porcentajeEfectivo = Float.parseFloat(porcentajeE);
        this.porcentajePagado = Float.parseFloat(porcentajeP);
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getCodigoTiendaOrigen() {
        return codigoTiendaOrigen;
    }

    public void setCodigoTiendaOrigen(String codigoTiendaOrigen) {
        this.codigoTiendaOrigen = codigoTiendaOrigen;
    }

    public String getCodigoTiendaDestino() {
        return codigoTiendaDestino;
    }

    public void setCodigoTiendaDestino(String codigoTiendaDestino) {
        this.codigoTiendaDestino = codigoTiendaDestino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(LocalDate fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public float getPorcentajeEfectivo() {
        return porcentajeEfectivo;
    }

    public void setPorcentajeEfectivo(float porcentajeEfectivo) {
        this.porcentajeEfectivo = porcentajeEfectivo;
    }

    public float getPorcentajeCredito() {
        return porcentajeCredito;
    }

    public void setPorcentajeCredito(float porcentajeCredito) {
        this.porcentajeCredito = porcentajeCredito;
    }

    public float getPorcentajePagado() {
        return porcentajePagado;
    }

    public void setPorcentajePagado(float porcentajePagado) {
        this.porcentajePagado = porcentajePagado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
