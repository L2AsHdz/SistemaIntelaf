package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.ui.tienda.AddTiendaView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiendaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class TiendaController extends MouseAdapter{
    private TiendaView tiendaV;
    
    private AddTiendaView addTiendaV;

    public TiendaController(TiendaView tiendaV) {
        this.tiendaV = tiendaV;
        
        this.tiendaV.getOpTienda().addMouseListener(this);
        this.tiendaV.getOpTiempos().addMouseListener(this);
    }
    
    public void iniciar(JPanel parent){
        parent.add(tiendaV);
        
        tiendaV.setSize(parent.getSize());
        tiendaV.setLocation(180, 50);
        tiendaV.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (tiendaV.getOpTienda() == e.getSource()) {
            
        } else if (tiendaV.getOpTiempos() == e.getSource()) {
            
        }
    }
}
