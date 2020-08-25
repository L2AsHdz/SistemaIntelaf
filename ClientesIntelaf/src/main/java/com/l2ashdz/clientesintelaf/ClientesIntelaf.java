package com.l2ashdz.clientesintelaf;

import com.l2ashdz.clientesintelaf.controller.PrincipalUIController;
import com.l2ashdz.clientesintelaf.ui.PrincipalUI;

/**
 *
 * @author asael
 */
public class ClientesIntelaf {

    public static void main(String[] args) {
        PrincipalUI clienteUI = new PrincipalUI();
        PrincipalUIController clienteUIC = new PrincipalUIController(clienteUI);
        clienteUIC.iniciar();
    }
    
}
