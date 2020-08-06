package com.l2ashdz.sistemaintelaf.dao.empleado;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Empleado;
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
public class EmpleadoDAOImpl implements EmpleadoDAO{
    private static EmpleadoDAOImpl empleadoDAO = null;
    private final Connection conexion = Conexion.getConexion();
    
    private EmpleadoDAOImpl(){}
    
    public static EmpleadoDAOImpl getEmpleadoDAO(){
        if (empleadoDAO == null) {
            empleadoDAO = new EmpleadoDAOImpl();
        }
        return empleadoDAO;
    }

    //Decuelve un listado de Empleados
    @Override
    public List<Empleado> getListado() {
        List<Empleado> empleados = null;
        
        try {
            String sql = "SELECT * FROM Empleado";
            Statement declaracion = conexion.createStatement();
            
            empleados = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(rs.getString("Codigo"));
                empleado.setCUI(rs.getString("CUI"));
                empleado.setNitI(rs.getString("Nit"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setCorreo(rs.getString("Correo"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setTelefono(rs.getString("Telefono"));
                empleado.setEstado(rs.getInt("Estado"));
                empleados.add(empleado);
            }
            System.out.println("Listado de Empleados Obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Empleado getObject(Object codigo) {
        Empleado e = new Empleado();
        try {
            String sql = "SELECT * FROM Empleado WHERE Codigo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, (String)codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                e.setCodigo(rs.getString("Codigo"));
                e.setCUI(rs.getString("CUI"));
                e.setNitI(rs.getString("Nit"));
                e.setNombre(rs.getString("Nombre"));
                e.setCorreo(rs.getString("Correo"));
                e.setDireccion(rs.getString("Direccion"));
                e.setTelefono(rs.getString("Telefono"));
                e.setEstado(rs.getInt("Estado"));
            }
            System.out.println("Empleado obtenido de la BD");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el empleado");
            ex.printStackTrace();
        }
        return e;
    }

    @Override
    public void create(Empleado e) {
        try {
            String sql = "INSERT INTO Empleado (Codigo, CUI, Nit, Nombre, "
                    + "Correo, Direccion, Telefono) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, e.getCodigo());
            ps.setString(2, e.getCUI());
            ps.setString(3, e.getNitI());
            ps.setString(4, e.getNombre());
            ps.setString(5, e.getCorreo());
            ps.setString(6, e.getDireccion());
            ps.setString(7, e.getTelefono());
            ps.executeUpdate();
            System.out.println("Empleado Ingresado Correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el empleado");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Empleado u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disableUser(String codigo) {
        try {
            String sql = "UPDATE Empleado SET Estado = ? WHERE Codigo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setString(2, codigo);
            ps.executeUpdate();
            System.out.println("Empleado deshabilitado");
            JOptionPane.showMessageDialog(null, "Empleado con Codigo: "+codigo+
            " fue deshabilitado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            System.out.println("No se deshabilito el empleado");
            ex.printStackTrace();
        } 
    }
}
