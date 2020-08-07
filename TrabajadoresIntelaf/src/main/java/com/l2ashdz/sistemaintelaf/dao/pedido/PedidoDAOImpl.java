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
    
    private PedidoDAOImpl(){}
    
    public static PedidoDAOImpl getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAOImpl();
        }
        return pedidoDAO;
    }

    @Override
    public List<Pedido> getListado() {
        List<Pedido> pedidos = null;
        
        try {
            String sql = "SELECT * FROM pedido";
            Statement declaracion = conexion.createStatement();
            
            pedidos = new ArrayList();
            ResultSet rs = declaracion.executeQuery(sql);
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setCodigo(rs.getInt("codigo"));
                pedido.setNitCliente(rs.getString("nit_cliente"));
                pedido.setCodigoTiendaOrigen(rs.getString("codigo_tienda_origen"));
                pedido.setCodigoTiendaDestino(rs.getString("codigo_tienda_destino"));
                pedido.setFecha(LocalDate.parse(rs.getString("fecha")));
                pedido.setFechaVerificacion(LocalDate.parse(rs.getString("fecha_verificacion")));
                pedido.setFechaRetiro(LocalDate.parse(rs.getString("fecha_retiro")));
                pedido.setPorcentajeEfectivo(rs.getFloat("porcentaje_efectivo"));
                pedido.setPorcentajeCredito(rs.getFloat("porcentaje_credito"));
                pedido.setPorcentajePagado(rs.getFloat("porcentaje_pagado"));
                pedido.setEstado(rs.getInt("estado"));
                pedidos.add(pedido);
            }
            System.out.println("Listado de pedidos obtenido");
            rs.close();
            declaracion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void create(Pedido p) {
        try {
            String sql = "INSERT INTO cliente (codigo, nit_cliente, codigo_tienda_origen, "
                    + "codigo_tienda_destino, fecha, porcentaje_efectivo, porcentaje_credito, "
                    + "porcentaje_pagado) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
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
            ps.close();
        } catch (SQLException ex) {
            System.out.println("No se inserto el pedido");
            ex.printStackTrace();
        }
    }

    @Override
    public Pedido getObject(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Pedido t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
