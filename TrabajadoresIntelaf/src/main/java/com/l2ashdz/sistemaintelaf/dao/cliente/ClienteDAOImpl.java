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
public class ClienteDAOImpl implements ClienteDAO {

    private static ClienteDAOImpl clienteDAO = null;
    private Connection conexion = Conexion.getConexion();

    private ClienteDAOImpl() {
    }

    public static ClienteDAOImpl getClienteDAO() {
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAOImpl();
        }
        return clienteDAO;
    }

    @Override
    public List<Cliente> getListado() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = null;

        try (Statement declaracion = conexion.createStatement();
                ResultSet rs = declaracion.executeQuery(sql)) {
            clientes = new ArrayList();

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return clientes;
    }

    @Override
    public void create(Cliente c) {
        String sql = "INSERT INTO cliente VALUES (?,?,?,?,?,?,?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, c.getNit());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCui());
            ps.setFloat(5, c.getCreditoCompra());
            ps.setString(6, c.getCorreo());
            ps.setString(7, c.getDireccion());
            ps.executeUpdate();
            System.out.println("Cliente ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el cliente");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public Cliente getObject(String nit) {
        String sql = "SELECT * FROM cliente WHERE nit = ?";

        Cliente c = null;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    c = new Cliente();
                    c.setNit(rs.getString("nit"));
                    c.setNombre(rs.getString("nombre"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCui(rs.getString("cui"));
                    c.setCreditoCompra(rs.getFloat("credito_compra"));
                    c.setCorreo(rs.getString("correo"));
                    c.setDireccion(rs.getString("direccion"));
                }
                System.out.println("Cliente obtenido de la BD");
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el cliente");
            ex.printStackTrace(System.out);
        }
        return c;
    }

    @Override
    public void update(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, cui = ?, direccion = ?,"
                + "telefono = ?, correo = ? WHERE nit = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCui());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getNit());
            ps.executeUpdate();
            System.out.println("Cliente actualizado");
        } catch (SQLException ex) {
            System.out.println("No se actualizo el cliente");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> getFilteredList(String filtro, int opcion) {
        String sql1 = "SELECT * FROM cliente WHERE nit LIKE ? ORDER BY nit";
        String sql2 = "SELECT * FROM cliente WHERE nombre LIKE ? ORDER BY nit";
        PreparedStatement ps = null;
        List<Cliente> clientes = null;

        try {
            switch (opcion) {
                case 1:
                    ps = conexion.prepareStatement(sql1);
                    ps.setString(1, "%" + filtro + "%");
                    break;
                case 2:
                    ps = conexion.prepareStatement(sql2);
                    ps.setString(1, "%" + filtro + "%");
                    break;
            }
            try (ResultSet rs = ps.executeQuery()) {
                clientes = new ArrayList();
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNit(rs.getString("nit"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setCui(rs.getString("cui"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setCreditoCompra(rs.getFloat("credito_compra"));
                    clientes.add(cliente);
                }
            }
            System.out.println("Listado de clientes obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return clientes;
    }

    @Override
    public void restarCredito(String nitCliente, float creditoUsado) {
        String sql = "UPDATE cliente SET credito_compra = credito_compra - ? WHERE nit = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setFloat(1, creditoUsado);
            ps.setString(2, nitCliente);
            ps.executeUpdate();
            System.out.println("Credito de compra actualizado");
        } catch (SQLException ex) {
            System.out.println("No se actualizo el credito de compra");
            ex.printStackTrace(System.out);
        }
    }

}
