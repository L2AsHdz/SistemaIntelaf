package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class Cliente extends Persona{
    private float creditoCompra;

    public Cliente() {
    }

    public float getCreditoCompra() {
        return creditoCompra;
    }

    public void setCreditoCompra(float creditoCompra) {
        this.creditoCompra = creditoCompra;
    }
}
