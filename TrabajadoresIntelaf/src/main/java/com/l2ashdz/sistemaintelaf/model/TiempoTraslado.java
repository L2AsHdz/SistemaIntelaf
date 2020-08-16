package com.l2ashdz.sistemaintelaf.model;

/**
 *
 * @author asael
 */
public class TiempoTraslado {
    private String codigoT1;
    private String nombreT1;
    private String telT1;
    private String codigoT2;
    private String nombreT2;
    private String telT2;
    private int tiempo;

    public TiempoTraslado() {
    }

    public String getCodigoT1() {
        return codigoT1;
    }

    public void setCodigoT1(String codigoT1) {
        this.codigoT1 = codigoT1;
    }

    public String getNombreT1() {
        return nombreT1;
    }

    public void setNombreT1(String nombreT1) {
        this.nombreT1 = nombreT1;
    }

    public String getTelT1() {
        return telT1;
    }

    public void setTelT1(String telT1) {
        this.telT1 = telT1;
    }

    public String getCodigoT2() {
        return codigoT2;
    }

    public void setCodigoT2(String codigoT2) {
        this.codigoT2 = codigoT2;
    }

    public String getNombreT2() {
        return nombreT2;
    }

    public void setNombreT2(String nombreT2) {
        this.nombreT2 = nombreT2;
    }

    public String getTelT2() {
        return telT2;
    }

    public void setTelT2(String telT2) {
        this.telT2 = telT2;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}
