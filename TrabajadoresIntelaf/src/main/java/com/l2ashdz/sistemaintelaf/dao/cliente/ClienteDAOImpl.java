package com.l2ashdz.sistemaintelaf.dao.cliente;

import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Conexion;
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
public class ClienteDAOImpl implements ClienteDAO{
    private static ClienteDAOImpl clienteDAO = null;
    private Connection conexion = Conexion.getConexion();
    
    private ClienteDAOImpl(){}
    
    public static ClienteDAOImpl getClienteDAO(){
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAOImpl();
        }
        return clienteDAO;
    }

    @Override
    public List<Cliente> getListado() {
        List<Cliente> clientes = null;
        
        try {
            String sql = "SELECT * FROM cliente";
            Statement declaracion = conexion.createStatement();
            
            clientes = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNit(rs.getString("nit"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCui(rs.getString("cui"));
                cliente.setCreditoCompra(rs.getFloat("credito_compra"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setDireccion(rs.getString("direccion"));
                clientes.add(cliente);
            }
            System.out.println("Listado de clientes obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void create(Cliente c) {
        try {
            String sql = "INSERT INTO cliente (nit, nombre, telefono, cui, "
                    + "credito_compra, correo, direccion) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, c.getNit());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCui());
            ps.setFloat(5, c.getCreditoCompra());
            ps.setString(6, c.getCorreo());
            ps.setString(7, c.getDireccion());
            ps.executeUpdate();
            System.out.println("Cliente ingresado correctamente");
            ps.close();
            ps = null;
        } catch (SQLException ex) {
            System.out.println("No se inserto el cliente");
            ex.printStackTrace();
        }
    }

    @Override
    public Cliente getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Cliente t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
