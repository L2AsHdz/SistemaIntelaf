package com.l2ashdz.sistemaintelaf.controller.empleado;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAO;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.ui.empleado.AddEmpleadoView;
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
public class AddEmpleadoController extends MouseAdapter implements ActionListener {

    private AddEmpleadoView addEmpleadoV;
    private Empleado empleado;
    private List<Empleado> empleados;
    private EmpleadoDAO empleadoDAO;

    private String codigo;
    private String nombre;
    private String cui;
    private String nit;
    private String correo;
    private String direccion;
    private String telefono;

    public AddEmpleadoController(AddEmpleadoView empleadoV) {
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        this.addEmpleadoV = empleadoV;
        this.addEmpleadoV.getBtnAgregar().addActionListener(this);
        this.addEmpleadoV.getBtnActualizar().addActionListener(this);
        this.addEmpleadoV.getBtnLimpiar().addActionListener(this);
        this.addEmpleadoV.getBtnListarEmpleados().addActionListener(this);
        this.addEmpleadoV.getTblEmpleados().addMouseListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!addEmpleadoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            addEmpleadoV.setSize(parent.getSize());
            addEmpleadoV.setEnabled(true);
            addEmpleadoV.setVisible(true);
            parent.add(addEmpleadoV);
            parent.validate();
            limpiarCampos();
            limpiarFiltros();
        } else {
            System.out.println("Ya se esta mostrando addEmpleado");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Resitra un nuevo empleado
        if (addEmpleadoV.getBtnAgregar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarAddEmpleado(codigo, nombre, cui, direccion, telefono, correo)) {
                    empleadoDAO.create(nuevoEmpleado(codigo, nombre, cui, nit, correo, direccion, telefono));
                }
                JOptionPane.showMessageDialog(null, "Empleado registrado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                limpiarFiltros();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Actualiza un empleado existente
        } else if (addEmpleadoV.getBtnActualizar() == e.getSource()) {

            obtenerDatos();
            try {
                if (validarUpdateEmpleado(nombre, cui, direccion, telefono, correo)) {
                    empleadoDAO.update(nuevoEmpleado(codigo, nombre, cui, nit, correo, direccion, telefono));
                }
                JOptionPane.showMessageDialog(null, "Empleado actualizado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                limpiarFiltros();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
            
            //Limpia los campos
        } else if (addEmpleadoV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            limpiarFiltros();
            
            //Lista  los empleados con o sin filtro
        } else if (addEmpleadoV.getBtnListarEmpleados() == e.getSource()) {
            codigo = addEmpleadoV.getTxtFiltroCodigo().getText();
            nombre = addEmpleadoV.getTxtFiltroNombre().getText();
            
            if (addEmpleadoV.getRbFiltroCodigo().isSelected()) {
                empleados = empleadoDAO.getFilteredList(codigo, nombre, 1);
            } else if (addEmpleadoV.getRbFiltroNombre().isSelected()) {
                empleados = empleadoDAO.getFilteredList(codigo, nombre, 2);
            } else {
                empleados = empleadoDAO.getListado();
            }
            
            //Actualiza los datos en la tabla empleados
            addEmpleadoV.getEmpleadoObservableList().clear();
            addEmpleadoV.getEmpleadoObservableList().addAll(empleados);
        }
    }

    private void limpiarCampos() {
        addEmpleadoV.getTxtCodigo().setText("");
        addEmpleadoV.getTxtCodigo().setEnabled(true);
        addEmpleadoV.getTxtNombre().setText("");
        addEmpleadoV.getTxtCUI().setText("");
        addEmpleadoV.getTxtNit().setText("");
        addEmpleadoV.getTxtCorreo().setText("");
        addEmpleadoV.getTxtDireccion().setText("");
        addEmpleadoV.getTxtTelefono().setText("");
        addEmpleadoV.getBtnActualizar().setEnabled(false);
        addEmpleadoV.getTxtCodigo().requestFocus();
    }

    private void limpiarFiltros() {
        addEmpleadoV.getEmpleadoObservableList().clear();
        addEmpleadoV.getBgFiltro().clearSelection();
        addEmpleadoV.getTxtFiltroCodigo().setText("");
        addEmpleadoV.getTxtFiltroNombre().setText("");
    }

    private void obtenerDatos() {
        codigo = addEmpleadoV.getTxtCodigo().getText();
        nombre = addEmpleadoV.getTxtNombre().getText();
        cui = addEmpleadoV.getTxtCUI().getText();
        nit = addEmpleadoV.getTxtNit().getText();
        direccion = addEmpleadoV.getTxtDireccion().getText();
        telefono = addEmpleadoV.getTxtTelefono().getText();
        correo = addEmpleadoV.getTxtCorreo().getText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addEmpleadoV.getTblEmpleados().getSelectedRow();
        codigo = addEmpleadoV.getTblEmpleados().getValueAt(fila, 0).toString();
        empleado = empleadoDAO.getObject(codigo);
        addEmpleadoV.getTxtCodigo().setText(empleado.getCodigo());
        addEmpleadoV.getTxtCodigo().setEnabled(false);
        addEmpleadoV.getTxtNombre().setText(empleado.getNombre());
        addEmpleadoV.getTxtCUI().setText(empleado.getCUI());
        addEmpleadoV.getTxtNit().setText(empleado.getNit());
        addEmpleadoV.getTxtCorreo().setText(empleado.getCorreo());
        addEmpleadoV.getTxtDireccion().setText(empleado.getDireccion());
        addEmpleadoV.getTxtTelefono().setText(empleado.getTelefono());
        addEmpleadoV.getBtnActualizar().setEnabled(true);
    }

}
