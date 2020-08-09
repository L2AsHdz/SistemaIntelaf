package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;
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
    
    public static Producto nuevoProducto(String[] parametros){
        Producto producto = new Producto();
        producto.setCodigo(parametros[3]);
        producto.setNombre(parametros[1]);
        producto.setFabricante(parametros[2]);
        producto.setPrecio(Float.parseFloat(parametros[5]));
        return producto;
    }
    
    public static ExistenciaProducto nuevaExistenciaProducto(String[] parametros){
        ExistenciaProducto existenciaP = new ExistenciaProducto();
        existenciaP.setCodigoProducto(parametros[3]);
        existenciaP.setCodigoTienda(parametros[6]);
        existenciaP.setExistencias(Integer.parseInt(parametros[4]));
        return existenciaP;
    }
    
    public static Cliente nuevoCliente(String[] parametros){
        Cliente cliente = new Cliente();
        cliente.setNombre(parametros[1]);
        cliente.setNit(parametros[2]);
        cliente.setTelefono(parametros[3]);
        cliente.setCreditoCompra(Float.parseFloat(parametros[4]));
        return cliente;
    }
}
