package com.l2ashdz.sistemaintelaf.dao.pedido;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asael
 */
public class ProductoPedidoDAOImpl implements ProductoPedidoDAO {
    private static ProductoPedidoDAOImpl productoPDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ProductoPedidoDAOImpl(){}
    
    public static ProductoPedidoDAOImpl getProductoPDAO() {
        if (productoPDAO == null) {
            productoPDAO = new ProductoPedidoDAOImpl();
        }
        return productoPDAO;
    }

    @Override
    public List<ProductoPedido> getListado() {
        List<ProductoPedido> productosP = null;
        
        try {
            String sql = "SELECT * FROM producto_pedido";
            Statement declaracion = conexion.createStatement();
            
            productosP = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                ProductoPedido productoP = new ProductoPedido();
                productoP.setCodigoPedido(rs.getInt("codigo_pedido"));
                productoP.setCodigoProducto(rs.getString("codigo_producto"));
                productoP.setPrecio(rs.getFloat("precio"));
                productoP.setCantidad(rs.getInt("cantidad"));
                productosP.add(productoP);
            }
            System.out.println("Listado de productos de pedido obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return productosP;
    }

    @Override
    public void create(ProductoPedido pp) {
        try {
            String sql = "INSERT INTO producto_pedido VALUES (?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, pp.getCodigoPedido());
            ps.setString(2, pp.getCodigoProducto());
            ps.setFloat(3, pp.getPrecio());
            ps.setInt(4, pp.getCantidad());
            ps.executeUpdate();
            System.out.println("ProductoPedido ingresado correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el ProductoPedido");
            ex.printStackTrace();
        }
    }

    @Override
    public ProductoPedido getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ProductoPedido t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
