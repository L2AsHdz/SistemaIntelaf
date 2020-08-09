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
        String sql = "SELECT * FROM producto";
        Statement declaracion = null;
        ResultSet rs = null;
        List<Producto> productos = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            productos = new ArrayList();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setFabricante(rs.getString("fabricante"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setGarantiaMeses(rs.getInt("garantia_meses"));
                productos.add(producto);
            }
            System.out.println("Listado de productos obtenido");
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
        return productos;
    }

    @Override
    public void create(Producto p) {
            String sql = "INSERT INTO producto VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
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
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    @Override
    public Producto getObject(Object codigo) {
        String sql = "SELECT * FROM producto WHERE codigo = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producto p = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, (String) codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                p = new Producto();
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setFabricante(rs.getString("fabricante"));
                p.setPrecio(rs.getFloat("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setGarantiaMeses(rs.getInt("garantia_meses"));
            }
            System.out.println("Producto obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el producto");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return p;
    }

    @Override
    public void update(Producto t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
