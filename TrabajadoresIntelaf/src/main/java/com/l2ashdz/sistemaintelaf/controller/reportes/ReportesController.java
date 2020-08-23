package com.l2ashdz.sistemaintelaf.controller.reportes;

import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Producto;
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

    private String tiendaActual;

    public ReportesController(ReportesView reportesV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        ventaDAO = VentaDAOImpl.getVentaDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
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
            Date input = reportesV.getTxtFechaInicio().getDate();
            LocalDate fechaInicial = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            input = reportesV.getTxtFechaFinal().getDate();
            LocalDate fechaFinal = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            switch (reportesV.getCbReportes().getSelectedIndex()) {
                case 4:
                    if (!nit.isEmpty()) {
                        mostrarTabla(reportesV.getPnlTabla(), reporteVentas);
                        ventas = ventaDAO.getComprasDeUnCliente(nit);
                        reporteVentas.getVentaObservableList().clear();
                        reporteVentas.getVentaObservableList().addAll(ventas);
                    } else {
                        JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                                "Info", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 5:
                    if (!nit.isEmpty()) {
                        mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
                        pedidos = pedidoDAO.getPedidosDeUnCliente(nit);
                        reportePedidos.getPedidoObservableList().clear();
                        reportePedidos.getPedidoObservableList().addAll(pedidos);
                    } else {
                        JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                                "Info", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 6:
                    //if (!nit.isEmpty()) {
                        mostrarTabla(reportesV.getPnlTabla(), reporteProductos);
                        productos = productoDAO.getMostSelledProducts(fechaInicial, fechaFinal);
                        reporteProductos.getProductoObservableList().clear();
                        reporteProductos.getProductoObservableList().addAll(productos);
                    //} else {
                     //   JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                        //        "Info", JOptionPane.ERROR_MESSAGE);
                    //}
                    break;
            }
            limpiarCampos();

        } else if (reportesV.getBtnExportar() == e.getSource()) {

        }

    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        int state = evt.getStateChange();
        tiendaActual = PrincipalView.lblCodigo.getText();
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
            setEnableFiltros(true, false, false);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 5, state)) {
            setEnableFiltros(true, false, false);
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 6, state)) {
            setEnableFiltros(false, true, false);
            reportesV.getBtnCargarReporte().setEnabled(true);

        } else if (selccion(evt, 7, state)) {
            setEnableFiltros(false, true, true);
            reportesV.getBtnCargarReporte().setEnabled(true);

        } else if (selccion(evt, 8, state)) {
            System.out.println("Reporte 9");
            setEnableFiltros(false, false, true);
            reportesV.getTxtCodTienda().requestFocus();
        }
        reportesV.getBtnExportar().setEnabled(true);

    }

    private void limpiarCampos() {
        reportesV.getTxtNit().setText("");
        reportesV.getTxtCodTienda().setText("");
        reportesV.getTxtFechaInicio().setDate(new Date());
        reportesV.getTxtFechaFinal().setDate(new Date());
    }

    private void limpiarInterfaz() {
        reportesV.getCbReportes().setSelectedIndex(-1);
        reportesV.getBtnCargarReporte().setEnabled(false);
        reportesV.getBtnExportar().setEnabled(false);
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
