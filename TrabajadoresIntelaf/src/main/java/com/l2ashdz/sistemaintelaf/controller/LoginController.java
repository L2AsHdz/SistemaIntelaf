package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.ui.LoginView;
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

    private String codigo = "";

    public LoginController(LoginView log) {
        this.login = log;
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        login.getBtnLogin().addActionListener(this);
        login.getBtnSalir().addActionListener(this);
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
                        login.getTxtCodigoEmpleado().getText(), login.getTxtPassword().getText());
            } catch (Exception ex) {
                System.out.println("Error al intentar validar");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        } else if (login.getBtnSalir() == e.getSource()) {
            System.exit(0);
        }
    }

    private void validarLogin(ArrayList<Empleado> empleados, String codigo, String pass) {
        if (codigo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error! - Campos vacios");
            login.getTxtCodigoEmpleado().requestFocus();
        } else {
            int opcion = 0;
            for (Empleado u : empleados) {
                if (Integer.parseInt(codigo) == u.getCodigo() && pass.equals(u.getPassword())) {
                    if (u.getEstado() == 0) {
                        opcion = 1;
                    } else {
                        opcion = 2;
                    }
                }
            }
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", 
                    "Error login", JOptionPane.WARNING_MESSAGE);
                    limpiarCampos();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "El usuario esta deshabilitado",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Echese unas partidas en cod perro",
                            "Holi", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    break;
            }
        }
    }

    //Limpia los campos del login y coloca el cursor en el txtCodigoEmpleado
    private void limpiarCampos() {
        login.getTxtCodigoEmpleado().setText("");
        login.getTxtPassword().setText("");
        login.getTxtCodigoEmpleado().requestFocus();
    }
}
