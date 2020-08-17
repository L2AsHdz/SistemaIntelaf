package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.controller.PrincipalUIController;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.tienda.SeleccionTiendaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author asael
 */
public class SeleccionTiendaController extends MouseAdapter implements ActionListener{
    
    private SeleccionTiendaView selecTiendaV;
    private Tienda tienda;
    private List<Tienda> tiendas;
    private CRUD<Tienda> tiendaDAO;
    
    private PrincipalView principalV = new PrincipalView();
    private PrincipalUIController principalC;

    public SeleccionTiendaController(SeleccionTiendaView selecTiendaV) {
        this.tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        this.selecTiendaV = selecTiendaV;
        this.selecTiendaV.getTblTiendas().addMouseListener(this);
        this.selecTiendaV.getBtnSiguiente().addActionListener(this);
    }
    
    public void iniciar(){
        selecTiendaV.pack();
        selecTiendaV.setResizable(false);
        selecTiendaV.setLocationRelativeTo(null);
        selecTiendaV.setVisible(true);
        tiendas = tiendaDAO.getListado();
        selecTiendaV.getTiendaObservableList().clear();
        selecTiendaV.getTiendaObservableList().addAll(tiendas);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = selecTiendaV.getTblTiendas().getSelectedRow();
        String codigo = selecTiendaV.getTblTiendas().getValueAt(fila, 0).toString();
        tienda = tiendaDAO.getObject(codigo);
        if (!selecTiendaV.getBtnSiguiente().isEnabled()) {
            selecTiendaV.getBtnSiguiente().setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        principalC = new PrincipalUIController(principalV, tienda.getCodigo(), tienda.getNombre());
        principalC.iniciar();
        selecTiendaV.dispose();
    }
}
