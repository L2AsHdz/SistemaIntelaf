package com.l2ashdz.clientesintelaf.model;

import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class Pedido {

    private int codigo;
    private String nitCliente;
    private String tiendaOrigen;
    private String tiendaDestino;
    private LocalDate fecha;
    private LocalDate fechaVerificacion;
    private LocalDate fechaRetiro;
    private float porcentajeEfectivo;
    private float porcentajeCredito;
    private float porcentajePagado;
    private float total;
    private int cantProductos;
    private int estadoP;
    private String estado;

    public Pedido() {
    }

    public Pedido(String codigo, String nitC, String codTO, String codTD, String fecha,
            String porcentajeC, String porcentajeE, String porcentajeP, int estado) {
        this.codigo = Integer.parseInt(codigo);
        this.nitCliente = nitC;
        this.tiendaOrigen = codTO;
        this.tiendaDestino = codTD;
        this.fecha = LocalDate.parse(fecha);
        this.porcentajeCredito = Float.parseFloat(porcentajeC);
        this.porcentajeEfectivo = Float.parseFloat(porcentajeE);
        this.porcentajePagado = Float.parseFloat(porcentajeP);
        this.estadoP = estado;
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

    public String getTiendaOrigen() {
        return tiendaOrigen;
    }

    public void setTiendaOrigen(String codigoTiendaOrigen) {
        this.tiendaOrigen = codigoTiendaOrigen;
    }

    public String getTiendaDestino() {
        return tiendaDestino;
    }

    public void setTiendaDestino(String codigoTiendaDestino) {
        this.tiendaDestino = codigoTiendaDestino;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getCantProductos() {
        return cantProductos;
    }

    public void setCantProductos(int cantProductos) {
        this.cantProductos = cantProductos;
    }

    public int getEstadoP() {
        return estadoP;
    }

    public void setEstadoP(int estado) {
        this.estadoP = estado;
        setEstado();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado() {
        switch (estadoP) {
            case 0:
                this.estado = "En Ruta";
                break;
            case 1:
                this.estado = "Atrasado";
                break;
            case 2:
                this.estado = "En espera";
                break;
            case 3:
                this.estado = "Retirado";
                break;
        }
    }
}
