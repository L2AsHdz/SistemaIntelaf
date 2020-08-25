package com.l2ashdz.clientesintelaf.controller;

import com.l2ashdz.clientesintelaf.dao.ProductoDAO;
import com.l2ashdz.clientesintelaf.model.Producto;
import com.l2ashdz.clientesintelaf.ui.BusquedaPView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class BusquedaPController implements ActionListener {
    
    private BusquedaPView busquedaV;
    private List<Producto> productos;
    private ProductoDAO productoDAO;
    
    public BusquedaPController(BusquedaPView busquedaV) {
        productoDAO = ProductoDAO.getProductoDAO();
        this.busquedaV = busquedaV;
        this.busquedaV.getBtnBuscar().addActionListener(this);
    }
    
    public void iniciar(JPanel parent) {
        if (!busquedaV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            busquedaV.setSize(parent.getSize());
            busquedaV.setEnabled(true);
            busquedaV.setVisible(true);
            parent.add(busquedaV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("Interfaz catalogo ya esta visible");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (busquedaV.getBtnBuscar() == e.getSource()) {
            String codigo = busquedaV.getTxtCodigo().getText();
            String nombre = busquedaV.getTxtNombre().getText();
            String fabricante = busquedaV.getTxtFabricante().getText();
            
            if (busquedaV.getChbCodigo().isSelected()
                    && busquedaV.getChbFabricante().isSelected()
                    && busquedaV.getChbNombre().isSelected()) {
                //todos los filtros
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 1);
            } else if (busquedaV.getChbCodigo().isSelected()
                    && busquedaV.getChbFabricante().isSelected()
                    && !busquedaV.getChbNombre().isSelected()) {
                //Solo codio y fabricante
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 2);
            } else if (!busquedaV.getChbCodigo().isSelected()
                    && busquedaV.getChbFabricante().isSelected()
                    && busquedaV.getChbNombre().isSelected()) {
                //solo fabricante y nombre
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 3);
            } else if (busquedaV.getChbCodigo().isSelected()
                    && !busquedaV.getChbFabricante().isSelected()
                    && busquedaV.getChbNombre().isSelected()) {
                //solo nombre y codigo
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 4);
            } else if (busquedaV.getChbCodigo().isSelected()
                    && !busquedaV.getChbFabricante().isSelected()
                    && !busquedaV.getChbNombre().isSelected()) {
                //solo codigo
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 5);
            } else if (!busquedaV.getChbCodigo().isSelected()
                    && busquedaV.getChbFabricante().isSelected()
                    && !busquedaV.getChbNombre().isSelected()) {
                //solo fabricante
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 6);
            } else if (!busquedaV.getChbCodigo().isSelected()
                    && !busquedaV.getChbFabricante().isSelected()
                    && busquedaV.getChbNombre().isSelected()) {
                //solo nombre
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 7);
            } else {
                //si filtros
                productos = productoDAO.getFilteredList(codigo, nombre, fabricante, 8);
            }
            
            busquedaV.getProductoObservableList().clear();
            busquedaV.getProductoObservableList().addAll(productos);
        }
    }
    
    private void limpiarCampos() {
        busquedaV.getTxtCodigo().setText("");
        busquedaV.getTxtFabricante().setText("");
        busquedaV.getTxtNombre().setText("");
//        busquedaV.getTxtCodigo().setEditable(false);
//        busquedaV.getTxtFabricante().setEditable(false);
//        busquedaV.getTxtNombre().setEditable(false);
        busquedaV.getChbCodigo().setSelected(false);
        busquedaV.getChbFabricante().setSelected(false);
        busquedaV.getChbNombre().setSelected(false);
        busquedaV.getProductoObservableList().clear();
    }
    
}
