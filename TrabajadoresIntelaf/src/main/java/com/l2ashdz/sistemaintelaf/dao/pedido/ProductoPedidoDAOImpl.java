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

    private ProductoPedidoDAOImpl() {
    }

    public static ProductoPedidoDAOImpl getProductoPDAO() {
        if (productoPDAO == null) {
            productoPDAO = new ProductoPedidoDAOImpl();
        }
        return productoPDAO;
    }

    @Override
    public List<ProductoPedido> getListado() {
        String sql = "SELECT * FROM producto_pedido";
        Statement declaracion = null;
        ResultSet rs = null;
        List<ProductoPedido> productosP = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            productosP = new ArrayList();

            while (rs.next()) {
                ProductoPedido productoP = new ProductoPedido();
                productoP.setCodigoPedido(rs.getInt("codigo_pedido"));
                productoP.setCodigoProducto(rs.getString("codigo_producto"));
                productoP.setPrecio(rs.getFloat("precio"));
                productoP.setCantidad(rs.getInt("cantidad"));
                productosP.add(productoP);
            }
            System.out.println("Listado de productos de pedido obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                declaracion.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return productosP;
    }

    @Override
    public void create(ProductoPedido pp) {
        String sql = "INSERT INTO producto_pedido VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, pp.getCodigoPedido());
            ps.setString(2, pp.getCodigoProducto());
            ps.setFloat(3, pp.getPrecio());
            ps.setInt(4, pp.getCantidad());
            ps.executeUpdate();
            System.out.println("ProductoPedido ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el ProductoPedido");
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
    public ProductoPedido getObject(String t) {
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

    @Override
    public ProductoPedido getProductoInPedido(int codPedido, String codProducto) {
        String sql = "SELECT * FROM producto_pedido WHERE codigo_pedido = ? AND "
                + "codigo_producto = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductoPedido pp = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPedido);
            ps.setString(2, (String) codProducto);
            rs = ps.executeQuery();
            while (rs.next()) {
                pp = new ProductoPedido();
                pp.setCodigoPedido(rs.getInt("codigo_pedido"));
                pp.setCodigoProducto(rs.getString("codigo_producto"));
                pp.setPrecio(rs.getFloat("precio"));
                pp.setCantidad(rs.getInt("cantidad"));
            }
            System.out.println("Producto en un pedido obtenidas de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el producto del pedido");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return pp;
    }

}
