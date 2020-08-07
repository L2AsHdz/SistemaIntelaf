package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class TiempoTraslado {
    private String codigoTienda1;
    private String codigoTienda2;
    private int tiempo;

    public TiempoTraslado() {
    }

    public String getCodigoTienda1() {
        return codigoTienda1;
    }

    public void setCodigoTienda1(String codigoTienda1) {
        this.codigoTienda1 = codigoTienda1;
    }

    public String getCodigoTienda2() {
        return codigoTienda2;
    }

    public void setCodigoTienda2(String codigoTienda2) {
        this.codigoTienda2 = codigoTienda2;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}
