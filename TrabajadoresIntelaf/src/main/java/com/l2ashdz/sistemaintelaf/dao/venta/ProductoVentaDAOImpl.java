package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pv.getIdVenta());
            ps.setString(2, pv.getCodigo());
            ps.setFloat(3, pv.getPrecio());
            ps.setInt(4, pv.getCantidad());
            ps.executeUpdate();
            System.out.println("ProductoVenta ingresado");
        } catch (SQLException ex) {
            System.out.println("No se registro el productoVenta");
            ex.printStackTrace(System.out);
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

    @Override
    public List<ProductoVenta> getProductosInVenta(int idVenta) {
        String sql = "SELECT p.*, pv.id_venta, pv.cantidad FROM producto p INNER JOIN "
                + "producto_venta pv on p.codigo = pv.codigo_producto WHERE pv.id_venta = ?";
        List<ProductoVenta> productosV = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                productosV = new ArrayList<>();
                
                while (rs.next()) {
                    ProductoVenta pv = new ProductoVenta();
                    pv.setCodigo(rs.getString("codigo"));
                    pv.setNombre(rs.getString("nombre"));
                    pv.setFabricante(rs.getString("fabricante"));
                    pv.setPrecio(rs.getFloat("precio"));
                    pv.setDescripcion(rs.getString("descripcion"));
                    pv.setGarantiaMeses(rs.getInt("garantia_meses"));
                    pv.setCantidad(rs.getInt("cantidad"));
                    pv.setIdVenta(rs.getInt("id_venta"));
                    productosV.add(pv);
                }
            }
            System.out.println("Listado de productos de la venta obtenido");
        } catch (SQLException e) {
            System.out.println("No se obtuvo el listado de productos de la venta");
            e.printStackTrace(System.out);
        }
        return productosV;
    }
}
