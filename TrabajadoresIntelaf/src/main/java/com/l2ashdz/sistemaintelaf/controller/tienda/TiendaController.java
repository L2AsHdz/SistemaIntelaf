package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.ui.tienda.AddTiendaView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiempoTrasladoView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiendaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class TiendaController extends MouseAdapter {

    private TiendaView tiendaV;

    //Vista y controlador para agregar tiendas
    private AddTiendaView addTiendaV = new AddTiendaView();
    private AddTiendaController addTiendaC = new AddTiendaController(addTiendaV);
    
    //Vista y controlador para modificar tiempos
    private TiempoTrasladoView tiempoV = new TiempoTrasladoView();
    private TiempoTrasladoController tiempoC = new TiempoTrasladoController(tiempoV);

    public TiendaController(TiendaView tiendaV) {

        this.tiendaV = tiendaV;
        this.tiendaV.getOpTienda().addMouseListener(this);
        this.tiendaV.getOpTiempos().addMouseListener(this);
    }

    //inicia la interfaz de tiendas
    public void iniciar(JPanel parent) {

        if (!tiendaV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            tiendaV.setSize(parent.getSize());
            tiendaV.setVisible(true);
            tiendaV.setEnabled(true);
            parent.add(tiendaV);
            parent.validate();
            tiendaV.setColor(tiendaV.getOpTienda());
            tiendaV.resetColor(tiendaV.getOpTiempos());
            addTiendaC.iniciar(tiendaV.getPnlDesk());
        } else {
            System.out.println("Menu tienda visible");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //muestra la interfaz para agregar una tienda
        if (tiendaV.getOpTienda() == e.getSource()) {
            tiempoV.setEnabled(false);
            addTiendaC.iniciar(tiendaV.getPnlDesk());
        } else if (tiendaV.getOpTiempos() == e.getSource()) {
            addTiendaV.setEnabled(false);
            tiempoC.iniciar(tiendaV.getPnlDesk());
        }
    }
}
