package com.l2ashdz.sistemaintelaf.dao.tienda;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Tienda;
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
public class TiendaDAOImpl implements TiendaDAO {

    private static TiendaDAOImpl tiendaDAO = null;
    private final Connection conexion = Conexion.getConexion();

    private TiendaDAOImpl() {
    }

    public static TiendaDAOImpl getTiendaDAO() {
        if (tiendaDAO == null) {
            tiendaDAO = new TiendaDAOImpl();
        }
        return tiendaDAO;
    }

    @Override
    public List<Tienda> getListado() {
        String sql = "SELECT * FROM tienda";
        Statement declaracion = null;
        ResultSet rs = null;
        List<Tienda> tiendas = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            tiendas = new ArrayList();
            while (rs.next()) {
                Tienda tienda = new Tienda();
                tienda.setCodigo(rs.getString("codigo"));
                tienda.setNombre(rs.getString("nombre"));
                tienda.setDireccion(rs.getString("direccion"));
                tienda.setTelefono1(rs.getString("telefono_1"));
                tienda.setTelefono2(rs.getString("telefono_2"));
                tienda.setCorreo(rs.getString("correo"));
                tienda.setHorario(rs.getString("horario"));
                tiendas.add(tienda);
            }
            System.out.println("Listado de tiendas obtenido");
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
        return tiendas;
    }

    @Override
    public void create(Tienda t) {
        String sql = "INSERT INTO tienda (codigo, nombre, direccion, telefono_1,"
                + " telefono_2, correo, horario) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getCodigo());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getDireccion());
            ps.setString(4, t.getTelefono1());
            ps.setString(5, t.getTelefono2());
            ps.setString(6, t.getCorreo());
            ps.setString(7, t.getHorario());
            ps.executeUpdate();
            System.out.println("Tieda ingresada");
        } catch (SQLException ex) {
            System.out.println("No se inserto la tienda");
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
    public Tienda getObject(String codigo) {
        String sql = "SELECT * FROM tienda WHERE codigo = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tienda t = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new Tienda();
                t.setCodigo(rs.getString("codigo"));
                t.setNombre(rs.getString("nombre"));
                t.setDireccion(rs.getString("direccion"));
                t.setTelefono1(rs.getString("telefono_1"));
                t.setTelefono2(rs.getString("telefono_2"));
                t.setCorreo(rs.getString("correo"));
                t.setHorario(rs.getString("horario"));
            }
            System.out.println("Tienda obtenida de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la tienda");
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

    @Override
    public void update(Tienda t) {
        String sql = "UPDATE tienda SET nombre = ?, direccion = ?, telefono_1 = ?,"
                + " telefono_2 = ?, correo = ?, horario = ? WHERE codigo = ?";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDireccion());
            ps.setString(3, t.getTelefono1());
            ps.setString(4, t.getTelefono2());
            ps.setString(5, t.getCorreo());
            ps.setString(6, t.getHorario());
            ps.setString(7, t.getCodigo());
            ps.executeUpdate();
            System.out.println("Tienda actualizada");
        } catch (SQLException ex) {
            System.out.println("No se actualizo la tienda");
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
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Tienda> getFilteredList(String nombre, String codigo, int opcion) {
        String sql1 = "SELECT * FROM tienda WHERE codigo LIKE ? ORDER BY nombre";
        String sql2 = "SELECT * FROM tienda WHERE nombre LIKE ? ORDER BY nombre";
        String sql3 = "SELECT * FROM tienda WHERE codigo LIKE ? OR nombre LIKE ? ORDER BY nombre";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Tienda> tiendas = null;

        try {
            switch (opcion) {
                case 1:
                    ps = conexion.prepareStatement(sql1);
                    ps.setString(1, "%" + codigo + "%");
                    break;
                case 2:
                    ps = conexion.prepareStatement(sql2);
                    ps.setString(1, "%" + nombre + "%");
                    break;
                case 3:
                    ps = conexion.prepareStatement(sql3);
                    ps.setString(1, "%" + codigo + "%");
                    ps.setString(2, "%" + nombre + "%");
                    break;
            }
            rs = ps.executeQuery();
            tiendas = new ArrayList();
            while (rs.next()) {
                Tienda tienda = new Tienda();
                tienda.setCodigo(rs.getString("codigo"));
                tienda.setNombre(rs.getString("nombre"));
                tienda.setDireccion(rs.getString("direccion"));
                tienda.setTelefono1(rs.getString("telefono_1"));
                tienda.setTelefono2(rs.getString("telefono_2"));
                tienda.setCorreo(rs.getString("correo"));
                tienda.setHorario(rs.getString("horario"));
                tiendas.add(tienda);
            }
            System.out.println("Listado de tiendas obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return tiendas;
    }

}
