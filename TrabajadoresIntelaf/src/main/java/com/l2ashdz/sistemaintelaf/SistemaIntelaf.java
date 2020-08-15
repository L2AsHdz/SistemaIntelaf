package com.l2ashdz.sistemaintelaf;

import com.l2ashdz.sistemaintelaf.controller.PrincipalUIController;
import com.l2ashdz.sistemaintelaf.ui.Login2View;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;

public class SistemaIntelaf {

    public static void main(String[] args) {
        PrincipalView pUI = new PrincipalView();
        PrincipalUIController pUIC = new PrincipalUIController(pUI);
        pUIC.iniciar();
    }
}
