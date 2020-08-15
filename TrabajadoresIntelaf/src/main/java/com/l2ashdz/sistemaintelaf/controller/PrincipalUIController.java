package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.controller.tienda.TiendaController;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiendaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author asael
 */
public class PrincipalUIController extends MouseAdapter{
    private PrincipalView principalUI;
    
    //Vista y controlador para tienda
    private TiendaView tiendaV = new TiendaView();
    private TiendaController tiendaC = new TiendaController(tiendaV);;

    public PrincipalUIController(PrincipalView principalUI) {
        this.principalUI = principalUI;
        this.principalUI.getBtnTiendas().addMouseListener(this);
    }
    
    public void iniciar(){
        principalUI.pack();
        principalUI.setResizable(false);
        principalUI.setLocationRelativeTo(null);
        principalUI.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Inicia la interfaz de tienda
        if (principalUI.getBtnTiendas() == e.getSource()) {
            tiendaC.iniciar(principalUI.getPnlDesk());
        }
    }
    
    
}
