package com.l2ashdz.clientesintelaf.dao;

import com.l2ashdz.clientesintelaf.model.Conexion;
import com.l2ashdz.clientesintelaf.model.ProductoPedido;
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
public class ProductoPedidoDAO {

    private static ProductoPedidoDAO productoPDAO = null;
    private Connection conexion = Conexion.getConexion();

    private ProductoPedidoDAO() {
    }

    public static ProductoPedidoDAO getProductoPDAO() {
        if (productoPDAO == null) {
            productoPDAO = new ProductoPedidoDAO();
        }
        return productoPDAO;
    }

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
