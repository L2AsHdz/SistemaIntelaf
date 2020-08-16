package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.ui.tienda.AddTiendaView;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAO;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Tienda;
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
public class AddTiendaController extends MouseAdapter implements ActionListener {

    private AddTiendaView addTiendaV;
    private Tienda tienda;
    private List<Tienda> tiendas;
    private TiendaDAO tiendaDAO;

    private String[] parametros = new String[5];
    private String tel2;
    private String correo;
    private String horario;

    public AddTiendaController(AddTiendaView addTiendaView) {
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        this.addTiendaV = addTiendaView;
        this.addTiendaV.getBtnAgregar().addActionListener(this);
        this.addTiendaV.getBtnActualizar().addActionListener(this);
        this.addTiendaV.getBtnLimpiar().addActionListener(this);
        this.addTiendaV.getBtnBuscar().addActionListener(this);
        this.addTiendaV.getTblTiendas().addMouseListener(this);
    }

    //inicia la interfaz para agregar una tienda
    public void iniciar(JPanel parent) {
        if (!addTiendaV.isEnabled()) {
            addTiendaV.setSize(parent.getSize());
            limpiarCampos();
            addTiendaV.setVisible(true);
            addTiendaV.setEnabled(true);
            parent.add(addTiendaV);
            parent.validate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addTiendaV.getBtnAgregar() == e.getSource()) {

            obtenerDatos();
            try {
                if (verificarTienda(parametros)) {
                    //tiendaDAO.create(nuevaTienda(parametros, tel2, correo, horario));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

        } else if (addTiendaV.getBtnActualizar() == e.getSource()) {

            obtenerDatos();
            try {
                if (verificarTienda(parametros)) {
                    //tiendaDAO.update(nuevaTienda(parametros, tel2, correo, horario));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        } else if (addTiendaV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
        } else if (addTiendaV.getBtnBuscar()== e.getSource()) {
            String codigo = addTiendaV.getTxtFiltroCodigo().getText();
            String nombre = addTiendaV.getTxtFiltroNombre().getText();
            if (addTiendaV.getCbCodigo().isSelected()
                    && !addTiendaV.getCbNombre().isSelected()) {
                tiendas = tiendaDAO.getFilteredList(nombre, codigo, 1);
            } else if (addTiendaV.getCbNombre().isSelected()
                    && !addTiendaV.getCbCodigo().isSelected()) {
                tiendas = tiendaDAO.getFilteredList(nombre, codigo, 2);
            } else if (addTiendaV.getCbCodigo().isSelected()
                    && addTiendaV.getCbNombre().isSelected()) {
                tiendas = tiendaDAO.getFilteredList(nombre, codigo, 3);
            } else {
                tiendas = tiendaDAO.getFilteredList(nombre, codigo, 3);
            }
            addTiendaV.getTiendaObservableList().clear();
            addTiendaV.getTiendaObservableList().addAll(tiendas);
            
        }
    }

    private void limpiarCampos() {
        addTiendaV.getTxtCodigo().setText("");
        addTiendaV.getTxtCodigo().setEditable(true);
        addTiendaV.getTxtCorreo().setText("");
        addTiendaV.getTxtNombre().setText("");
        addTiendaV.getTxtDireccion().setText("");
        addTiendaV.getTxtHorario().setText("");
        addTiendaV.getTxtTelefono().setText("");
        addTiendaV.getTxtTelefono2().setText("");
        addTiendaV.getTxtCodigo().requestFocus();
    }

    private void obtenerDatos() {
        parametros[0] = "";
        parametros[1] = addTiendaV.getTxtNombre().getText();
        parametros[2] = addTiendaV.getTxtDireccion().getText();
        parametros[3] = addTiendaV.getTxtCodigo().getText();
        parametros[4] = addTiendaV.getTxtTelefono().getText();

        tel2 = addTiendaV.getTxtTelefono2().getText();
        correo = addTiendaV.getTxtCorreo().getText();
        horario = addTiendaV.getTxtHorario().getText();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int fila = addTiendaV.getTblTiendas().getSelectedRow();
        String codigo = addTiendaV.getTblTiendas().getValueAt(fila, 0).toString();
        tienda = tiendaDAO.getObject(codigo);
        addTiendaV.getTxtCodigo().setText(tienda.getCodigo());
        addTiendaV.getTxtCodigo().setEditable(false);
        addTiendaV.getTxtNombre().setText(tienda.getNombre());
        addTiendaV.getTxtDireccion().setText(tienda.getDireccion());
        addTiendaV.getTxtTelefono().setText(tienda.getTelefono1());
        addTiendaV.getTxtTelefono2().setText(tienda.getTelefono2());
        addTiendaV.getTxtCorreo().setText(tienda.getCorreo());
        addTiendaV.getTxtHorario().setText(tienda.getHorario());
        addTiendaV.getBtnActualizar().setEnabled(true);
    }

}
