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
        String sql = "SELECT p.*, pp.codigo_pedido, pp.cantidad FROM producto p INNER JOIN "
                + "producto_pedido pp on p.codigo = pp.codigo_producto";
        List<ProductoPedido> productosP = null;

        try (Statement declaracion = conexion.createStatement();
                ResultSet rs = declaracion.executeQuery(sql)) {
            productosP = new ArrayList<>();

            while (rs.next()) {
                ProductoPedido productoP = new ProductoPedido();
                productoP.setCodigo(rs.getString("codigo"));
                productoP.setNombre(rs.getString("nombre"));
                productoP.setFabricante(rs.getString("fabricante"));
                productoP.setPrecio(rs.getFloat("precio"));
                productoP.setDescripcion(rs.getString("descripcion"));
                productoP.setGarantiaMeses(rs.getInt("garantia_meses"));
                productoP.setCantidad(rs.getInt("cantidad"));
                productoP.setCodigoPedido(rs.getInt("codigo_pedido"));
                productosP.add(productoP);
            }
            System.out.println("Listado de productos de pedido obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return productosP;
    }

    @Override
    public void create(ProductoPedido pp) {
        String sql = "INSERT INTO producto_pedido VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pp.getCodigoPedido());
            ps.setString(2, pp.getCodigo());
            ps.setFloat(3, pp.getPrecio());
            ps.setInt(4, pp.getCantidad());
            ps.executeUpdate();
            System.out.println("ProductoPedido ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el ProductoPedido");
            ex.printStackTrace(System.out);
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
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoPedido getProductoInPedido(int codPedido, String codProducto) {
        String sql = "SELECT * FROM producto_pedido WHERE codigo_pedido = ? AND "
                + "codigo_producto = ?";
        ProductoPedido pp = null;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codPedido);
            ps.setString(2, (String) codProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pp = new ProductoPedido();
                    pp.setCodigoPedido(rs.getInt("codigo_pedido"));
                    pp.setCodigo(rs.getString("codigo_producto"));
                    pp.setPrecio(rs.getFloat("precio"));
                    pp.setCantidad(rs.getInt("cantidad"));
                }
            }
            System.out.println("Producto en un pedido obtenidas de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el producto del pedido");
            ex.printStackTrace(System.out);
        }
        return pp;
    }

    @Override
    public void deleteProductosDePedido(String codigoPedido) {
        String sql = "DELETE FROM producto_pedido WHERE codigo_pedido = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(codigoPedido));
            ps.executeUpdate();
            System.out.println("se eliminaron los productos del pedido");
        } catch (SQLException ex) {
            System.out.println("No se eliminaron los productos del pedido");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public List<ProductoPedido> getProductosInPedido(int codP) {
        String sql = "SELECT p.*, pp.codigo_pedido, pp.cantidad FROM producto p INNER JOIN "
                + "producto_pedido pp on p.codigo = pp.codigo_producto WHERE pp.codigo_pedido = ?";
        List<ProductoPedido> productosP = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codP);
            try (ResultSet rs = ps.executeQuery()) {
                productosP = new ArrayList<>();
                
                while (rs.next()) {
                    ProductoPedido productoP = new ProductoPedido();
                    productoP.setCodigo(rs.getString("codigo"));
                    productoP.setNombre(rs.getString("nombre"));
                    productoP.setFabricante(rs.getString("fabricante"));
                    productoP.setPrecio(rs.getFloat("precio"));
                    productoP.setDescripcion(rs.getString("descripcion"));
                    productoP.setGarantiaMeses(rs.getInt("garantia_meses"));
                    productoP.setCantidad(rs.getInt("cantidad"));
                    productoP.setCodigoPedido(rs.getInt("codigo_pedido"));
                    productosP.add(productoP);
                }
            }
            System.out.println("Listado de productos de pedido obtenido");
        } catch (SQLException e) {
            System.out.println("No se obtuvo el listado de productos del pedido");
            e.printStackTrace(System.out);
        }
        return productosP;
    }

}
