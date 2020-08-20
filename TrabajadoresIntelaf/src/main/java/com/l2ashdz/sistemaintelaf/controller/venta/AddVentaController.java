package com.l2ashdz.sistemaintelaf.controller.venta;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isMayorACero;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAO;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.ProductoVentaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.venta.AddVentaView;
import com.l2ashdz.sistemaintelaf.ui.venta.FacturaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
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
public class AddVentaController extends MouseAdapter implements ActionListener, FocusListener {

    private AddVentaView addVentaV;
    private FacturaView facturaV;
    private Producto prodVenta;
    private Cliente cliente;
    private List<ProductoVenta> productosV;
    private List<Producto> productos;
    private VentaDAO ventaDAO;
    private CRUD<ProductoVenta> productoVDAO;
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
    private String tiendaActual;
    private String idVenta;
    private String porcentajeEfectivo;
    private String porcentajeCredito;
    private float credito;
    private int filaProducto;
    private String cantidad;

    public AddVentaController(AddVentaView addVentaV) {
        conexion = Conexion.getConexion();
        productosV = new ArrayList<>();
        facturaV = new FacturaView();
        ventaDAO = VentaDAOImpl.getVentaDAO();
        productoVDAO = ProductoVentaDAOImpl.getProductoVentaDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
        this.addVentaV = addVentaV;
        this.addVentaV.getBtnAddProducto().addActionListener(this);
        this.addVentaV.getBtnFinalizar().addActionListener(this);
        this.addVentaV.getBtnCambiarCant().addActionListener(this);
        this.addVentaV.getBtnEliminarP().addActionListener(this);
        this.addVentaV.getBtnLimpiar().addActionListener(this);
        this.addVentaV.getTblProductosVenta().addMouseListener(this);
        this.addVentaV.getTxtNit().addFocusListener(this);
    }

    //Inicia la interfaz
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
            actualizarDatosP();
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
                    cantidad = JOptionPane.showInputDialog(null, "Debe ser un dato numerico y mayor a cero\n\n"
                            + "Ingrese cantidad del producto", "Agregar producto", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    validarAddProducVenta(cantidad, prodVenta, productosV);
                    productosV.add(new ProductoVenta(prodVenta, idVenta, cantidad));
                    actualizarTablaP(productosV);
                    addVentaV.getLblTotal().setText(getTotal(productosV));
                    addVentaV.getCbBusquedaProducto().setSelectedIndex(-1);
                    addVentaV.getCbBusquedaProducto().requestFocus();

                } catch (UserInputException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un producto",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Elimina un producto de la venta
        } else if (addVentaV.getBtnEliminarP() == e.getSource()) {
            productosV.remove(filaProducto);
            actualizarTablaP(productosV);
            setEnableBtns(false);
            addVentaV.getLblTotal().setText(getTotal(productosV));
            addVentaV.getCbBusquedaProducto().setSelectedIndex(-1);
            addVentaV.getCbBusquedaProducto().requestFocus();

            //Cambia la cantidad a vender de un producto
        } else if (addVentaV.getBtnCambiarCant() == e.getSource()) {
            cantidad = JOptionPane.showInputDialog(null, "Ingrese cantidad del producto", "Agregar producto",
                    JOptionPane.QUESTION_MESSAGE);
            while (!isInt(cantidad) || !isMayorACero(cantidad)) {
                cantidad = JOptionPane.showInputDialog(null, "Debe ser un dato numerico y mayor a cero\n\n"
                        + "Ingrese cantidad del producto", "Agregar producto", JOptionPane.ERROR_MESSAGE);
            }
            try {
                validarExistencias(cantidad, productosV.get(filaProducto));
                productosV.get(filaProducto).setCantidad(Integer.parseInt(cantidad));
                actualizarTablaP(productosV);
                setEnableBtns(false);
                addVentaV.getLblTotal().setText(getTotal(productosV));
                addVentaV.getCbBusquedaProducto().setSelectedIndex(-1);
                addVentaV.getCbBusquedaProducto().requestFocus();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Limpia completamente la interfaz
        } else if (addVentaV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            setEnableBtns(false);

            //Valida los datos y registra la venta
        } else if (addVentaV.getBtnFinalizar() == e.getSource()) {
            idVenta = String.valueOf(ventaDAO.getIdVenta());
            obtenerDatosV();
            obtenerDatosC();

            try {
                conexion.setAutoCommit(false);
                validarAddCliente2(nombre, nitCliente, telefono);
                validarVenta(fecha, porcentajeEfectivo, porcentajeCredito);
                if (clienteDAO.getObject(nitCliente) == null) {
                    clienteDAO.create(nuevoCliente(nitCliente, nombre, cui, direccion, telefono, correo));
                }
                ventaDAO.create(nuevaVenta(nitCliente, fecha, porcentajeCredito, porcentajeEfectivo, tiendaActual));
                productosV.forEach(pv -> productoVDAO.create(pv));
                productosV.forEach(pv -> existenciaDAO.restarExistencias(tiendaActual,
                        pv.getCodigo(), pv.getCantidad()));

                clienteDAO.restarCredito(nitCliente, credito);
                conexion.commit();
                conexion.setAutoCommit(true);

                generarFactura();
                actualizarDatosP();
                limpiarCampos();

            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                try {
                    conexion.rollback();
                } catch (SQLException ex2) {
                    ex2.printStackTrace(System.out);
                }
            }

        }
    }

    private void limpiarCampos() {
        limpiarCamposCliente();
        addVentaV.getTxtPorcentCredito().setText("");
        addVentaV.getTxtPorcentEfectivo().setText("");
        addVentaV.getTxtEfectivo().setText("");
        addVentaV.getTxtCredito().setText("");
        addVentaV.getLblTotal().setText("0");
        addVentaV.getTxtFecha().setDate(new Date());
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
        addVentaV.getLblCreditoCompra().setText("0");
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

    private void obtenerDatosV() {
        Date input = addVentaV.getTxtFecha().getDate();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fecha = date.toString() == null ? "" : date.toString();
        tiendaActual = PrincipalView.lblCodigo.getText();
        porcentajeCredito = addVentaV.getTxtPorcentCredito().getText();
        porcentajeEfectivo = addVentaV.getTxtPorcentEfectivo().getText();

        if (!addVentaV.getTxtCredito().getText().isEmpty()) {
            credito = Float.parseFloat(addVentaV.getTxtCredito().getText());
        }
    }

    private String getTotal(List<ProductoVenta> pvList) {
        float total = 0;
        for (ProductoVenta pv : pvList) {
            total += pv.getSubtotal();
        }
        return String.valueOf(total);
    }

    private void actualizarTablaP(List<ProductoVenta> pList) {
        addVentaV.getProdVentaObservableList().clear();
        addVentaV.getProdVentaObservableList().addAll(pList);
    }

    private void actualizarDatosP() {
        addVentaV.getProductosObservableList().clear();
        productos = productoDAO.getFilteredList(PrincipalView.lblCodigo.getText(), 5);
        addVentaV.getProductosObservableList().addAll(productos);
    }

    private void setEnableBtns(boolean enable) {
        addVentaV.getBtnEliminarP().setEnabled(enable);
        addVentaV.getBtnCambiarCant().setEnabled(enable);
    }

    private void setEnabledCamposC(boolean enable) {
        addVentaV.getTxtNombre().setEditable(enable);
        addVentaV.getTxtTelefono().setEditable(enable);
        addVentaV.getTxtCorreo().setEditable(enable);
        addVentaV.getTxtDireccion().setEditable(enable);
        addVentaV.getTxtCUI().setEditable(enable);
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
    public void mouseClicked(MouseEvent e) {
        filaProducto = addVentaV.getTblProductosVenta().getSelectedRow();
        setEnableBtns(true);
    }

    private void generarFactura() {
        insertarTextfactura("Factura No. "+idVenta);
        insertarTextfactura("\nFecha: "+fecha);
        
        insertarTextfactura("\nCliente:");
        insertarTextfactura("\nNombre:\t" + nombre);
        insertarTextfactura("\nNit:\t" + nitCliente);
        insertarTextfactura("\nDireccion:\t" + direccion);
        insertarTextfactura("\nTelefono:\t" + telefono + "\n");

        insertarTextfactura("\nProductos:");
        productosV.forEach(pv -> {
            insertarTextfactura("\n" + pv.getNombre() + " (x" + pv.getCantidad() + ")"
                    + "\tQ." + pv.getSubtotal());
        });

        insertarTextfactura("\n\nTotal:\t\tQ." + getTotal(productosV));

        facturaV.setLocationRelativeTo(null);
        facturaV.setVisible(true);
    }
    
    private void insertarTextfactura(String text) {
        facturaV.getTxtAFactura().append(text);
    }
}
