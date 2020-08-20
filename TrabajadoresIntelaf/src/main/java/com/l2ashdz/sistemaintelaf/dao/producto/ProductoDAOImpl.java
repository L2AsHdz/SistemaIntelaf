package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Producto;
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
public class ProductoDAOImpl implements ProductoDAO {

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

    @Override
    public List<Producto> getListado() {
        String sql = "SELECT p.*, e.codigo_tienda, e.existencias FROM producto p "
                + "INNER JOIN existencia_producto e ON p.codigo=e.codigo_producto";
        List<Producto> productos = null;

        try (Statement declaracion = conexion.createStatement();
                ResultSet rs = declaracion.executeQuery(sql)) {
            productos = new ArrayList();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setFabricante(rs.getString("fabricante"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setGarantiaMeses(rs.getInt("garantia_meses"));
                producto.setCodTienda(rs.getString("codigo_tienda"));
                producto.setExistencias(rs.getInt("existencias"));
                productos.add(producto);
            }
            System.out.println("Listado de productos obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return productos;
    }

    @Override
    public void create(Producto p) {
        String sql = "INSERT INTO producto VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getFabricante());
            ps.setFloat(4, p.getPrecio());
            ps.setString(5, p.getDescripcion());
            ps.setInt(6, p.getGarantiaMeses());
            ps.executeUpdate();
            System.out.println("Producto ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el producto");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public Producto getObject(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Producto p) {
        String sql = "UPDATE producto SET nombre = ?, fabricante = ?, descripcion = ?,"
                + " precio = ?, garantia_meses = ? WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getFabricante());
            ps.setString(3, p.getDescripcion());
            ps.setFloat(4, p.getPrecio());
            ps.setInt(5, p.getGarantiaMeses());
            ps.setString(6, p.getCodigo());
            ps.executeUpdate();
            System.out.println("Producto actualizado");
        } catch (SQLException ex) {
            System.out.println("No se actualizo el producto");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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

    @Override
    public Producto getProducto(String codT, String codP) {
        String sql = "SELECT p.*, e.codigo_tienda, e.existencias FROM producto p "
                + "INNER JOIN existencia_producto e ON p.codigo=e.codigo_producto "
                + "WHERE e.codigo_tienda = ? AND p.codigo = ?";
        Producto p = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codT);
            ps.setString(2, codP);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p = new Producto();
                    p.setCodigo(rs.getString("codigo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setFabricante(rs.getString("fabricante"));
                    p.setPrecio(rs.getFloat("precio"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setGarantiaMeses(rs.getInt("garantia_meses"));
                    p.setExistencias(rs.getInt("existencias"));
                    p.setCodTienda(rs.getString("codigo_tienda"));
                }
            }
            System.out.println("Producto obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el producto");
            ex.printStackTrace(System.out);
        }
        return p;
    }

}
