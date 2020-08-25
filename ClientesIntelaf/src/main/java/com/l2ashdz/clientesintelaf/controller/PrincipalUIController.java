package com.l2ashdz.clientesintelaf.controller;

import com.l2ashdz.clientesintelaf.ui.BusquedaPView;
import com.l2ashdz.clientesintelaf.ui.PrincipalUI;
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
        if (principalUI.getBtnBuscar() == e.getSource()) {
            busqquedaC.iniciar(principalUI.getPnlDesk());
        } else if (principalUI.getBtnRastrear() == e.getSource()) {
            
        }
    }
}
