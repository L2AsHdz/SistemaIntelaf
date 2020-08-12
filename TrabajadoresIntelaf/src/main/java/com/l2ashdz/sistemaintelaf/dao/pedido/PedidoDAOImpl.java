package com.l2ashdz.sistemaintelaf.dao.pedido;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asael
 */
public class PedidoDAOImpl implements PedidoDAO {

    private static PedidoDAOImpl pedidoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private PedidoDAOImpl() {
    }

    public static PedidoDAOImpl getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAOImpl();
        }
        return pedidoDAO;
    }

    @Override
    public List<Pedido> getListado() {
        String sql = "SELECT * FROM pedido";
        String fecha;
        Statement declaracion = null;
        ResultSet rs = null;
        List<Pedido> pedidos = null;

        try {
            declaracion = conexion.createStatement();
            rs = declaracion.executeQuery(sql);
            pedidos = new ArrayList();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setCodigo(rs.getInt("codigo"));
                pedido.setNitCliente(rs.getString("nit_cliente"));
                pedido.setCodigoTiendaOrigen(rs.getString("codigo_tienda_origen"));
                pedido.setCodigoTiendaDestino(rs.getString("codigo_tienda_destino"));
                pedido.setFecha(LocalDate.parse(rs.getString("fecha")));
                fecha = rs.getString("fecha_verificacion");
                pedido.setFechaVerificacion((fecha == null) ? null : LocalDate.parse(fecha));
                fecha = rs.getString("fecha_retiro");
                pedido.setFechaRetiro((fecha == null) ? null : LocalDate.parse(fecha));
                pedido.setPorcentajeEfectivo(rs.getFloat("porcentaje_efectivo"));
                pedido.setPorcentajeCredito(rs.getFloat("porcentaje_credito"));
                pedido.setPorcentajePagado(rs.getFloat("porcentaje_pagado"));
                pedido.setEstado(rs.getInt("estado"));
                pedidos.add(pedido);
            }
            System.out.println("Listado de pedidos obtenido");
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
        return pedidos;
    }

    @Override
    public void create(Pedido p) {
        String sql = "INSERT INTO pedido (codigo, nit_cliente, codigo_tienda_origen, "
                + "codigo_tienda_destino, fecha, porcentaje_efectivo, porcentaje_credito, "
                + "porcentaje_pagado) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, p.getCodigo());
            ps.setString(2, p.getNitCliente());
            ps.setString(3, p.getCodigoTiendaOrigen());
            ps.setString(4, p.getCodigoTiendaDestino());
            ps.setString(5, p.getFecha().toString());
            ps.setFloat(6, p.getPorcentajeEfectivo());
            ps.setFloat(7, p.getPorcentajeCredito());
            ps.setFloat(8, p.getPorcentajePagado());
            ps.executeUpdate();
            System.out.println("Pedido ingresado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se inserto el pedido");
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
    public Pedido getObject(String codigo) {
        String sql = "SELECT * FROM pedido WHERE codigo = ?";
        String fecha;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pedido p = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(codigo));
            rs = ps.executeQuery();
            while (rs.next()) {
                p = new Pedido();
                p.setCodigo(rs.getInt("codigo"));
                p.setNitCliente(rs.getString("nit_cliente"));
                p.setCodigoTiendaOrigen(rs.getString("codigo_tienda_origen"));
                p.setCodigoTiendaDestino(rs.getString("codigo_tienda_destino"));
                p.setFecha(LocalDate.parse(rs.getString("fecha")));
                fecha = rs.getString("fecha_verificacion");
                p.setFechaVerificacion((fecha == null) ? null : LocalDate.parse(fecha));
                fecha = rs.getString("fecha_retiro");
                p.setFechaRetiro((fecha == null) ? null : LocalDate.parse(fecha));
                p.setPorcentajeEfectivo(rs.getFloat("porcentaje_efectivo"));
                p.setPorcentajeCredito(rs.getFloat("porcentaje_credito"));
                p.setPorcentajePagado(rs.getFloat("porcentaje_pagado"));
                p.setEstado(rs.getInt("estado"));
            }
            System.out.println("Pedido obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el pedido");
            ex.printStackTrace(System.out);
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return p;
    }

    @Override
    public void update(Pedido t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String codigo) {
        String sql = "DELETE FROM pedido WHERE codigo = ?";
        PreparedStatement ps = null;
        
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(codigo));
            ps.executeUpdate();
            System.out.println("se elimino el pedido");
        } catch (SQLException ex) {
            System.out.println("No se elimino el pedido");
            ex.printStackTrace(System.out);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

}
