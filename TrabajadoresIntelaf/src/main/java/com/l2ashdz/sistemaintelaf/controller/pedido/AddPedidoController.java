package com.l2ashdz.sistemaintelaf.controller.pedido;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isMayorACero;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAO;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.pedido.AddPedidoView;
import com.l2ashdz.sistemaintelaf.ui.venta.FacturaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private FacturaView facturaV;
    private Producto prodPedido;
    private Cliente cliente;
    private Tienda tienda;
    private List<ProductoPedido> productosP;
    private List<Producto> productos;
    private List<Tienda> tiendas;
    private PedidoDAO pedidoDAO;
    private CRUD<ProductoPedido> productoPDAO;
    private CRUD<Tienda> tiendaDAO;
    private TiempoTrasladoDAO tiempoDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private ExistenciaProductoDAO existenciaDAO;

    //datos cliente
    private String nitCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cui;
    private String correo;

    //datos venta y productosVenta
    private LocalDate date;
    private String fecha;
    private String tiendaOrigen;
    private String tiendaDestino;
    private String codigoPedido;
    private String porcentajeEfectivo;
    private String porcentajeCredito;
    private String porcentajePagado;
    private float credito;
    private int filaProducto;
    int diasTraslado;
    private String cantidad;

    public AddPedidoController(AddPedidoView addPedidoView) {
        productosP = new ArrayList<>();
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        productoPDAO = ProductoPedidoDAOImpl.getProductoPDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
        existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
        this.addPedidoV = addPedidoView;
        this.addPedidoV.getBtnContinuar().addActionListener(this);
        this.addPedidoV.getBtnAddProducto().addActionListener(this);
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
            System.out.println("Interfaz addpedido ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addPedidoV.getBtnContinuar() == e.getSource()) {
            if (addPedidoV.getCbBusquedaTienda().getSelectedItem() != null) {
                tienda = (Tienda) addPedidoV.getCbBusquedaTienda().getSelectedItem();
                tiendaOrigen = tienda.getCodigo();
                tiendaDestino = PrincipalView.lblCodigo.getText();
                getDiasTraslado();
                addPedidoV.getLblTiempo().setText(String.valueOf(diasTraslado));
                setEditableCamposPedido(true);
                actualizarProductos(tiendaOrigen);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una tienda",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Agrega un producto al pedido
        } else if (addPedidoV.getBtnAddProducto() == e.getSource()) {
            if (addPedidoV.getCbBusquedaProducto() != null) {
                codigoPedido = String.valueOf(pedidoDAO.getCodigoPedido());
                prodPedido = (Producto) addPedidoV.getCbBusquedaProducto().getSelectedItem();
                cantidad = JOptionPane.showInputDialog(null, "Ingrese cantidad del producto", "Agregar producto",
                        JOptionPane.QUESTION_MESSAGE);
                while (!isInt(cantidad) || !isMayorACero(cantidad)) {
                    cantidad = JOptionPane.showInputDialog(null, "Debe ser un dato numerico y mayor a cero\n\n"
                            + "Ingrese cantidad del producto", "Agregar producto", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    validarAddProducPedido(cantidad, prodPedido, productosP);
                    productosP.add(new ProductoPedido(prodPedido, codigoPedido, cantidad));
                    actualizarTablaP(productosP);
                    addPedidoV.getLblTotal().setText(getTotal(productosP));
                    addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
                    addPedidoV.getCbBusquedaProducto().requestFocus();

                } catch (UserInputException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un producto",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //cambia la cantidad de un producto ya agregado al pedido
        } else if (addPedidoV.getBtnCambiarCant() == e.getSource()) {
            cantidad = JOptionPane.showInputDialog(null, "Ingrese cantidad del producto", "Agregar producto",
                    JOptionPane.QUESTION_MESSAGE);
            while (!isInt(cantidad) || !isMayorACero(cantidad)) {
                cantidad = JOptionPane.showInputDialog(null, "Debe ser un dato numerico y mayor a cero\n\n"
                        + "Ingrese cantidad del producto", "Agregar producto", JOptionPane.ERROR_MESSAGE);
            }
            try {
                validarExistencias(cantidad, productosP.get(filaProducto));
                productosP.get(filaProducto).setCantidad(Integer.parseInt(cantidad));
                actualizarTablaP(productosP);
                setEnableBtnsP(false);
                addPedidoV.getLblTotal().setText(getTotal(productosP));
                addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
                addPedidoV.getCbBusquedaProducto().requestFocus();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Elimina un produccto del pedido
        } else if (addPedidoV.getBtnEliminarP() == e.getSource()) {
            productosP.remove(filaProducto);
            actualizarTablaP(productosP);
            setEnableBtnsP(false);
            addPedidoV.getLblTotal().setText(getTotal(productosP));
            addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
            addPedidoV.getCbBusquedaProducto().requestFocus();

            //limpia el formulario de pedido
        } else if (addPedidoV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            setEnableBtnsP(false);

            //Registra un nuevo pedido
        } else if (addPedidoV.getBtnFinalizar() == e.getSource()) {
            obtenerDatosC();
            obtenerDatosP();

            try {
                validarAddCliente2(nombre, nitCliente, telefono);
                validarPedido(porcentajeEfectivo, porcentajeCredito, porcentajePagado);
                if (clienteDAO.getObject(nitCliente) == null) {
                    clienteDAO.create(nuevoCliente(nitCliente, nombre, cui, direccion, telefono, correo));
                }
                pedidoDAO.create(new Pedido(codigoPedido, nitCliente, tiendaOrigen, tiendaDestino,
                        fecha, porcentajeCredito, porcentajeEfectivo, porcentajePagado, 1));
                productosP.forEach(pp -> productoPDAO.create(pp));
                productosP.forEach(pp -> existenciaDAO.restarExistencias(tiendaDestino, pp.getCodigo(), pp.getCantidad()));

                clienteDAO.restarCredito(nitCliente, credito);

                generarFactura();
                actualizarProductos(tiendaOrigen);
                limpiarCampos();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        limpiarCamposCliente();
        addPedidoV.getTxtPorcentCredito().setText("");
        addPedidoV.getTxtPorcentEfectivo().setText("");
        addPedidoV.getTxtPorcentAnticipo().setText("");
        addPedidoV.getTxtAnticipo().setText("");
        addPedidoV.getTxtEfectivo().setText("");
        addPedidoV.getTxtCredito().setText("");
        addPedidoV.getLblTotal().setText("0");
        addPedidoV.getTxtFecha().setDate(new Date());
        productosP.removeAll(productosP);
        addPedidoV.getProdPedidoObservableList().clear();
        addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
        addPedidoV.getCbBusquedaTienda().setSelectedIndex(-1);
        setEditableCamposPedido(false);
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

    private void obtenerDatosP() {
        Date input = addPedidoV.getTxtFecha().getDate();
        date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fecha = date.toString() == null ? "" : date.toString();
        porcentajeCredito = addPedidoV.getTxtPorcentCredito().getText();
        porcentajeEfectivo = addPedidoV.getTxtPorcentEfectivo().getText();
        porcentajePagado = addPedidoV.getTxtPorcentAnticipo().getText();

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
        productos = productoDAO.getFilteredList(tiendaO, 5);
        addPedidoV.getProductosObservableList().addAll(productos);
        addPedidoV.getCbBusquedaProducto().setSelectedIndex(-1);
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

    private void setEnableBtnsP(boolean enable) {
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
        setEnableBtnsP(true);
    }

    private void generarFactura() {
        facturaV = new FacturaView();
        insertarTextfactura("Codigo del pedido: " + codigoPedido);
        insertarTextfactura("\nFecha del pedido: " + fecha);

        tienda = tiendaDAO.getObject(tiendaOrigen);
        insertarTextfactura("\n\nTienda Origen:");
        insertarTextfactura("\nCodigo:\t" + tienda.getCodigo());
        insertarTextfactura("\nNombre:\t" + tienda.getNombre());
        insertarTextfactura("\nDireccion:\t" + tienda.getDireccion());
        insertarTextfactura("\nTelfono:\t" + tienda.getTelefono1());

        tienda = tiendaDAO.getObject(tiendaDestino);
        insertarTextfactura("\n\nTienda Destino:");
        insertarTextfactura("\nCodigo:\t" + tienda.getCodigo());
        insertarTextfactura("\nNombre:\t" + tienda.getNombre());
        insertarTextfactura("\nDireccion:\t" + tienda.getDireccion());
        insertarTextfactura("\nTelfono:\t" + tienda.getTelefono1());

        insertarTextfactura("\n\nCliente:");
        insertarTextfactura("\nNombre:\t" + nombre);
        insertarTextfactura("\nNit:\t" + nitCliente);
        insertarTextfactura("\nDireccion:\t" + direccion);
        insertarTextfactura("\nTelefono:\t" + telefono + "\n");

        insertarTextfactura("\nProductos:");
        productosP.forEach(pv -> {
            insertarTextfactura("\n" + pv.getNombre() + " (x" + pv.getCantidad() + ")"
                    + " \tQ." + pv.getSubtotal());
        });

        insertarTextfactura("\n\nTotal:\t\t Q." + getTotal(productosP));
        insertarTextfactura("\nAnticipo:\t\t Q."
                + Float.parseFloat(getTotal(productosP)) * Float.parseFloat(porcentajePagado));

        getDiasTraslado();
        insertarTextfactura("\n\nFecha llegada: \t " + date.plusDays(diasTraslado));

        facturaV.setLocationRelativeTo(null);
        facturaV.setVisible(true);
    }

    private void insertarTextfactura(String text) {
        facturaV.getTxtAFactura().append(text);
    }
    
    private void getDiasTraslado(){
        if (tiempoDAO.getTiempoT(tiendaDestino, tiendaOrigen) == null) {
            diasTraslado = tiempoDAO.getTiempoT(tiendaOrigen, tiendaDestino).getTiempo();
        } else {
            diasTraslado = tiempoDAO.getTiempoT(tiendaDestino, tiendaOrigen).getTiempo();
        }
    }
}
