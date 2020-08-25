package com.l2ashdz.clientesintelaf.dao;

import com.l2ashdz.clientesintelaf.model.Conexion;
import com.l2ashdz.clientesintelaf.model.TiempoTraslado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author asael
 */
public class TiempoTrasladoDAO {

    private static TiempoTrasladoDAO tiempoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private TiempoTrasladoDAO() {
    }

    public static TiempoTrasladoDAO getTiempoDAO() {
        if (tiempoDAO == null) {
            tiempoDAO = new TiempoTrasladoDAO();
        }
        return tiempoDAO;
    }
    
    public TiempoTraslado getTiempoT(String codT1, String codT2) {
        String sql = "SELECT * FROM tiempo_traslado WHERE codigo_tienda_1 = ? AND "
                + "codigo_tienda_2 = ?";
        TiempoTraslado t = null;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, (String) codT1);
            ps.setString(2, (String) codT2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    t = new TiempoTraslado();
                    t.setCodigoT1(rs.getString("codigo_tienda_1"));
                    t.setCodigoT2(rs.getString("codigo_tienda_2"));
                    t.setTiempo(rs.getInt("tiempo"));
                }
            }
            System.out.println("TiempoTraslado obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el tiempo");
            ex.printStackTrace(System.out);
        }
        return t;
    }
}
