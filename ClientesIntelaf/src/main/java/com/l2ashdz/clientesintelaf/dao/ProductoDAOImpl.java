package com.l2ashdz.clientesintelaf.dao;

import com.l2ashdz.clientesintelaf.model.Conexion;
import com.l2ashdz.clientesintelaf.model.Producto;
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
public class ProductoDAOImpl {

    private static ProductoDAOImpl productoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private ProductoDAOImpl() {
    }

    public static ProductoDAOImpl getProductoDAO() {
        if (productoDAO == null) {
            productoDAO = new ProductoDAOImpl();
        }
        return productoDAO;
    }
    
    public List<Producto> getFilteredList(String filtro, int opcion) {
        String sql = "SELECT p.*, e.codigo_tienda, e.existencias FROM producto p "
                + "INNER JOIN existencia_producto e ON p.codigo=e.codigo_producto "
                + "WHERE ";
        String order = "ORDER BY p.codigo";
        PreparedStatement ps = null;
        List<Producto> productos = null;

        try {
            switch (opcion) {
                case 1:
                    ps = conexion.prepareStatement(sql + "p.codigo LIKE ? " + order);
                    ps.setString(1, "%" + filtro + "%");
                    break;
                case 2:
                    ps = conexion.prepareStatement(sql + "p.nombre LIKE ? " + order);
                    ps.setString(1, "%" + filtro + "%");
                    break;
                case 3:
                    ps = conexion.prepareStatement(sql + "e.codigo_tienda LIKE ? " + order);
                    ps.setString(1, "%" + filtro + "%");
                    break;
                case 4:
                    ps = conexion.prepareStatement(sql + "e.codigo_tienda = ? " + order);
                    ps.setString(1, filtro);
                    break;
                case 5:
                    ps = conexion.prepareStatement(sql + "e.codigo_tienda = ? AND e.existencias > 0 " + order);
                    ps.setString(1, filtro);

            }
            try (ResultSet rs = ps.executeQuery()) {
                productos = new ArrayList();
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setCodigo(rs.getString("codigo"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setFabricante(rs.getString("fabricante"));
                    producto.setPrecio(rs.getFloat("precio"));
                    producto.setExistencias(rs.getInt("existencias"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setGarantiaMeses(rs.getInt("garantia_meses"));
                    producto.setCodTienda(rs.getString("codigo_tienda"));
                    productos.add(producto);
                }
            }
            System.out.println("Listado de productos obtenido");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return productos;
    }
}
