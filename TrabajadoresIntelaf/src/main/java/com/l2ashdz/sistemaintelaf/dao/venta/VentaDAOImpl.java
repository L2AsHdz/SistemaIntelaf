package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Venta;
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
public class VentaDAOImpl implements VentaDAO {

    private static VentaDAOImpl ventaDAO = null;
    private final Connection conexion = Conexion.getConexion();

    private VentaDAOImpl() {
    }

    public static VentaDAOImpl getVentaDAO() {
        if (ventaDAO == null) {
            ventaDAO = new VentaDAOImpl();
        }
        return ventaDAO;
    }

    @Override
    public List<Venta> getListado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Venta v) {
        String sql = "INSERT INTO venta (nit_cliente, codigo_tienda, fecha, porcentaje_efectivo,"
                + "porcentaje_credito) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, v.getNitCliente());
            ps.setString(2, v.getCodTienda());
            ps.setString(3, v.getFecha().toString());
            ps.setFloat(4, v.getPorcentEfectivo());
            ps.setFloat(5, v.getPorcentCredito());
            ps.executeUpdate();
            System.out.println("Venta ingresada");
        } catch (SQLException ex) {
            System.out.println("No se inserto la venta");
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public Venta getObject(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Venta t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIdVenta() {
        String sql = "SELECT id FROM venta ORDER BY id DESC LIMIT 1";
        int id = 1;
        try (Statement declaracion = conexion.createStatement();
                ResultSet rs = declaracion.executeQuery(sql)) {

            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            System.out.println("id de venta obtenido");
        } catch (SQLException ex) {
            System.out.println("No se pudo leer el id");
            ex.printStackTrace(System.out);
        }
        return id;
    }

    @Override
    public List<Venta> getComprasDeUnCliente(String nitCliente) {
        String sql = "SELECT v.*, SUM(pv.precio*pv.cantidad) total, COUNT(pv.codigo_producto)"
                + " cantProductos FROM venta v INNER JOIN producto_venta pv ON "
                + "v.id=pv.id_venta WHERE v.nit_cliente = ? GROUP BY v.id";
        List<Venta> ventas = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nitCliente);
            try (ResultSet rs = ps.executeQuery()) {
                ventas = new ArrayList();

                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("id"));
                    venta.setNitCliente(rs.getString("nit_cliente"));
                    venta.setCodTienda(rs.getString("codigo_tienda"));
                    venta.setFecha(LocalDate.parse(rs.getString("fecha")));
                    venta.setPorcentCredito(rs.getFloat("porcentaje_efectivo"));
                    venta.setPorcentEfectivo(rs.getFloat("porcentaje_credito"));
                    venta.setCantProductos(rs.getInt("cantProductos"));
                    venta.setTotal(rs.getFloat("total"));
                    ventas.add(venta);
                }
            }
            System.out.println("Listado de pedidos obtenido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return ventas;
    }

}
