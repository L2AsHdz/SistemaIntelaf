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
public class ProductoDAO {

    private static ProductoDAO productoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private ProductoDAO() {
    }

    public static ProductoDAO getProductoDAO() {
        if (productoDAO == null) {
            productoDAO = new ProductoDAO();
        }
        return productoDAO;
    }

    public List<Producto> getFilteredList(String codigo, String nombre, String fabricante, int opcion) {
        String sql = "SELECT p.*, e.codigo_tienda, e.existencias FROM producto p "
                + "INNER JOIN existencia_producto e ON p.codigo=e.codigo_producto ";
        String order = "ORDER BY p.codigo";
        String filtro;
        PreparedStatement ps = null;
        List<Producto> productos = null;

        try {
            switch (opcion) {
                case 1:
                    filtro = "WHERE p.codigo LIKE ? AND p.nombre LIKE ? AND p.fabricante LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + codigo + "%");
                    ps.setString(2, "%" + nombre + "%");
                    ps.setString(3, "%" + fabricante + "%");
                    break;
                case 2:
                    filtro = "WHERE p.codigo LIKE ? AND p.fabricante LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + codigo + "%");
                    ps.setString(2, "%" + fabricante + "%");
                    break;
                case 3:
                    filtro = "WHERE p.fabricante LIKE ? AND p.nombre LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + fabricante + "%");
                    ps.setString(2, "%" + nombre + "%");
                    break;
                case 4:
                    filtro = "WHERE p.codigo LIKE ? AND p.nombre LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + codigo + "%");
                    ps.setString(2, "%" + nombre + "%");
                    break;
                case 5:
                    filtro = "WHERE p.codigo LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + codigo + "%");
                    break;
                case 6:
                    filtro = "WHERE p.fabricante LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + fabricante + "%");
                    break;
                case 7:
                    filtro = "WHERE p.nombre LIKE ? ";
                    ps = conexion.prepareStatement(sql + filtro + order);
                    ps.setString(1, "%" + nombre + "%");
                    break;
                case 8:
                    ps = conexion.prepareStatement(sql + order);

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
