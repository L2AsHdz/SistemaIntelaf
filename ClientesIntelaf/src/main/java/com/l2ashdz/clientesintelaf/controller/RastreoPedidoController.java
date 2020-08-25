package com.l2ashdz.clientesintelaf.controller;

import com.l2ashdz.clientesintelaf.dao.PedidoDAO;
import com.l2ashdz.clientesintelaf.dao.ProductoPedidoDAO;
import com.l2ashdz.clientesintelaf.dao.TiempoTrasladoDAO;
import com.l2ashdz.clientesintelaf.model.Pedido;
import com.l2ashdz.clientesintelaf.model.ProductoPedido;
import com.l2ashdz.clientesintelaf.model.TiempoTraslado;
import com.l2ashdz.clientesintelaf.ui.RastreoPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class RastreoPedidoController implements ActionListener {

    private RastreoPedidoView rastreoV;

    private Pedido pedido;
    private TiempoTraslado tiempo;
    private List<ProductoPedido> productos;

    private PedidoDAO pedidoDAO;
    private ProductoPedidoDAO productoPDAO;
    private TiempoTrasladoDAO tiempoDAO;

    public RastreoPedidoController(RastreoPedidoView rastreoV) {
        pedidoDAO = PedidoDAO.getPedidoDAO();
        productoPDAO = ProductoPedidoDAO.getProductoPDAO();
        tiempoDAO = TiempoTrasladoDAO.getTiempoDAO();
        this.rastreoV = rastreoV;
        this.rastreoV.getBtnVerInfo().addActionListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!rastreoV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            rastreoV.setSize(parent.getSize());
            rastreoV.setEnabled(true);
            rastreoV.setVisible(true);
            parent.add(rastreoV);
            parent.validate();
            limpiarCampos();
        } else {
            System.out.println("Interfaz rastreo pedido ya esta visible");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (rastreoV.getBtnVerInfo() == e.getSource()) {
            String codigo = rastreoV.getTxtCodigo().getText();
            if (!codigo.isEmpty()) {
                try {
                    pedido = pedidoDAO.getObject(codigo);
                    if (tiempoDAO.getTiempoT(pedido.getTiendaOrigen(), pedido.getTiendaDestino()) != null) {
                        tiempo = tiempoDAO.getTiempoT(pedido.getTiendaOrigen(), pedido.getTiendaDestino());
                    } else {
                        tiempo = tiempoDAO.getTiempoT(pedido.getTiendaDestino(), pedido.getTiendaOrigen());
                    }
                    productos = productoPDAO.getProductosInPedido(Integer.parseInt(codigo));

                    mostrarInformacion();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "No existe el pedido con el codigo: " + codigo,
                            "Advertencia", JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No ha ingresado un codigo de pedido",
                        "Advertencia", JOptionPane.ERROR_MESSAGE);
                limpiarCampos();
            }

        }
    }

    private void limpiarCampos() {
        rastreoV.getTxtCodigo().setText("");
        rastreoV.getLblAnticipo().setText("");
        rastreoV.getLblDiasRestantes().setText("");
        rastreoV.getLblPagoRestante().setText("");
        rastreoV.getLblTiendaDestino().setText("");
        rastreoV.getLblTiendaOrigen().setText("");
        rastreoV.getLblTotal().setText("");
        rastreoV.getLblEstado().setText("");
        rastreoV.getProductoObservableList().clear();
        rastreoV.getTxtCodigo().requestFocus();
    }

    private void mostrarInformacion() {
        float anticipo = pedido.getTotal() * pedido.getPorcentajePagado();
        float pagoRestante = pedido.getTotal() - anticipo;
        LocalDate fechaLlegada = pedido.getFecha().plusDays(tiempo.getTiempo());
        long diasFaltantes = DAYS.between(LocalDate.now(), fechaLlegada);
        if (diasFaltantes < 0) {
            diasFaltantes = 0;
        }
        rastreoV.getLblDiasRestantes().setText(String.valueOf(diasFaltantes));
        rastreoV.getLblAnticipo().setText(String.valueOf(anticipo));
        rastreoV.getLblPagoRestante().setText(String.valueOf(pagoRestante));
        rastreoV.getLblTiendaDestino().setText(pedido.getTiendaDestino());
        rastreoV.getLblTiendaOrigen().setText(pedido.getTiendaOrigen());
        rastreoV.getLblTotal().setText(String.valueOf(pedido.getTotal()));
        rastreoV.getLblEstado().setText(pedido.getEstado());

        rastreoV.getProductoObservableList().clear();
        rastreoV.getProductoObservableList().addAll(productos);
    }
}
