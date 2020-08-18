package com.l2ashdz.sistemaintelaf.controller.tienda;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.nuevoTiempo;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.validarUpdateTiempo;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.tienda.TiempoTrasladoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class TiempoTrasladoController extends MouseAdapter implements ActionListener {

    private TiempoTrasladoView tiempoV;

    private TiempoTraslado tiempoTraslado;
    private List<TiempoTraslado> tiempos;
    private TiempoTrasladoDAO tiempoDAO;

    private String tienda1;
    private String tienda2;
    private String tiempo;

    public TiempoTrasladoController(TiempoTrasladoView tiempoV) {
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
        this.tiempoV = tiempoV;
        this.tiempoV.getBtnActualizar().addActionListener(this);
        this.tiempoV.getBtnLimpiar().addActionListener(this);
        this.tiempoV.getBtnListarTiempos().addActionListener(this);
        this.tiempoV.getTblTiempos().addMouseListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!tiempoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            tiempoV.setSize(parent.getSize());
            tiempoV.setEnabled(true);
            tiempoV.setVisible(true);
            parent.add(tiempoV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("ya esta visible tiempo");
        }
        try {

        } catch (Exception e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Actualiza el tiempo entre tiendas
        if (tiempoV.getBtnActualizar() == e.getSource()) {

            tienda1 = tiempoV.getTxtTienda1().getText();
            tienda2 = tiempoV.getTxtTienda2().getText();
            tiempo = tiempoV.getTxtTiempo().getText().trim();

            try {
                validarUpdateTiempo(tiempo);
                tiempoDAO.update(nuevoTiempo(tienda1, tienda2, Integer.parseInt(tiempo)));
                JOptionPane.showMessageDialog(null, "Tiempo actualizado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                tiempoV.getBtnActualizar().setEnabled(false);
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Limpia los campos y la tabla
        } else if (tiempoV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();

            //Lista los tiempos entre tiendas
        } else if (tiempoV.getBtnListarTiempos() == e.getSource()) {
            if (tiempoV.getRbTiendaAcual().isSelected()) {
                tiempos = tiempoDAO.getTiemposOfT(PrincipalView.lblCodigo.getText());

            } else {
                tiempos = tiempoDAO.getListado();
            }

            tiempoV.getTiendaObservableList().clear();
            tiempoV.getTiendaObservableList().addAll(tiempos);
        }
    }

    private void limpiarCampos() {
        tiempoV.getTiendaObservableList().clear();
        tiempoV.getTxtTienda1().setText("");
        tiempoV.getTxtTienda2().setText("");
        tiempoV.getTxtTiempo().setText("");
        tiempoV.getBgOpcionesFiltro().clearSelection();
        tiempoV.getBtnListarTiempos().setEnabled(false);
        tiempoV.getBtnActualizar().setEnabled(false);
    }
    
    private void limpiarCampos2(){
        tiempoV.getTxtTienda1().setText("");
        tiempoV.getTxtTienda2().setText("");
        tiempoV.getTxtTiempo().setText("");
        tiempoV.getBtnActualizar().setEnabled(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String tiendaActual = PrincipalView.lblCodigo.getText();
        int fila = tiempoV.getTblTiempos().getSelectedRow();
        tienda1 = tiempoV.getTblTiempos().getValueAt(fila, 0).toString();
        tienda2 = tiempoV.getTblTiempos().getValueAt(fila, 3).toString();
        if (tiempoDAO.getTiempoT(tienda1, tienda2) == null) {
            tiempoTraslado = tiempoDAO.getTiempoT(tienda2, tienda1);
        } else {
            tiempoTraslado = tiempoDAO.getTiempoT(tienda1, tienda2);
        }
        if (tienda1.equals(tiendaActual) || tienda2.equals(tiendaActual)) {
            tiempoV.getTxtTienda1().setText(tiempoTraslado.getCodigoT1());
            tiempoV.getTxtTienda2().setText(tiempoTraslado.getCodigoT2());
            tiempoV.getTxtTiempo().setText(String.valueOf(tiempoTraslado.getTiempo()));
            tiempoV.getBtnActualizar().setEnabled(true);
        } else {
            limpiarCampos2();
        }
    }

}
