package com.l2ashdz.sistemaintelaf.ClasesAuxiliares;

import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class FabricaEntidades {
    
    public static Tienda getTienda(String codigo, String nombre, String dir, String tel){
        Tienda tienda = new Tienda();
        tienda.setCodigo(codigo);
        tienda.setNombre(nombre);
        tienda.setDireccion(dir);
        tienda.setTelefono1(tel);
        return tienda;
    }
    
    public static TiempoTraslado getTiempoT(String cod1, String cod2, int t){
        TiempoTraslado tiempoT = new TiempoTraslado();
        tiempoT.setCodigoTienda1(cod1);
        tiempoT.setCodigoTienda2(cod2);
        tiempoT.setTiempo(t);
        return tiempoT;
    }
    
//    public static Producto getProducto(String ){
//        
//    }
}
