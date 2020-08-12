package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;
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
public class ExistenciaProductoDAOImpl implements ExistenciaProductoDAO {

    private static ExistenciaProductoDAOImpl existenciaDAO = null;
    private Connection conexion = Conexion.getConexion();

    private ExistenciaProductoDAOImpl() {
    }

    public static ExistenciaProductoDAOImpl getExistenciaDAO() {
        if (existenciaDAO == null) {
            existenciaDAO = new ExistenciaProductoDAOImpl();
        }
        return existenciaDAO;
    }

    @Override
    public List<ExistenciaProducto> getListado() {
        String sql = "SELECT * FROM existencia_producto";
        Statement declaracion = null;
        ResultSet rs = null;
        List<ExistenciaProducto> existencias = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            existencias = new ArrayList();

            while (rs.next()) {
                ExistenciaProducto existencia = new ExistenciaProducto();
                existencia.setCodigoTienda(rs.getString("codigo_tienda"));
                existencia.setCodigoProducto(rs.getString("codigo_producto"));
                existencia.setExistencias(rs.getInt("existencias"));
                existencias.add(existencia);
            }
            System.out.println("Listado de existncias obtenido");
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
        return existencias;
    }

    @Override
    public void create(ExistenciaProducto e) {
        String sql = "INSERT INTO existencia_producto VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, e.getCodigoTienda());
            ps.setString(2, e.getCodigoProducto());
            ps.setInt(3, e.getExistencias());
            ps.executeUpdate();
            System.out.println("Existencia ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto la existencia");
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
    public ExistenciaProducto getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ExistenciaProducto t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExistenciaProducto getProductoInTienda(String codT, String codP) {
        String sql = "SELECT * FROM existencia_producto WHERE codigo_tienda = ? AND "
                + "codigo_producto = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ExistenciaProducto ep = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, (String) codT);
            ps.setString(2, (String) codP);
            rs = ps.executeQuery();
            while (rs.next()) {
                ep = new ExistenciaProducto();
                ep.setCodigoTienda(rs.getString("codigo_tienda"));
                ep.setCodigoProducto(rs.getString("codigo_producto"));
                ep.setExistencias(rs.getInt("existencias"));
            }
            System.out.println("Existencias obtenidas de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer las existencias");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return ep;
    }

    @Override
    public int getExistencias(String codT, String codP) {
        String sql = "SELECT existencias FROM existencia_producto WHERE codigo_tienda = ? AND "
                + "codigo_producto = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int existencias = -1;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, (String) codT);
            ps.setString(2, (String) codP);
            rs = ps.executeQuery();
            while (rs.next()) {
                existencias = rs.getInt("existencias");
            }
            System.out.println("Existencias obtenidas de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer las existencias");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return existencias;
    }

}
