package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author asael
 */
public class ProductoVentaDAOImpl implements ProductoVentaDAO {
    
    private static ProductoVentaDAOImpl productoVDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ProductoVentaDAOImpl(){
    }
    
    public static ProductoVentaDAOImpl getProductoVentaDAO() {
        if (productoVDAO == null) {
            productoVDAO = new ProductoVentaDAOImpl();
        }
        return productoVDAO;
    }

    @Override
    public List<ProductoVenta> getListado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(ProductoVenta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoVenta getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ProductoVenta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
