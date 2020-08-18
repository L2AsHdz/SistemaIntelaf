package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class ProductoPedido extends Producto {
    private int codigoPedido;
    private int cantidad;

    public ProductoPedido() {
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
