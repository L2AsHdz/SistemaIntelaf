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
        List<Tienda> tiendas = null;

        try {
            String sql = "SELECT * FROM tienda";
            Statement declaracion = conexion.createStatement();

            tiendas = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
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
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tiendas;
    }

    @Override
    public void create(Tienda t) {
        try {
            String sql = "INSERT INTO tienda (codigo, nombre, direccion, telefono_1,"
                    + " telefono_2, correo, horario) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, t.getCodigo());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getDireccion());
            ps.setString(4, t.getTelefono1());
            ps.setString(5, t.getTelefono2());
            ps.setString(6, t.getCorreo());
            ps.setString(7, t.getHorario());
            ps.executeUpdate();
            System.out.println("Tieda ingresada");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto la tienda");
            ex.printStackTrace();
        }
    }

    @Override
    public Tienda getObject(Object codigo) {
        Tienda t=  new Tienda();
        try {
            String sql = "SELECT * FROM tienda WHERE codigo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, (String)codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                t.setCodigo(rs.getString("codigo"));
                t.setNombre(rs.getString("nombre"));
                t.setDireccion(rs.getString("direccion"));
                t.setTelefono1(rs.getString("telefono_1"));
                t.setTelefono2(rs.getString("telefono_2"));
                t.setCorreo(rs.getString("correo"));
                t.setHorario(rs.getString("horario"));
            }
            System.out.println("Tienda obtenida de la BD");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo leer la tienda");
            ex.printStackTrace();
        }
        return t;
    }

    @Override
    public void update(Tienda t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
