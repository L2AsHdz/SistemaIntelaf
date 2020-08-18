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

    public ProductoVenta() {
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
    }
}
