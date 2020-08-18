package com.l2ashdz.sistemaintelaf.controller.venta;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.ProductoVentaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
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
    private List<ProductoVenta> productosV;
    private List<Producto> productos;
    private CRUD<Venta> ventaDAO;
    private CRUD<ProductoVenta> productoVDAO;
    private ProductoDAO productoDAO;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void limpiarCampos() {
        addVentaV.getTxtNit().setText("");
        addVentaV.getTxtNombre().setText("");
        addVentaV.getTxtNombre().setEditable(false);
        addVentaV.getTxtTelefono().setText("");
        addVentaV.getTxtTelefono().setEditable(false);
        addVentaV.getTxtCorreo().setText("");
        addVentaV.getTxtCorreo().setEditable(false);
        addVentaV.getTxtDireccion().setText("");
        addVentaV.getTxtDireccion().setEditable(false);
        addVentaV.getTxtCUI().setText("");
        addVentaV.getTxtCUI().setEditable(false);
        addVentaV.getTxtPorcentCredito().setText("");
        addVentaV.getTxtPorcentEfectivo().setText("");
        addVentaV.getTxtEfectivo().setText("");
        addVentaV.getTxtCredito().setText("");
        addVentaV.getTxtFecha().setCalendar(null);
        addVentaV.getProdVentaObservableList().clear();
        addVentaV.getCbBusquedaProducto().setSelectedIndex(-1);
        addVentaV.getTxtNit().requestFocus();
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
    
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

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
