package com.l2ashdz.sistemaintelaf.controller.reportes;

import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Venta;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReporteVenta;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesPedido;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private ReporteVenta reporteVenta = new ReporteVenta();

    private List<Pedido> pedidos;
    private PedidoDAO pedidoDAO;
    
    private List<Venta> ventas;
    private VentaDAO ventaDAO;

    private String tiendaActual;

    public ReportesController(ReportesView reportesV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        ventaDAO = VentaDAOImpl.getVentaDAO();
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
        } else {
            System.out.println("ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (reportesV.getBtnCargarReporte() == e.getSource()) {

            if (reportesV.getCbReportes().getSelectedIndex() == 4) {
                String nit = reportesV.getTxtNit().getText();
                if (!nit.isEmpty()) {
                    mostrarTabla(reportesV.getPnlTabla(), reporteVenta);
                    ventas = ventaDAO.getComprasDeUnCliente(nit);
                    reportePedidos.getPedidoObservableList().clear();
                    reporteVenta.getVentaObservableList().addAll(ventas);
                    reportesV.getBtnCargarReporte().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "El nit del cliente es necesario",
                            "Info", JOptionPane.ERROR_MESSAGE);
                }
            }

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
            System.out.println("Reporte 6");
            setEnableFiltros(true, false, false);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 6, state)) {
            System.out.println("Reporte 7");
            setEnableFiltros(false, true, false);

        } else if (selccion(evt, 7, state)) {
            System.out.println("Reporte 8");
            setEnableFiltros(false, true, true);

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
        reportesV.getTxtFechaInicio().setDate(null);
        reportesV.getTxtFechaFinal().setDate(null);
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
