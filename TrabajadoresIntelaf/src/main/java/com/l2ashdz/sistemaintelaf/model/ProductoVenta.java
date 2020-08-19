/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class ProductoVenta extends Producto {

    private int idVenta;
    private int cantidad;
    private float subtotal;

    public ProductoVenta() {
    }

    public ProductoVenta(Producto p, String idVenta, String cantidad) {
        super.setCodigo(p.getCodigo());
        super.setNombre(p.getNombre());
        super.setFabricante(p.getFabricante());
        super.setPrecio(p.getPrecio());
        super.setGarantiaMeses(p.getGarantiaMeses());
        super.setDescripcion(p.getDescripcion());
        super.setExistencias(p.getExistencias());
        this.idVenta = Integer.parseInt(idVenta);
        this.cantidad = Integer.parseInt(cantidad);
        this.subtotal = this.cantidad * super.getPrecio();
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.cantidad * super.getPrecio();
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
