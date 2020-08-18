package com.l2ashdz.sistemaintelaf.controller.venta;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.ProductoVentaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import com.l2ashdz.sistemaintelaf.model.Venta;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.venta.AddVentaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class AddVentaController extends MouseAdapter implements ActionListener, KeyListener, FocusListener {

    private AddVentaView addVentaV;
    private Venta venta;
    private ProductoVenta productoVenta;
    private Cliente cliente;
    private List<ProductoVenta> productosV;
    private List<Producto> productos;
    private CRUD<Venta> ventaDAO;
    private CRUD<ProductoVenta> productoVDAO;
    private ProductoDAO productoDAO;
    private CRUD<Cliente> clienteDAO;

    private String nitCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cui;
    private String correo;
    private String fecha;
    private String tiedaActual;
    private String idVenta;
    private String porcentajeEfectivo;
    private String porcentajeCredito;
    private String credito;
    private String efectivo;

    public AddVentaController(AddVentaView addVentaV) {
        ventaDAO = VentaDAOImpl.getVentaDAO();
        productoVDAO = ProductoVentaDAOImpl.getProductoVentaDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        this.addVentaV = addVentaV;
        this.addVentaV.getBtnAddProducto().addActionListener(this);
        this.addVentaV.getBtnSiguiente().addActionListener(this);
        this.addVentaV.getTblProductosVenta().addMouseListener(this);
        this.addVentaV.getCbBusquedaProducto().getEditor().getEditorComponent().addKeyListener(this);
        this.addVentaV.getTxtNit().addFocusListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!addVentaV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            addVentaV.setSize(parent.getSize());
            addVentaV.setEnabled(true);
            addVentaV.setVisible(true);
            parent.add(addVentaV);
            parent.validate();
            limpiarCampos();

        } else {
            System.out.println("Ya se esta mostrando addVenta");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void limpiarCampos() {
        limpiarCamposCliente();
        addVentaV.getTxtPorcentCredito().setText("");
        addVentaV.getTxtPorcentEfectivo().setText("");
        addVentaV.getTxtEfectivo().setText("");
        addVentaV.getTxtCredito().setText("");
        addVentaV.getTxtFecha().setCalendar(null);
        addVentaV.getProdVentaObservableList().clear();
        addVentaV.getCbBusquedaProducto().setSelectedIndex(-1);
        addVentaV.getTxtNit().requestFocus();
    }

    private void limpiarCamposCliente() {
        addVentaV.getTxtNit().setText("");
        addVentaV.getTxtNombre().setText("");
        addVentaV.getTxtTelefono().setText("");
        addVentaV.getTxtCorreo().setText("");
        addVentaV.getTxtDireccion().setText("");
        addVentaV.getTxtCUI().setText("");
        setEnabledCamposC(false);
    }

    private void obtenerDatos() {
        nitCliente = addVentaV.getTxtNit().getText();
        nombre = addVentaV.getTxtNombre().getText();
        telefono = addVentaV.getTxtTelefono().getText().trim();
        direccion = addVentaV.getTxtDireccion().getText();
        cui = addVentaV.getTxtCUI().getText();
        correo = addVentaV.getTxtCorreo().getText();
        fecha = addVentaV.getTxtFecha().getDateFormatString();
        tiedaActual = PrincipalView.lblCodigo.toString();
        porcentajeCredito = addVentaV.getTxtPorcentCredito().getText();
        porcentajeEfectivo = addVentaV.getTxtPorcentEfectivo().getText();
        efectivo = addVentaV.getTxtEfectivo().getText();
        credito = addVentaV.getTxtEfectivo().getText();
    }

    private void setEnabledCamposC(boolean bool) {
        addVentaV.getTxtNombre().setEditable(bool);
        addVentaV.getTxtTelefono().setEditable(bool);
        addVentaV.getTxtCorreo().setEditable(bool);
        addVentaV.getTxtDireccion().setEditable(bool);
        addVentaV.getTxtCUI().setEditable(bool);
    }

    @Override
    public void focusGained(FocusEvent e) {
        addVentaV.getTxtNit().setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (addVentaV.getTxtNit() == e.getComponent()) {
            nitCliente = addVentaV.getTxtNit().getText();
            cliente = clienteDAO.getObject(nitCliente);
            if (cliente != null) {
                addVentaV.getTxtNombre().setText(cliente.getNombre());
                addVentaV.getTxtTelefono().setText(cliente.getTelefono());
                addVentaV.getTxtCorreo().setText(cliente.getCorreo());
                addVentaV.getTxtDireccion().setText(cliente.getDireccion());
                addVentaV.getTxtCUI().setText(cliente.getCui());
                addVentaV.getCbBusquedaProducto().requestFocus();
            } else {
                System.out.println("cliente no existe");
                limpiarCamposCliente();
                setEnabledCamposC(true);
                addVentaV.getTxtNombre().requestFocus();
            }
            addVentaV.getTxtNit().setText(nitCliente);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

}
