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
    
    private ExistenciaProductoDAOImpl(){}
    
    public static ExistenciaProductoDAOImpl getExistenciaDAO() {
        if (existenciaDAO == null) {
            existenciaDAO = new ExistenciaProductoDAOImpl();
        }
        return existenciaDAO;
    }

    @Override
    public List<ExistenciaProducto> getListado() {
        List<ExistenciaProducto> existencias = null;
        
        try {
            String sql = "SELECT * FROM existencia_producto";
            Statement declaracion = conexion.createStatement();
            
            existencias = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                ExistenciaProducto existencia = new ExistenciaProducto();
                existencia.setCodigoTienda(rs.getString("codigo_tienda"));
                existencia.setCodigoProducto(rs.getString("codigo_producto"));
                existencia.setExistencias(rs.getInt("existencias"));
                existencias.add(existencia);
            }
            System.out.println("Listado de existncias obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return existencias;
    }

    @Override
    public void create(ExistenciaProducto e) {
        try {
            String sql = "INSERT INTO existencia_producto VALUES (?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, e.getCodigoTienda());
            ps.setString(2, e.getCodigoProducto());
            ps.setInt(3, e.getExistencias());
            ps.executeUpdate();
            System.out.println("Existencia ingresado correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto la existencia");
            ex.printStackTrace();
        }
    }

    @Override
    public ExistenciaProducto getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ExistenciaProducto t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
