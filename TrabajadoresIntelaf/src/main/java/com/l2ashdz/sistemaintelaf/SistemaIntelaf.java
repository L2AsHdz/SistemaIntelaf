package com.l2ashdz.sistemaintelaf;

import com.l2ashdz.sistemaintelaf.controller.archivoEntrada.ArchivoEntradaController;
import com.l2ashdz.sistemaintelaf.ui.ArchivoEntradaView;

public class SistemaIntelaf {

    public static void main(String[] args) {
        ArchivoEntradaView archvoView = new ArchivoEntradaView();
        ArchivoEntradaController lecturaC = new ArchivoEntradaController(archvoView);
        lecturaC.iniciar();
    }
}
