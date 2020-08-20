package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class ProductoPedido extends Producto {
    private int codigoPedido;
    private int cantidad;
    private float subtotal;

    public ProductoPedido() {
    }

    public ProductoPedido(Producto p, String codigoPedido, String cantidad) {
        super.setCodigo(p.getCodigo());
        super.setNombre(p.getNombre());
        super.setFabricante(p.getFabricante());
        super.setPrecio(p.getPrecio());
        super.setGarantiaMeses(p.getGarantiaMeses());
        super.setDescripcion(p.getDescripcion());
        super.setExistencias(p.getExistencias());
        this.codigoPedido = Integer.parseInt(codigoPedido);
        this.cantidad = Integer.parseInt(cantidad);
        this.subtotal = this.cantidad * super.getPrecio();
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
        this.subtotal = this.cantidad * super.getPrecio();
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
