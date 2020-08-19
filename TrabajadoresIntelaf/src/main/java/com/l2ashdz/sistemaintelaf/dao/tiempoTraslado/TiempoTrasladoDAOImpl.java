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
        String sql = "SELECT tt.codigo_tienda_1, t1.nombre nombreT1, t1.telefono_1 telT1, tt.codigo_tienda_2, "
                + "t2.nombre nombreT2, t2.telefono_1 telT2,  tt.tiempo FROM tiempo_traslado tt INNER JOIN "
                + "tienda t1 ON tt.codigo_tienda_1 = t1.codigo INNER JOIN tienda t2 ON "
                + "tt.codigo_tienda_2 = t2.codigo ORDER BY tiempo";
        List<TiempoTraslado> tiempos = null;

        try (Statement declaracion = conexion.createStatement();
                ResultSet rs = declaracion.executeQuery(sql)) {
            tiempos = new ArrayList();
            while (rs.next()) {
                TiempoTraslado tiempo = new TiempoTraslado();
                tiempo.setCodigoT1(rs.getString("codigo_tienda_1"));
                tiempo.setNombreT1(rs.getString("nombreT1"));
                tiempo.setTelT1(rs.getString("telT1"));
                tiempo.setCodigoT2(rs.getString("codigo_tienda_2"));
                tiempo.setNombreT2(rs.getString("nombreT2"));
                tiempo.setTelT2(rs.getString("telT2"));
                tiempo.setTiempo(rs.getInt("tiempo"));
                tiempos.add(tiempo);
            }
            System.out.println("Listado de tiempos obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return tiempos;
    }

    @Override
    public void create(TiempoTraslado t) {
        String sql = "INSERT INTO tiempo_traslado VALUES (?,?,?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, t.getCodigoT1());
            ps.setString(2, t.getCodigoT2());
            ps.setInt(3, t.getTiempo());
            ps.executeUpdate();
            System.out.println("Tiempo ingresado Correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el tiempo");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public TiempoTraslado getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(TiempoTraslado t) {
        String sql = "UPDATE tiempo_traslado SET tiempo = ? WHERE codigo_tienda_1 = ? AND codigo_tienda_2 = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, t.getTiempo());
            ps.setString(2, t.getCodigoT1());
            ps.setString(3, t.getCodigoT2());
            ps.executeUpdate();
            System.out.println("Tiempo actualizado");
        } catch (SQLException ex) {
            System.out.println("No se actualizo el tiempo");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
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

    @Override
    public List<TiempoTraslado> getTiemposOfT(String codTActual) {
        String sql = "SELECT tt.codigo_tienda_1, t1.nombre nombreT1, t1.telefono_1 telT1, tt.codigo_tienda_2, "
                + "t2.nombre nombreT2, t2.telefono_1 telT2,  tt.tiempo FROM tiempo_traslado tt INNER JOIN "
                + "tienda t1 ON tt.codigo_tienda_1 = t1.codigo INNER JOIN tienda t2 ON "
                + "tt.codigo_tienda_2 = t2.codigo WHERE t1.codigo = ? OR t2.codigo = ? ORDER BY tiempo";
        List<TiempoTraslado> tiempos = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codTActual);
            ps.setString(2, codTActual);
            try (ResultSet rs = ps.executeQuery()) {
                tiempos = new ArrayList();
                while (rs.next()) {
                    TiempoTraslado tiempo = new TiempoTraslado();
                    tiempo.setCodigoT1(rs.getString("codigo_tienda_1"));
                    tiempo.setNombreT1(rs.getString("nombreT1"));
                    tiempo.setTelT1(rs.getString("telT1"));
                    tiempo.setCodigoT2(rs.getString("codigo_tienda_2"));
                    tiempo.setNombreT2(rs.getString("nombreT2"));
                    tiempo.setTelT2(rs.getString("telT2"));
                    tiempo.setTiempo(rs.getInt("tiempo"));
                    tiempos.add(tiempo);
                }
            }
            System.out.println("Listado de tiempos obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return tiempos;
    }

}
