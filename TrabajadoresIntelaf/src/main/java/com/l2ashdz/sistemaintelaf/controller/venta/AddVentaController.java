package com.l2ashdz.sistemaintelaf.controller.venta;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isMayorACero;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.ProductoVentaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class AddVentaController extends MouseAdapter implements ActionListener, KeyListener, FocusListener {

    private AddVentaView addVentaV;
    private Venta venta;
    private Producto prodVenta;
    private Cliente cliente;
    private List<ProductoVenta> productosV;
    private List<Producto> productos;
    private VentaDAO ventaDAO;
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
    private String cantidad;
    private float subTotal;

    public AddVentaController(AddVentaView addVentaV) {
        productosV = new ArrayList<>();
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

            //carga los productos al JComboBox
            addVentaV.getProductosObservableList().clear();
            productos = productoDAO.getFilteredList(PrincipalView.lblCodigo.getText(), 4);
            addVentaV.getProductosObservableList().addAll(productos);
            limpiarCampos();

        } else {
            System.out.println("Ya se esta mostrando addVenta");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Agrega un producto a la venta
        if (addVentaV.getBtnAddProducto() == e.getSource()) {

            if (addVentaV.getCbBusquedaProducto().getSelectedItem() != null) {
                idVenta = String.valueOf(ventaDAO.getIdVenta());
                prodVenta = (Producto) addVentaV.getCbBusquedaProducto().getSelectedItem();
                cantidad = JOptionPane.showInputDialog(null, "Ingrese cantidad del producto", "Agregar producto",
                        JOptionPane.QUESTION_MESSAGE);
                while (!isInt(cantidad) || !isMayorACero(cantidad)) {
                    cantidad = JOptionPane.showInputDialog(null, "Debe ser un dato numerico mayor a cero\n"
                            + "Ingrese cantidad del producto", "Agregar producto", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    validarAddProducVenta(cantidad, prodVenta, productosV);
                    productosV.add(new ProductoVenta(prodVenta, idVenta, cantidad));
                    addVentaV.getProdVentaObservableList().clear();
                    addVentaV.getProdVentaObservableList().addAll(productosV);
                    subTotal += Integer.parseInt(cantidad)*prodVenta.getPrecio();
                    addVentaV.getLblTotal().setText(String.valueOf(subTotal));

                } catch (UserInputException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un producto",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        limpiarCamposCliente();
        subTotal = 0;
        addVentaV.getTxtPorcentCredito().setText("");
        addVentaV.getTxtPorcentEfectivo().setText("");
        addVentaV.getTxtEfectivo().setText("");
        addVentaV.getTxtCredito().setText("");
        addVentaV.getLblTotal().setText("####");
        addVentaV.getTxtFecha().setCalendar(null);
        productosV.removeAll(productosV);
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
        addVentaV.getLblCreditoCompra().setText("####");
        setEnabledCamposC(false);
    }

    private void obtenerDatosC() {
        nitCliente = addVentaV.getTxtNit().getText();
        nombre = addVentaV.getTxtNombre().getText();
        telefono = addVentaV.getTxtTelefono().getText().trim();
        direccion = addVentaV.getTxtDireccion().getText();
        cui = addVentaV.getTxtCUI().getText();
        correo = addVentaV.getTxtCorreo().getText();
    }

    private void obtenerDatosP(Producto p) {
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
                addVentaV.getLblCreditoCompra().setText(String.valueOf(cliente.getCreditoCompra()));
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
