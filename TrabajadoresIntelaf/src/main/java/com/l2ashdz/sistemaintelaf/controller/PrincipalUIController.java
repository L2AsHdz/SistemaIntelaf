package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.controller.cliente.AddClienteController;
import com.l2ashdz.sistemaintelaf.controller.empleado.AddEmpleadoController;
import com.l2ashdz.sistemaintelaf.controller.pedido.PedidoController;
import com.l2ashdz.sistemaintelaf.controller.producto.AddProductoController;
import com.l2ashdz.sistemaintelaf.controller.producto.BusquedaPController;
import com.l2ashdz.sistemaintelaf.controller.reportes.ReportesController;
import com.l2ashdz.sistemaintelaf.controller.tienda.TiendaController;
import com.l2ashdz.sistemaintelaf.controller.venta.AddVentaController;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.cliente.AddClienteView;
import com.l2ashdz.sistemaintelaf.ui.empleado.AddEmpleadoView;
import com.l2ashdz.sistemaintelaf.ui.pedido.PedidoView;
import com.l2ashdz.sistemaintelaf.ui.producto.AddProductoView;
import com.l2ashdz.sistemaintelaf.ui.producto.BusquedaPView;
import com.l2ashdz.sistemaintelaf.ui.reportes.ReportesView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiendaView;
import com.l2ashdz.sistemaintelaf.ui.venta.AddVentaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author asael
 */
public class PrincipalUIController extends MouseAdapter {

    private PrincipalView principalUI;

    //Vista y controlador para busqueda de productos
    private BusquedaPView busquedaV = new BusquedaPView();
    private BusquedaPController busqquedaC = new BusquedaPController(busquedaV);

    //Vista y controlador para interfaz tienda
    private TiendaView tiendaV = new TiendaView();
    private TiendaController tiendaC = new TiendaController(tiendaV);

    //Vista y controlador para interfaz productos
    private AddProductoView productoV = new AddProductoView();
    private AddProductoController productoC = new AddProductoController(productoV);

    //Vista y controlador para interfaz empleados
    private AddEmpleadoView empleadoV = new AddEmpleadoView();
    private AddEmpleadoController empleadoC = new AddEmpleadoController(empleadoV);

    //Vista y controlador para interfaz clientes
    private AddClienteView clienteV = new AddClienteView();
    private AddClienteController clienteC = new AddClienteController(clienteV);

    //Vista y controlador para interfaz Ventas
    private AddVentaView ventaV = new AddVentaView();
    private AddVentaController ventaC = new AddVentaController(ventaV);

    //Vista y controlador para interfaz Pedidos
    private PedidoView pedidoV = new PedidoView();
    private PedidoController pedidoC = new PedidoController(pedidoV);

    //Vista y controlador para interfaz reportes
    private ReportesView reporteV = new ReportesView();
    private ReportesController reporteC = new ReportesController(reporteV);

    public PrincipalUIController(PrincipalView principalUI, String codigo, String nombre) {
        this.principalUI = principalUI;
        this.principalUI.getLblCodigo().setText(codigo);
        this.principalUI.getLblNombreT().setText(nombre);
        this.principalUI.getBtnTiendas().addMouseListener(this);
        this.principalUI.getBtnProductos().addMouseListener(this);
        this.principalUI.getBtnClientes().addMouseListener(this);
        this.principalUI.getBtnEmpleados().addMouseListener(this);
        this.principalUI.getBtnPedidos().addMouseListener(this);
        this.principalUI.getBtnVentas().addMouseListener(this);
        this.principalUI.getBtnReportes().addMouseListener(this);
        this.principalUI.getBtnInicio().addMouseListener(this);
    }

    public void iniciar() {
        principalUI.pack();
        principalUI.setResizable(false);
        principalUI.setLocationRelativeTo(null);
        principalUI.setVisible(true);
        mostrarBusqueda();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Inicia la interfaz de tienda
        if (principalUI.getBtnTiendas() == e.getSource()) {
            productoV.setEnabled(false);
            empleadoV.setEnabled(false);
            clienteV.setEnabled(false);
            ventaV.setEnabled(false);
            pedidoV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            tiendaC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnProductos() == e.getSource()) {
            tiendaV.setEnabled(false);
            empleadoV.setEnabled(false);
            clienteV.setEnabled(false);
            ventaV.setEnabled(false);
            pedidoV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            productoC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnEmpleados() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoV.setEnabled(false);
            clienteV.setEnabled(false);
            ventaV.setEnabled(false);
            pedidoV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            empleadoC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnClientes() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoV.setEnabled(false);
            empleadoV.setEnabled(false);
            ventaV.setEnabled(false);
            pedidoV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            clienteC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnVentas() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoV.setEnabled(false);
            empleadoV.setEnabled(false);
            clienteV.setEnabled(false);
            pedidoV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            ventaC.iniciar(principalUI.getPnlDesk());

        } else if (principalUI.getBtnPedidos() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoV.setEnabled(false);
            empleadoV.setEnabled(false);
            clienteV.setEnabled(false);
            ventaV.setEnabled(false);
            reporteV.setEnabled(false);
            busquedaV.setEnabled(false);
            pedidoC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnReportes() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoV.setEnabled(false);
            empleadoV.setEnabled(false);
            clienteV.setEnabled(false);
            ventaV.setEnabled(false);
            pedidoV.setEnabled(false);
            busquedaV.setEnabled(false);
            reporteC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnInicio() == e.getSource()) {
            mostrarBusqueda();
        }
    }

    private void mostrarBusqueda() {
        tiendaV.setEnabled(false);
        productoV.setEnabled(false);
        empleadoV.setEnabled(false);
        clienteV.setEnabled(false);
        ventaV.setEnabled(false);
        pedidoV.setEnabled(false);
        reporteV.setEnabled(false);
        busqquedaC.iniciar(principalUI.getPnlDesk());
    }
}
