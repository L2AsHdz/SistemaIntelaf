package com.l2ashdz.sistemaintelaf;

import com.l2ashdz.sistemaintelaf.controller.tienda.SeleccionTiendaController;
import com.l2ashdz.sistemaintelaf.ui.tienda.SeleccionTiendaView;

public class SistemaIntelaf {

    public static void main(String[] args) {
        SeleccionTiendaView selectTienda = new SeleccionTiendaView();
        SeleccionTiendaController selectTiendaC = new SeleccionTiendaController(selectTienda);
        selectTiendaC.iniciar();
    }
}
