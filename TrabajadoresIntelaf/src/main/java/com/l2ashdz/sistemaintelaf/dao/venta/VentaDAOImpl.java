package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author asael
 */
public class VentaDAOImpl implements VentaDAO {
    private static VentaDAOImpl ventaDAO = null;
    private final Connection conexion = Conexion.getConexion();
    
    private VentaDAOImpl(){
    }
    
    public static VentaDAOImpl getVentaDAO() {
        if (ventaDAO == null) {
            ventaDAO = new VentaDAOImpl();
        }
        return ventaDAO;
    }

    @Override
    public List<Venta> getListado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Venta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venta getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Venta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIdVenta() {
            String sql = "SELECT id FROM venta ORDER BY id DESC LIMIT 1";
        int id = 0;
        try {
            Statement stmt = conexion.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1)+1;
            }
            
            System.out.println("id de venta obtenido");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el id");
            ex.printStackTrace(System.out);
        }
        return id;
    }
    
}
