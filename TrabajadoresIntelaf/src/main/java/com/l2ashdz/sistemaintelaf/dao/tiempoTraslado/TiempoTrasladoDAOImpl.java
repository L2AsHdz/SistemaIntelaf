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
        List<TiempoTraslado> tiempos = null;

        try {
            String sql = "SELECT * FROM tiempo_traslado";
            Statement declaracion = conexion.createStatement();

            tiempos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                TiempoTraslado tiempo = new TiempoTraslado();
                tiempo.setCodigoTienda1(rs.getString("codigo_tienda_1"));
                tiempo.setCodigoTienda2(rs.getString("codigo_tienda_2"));
                tiempo.setTiempo(rs.getInt("tiempo"));
                tiempos.add(tiempo);
            }
            System.out.println("Listado de tiempos obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tiempos;
    }

    @Override
    public void create(TiempoTraslado t) {
        try {
            String sql = "INSERT INTO tiempo_traslado VALUES (?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getCodigoTienda1());
            ps.setString(2, t.getCodigoTienda2());
            ps.setInt(3, t.getTiempo());
            ps.executeUpdate();
            System.out.println("Tiempo ingresado Correctamente");
            ps.close();
        } catch (SQLException ex) {
            System.out.println("No se inserto el tiempo");
            ex.printStackTrace();
        }
    }

    @Override
    public TiempoTraslado getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(TiempoTraslado t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
