package com.l2ashdz.sistemaintelaf.controller.reportes;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.model.Venta;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesVenta;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesPedido;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesProducto;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class ReportesController implements ActionListener, ItemListener {

    private ReportesView reportesV;

    private ReportesPedido reportePedidos = new ReportesPedido();
    private ReportesVenta reporteVentas = new ReportesVenta();
    private ReportesProducto reporteProductos = new ReportesProducto();

    private List<Pedido> pedidos;
    private PedidoDAO pedidoDAO;

    private List<Venta> ventas;
    private VentaDAO ventaDAO;

    private List<Producto> productos;
    private ProductoDAO productoDAO;

    private CRUD<Cliente> clienteDAO;
    private CRUD<Tienda> tiendaDAO;

    private String tiendaActual;

    public ReportesController(ReportesView reportesV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        ventaDAO = VentaDAOImpl.getVentaDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        this.reportesV = reportesV;
        this.reportesV.getCbReportes().addItemListener(this);
        this.reportesV.getBtnCargarReporte().addActionListener(this);
        this.reportesV.getBtnExportar().addActionListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!reportesV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            reportesV.setSize(parent.getSize());
            reportesV.setEnabled(true);
            reportesV.setVisible(true);
            parent.add(reportesV);
            parent.validate();
            limpiarCampos();
            limpiarInterfaz();
        } else {
            System.out.println("ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (reportesV.getBtnCargarReporte() == e.getSource()) {
            String nit = reportesV.getTxtNit().getText();
            String codTienda = reportesV.getTxtCodTienda().getText();
            Date input = reportesV.getTxtFechaInicio().getDate();
            Date input2 = reportesV.getTxtFechaFinal().getDate();
            LocalDate fechaInicial = null;
            LocalDate fechaFinal = null;
            switch (reportesV.getCbReportes().getSelectedIndex()) {
                case 4:
                    if (nit.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtNit().requestFocus();
                    } else if (clienteDAO.getObject(nit) == null) {
                        JOptionPane.showMessageDialog(null, "El cliente no existe",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtNit().requestFocus();
                    } else {
                        mostrarTabla(reportesV.getPnlTabla(), reporteVentas);
                        ventas = ventaDAO.getComprasDeUnCliente(nit);
                        reporteVentas.getVentaObservableList().clear();
                        reporteVentas.getVentaObservableList().addAll(ventas);
                    }
                    limpiarCampos();
                    break;
                case 5:
                    if (nit.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtNit().requestFocus();
                    } else if (clienteDAO.getObject(nit) == null) {
                        JOptionPane.showMessageDialog(null, "El cliente no existe",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtNit().requestFocus();
                    } else {
                        mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
                        pedidos = pedidoDAO.getPedidosDeUnCliente(nit);
                        reportePedidos.getPedidoObservableList().clear();
                        reportePedidos.getPedidoObservableList().addAll(pedidos);
                    }
                    limpiarCampos();
                    break;
                case 6:
                    try {
                        fechaInicial = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        fechaFinal = input2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                        productos = productoDAO.getMostSelledProducts(fechaInicial, fechaFinal, 1);
                        reporteProductos.getProductoObservableList().clear();
                        reporteProductos.getProductoObservableList().addAll(productos);

                    } catch (Exception ex) {
                        mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                        productos = productoDAO.getMostSelledProducts(fechaInicial, fechaFinal, 2);
                        reporteProductos.getProductoObservableList().clear();
                        reporteProductos.getProductoObservableList().addAll(productos);
                    }
                    break;
                case 7:
                    if (codTienda.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se ha ingresado codigo de tienda",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtCodTienda().requestFocus();
                    } else if (tiendaDAO.getObject(codTienda) == null) {
                        JOptionPane.showMessageDialog(null, "La tienda no existe",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtCodTienda().requestFocus();
                    } else {
                        try {
                            fechaInicial = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            fechaFinal = input2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                            productos = productoDAO.getMostSelledProdPorTienda(codTienda, fechaInicial, fechaFinal, 1);
                            reporteProductos.getProductoObservableList().clear();
                            reporteProductos.getProductoObservableList().addAll(productos);

                        } catch (Exception ex) {
                            mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                            productos = productoDAO.getMostSelledProdPorTienda(codTienda, fechaInicial, fechaFinal, 2);
                            reporteProductos.getProductoObservableList().clear();
                            reporteProductos.getProductoObservableList().addAll(productos);
                        }
                    }
                    break;
                case 8:
                    if (codTienda.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se ha ingresado codigo de tienda",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtCodTienda().requestFocus();
                    } else if (tiendaDAO.getObject(codTienda) == null) {
                        JOptionPane.showMessageDialog(null, "La tienda no existe",
                                "Info", JOptionPane.ERROR_MESSAGE);
                        reportesV.getTxtCodTienda().requestFocus();
                    } else {
                        mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                        productos = productoDAO.getProductosSinVentas(codTienda);
                        reporteProductos.getProductoObservableList().clear();
                        reporteProductos.getProductoObservableList().addAll(productos);
                        limpiarCampos();
                    }
                    break;
            }

        } else if (reportesV.getBtnExportar() == e.getSource()) {

        }

    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        int state = evt.getStateChange();
        tiendaActual = PrincipalView.lblCodigo.getText();
        limpiarCampos();
        if (selccion(evt, 0, state)) {
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosEnRuta(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            setEnableFiltros(false, false, false);

        } else if (selccion(evt, 1, state)) {
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosSinVerificar(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            setEnableFiltros(false, false, false);

        } else if (selccion(evt, 2, state)) {
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosAtrasados(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            setEnableFiltros(false, false, false);

        } else if (selccion(evt, 3, state)) {
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosOutOfHere(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            setEnableFiltros(false, false, false);

        } else if (selccion(evt, 4, state)) {
            limpiarTabla();
            setEnableFiltros(true, false, false);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 5, state)) {
            limpiarTabla();
            setEnableFiltros(true, false, false);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 6, state)) {
            limpiarTabla();
            setEnableFiltros(false, true, false);
            reportesV.getBtnCargarReporte().setEnabled(true);

        } else if (selccion(evt, 7, state)) {
            limpiarTabla();
            setEnableFiltros(false, true, true);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtCodTienda().requestFocus();

        } else if (selccion(evt, 8, state)) {
            limpiarTabla();
            setEnableFiltros(false, false, true);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtCodTienda().requestFocus();
        }
        reportesV.getBtnExportar().setEnabled(true);

    }

    private void limpiarCampos() {
        reportesV.getTxtNit().setText("");
        reportesV.getTxtCodTienda().setText("");
        reportesV.getTxtFechaInicio().setDate(null);
        reportesV.getTxtFechaFinal().setDate(null);
    }

    private void limpiarInterfaz() {
        reportesV.getCbReportes().setSelectedIndex(-1);
        reportesV.getBtnCargarReporte().setEnabled(false);
        reportesV.getBtnExportar().setEnabled(false);
        limpiarTabla();
    }

    private void limpiarTabla() {
        reportesV.getPnlTabla().removeAll();
        reportesV.getPnlTabla().repaint();
    }

    private void setEnableFiltros(boolean nit, boolean fecha, boolean tienda) {
        reportesV.getTxtNit().setEditable(nit);
        reportesV.getTxtFechaInicio().setEnabled(fecha);
        reportesV.getTxtFechaFinal().setEnabled(fecha);
        reportesV.getTxtCodTienda().setEditable(tienda);
    }

    private boolean selccion(ItemEvent e, int index, int state) {
        return reportesV.getCbReportes().getItemAt(index) == e.getItem() && state == ItemEvent.SELECTED;
    }

    private void mostrarTabla(JPanel parent, JPanel reporte) {
        parent.removeAll();
        parent.repaint();
        reporte.setSize(parent.getSize());
        reporte.setEnabled(true);
        reporte.setVisible(true);
        parent.add(reporte);
        parent.validate();
    }
}
