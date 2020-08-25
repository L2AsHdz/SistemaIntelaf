package com.l2ashdz.clientesintelaf.controller;

import com.l2ashdz.clientesintelaf.ui.PrincipalUI;
import java.awt.event.MouseAdapter;

/**
 *
 * @author asael
 */
public class PrincipalUIController extends MouseAdapter {
    
    private PrincipalUI principalUI;

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
    
}
