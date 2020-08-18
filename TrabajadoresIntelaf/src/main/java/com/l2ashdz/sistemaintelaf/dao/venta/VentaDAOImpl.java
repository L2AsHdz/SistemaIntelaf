package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Venta;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author asael
 */
public class VentaDAOImpl implements VentaDAO {
    private static VentaDAOImpl ventaDAO = null;
    private final Connection conexion = Conexion.getConexion();
    
    private VentaDAOImpl(){
    }
    
    public static VentaDAOImpl getVentaDAO() {
        if (ventaDAO == null) {
            ventaDAO = new VentaDAOImpl();
        }
        return ventaDAO;
    }

    @Override
    public List<Venta> getListado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Venta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venta getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Venta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
