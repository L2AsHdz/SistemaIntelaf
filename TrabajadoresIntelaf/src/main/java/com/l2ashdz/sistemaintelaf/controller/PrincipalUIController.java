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
    
    private TiendaController tiendaC;
    private TiendaView tiendaV;

    public PrincipalUIController(PrincipalView principalUI) {
        this.principalUI = principalUI;
        this.principalUI.getBtnTiendas().addMouseListener(this);
    }
    
    public void iniciar(){
        principalUI.pack();
        //principalUI.setResizable(false);
        principalUI.setLocationRelativeTo(null);
        principalUI.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (principalUI.getBtnTiendas() == e.getSource()) {
            principalUI.getPnlInicio().setVisible(false);
            principalUI.setColor(principalUI.getBtnTiendas());
            
            tiendaV = new TiendaView();
            tiendaC = new TiendaController(tiendaV);
            tiendaC.iniciar(principalUI.getPnlPrincipal());
        }
    }
    
    
}
