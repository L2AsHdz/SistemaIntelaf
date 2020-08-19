package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void create(ProductoVenta pv) {
        String sql = "INSERT INTO producto_venta VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, pv.getIdVenta());
            ps.setString(2, pv.getCodigo());
            ps.setFloat(3, pv.getPrecio());
            ps.setInt(4, pv.getCantidad());
            ps.executeUpdate();
            System.out.println("ProductoVenta ingresado");
        } catch (SQLException ex) {
            System.out.println("No se registro el productoVenta");
            ex.printStackTrace(System.out);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
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
