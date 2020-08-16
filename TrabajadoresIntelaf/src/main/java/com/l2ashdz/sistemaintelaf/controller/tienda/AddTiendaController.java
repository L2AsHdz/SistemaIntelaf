package com.l2ashdz.sistemaintelaf.controller.tienda;

import com.l2ashdz.sistemaintelaf.ui.tienda.AddTiendaView;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAO;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
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

    private CRUD<TiempoTraslado> tiempoDAO;

    private String codigo;
    private String nombre;
    private String direccion;
    private String tel1;
    private String tel2;
    private String correo;
    private String horario;

    public AddTiendaController(AddTiendaView addTiendaView) {
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
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
            parent.removeAll();
            parent.repaint();
            addTiendaV.setSize(parent.getSize());
            addTiendaV.setEnabled(true);
            addTiendaV.setVisible(true);
            parent.add(addTiendaV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Valida los datos y agrega una nueva tienda
        if (addTiendaV.getBtnAgregar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarAddTienda(codigo, nombre, direccion, tel1)) {
                    tiendaDAO.create(nuevaTienda(codigo, nombre, tel1, direccion, tel2, correo, horario));
                }
                registrarTiempos();
                JOptionPane.showMessageDialog(null, "Tienda registrada", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Valida los datos y actualiza los datos de una tienda
        } else if (addTiendaV.getBtnActualizar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarUpdateTienda(nombre, direccion, tel1)) {
                    tiendaDAO.update(nuevaTienda(codigo, nombre, tel1, direccion, tel2, correo, horario));
                }
                JOptionPane.showMessageDialog(null, "Tienda actualizada", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                addTiendaV.getBtnActualizar().setEnabled(false);
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Limpia los campos
        } else if (addTiendaV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();

            //Filtra las tiendas por codigo y/o nombre
        } else if (addTiendaV.getBtnBuscar() == e.getSource()) {
            codigo = addTiendaV.getTxtFiltroCodigo().getText();
            nombre = addTiendaV.getTxtFiltroNombre().getText();
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

            //actualiza los datos de la tabla
            addTiendaV.getTiendaObservableList().clear();
            addTiendaV.getTiendaObservableList().addAll(tiendas);

        }
    }

    private void registrarTiempos() {
        String mensaje = "Ingrese el tiempo de traslado hacia la tienda: ";
        String tiempo;
        tiendas = tiendaDAO.getListado();
        for (Tienda t : tiendas) {
            if (!t.getCodigo().equals(codigo)) {
                tiempo = JOptionPane.showInputDialog(null, mensaje + t.getNombre(), "Tiempo traslado",
                        JOptionPane.QUESTION_MESSAGE);
                while (!isInt(tiempo)) {
                    tiempo = JOptionPane.showInputDialog(null, "Debe ser un dato numerico\n"
                            + mensaje + t.getNombre(), "Tiempo traslado", JOptionPane.ERROR_MESSAGE);
                }

                tiempoDAO.create(nuevoTiempo(codigo, t.getCodigo(), Integer.parseInt(tiempo)));
            }
        }
    }

    private void limpiarCampos() {
        addTiendaV.getTiendaObservableList().clear();
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
        codigo = addTiendaV.getTxtCodigo().getText();
        nombre = addTiendaV.getTxtNombre().getText();
        direccion = addTiendaV.getTxtDireccion().getText();
        tel1 = addTiendaV.getTxtTelefono().getText();
        tel2 = addTiendaV.getTxtTelefono2().getText();
        correo = addTiendaV.getTxtCorreo().getText();
        horario = addTiendaV.getTxtHorario().getText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addTiendaV.getTblTiendas().getSelectedRow();
        codigo = addTiendaV.getTblTiendas().getValueAt(fila, 0).toString();
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
