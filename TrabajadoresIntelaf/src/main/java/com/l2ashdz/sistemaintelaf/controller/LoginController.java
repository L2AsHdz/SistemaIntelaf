package com.l2ashdz.sistemaintelaf.controller;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.usuario.UsuarioDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Usuario;
import com.l2ashdz.sistemaintelaf.ui.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author asael
 */
public class LoginController implements ActionListener{
    private LoginView login;
    private CRUD<Usuario> userDAO;
    
    private String dpi = "";

    public LoginController(LoginView log) {
        this.login = log;
        userDAO = UsuarioDAOImpl.getUserDAO();
        login.getBtnLogin().addActionListener(this);
        login.getBtnSalir().addActionListener(this);
    }
    
    //Iniciar interfaz login
    public void iniciar(){
        login.pack();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

    //Eventos de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (login.getBtnLogin() == e.getSource()) {
             try {
                validarLogin((ArrayList<Usuario>) userDAO.getListado(), 
                login.getTxtUsuario().getText(), login.getTxtPassword().getText());
             } catch (Exception ex) {
                System.out.println("Error al intentar validar");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
             }
        }else if (login.getBtnSalir() == e.getSource()) {
            System.exit(0);
        }
    }

    private void validarLogin(ArrayList<Usuario> users,String nombre, String pass) {
        if (nombre.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error! - Campos vacios");
            login.getTxtUsuario().requestFocus();
        }else {
            int tipoUser = 0;
            for (Usuario u : users) {
                if (nombre.equalsIgnoreCase(u.getUserName()) && pass.equals(u.getPassword())) {
                    tipoUser = u.getTipo();          
                    dpi = u.getCUI();
                    if (u.getEstado() == 0) {
                        tipoUser = 4;
                    }
                }
            }
            switch (tipoUser) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", 
                    "Error login", JOptionPane.WARNING_MESSAGE);
                    limpiarCampos();
                    break;
                    
                case 1:
                    JOptionPane.showMessageDialog(null, "Echese unas partidas en cod perro", 
                    "Holi", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                case 3:
                case 4:
                    JOptionPane.showMessageDialog(null, "El usuario esta deshabilitado", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
    
    //Limpia los campos del login y coloca el cursor en el txtUsuario
    private void limpiarCampos(){
        login.getTxtUsuario().setText("");
        login.getTxtPassword().setText("");
        login.getTxtUsuario().requestFocus();
    }
}
