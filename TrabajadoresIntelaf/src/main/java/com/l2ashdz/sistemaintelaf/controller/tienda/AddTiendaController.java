package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.ui.tienda.AddTiendaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 *
 * @author asael
 */
public class AddTiendaController extends MouseAdapter implements ActionListener {
    private AddTiendaView addTiendaView;

    public AddTiendaController(AddTiendaView addTiendaView) {
        this.addTiendaView = addTiendaView;
    }
    
    public void iniciar(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
