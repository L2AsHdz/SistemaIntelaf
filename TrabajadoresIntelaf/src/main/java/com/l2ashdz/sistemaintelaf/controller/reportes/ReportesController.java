package com.l2ashdz.sistemaintelaf.controller.reportes;

import static com.l2ashdz.sistemaintelaf.model.Archivo.*;
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
import java.time.LocalDateTime;
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
    private String nit;
    private String codTienda;
    private LocalDate fechaInicial ;
    private LocalDate fechaFinal;
    private LocalDateTime fechaHora = LocalDateTime.now();

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

    //Inicia la interfaz
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

        //Muestra los reportes que necesitan entradas del usuario
        if (reportesV.getBtnCargarReporte() == e.getSource()) {
            nit = reportesV.getTxtNit().getText();
            codTienda = reportesV.getTxtCodTienda().getText();
            Date input = reportesV.getTxtFechaInicio().getDate();
            Date input2 = reportesV.getTxtFechaFinal().getDate();
            fechaInicial = null;
            fechaFinal = null;

            switch (reportesV.getCbReportes().getSelectedIndex()) {

                //Muestra el reporte de compras de un cliente
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
                        reportesV.getBtnExportar().setEnabled(true);
                    }
                    break;

                //Muestra el reporte de pedidos de un cliente
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
                        reportesV.getBtnExportar().setEnabled(true);
                    }
                    break;

                //Muestra el reporte de los 10 productos mas vendidos
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
                    reportesV.getBtnExportar().setEnabled(true);
                    break;

                //Muestra el reporte de los productos mas vendidos por tienda
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
                        reportesV.getBtnExportar().setEnabled(true);
                    }
                    break;

                //Muestra el reporte de productos que nunca se han vendido por tienda
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
                        reportesV.getBtnExportar().setEnabled(true);
                    }
                    break;
            }

            //Exporta el reporte a un archivo html
        } else if (reportesV.getBtnExportar() == e.getSource()) {
            String nombreR = guardarReporte() + " - " + fechaHora;

            if (!nombreR.isEmpty()) {
                switch (reportesV.getCbReportes().getSelectedIndex()) {
                    case 0:
                        crearReportePedido(nombreR, TITULOS_RPEDIDOS, REPORTE1,
                                detalleReportePedido(tiendaDAO.getObject(tiendaActual)),
                                pedidos);
                        break;
                    case 1:
                        crearReportePedido(nombreR, TITULOS_RPEDIDOS, REPORTE2,
                                detalleReportePedido(tiendaDAO.getObject(tiendaActual)),
                                pedidos);
                        break;
                    case 2:
                        crearReportePedido(nombreR, TITULOS_RPEDIDOS, REPORTE3,
                                detalleReportePedido(tiendaDAO.getObject(tiendaActual)),
                                pedidos);
                        break;
                    case 3:
                        crearReportePedido(nombreR, TITULOS_RPEDIDOS, REPORTE4,
                                detalleReportePedido(tiendaDAO.getObject(tiendaActual)),
                                pedidos);
                        break;
                    case 4:
                        crearReporteVenta(nombreR, TITULOS_RVENTAS, REPORTE5,
                                detalleReporteCliente(clienteDAO.getObject(nit), 1),
                                ventas);
                        break;
                    case 5:
                        crearReportePedido(nombreR, TITULOS_RPEDIDOS, REPORTE6,
                                detalleReporteCliente(clienteDAO.getObject(nit), 2),
                                pedidos);
                        break;
                    case 6:
                        crearReporteProducto(nombreR, TITULOS_RPRODUCTOS, REPORTE7,
                                detalleReporteProductos(fechaInicial, fechaFinal),
                                productos);
                        break;
                    case 7:
                        crearReporteProducto(nombreR, TITULOS_RPRODUCTOS, REPORTE8,
                                detalleReporteProductos(tiendaDAO.getObject(tiendaActual),
                                        fechaInicial, fechaFinal), productos);
                        break;
                    case 8:
                        crearReporteProducto(nombreR, TITULOS_RPRODUCTOS, REPORTE9,
                                detalleReporteProdNoVendidos(tiendaDAO.getObject(codTienda)),
                                 productos);
                        break;
                }
            }
        }

    }

    //Dependiendo del reporte muestra los datos o habilita los campos para que 
    //el usuario introduzaca los datos que delimitaran el reporte
    @Override
    public void itemStateChanged(ItemEvent evt) {
        int state = evt.getStateChange();
        tiendaActual = PrincipalView.lblCodigo.getText();
        limpiarCampos();
        if (selccion(evt, 0, state)) {
            setEnableFiltros(false, false, false);
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosEnRuta(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            reportesV.getBtnExportar().setEnabled(true);

        } else if (selccion(evt, 1, state)) {
            setEnableFiltros(false, false, false);
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosSinVerificar(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            reportesV.getBtnExportar().setEnabled(true);

        } else if (selccion(evt, 2, state)) {
            setEnableFiltros(false, false, false);
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosAtrasados(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            reportesV.getBtnExportar().setEnabled(true);

        } else if (selccion(evt, 3, state)) {
            setEnableFiltros(false, false, false);
            mostrarTabla(reportesV.getPnlTabla(), reportePedidos);
            pedidos = pedidoDAO.getPedidosOutOfHere(tiendaActual);
            reportePedidos.getPedidoObservableList().clear();
            reportePedidos.getPedidoObservableList().addAll(pedidos);
            reportesV.getBtnExportar().setEnabled(true);

        } else if (selccion(evt, 4, state)) {
            setEnableFiltros(true, false, false);
            limpiarTabla();
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getBtnExportar().setEnabled(false);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 5, state)) {
            setEnableFiltros(true, false, false);
            limpiarTabla();
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getBtnExportar().setEnabled(false);
            reportesV.getTxtNit().requestFocus();

        } else if (selccion(evt, 6, state)) {
            setEnableFiltros(false, true, false);
            limpiarTabla();
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getBtnExportar().setEnabled(false);

        } else if (selccion(evt, 7, state)) {
            setEnableFiltros(false, true, true);
            limpiarTabla();
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getBtnExportar().setEnabled(false);
            reportesV.getTxtCodTienda().requestFocus();

        } else if (selccion(evt, 8, state)) {
            setEnableFiltros(false, false, true);
            limpiarTabla();
            reportesV.getBtnCargarReporte().setEnabled(true);
            reportesV.getBtnExportar().setEnabled(false);
            reportesV.getTxtCodTienda().requestFocus();
        }
    }

    //Limpia los campos de entrada
    private void limpiarCampos() {
        reportesV.getTxtNit().setText("");
        reportesV.getTxtCodTienda().setText("");
        reportesV.getTxtFechaInicio().setDate(null);
        reportesV.getTxtFechaFinal().setDate(null);
    }

    //Limpia la sselecion del reporte y deshabilita los botones
    private void limpiarInterfaz() {
        reportesV.getCbReportes().setSelectedIndex(-1);
        reportesV.getBtnCargarReporte().setEnabled(false);
        reportesV.getBtnExportar().setEnabled(false);
        limpiarTabla();
    }

    //Limpia los datos de la tabla
    private void limpiarTabla() {
        reportesV.getPnlTabla().removeAll();
        reportesV.getPnlTabla().repaint();
    }

    //Habilita los campos dependiendo del reporte
    private void setEnableFiltros(boolean nit, boolean fecha, boolean tienda) {
        reportesV.getTxtNit().setEditable(nit);
        reportesV.getTxtFechaInicio().setEnabled(fecha);
        reportesV.getTxtFechaFinal().setEnabled(fecha);
        reportesV.getTxtCodTienda().setEditable(tienda);
    }

    //verifica que item esta seleccionado
    private boolean selccion(ItemEvent e, int index, int state) {
        return reportesV.getCbReportes().getItemAt(index) == e.getItem() && state == ItemEvent.SELECTED;
    }

    //Limpia el panel y muestra la tabla del reporte
    private void mostrarTabla(JPanel parent, JPanel reporte) {
        parent.removeAll();
        parent.repaint();
        reporte.setSize(parent.getSize());
        reporte.setEnabled(true);
        reporte.setVisible(true);
        parent.add(reporte);
        parent.validate();
    }

    private String detalleReporteCliente(Cliente c, int opcion) {
        String detalle = "";
        if (opcion == 1) {
            detalle += "Compras realizadas por el cliente: ";
        } else if (opcion == 2) {
            detalle += "Pedidos en curso del cliente: ";
        }
        detalle += "</p><p>Nit: " + c.getNit()
                + "</p><p>Nombre: " + c.getNombre()
                + "</p><p>Direccion: " + c.getDireccion()
                + "</p><p>Telefono: " + c.getTelefono();
        return detalle;
    }

    private String detalleReporteProductos(LocalDate fInicial, LocalDate fFinal) {
        String detalle = "";
        if (fInicial != null && fFinal != null) {
            detalle += "Los diez productos mas vendidos de " + fInicial + " a " + fFinal;
        }
        return detalle;
    }

    private String detalleReporteProductos(Tienda t, LocalDate fInicial, LocalDate fFinal) {
        String detalle = "Productos mas vendidos en la tienda ";
        if (fInicial != null && fFinal != null) {
            detalle += " de " + fInicial + " a " + fFinal;
        }
        detalle += "</p><p>Codigo: " + t.getCodigo()
                + "</p><p>Nombre: " + t.getNombre()
                + "</p><p>Direccion: " + t.getDireccion()
                + "</p><p>Telefono: " + t.getTelefono1();
        return detalle;
    }

    private String detalleReporteProdNoVendidos(Tienda t) {
        String detalle = "Datos de tienda:"
                + "</p><p>Codigo: " + t.getCodigo()
                + "</p><p>Nombre: " + t.getNombre()
                + "</p><p>Direccion: " + t.getDireccion()
                + "</p><p>Telefono: " + t.getTelefono1();
        return detalle;
    }

    private String detalleReportePedido(Tienda t) {
        String detalle = "Datos de tienda actual:"
                + "</p><p>Codigo: " + t.getCodigo()
                + "</p><p>Nombre: " + t.getNombre()
                + "</p><p>Direccion: " + t.getDireccion()
                + "</p><p>Telefono: " + t.getTelefono1();
        return detalle;
    }

    private final String[] TITULOS_RVENTAS = {"Id", "Nit", "Tienda", "Fecha", "CantProductos", "Total"};
    private final String[] TITULOS_RPEDIDOS = {"Codigo", "Nit", "Tienda Origen", "Tienda Destino",
        "Fecha", "CantidadProductos", "Total", "Estado"};
    private final String[] TITULOS_RPRODUCTOS = {"Codigo", "Nombre", "Descripcion", "Fabricante",
        "Precio", "Garantia", "CantVentas"};

    private final String REPORTE1 = "Pedidos que llegarán a la tienda.";
    private final String REPORTE2 = "Pedidos pendientes de verificar su ingreso.";
    private final String REPORTE3 = "Pedidos atrasados que llegarán a la tienda.";
    private final String REPORTE4 = "Pedidos que salieron de la tienda y están en tránsito.";
    private final String REPORTE5 = "Compras realizadas por u cliente.";
    private final String REPORTE6 = "Pedidos en curso de un cliente.";
    private final String REPORTE7 = "Los diez productos más vendidos en un intervalo de tiempo.";
    private final String REPORTE8 = "Productos más vendidos por tienda en un intervalo de tiempo.";
    private final String REPORTE9 = "Productos que nunca se han vendido por tienda.";
}
