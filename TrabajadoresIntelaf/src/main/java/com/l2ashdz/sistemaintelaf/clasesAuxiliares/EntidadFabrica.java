package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class EntidadFabrica {
    
    public static Tienda nuevaTienda(String[] parametros){
        Tienda tienda = new Tienda();
        tienda.setCodigo(parametros[3]);
        tienda.setNombre(parametros[1]);
        tienda.setDireccion(parametros[2]);
        tienda.setTelefono1(parametros[4]);
        return tienda;
    }
    
    public static TiempoTraslado nuevoTiempo(String[] parametros){
        TiempoTraslado tiempoT = new TiempoTraslado();
        tiempoT.setCodigoTienda1(parametros[1]);
        tiempoT.setCodigoTienda2(parametros[2]);
        tiempoT.setTiempo(Integer.parseInt(parametros[3]));
        return tiempoT;
    }
    
//    public static Producto getProducto(String ){
//        
//    }
}
