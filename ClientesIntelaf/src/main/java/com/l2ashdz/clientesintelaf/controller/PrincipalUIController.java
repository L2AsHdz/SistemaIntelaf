package com.l2ashdz.clientesintelaf.controller;

import com.l2ashdz.clientesintelaf.ui.BusquedaPView;
import com.l2ashdz.clientesintelaf.ui.PrincipalUI;
import com.l2ashdz.clientesintelaf.ui.RastreoPedidoView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author asael
 */
public class PrincipalUIController extends MouseAdapter {
    
    private PrincipalUI principalUI;
    
    //Vista y controlador para catalogo de productos
    private BusquedaPView busquedaV = new BusquedaPView();
    private BusquedaPController busqquedaC = new BusquedaPController(busquedaV);
    
    //Vista y controlador para rastreo de pedido
    private RastreoPedidoView rastreoV = new RastreoPedidoView();
    private RastreoPedidoController rastreoC = new RastreoPedidoController(rastreoV);

    public PrincipalUIController(PrincipalUI principalUI) {
        this.principalUI = principalUI;
        this.principalUI.getBtnBuscar().addMouseListener(this);
        this.principalUI.getBtnRastrear().addMouseListener(this);
    }
    
    public void iniciar() {
        principalUI.pack();
        principalUI.setResizable(false);
        principalUI.setLocationRelativeTo(null);
        principalUI.setVisible(true);
    }    

    @Override
    public void mouseClicked(MouseEvent e) {
        //Muestra la intefaz catalogo de productos
        if (principalUI.getBtnBuscar() == e.getSource()) {
            rastreoV.setEnabled(false);
            busqquedaC.iniciar(principalUI.getPnlDesk());
            
            //Mustra la interfaz de rastreo de pedido
        } else if (principalUI.getBtnRastrear() == e.getSource()) {
            busquedaV.setEnabled(false);
            rastreoC.iniciar(principalUI.getPnlDesk());
        }
    }
}
