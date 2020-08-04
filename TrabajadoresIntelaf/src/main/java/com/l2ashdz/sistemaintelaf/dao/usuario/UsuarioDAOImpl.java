package com.l2ashdz.sistemaintelaf.dao.usuario;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author asael
 */
public class UsuarioDAOImpl implements UsuarioDAO{
    private static UsuarioDAOImpl userDAO = null;
    private final Connection conexion = Conexion.getConexion();
    
    private UsuarioDAOImpl(){}
    
    public static UsuarioDAOImpl getUserDAO(){
        if (userDAO == null) {
            userDAO = new UsuarioDAOImpl();
        }
        return userDAO;
    }

    //Decuelve un listado de Usuarios
    @Override
    public List<Usuario> getListado() {
        List<Usuario> usuarios = null;
        
        try {
            String sql = "SELECT * FROM Usuario";
            Statement declaracion = conexion.createStatement();
            
            usuarios = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCUI(rs.getString("CUI"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setTipo(rs.getInt("Tipo"));
                usuario.setEstado(rs.getInt("Estado"));
                usuario.setPassword(rs.getString("Password"));
                usuario.setUserName(rs.getString("UserName"));
                usuarios.add(usuario);
            }
            System.out.println("Listado de Usuarios Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario getObject(Object CUI) {
        Usuario u = new Usuario();//sera posible usar un usuario global
        try {
            String sql = "SELECT * FROM Usuario WHERE CUI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, (String)CUI);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u.setCUI(rs.getString("CUI"));
                u.setNombre(rs.getString("Nombre"));
                u.setTipo(rs.getInt("Tipo"));
                u.setEstado(rs.getInt("Estado"));
                u.setPassword(rs.getString("Password"));
                u.setUserName(rs.getString("NombreUsuario"));
            }
            System.out.println("Usuario obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el usuario");
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public void create(Usuario u) {
        try {
            String sql = "INSERT INTO Usuario (CUI, Nombre, Tipo, Password, UserName) "
            + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getCUI());
            ps.setString(2, u.getNombre());
            ps.setInt(3,u.getTipo());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getUserName());
            ps.executeUpdate();
            System.out.println("Usuario Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el usuario");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disableUser(String CUI) {
        try {
            String sql = "UPDATE Usuario SET Estado = ? WHERE CUI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setString(2, CUI);
            ps.executeUpdate();
            System.out.println("Usuario deshabilitado");
            JOptionPane.showMessageDialog(null, "Usuario con DPI: "+CUI+
            " fue deshabilitado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se deshabilito el usuario");
            ex.printStackTrace();
        } 
    }

    @Override
    public void update(Usuario u, String CUI) {
        try {
            String sql = "UPDATE Usuario SET CUI = ?, Nombre = ?, Tipo = ?, "
            + "NombreUsuario = ?, Password = ? WHERE CUI = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getCUI());
            ps.setString(2, u.getNombre());
            ps.setInt(3, u.getTipo());
            ps.setString(4, u.getUserName());
            ps.setString(5, u.getPassword());
            ps.setString(6, CUI);
            ps.executeUpdate();
            System.out.println("Usuario actualizado");
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se actualizo el registro");
            ex.printStackTrace();
        } 
    }
}
