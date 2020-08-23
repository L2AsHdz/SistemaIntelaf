package com.l2ashdz.sistemaintelaf.controller.pedido;

import com.l2ashdz.sistemaintelaf.ui.pedido.AddPedidoView;
import com.l2ashdz.sistemaintelaf.ui.pedido.PedidoView;
import com.l2ashdz.sistemaintelaf.ui.pedido.VerificarPedidoView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class PedidoController extends MouseAdapter {

    private PedidoView pedidoV;

    //Vista y controlador para agregar pedidos
    private AddPedidoView addPedidoV = new AddPedidoView();
    private AddPedidoController addPedidoC = new AddPedidoController(addPedidoV);

    //Vista y controlador para verificar pedidos
    private VerificarPedidoView verificarPedV = new VerificarPedidoView();
    private VerificarPedidoController verificarPedC = new VerificarPedidoController(verificarPedV);

    public PedidoController(PedidoView pedidoV) {
        this.pedidoV = pedidoV;
        this.pedidoV.getOpAddPedido().addMouseListener(this);
        this.pedidoV.getOpVerificar().addMouseListener(this);
    }

    //inicia la interfaz de pedidos
    public void iniciar(JPanel parent) {

        if (!pedidoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            verificarPedV.setEnabled(false);
            pedidoV.setSize(parent.getSize());
            pedidoV.setVisible(true);
            pedidoV.setEnabled(true);
            parent.add(pedidoV);
            parent.validate();
            pedidoV.setColor(pedidoV.getOpAddPedido());
            pedidoV.resetColor(pedidoV.getOpVerificar());
            addPedidoC.iniciar(pedidoV.getPnlDesk());
        } else {
            System.out.println("Menu pedido visible");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //muestra la interfaz para agregar un pedido
        if (pedidoV.getOpAddPedido()== e.getSource()) {
            verificarPedV.setEnabled(false);
            addPedidoC.iniciar(pedidoV.getPnlDesk());
        } else if (pedidoV.getOpVerificar()== e.getSource()) {
            addPedidoV.setEnabled(false);
            verificarPedC.iniciar(pedidoV.getPnlDesk());
        }
    }
}
