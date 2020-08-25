package com.l2ashdz.clientesintelaf.dao;

import com.l2ashdz.clientesintelaf.model.Conexion;
import com.l2ashdz.clientesintelaf.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class PedidoDAO  {

    private static PedidoDAO pedidoDAO = null;
    private Connection conexion = Conexion.getConexion();

    private PedidoDAO() {
    }

    public static PedidoDAO getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAO();
        }
        return pedidoDAO;
    }
    
    public Pedido getObject(String codigo) {
        String sql = "SELECT p.*, SUM(pp.precio*pp.cantidad) total, COUNT(pp.codigo_producto)"
                + " cantProductos FROM pedido p INNER JOIN producto_pedido pp ON p.codigo=pp.codigo_pedido"
                + " WHERE p.codigo = ?";
        String fecha;
        Pedido p = null;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(codigo));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p = new Pedido();
                    p.setCodigo(rs.getInt("codigo"));
                    p.setNitCliente(rs.getString("nit_cliente"));
                    p.setTiendaOrigen(rs.getString("codigo_tienda_origen"));
                    p.setTiendaDestino(rs.getString("codigo_tienda_destino"));
                    p.setFecha(LocalDate.parse(rs.getString("fecha")));
                    fecha = rs.getString("fecha_verificacion");
                    p.setFechaVerificacion((fecha == null) ? null : LocalDate.parse(fecha));
                    fecha = rs.getString("fecha_retiro");
                    p.setFechaRetiro((fecha == null) ? null : LocalDate.parse(fecha));
                    p.setPorcentajeEfectivo(rs.getFloat("porcentaje_efectivo"));
                    p.setPorcentajeCredito(rs.getFloat("porcentaje_credito"));
                    p.setPorcentajePagado(rs.getFloat("porcentaje_pagado"));
                    p.setTotal(rs.getFloat("total"));
                    p.setCantProductos(rs.getInt("cantProductos"));
                    p.setEstadoP(rs.getInt("estado"));
                }
            }
            System.out.println("Pedido obtenido de la BD");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el pedido");
            ex.printStackTrace(System.out);
        }
        return p;
    }
}
