package com.l2ashdz.sistemaintelaf.controller.pedido;

import com.l2ashdz.sistemaintelaf.ui.pedido.VerificarPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class VerificarPedidoController extends MouseAdapter implements ActionListener {
    
    private VerificarPedidoView verificarPV;

    public VerificarPedidoController(VerificarPedidoView verificarPV) {
        this.verificarPV = verificarPV;
    }
    
    public void iniciar(JPanel parent){
        if (!verificarPV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            verificarPV.setSize(parent.getSize());
            verificarPV.setEnabled(true);
            verificarPV.setVisible(true);
            parent.add(verificarPV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("ya esta visible tiempo");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
