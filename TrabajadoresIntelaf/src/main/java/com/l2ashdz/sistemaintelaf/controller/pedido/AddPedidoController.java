package com.l2ashdz.sistemaintelaf.controller.pedido;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAO;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.pedido.AddPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class AddPedidoController extends MouseAdapter implements ActionListener, FocusListener {

    private AddPedidoView addPedidoV;
    private Producto prodPedido;
    private Cliente cliente;
    private List<ProductoPedido> productosP;
    private List<Producto> productos;
    private List<Tienda> tiendas;
    private CRUD<Pedido> pedidoDAO;
    private CRUD<ProductoPedido> productoPDAO;
    private CRUD<Tienda> tiendaDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private ExistenciaProductoDAO existenciaDAO;
    private Connection conexion;

    //datos cliente
    private String nitCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cui;
    private String correo;

    //datos venta y productosVenta
    private String fecha;
    private String tiendaOrigen;
    private String tiendaDestino;
    private String idPedido;
    private String porcentajeEfectivo;
    private String porcentajeCredito;
    private String porcentajePagado;
    private float credito;
    private int filaProducto;
    private String cantidad;

    public AddPedidoController(AddPedidoView addPedidoView) {
        conexion = Conexion.getConexion();
        productosP = new ArrayList<>();
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        productoPDAO = ProductoPedidoDAOImpl.getProductoPDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
        this.addPedidoV = addPedidoView;
        this.addPedidoV.getBtnContinuar().addActionListener(this);
        this.addPedidoV.getBtnFinalizar().addActionListener(this);
        this.addPedidoV.getBtnCambiarCant().addActionListener(this);
        this.addPedidoV.getBtnEliminarP().addActionListener(this);
        this.addPedidoV.getBtnLimpiar().addActionListener(this);
        this.addPedidoV.getTblProductosVenta().addMouseListener(this);
        this.addPedidoV.getTxtNit().addFocusListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!addPedidoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            addPedidoV.setSize(parent.getSize());
            addPedidoV.setEnabled(true);
            addPedidoV.setVisible(true);
            parent.add(addPedidoV);
            parent.validate();

            cargarTiendas();
            limpiarCampos();

        } else {
            System.out.println("Interfaz pedido ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addPedidoV.getBtnContinuar() == e.getSource()) {
            if (addPedidoV.getCbBusquedaTienda().getSelectedItem() != null) {
                tiendaOrigen = addPedidoV.getCbBusquedaTienda().getSelectedItem().toString();
                setEditableCamposPedido(true);
                addPedidoV.getTxtNit().requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una tienda",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (addPedidoV.getBtnAddProducto() == e.getSource()) {

        } else if (addPedidoV.getBtnCambiarCant() == e.getSource()) {

        } else if (addPedidoV.getBtnEliminarP() == e.getSource()) {

        } else if (addPedidoV.getBtnLimpiar() == e.getSource()) {

        } else if (addPedidoV.getBtnFinalizar() == e.getSource()) {

        }
    }

    private void limpiarCampos() {
        limpiarCamposCliente();
        addPedidoV.getTxtPorcentCredito().setText("");
        addPedidoV.getTxtPorcentEfectivo().setText("");
        addPedidoV.getTxtEfectivo().setText("");
        addPedidoV.getTxtCredito().setText("");
        addPedidoV.getLblTotal().setText("0");
        addPedidoV.getTxtFecha().setDate(new Date());
        productosP.removeAll(productosP);
        addPedidoV.getProdPedidoObservableList().clear();
        addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
        setEditableCamposPedido(false);
        addPedidoV.getCbBusquedaTienda().setSelectedIndex(-1);
        addPedidoV.getCbBusquedaTienda().requestFocus();
    }

    private void limpiarCamposCliente() {
        addPedidoV.getTxtNit().setText("");
        addPedidoV.getTxtNombre().setText("");
        addPedidoV.getTxtTelefono().setText("");
        addPedidoV.getTxtCorreo().setText("");
        addPedidoV.getTxtDireccion().setText("");
        addPedidoV.getTxtCUI().setText("");
        addPedidoV.getLblCreditoCompra().setText("0");
        setEnabledCamposC(false);
    }

    private void setEnabledCamposC(boolean enable) {
        addPedidoV.getTxtNombre().setEditable(enable);
        addPedidoV.getTxtTelefono().setEditable(enable);
        addPedidoV.getTxtCorreo().setEditable(enable);
        addPedidoV.getTxtDireccion().setEditable(enable);
        addPedidoV.getTxtCUI().setEditable(enable);
    }

    private void setEditableCamposPedido(boolean editable) {
        addPedidoV.getTxtNit().setEnabled(editable);
        addPedidoV.getCbBusquedaProducto().setEditable(editable);
        addPedidoV.getBtnAddProducto().setEnabled(editable);
        addPedidoV.getBtnLimpiar().setEnabled(editable);
        addPedidoV.getBtnFinalizar().setEnabled(editable);
        addPedidoV.getTxtPorcentCredito().setEditable(editable);
        addPedidoV.getTxtPorcentEfectivo().setEditable(editable);
        addPedidoV.getTxtCredito().setEditable(editable);
        addPedidoV.getTxtEfectivo().setEditable(editable);
        addPedidoV.getTxtFecha().setEnabled(editable);
    }

    private void obtenerDatosC() {
        nitCliente = addPedidoV.getTxtNit().getText();
        nombre = addPedidoV.getTxtNombre().getText();
        telefono = addPedidoV.getTxtTelefono().getText().trim();
        direccion = addPedidoV.getTxtDireccion().getText();
        cui = addPedidoV.getTxtCUI().getText();
        correo = addPedidoV.getTxtCorreo().getText();
    }

    private void obtenerDatosV() {
        Date input = addPedidoV.getTxtFecha().getDate();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fecha = date.toString() == null ? "" : date.toString();
        tiendaOrigen = addPedidoV.getCbBusquedaTienda().getSelectedItem().toString();
        porcentajeCredito = addPedidoV.getTxtPorcentCredito().getText();
        porcentajeEfectivo = addPedidoV.getTxtPorcentEfectivo().getText();

        if (!addPedidoV.getTxtCredito().getText().isEmpty()) {
            credito = Float.parseFloat(addPedidoV.getTxtCredito().getText());
        }
    }

    private String getTotal(List<ProductoPedido> pvList) {
        float total = 0;
        for (ProductoPedido pv : pvList) {
            total += pv.getSubtotal();
        }
        return String.valueOf(total);
    }

    private void actualizarTablaP(List<ProductoPedido> pList) {
        addPedidoV.getProdPedidoObservableList().clear();
        addPedidoV.getProdPedidoObservableList().addAll(pList);
    }

    private void actualizarProductos(String tiendaO) {
        addPedidoV.getProductosObservableList().clear();
        productos = productoDAO.getFilteredList(tiendaO, 4);
        addPedidoV.getProductosObservableList().addAll(productos);
    }

    private void cargarTiendas() {
        tiendaDestino = PrincipalView.lblCodigo.getText();
        tiendas = tiendaDAO.getListado();
        for (int i = 0; i < tiendas.size(); i++) {
            if (tiendas.get(i).getCodigo().equals(tiendaDestino)) {
                tiendas.remove(i);
            }
        }
        addPedidoV.getTiendaObservableList().clear();
        addPedidoV.getTiendaObservableList().addAll(tiendas);
    }

    private void setEnableBtns(boolean enable) {
        addPedidoV.getBtnEliminarP().setEnabled(enable);
        addPedidoV.getBtnCambiarCant().setEnabled(enable);
    }

    @Override
    public void focusGained(FocusEvent e) {
        addPedidoV.getTxtNit().setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (addPedidoV.getTxtNit() == e.getComponent()) {
            nitCliente = addPedidoV.getTxtNit().getText();
            cliente = clienteDAO.getObject(nitCliente);
            if (cliente != null) {
                addPedidoV.getTxtNombre().setText(cliente.getNombre());
                addPedidoV.getTxtTelefono().setText(cliente.getTelefono());
                addPedidoV.getTxtCorreo().setText(cliente.getCorreo());
                addPedidoV.getTxtDireccion().setText(cliente.getDireccion());
                addPedidoV.getTxtCUI().setText(cliente.getCui());
                addPedidoV.getLblCreditoCompra().setText(String.valueOf(cliente.getCreditoCompra()));
                addPedidoV.getCbBusquedaProducto().requestFocus();
            } else {
                System.out.println("cliente no existe");
                limpiarCamposCliente();
                setEnabledCamposC(true);
                addPedidoV.getTxtNombre().requestFocus();
            }
            addPedidoV.getTxtNit().setText(nitCliente);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        filaProducto = addPedidoV.getTblProductosVenta().getSelectedRow();
        setEnableBtns(true);
    }
}
