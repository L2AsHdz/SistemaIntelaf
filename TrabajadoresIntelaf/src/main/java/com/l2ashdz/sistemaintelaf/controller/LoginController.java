package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.controller.tienda.SeleccionTiendaController;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.ui.LoginView;
import com.l2ashdz.sistemaintelaf.ui.tienda.SeleccionTiendaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author asael
 */
public class LoginController implements ActionListener {

    private LoginView login;
    private CRUD<Empleado> empleadoDAO;

    public LoginController(LoginView log) {
        this.login = log;
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        login.getBtnLogin().addActionListener(this);
    }

    //Iniciar interfaz login
    public void iniciar() {
        login.pack();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

    //Eventos de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (login.getBtnLogin() == e.getSource()) {
            try {
                validarLogin((ArrayList<Empleado>) empleadoDAO.getListado(),
                        login.getTxtCodigoEmpleado().getText());
            } catch (Exception ex) {
                System.out.println("Error al intentar validar");
                ex.printStackTrace(System.out);
            }
        }
    }

    //valida que los datos ingresados correspondan a un usuario en la base de datos
    private void validarLogin(ArrayList<Empleado> empleados, String codigo) {
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No ha ingresado ningun codigo", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error! - Campos vacios");
            login.getTxtCodigoEmpleado().requestFocus();
        } else {
            int opcion = 0;
            for (Empleado e : empleados) {
                if (codigo.equals(e.getCodigo())) {
                    if (e.getEstado() == 0) {
                        opcion = 1;
                    } else {
                        opcion = 2;
                    }
                }
            }
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Codigo de empleado incorrecto",
                            "Error login", JOptionPane.WARNING_MESSAGE);
                    limpiarCampos();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "El empleado esta deshabilitado",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    break;
                case 2:
                    SeleccionTiendaView selectTienda = new SeleccionTiendaView();
                    SeleccionTiendaController selectTiendaC = new SeleccionTiendaController(selectTienda);
                    selectTiendaC.iniciar();
                    login.dispose();
                    break;
            }
        }
    }

    //Limpia los campos del login y coloca el cursor en el txtCodigoEmpleado
    private void limpiarCampos() {
        login.getTxtCodigoEmpleado().setText("");
        login.getTxtCodigoEmpleado().requestFocus();
    }
}
