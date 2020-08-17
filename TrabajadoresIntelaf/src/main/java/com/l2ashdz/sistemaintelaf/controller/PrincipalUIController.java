package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.controller.poducto.AddProductoController;
import com.l2ashdz.sistemaintelaf.controller.tienda.TiendaController;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.producto.AddProductoView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiendaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author asael
 */
public class PrincipalUIController extends MouseAdapter {

    private PrincipalView principalUI;

    //Vista y controlador para tienda
    private TiendaView tiendaV = new TiendaView();
    private TiendaController tiendaC = new TiendaController(tiendaV);

    //Vista y controlador para menu productos
    private AddProductoView productoV = new AddProductoView();
    private AddProductoController productoC = new AddProductoController(productoV);

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
    }

    public void iniciar() {
        principalUI.pack();
        principalUI.setResizable(false);
        principalUI.setLocationRelativeTo(null);
        principalUI.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Inicia la interfaz de tienda
        if (principalUI.getBtnTiendas() == e.getSource()) {
            productoV.setEnabled(false);
            tiendaC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnProductos() == e.getSource()) {
            tiendaV.setEnabled(false);
            productoC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnEmpleados() == e.getSource()) {

        } else if (principalUI.getBtnClientes() == e.getSource()) {

        } else if (principalUI.getBtnVentas() == e.getSource()) {

        } else if (principalUI.getBtnPedidos() == e.getSource()) {

        } else if (principalUI.getBtnReportes() == e.getSource()) {

        }
    }
}
