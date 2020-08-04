package com.l2ashdz.sistemaintelaf;

import com.l2ashdz.sistemaintelaf.controller.LoginController;
import com.l2ashdz.sistemaintelaf.ui.LoginView;

public class SistemaIntelaf {
    public static void main(String[] args) {
        LoginView log = new LoginView();
        LoginController logC = new LoginController(log);
        logC.iniciar();
    }
}
