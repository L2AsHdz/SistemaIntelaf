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
public class ProductoDAOImpl implements ProductoDAO{
    private static ProductoDAOImpl productoDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ProductoDAOImpl(){}
    
    public static ProductoDAOImpl getProductoDAO() {
        if (productoDAO == null) {
            productoDAO = new ProductoDAOImpl();
        }
        return productoDAO;
    }

    @Override
    public List<Producto> getListado() {
        List<Producto> productos = null;
        
        try {
            String sql = "SELECT * FROM producto";
            Statement declaracion = conexion.createStatement();
            
            productos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
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
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void create(Producto p) {
        try {
            String sql = "INSERT INTO producto (codigo, nombre, fabricante, precio, "
                    + "descripcion, garantia_meses) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getFabricante());
            ps.setFloat(4, p.getPrecio());
            ps.setString(5, p.getDescripcion());
            ps.setInt(6, p.getGarantiaMeses());
            ps.executeUpdate();
            System.out.println("Producto ingresado correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el producto");
            ex.printStackTrace();
        }
    }

    @Override
    public Producto getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
