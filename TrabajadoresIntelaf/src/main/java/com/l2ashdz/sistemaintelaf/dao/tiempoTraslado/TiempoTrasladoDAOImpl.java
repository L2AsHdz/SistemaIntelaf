package com.l2ashdz.sistemaintelaf.dao.tiempoTraslado;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
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
public class TiempoTrasladoDAOImpl implements TiempoTrasladoDAO {

    private static TiempoTrasladoDAOImpl tiempoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private TiempoTrasladoDAOImpl() {
    }

    public static TiempoTrasladoDAOImpl getTiempoDAO() {
        if (tiempoDAO == null) {
            tiempoDAO = new TiempoTrasladoDAOImpl();
        }
        return tiempoDAO;
    }

    @Override
    public List<TiempoTraslado> getListado() {
        String sql = "SELECT * FROM tiempo_traslado";
        Statement declaracion = null;
        ResultSet rs = null;
        List<TiempoTraslado> tiempos = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            tiempos = new ArrayList();
            while (rs.next()) {
                TiempoTraslado tiempo = new TiempoTraslado();
                tiempo.setCodigoTienda1(rs.getString("codigo_tienda_1"));
                tiempo.setCodigoTienda2(rs.getString("codigo_tienda_2"));
                tiempo.setTiempo(rs.getInt("tiempo"));
                tiempos.add(tiempo);
            }
            System.out.println("Listado de tiempos obtenido");
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
        return tiempos;
    }

    @Override
    public void create(TiempoTraslado t) {
        String sql = "INSERT INTO tiempo_traslado VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getCodigoTienda1());
            ps.setString(2, t.getCodigoTienda2());
            ps.setInt(3, t.getTiempo());
            ps.executeUpdate();
            System.out.println("Tiempo ingresado Correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el tiempo");
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
    public TiempoTraslado getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(TiempoTraslado t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TiempoTraslado getTiempoT(String codT1, String codT2) {
        String sql = "SELECT * FROM tiempo_traslado WHERE codigo_tienda_1 = ? AND "
                + "codigo_tienda_2 = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        TiempoTraslado t = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, (String) codT1);
            ps.setString(2, (String) codT2);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new TiempoTraslado();
                t.setCodigoTienda1(rs.getString("codigo_tienda_1"));
                t.setCodigoTienda2(rs.getString("codigo_tienda_2"));
                t.setTiempo(rs.getInt("tiempo"));
            }
            System.out.println("TiempoTraslado obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el tiempo");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
        return t;
    }

}
