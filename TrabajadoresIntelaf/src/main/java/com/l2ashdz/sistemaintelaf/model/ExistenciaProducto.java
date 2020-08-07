package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class ExistenciaProducto {
    private String codigoTienda;
    private String codigoProducto;
    private int existencias;

    public ExistenciaProducto() {
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
}
