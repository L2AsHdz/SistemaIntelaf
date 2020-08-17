package com.l2ashdz.sistemaintelaf.controller.poducto;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.producto.AddProductoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class AddProductoController extends MouseAdapter implements ActionListener {

    private AddProductoView addProductoV;
    private Producto producto;
    private List<Producto> productos;
    private ProductoDAO productoDAO;
    private CRUD<ExistenciaProducto> existenciaPDAO;

    private String codigo;
    private String nombre;
    private String fabricante;
    private String existencias;
    private String precio;
    private String descripcion;
    private String garantia;
    private String codTActual;

    public AddProductoController(AddProductoView productoV) {
        productoDAO = ProductoDAOImpl.getProductoDAO();
        existenciaPDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
        this.addProductoV = productoV;
        this.addProductoV.getBtnAgregar().addActionListener(this);
        this.addProductoV.getBtnActualizar().addActionListener(this);
        this.addProductoV.getBtnLimpiar().addActionListener(this);
        this.addProductoV.getBtnListarproductos().addActionListener(this);
        this.addProductoV.getTblProductos().addMouseListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!addProductoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            addProductoV.setSize(parent.getSize());
            addProductoV.setEnabled(true);
            addProductoV.setVisible(true);
            parent.add(addProductoV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("Ya se esta mostrando addProducto");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Valida los datos y registra un nuevo producto
        if (addProductoV.getBtnAgregar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarAddProducto(codigo, nombre, fabricante, existencias, precio,
                        garantia, codTActual)) {
                    //registra el producto en la base de datos
                    productoDAO.create(nuevoProducto(codigo, nombre, fabricante, precio,
                            existencias, descripcion, garantia, codTActual));
                    
                    //registra las existencias en la base de datos
                    existenciaPDAO.create(nuevaExistenciaProducto(codTActual, codigo, existencias));
                }
                JOptionPane.showMessageDialog(null, "Producto registrado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Actualiza los datos de un producto existente
        } else if (addProductoV.getBtnActualizar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarUpdateProducto(nombre, fabricante, existencias, precio, garantia)) {
                    productoDAO.update(nuevoProducto(codigo, nombre, fabricante, precio, 
                            existencias, descripcion, garantia, codTActual));
                    existenciaPDAO.update(nuevaExistenciaProducto(codTActual, codigo, existencias));
                }
                JOptionPane.showMessageDialog(null, "Produto actualizado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
            
            //Limpia los campos
        } else if (addProductoV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            
            //Mustra el listado de productos con o sin filtro
        } else if (addProductoV.getBtnListarproductos() == e.getSource()) {
            codigo = addProductoV.getTxtFiltroCodigo().getText();
            nombre = addProductoV.getTxtFiltroNombre().getText();
            codTActual = addProductoV.getTxtFiltroTienda().getText();
            
            if (addProductoV.getRbCodigo().isSelected()) {
                productos = productoDAO.getFilteredList(codigo, 1);
            } else if (addProductoV.getRbNombre().isSelected()) {
                productos = productoDAO.getFilteredList(nombre, 2);
            } else if (addProductoV.getRbTienda().isSelected()) {
                productos = productoDAO.getFilteredList(codTActual, 3);
            } else {
                productos = productoDAO.getFilteredList(PrincipalView.lblCodigo.getText(), 4);
            }
            
            //actualiza los datos de la tabla productos
            addProductoV.getProductoObservableList().clear();
            addProductoV.getProductoObservableList().addAll(productos);
        }
    }

    private void limpiarCampos() {
        addProductoV.getProductoObservableList().clear();
        addProductoV.getBgFiltro().clearSelection();
        addProductoV.getTxtCodigo().setText("");
        addProductoV.getTxtCodigo().setEditable(true);
        addProductoV.getTxtNombre().setText("");
        addProductoV.getTxtFabricante().setText("");
        addProductoV.getTxtExistencias().setText("");
        addProductoV.getTxtPrecio().setText("");
        addProductoV.getTxtDescricpcion().setText("");
        addProductoV.getTxtGarantia().setText("");
        addProductoV.getTxtCodigo().requestFocus();
    }

    private void obtenerDatos() {
        codigo = addProductoV.getTxtCodigo().getText();
        nombre = addProductoV.getTxtNombre().getText();
        fabricante = addProductoV.getTxtFabricante().getText();
        existencias = addProductoV.getTxtExistencias().getText();
        precio = addProductoV.getTxtPrecio().getText();
        descripcion = addProductoV.getTxtDescricpcion().getText();
        garantia = addProductoV.getTxtGarantia().getText();
        codTActual = PrincipalView.lblCodigo.getText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addProductoV.getTblProductos().getSelectedRow();
        codigo = addProductoV.getTblProductos().getValueAt(fila, 0).toString();
        producto = productoDAO.getObject(codigo);
        addProductoV.getTxtCodigo().setText(producto.getCodigo());
        addProductoV.getTxtNombre().setText(producto.getNombre());
        addProductoV.getTxtFabricante().setText(producto.getFabricante());
        addProductoV.getTxtExistencias().setText(String.valueOf(producto.getExistencias()));
        addProductoV.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        addProductoV.getTxtDescricpcion().setText(producto.getDescripcion());
        addProductoV.getTxtGarantia().setText(String.valueOf(producto.getGarantiaMeses()));
        addProductoV.getBtnActualizar().setEnabled(true);
    }

}
