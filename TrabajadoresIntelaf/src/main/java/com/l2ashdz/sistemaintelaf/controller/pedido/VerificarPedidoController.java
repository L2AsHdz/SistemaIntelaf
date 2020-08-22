package com.l2ashdz.sistemaintelaf.controller.pedido;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.pedido.VerificarPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class VerificarPedidoController extends MouseAdapter implements ActionListener {

    private VerificarPedidoView verificarPV;

    private Pedido pedido;
    private List<Pedido> pedidos;
    private PedidoDAO pedidoDAO;
    
    private Cliente cliente;
    private CRUD<Cliente> clienteDAO;
    
    private Tienda tiendaO;
    private Tienda tiendaD;
    private CRUD<Tienda> tiendaDAO;

    private String tiendaActual;

    public VerificarPedidoController(VerificarPedidoView verificarPV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        this.verificarPV = verificarPV;
        this.verificarPV.getBtnEspera().addActionListener(this);
        this.verificarPV.getBtnRecogido().addActionListener(this);
        this.verificarPV.getBtnRetrasado().addActionListener(this);
        this.verificarPV.getTblPedidos().addMouseListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!verificarPV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            verificarPV.setSize(parent.getSize());
            verificarPV.setEnabled(true);
            verificarPV.setVisible(true);
            parent.add(verificarPV);
            parent.validate();
            cargarPedidos();
            limpiarCampos();
        } else {
            System.out.println("ya esta visible tiempo");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void limpiarCampos() {
        verificarPV.getLblAnticipo().setText("");
        verificarPV.getLblCantProd().setText("");
        verificarPV.getLblCodPedido().setText("");
        verificarPV.getLblCodTO().setText("");
        verificarPV.getLblCodTD().setText("");
        verificarPV.getLblDirC().setText("");
        verificarPV.getLblFechaP().setText("");
        verificarPV.getLblNameC().setText("");
        verificarPV.getLblNameTD().setText("");
        verificarPV.getLblNameTO().setText("");
        verificarPV.getLblNitC().setText("");
        verificarPV.getLblPagoPendiente().setText("");
        verificarPV.getLblTelTO().setText("");
        verificarPV.getLblTelC().setText("");
        verificarPV.getLblCredito().setText("");
        verificarPV.getLblTelTD().setText("");
        verificarPV.getLblTotal().setText("");
        verificarPV.getBtnEspera().setEnabled(false);
        verificarPV.getBtnRecogido().setEnabled(false);
        verificarPV.getBtnRetrasado().setEnabled(false);
    }

    private void cargarPedidos() {
        tiendaActual = PrincipalView.lblCodigo.getText();
        pedidos = pedidoDAO.getPedidos(tiendaActual);
        verificarPV.getPedidosObservableList().clear();
        verificarPV.getPedidosObservableList().addAll(pedidos);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        float anticipo;
        float pagoPendiente;
        int fila = verificarPV.getTblPedidos().getSelectedRow();
        String codPedido = verificarPV.getTblPedidos().getValueAt(fila, 0).toString();
        pedido = pedidoDAO.getObject(codPedido);
        cliente = clienteDAO.getObject(pedido.getNitCliente());
        tiendaD = tiendaDAO.getObject(pedido.getTiendaDestino());
        tiendaO = tiendaDAO.getObject(pedido.getTiendaOrigen());
        anticipo = pedido.getTotal() * pedido.getPorcentajePagado();
        pagoPendiente = pedido.getTotal() - anticipo;

        verificarPV.getLblCodPedido().setText(String.valueOf(pedido.getCodigo()));
        verificarPV.getLblFechaP().setText(pedido.getFecha().toString());
        verificarPV.getLblCantProd().setText(String.valueOf(pedido.getCantProductos()));
        verificarPV.getLblAnticipo().setText(String.valueOf(anticipo));
        verificarPV.getLblTotal().setText(String.valueOf(pedido.getTotal()));
        verificarPV.getLblPagoPendiente().setText(String.valueOf(pagoPendiente));
        
        verificarPV.getLblNitC().setText(cliente.getNit());
        verificarPV.getLblNameC().setText(cliente.getNombre());
        verificarPV.getLblDirC().setText(cliente.getDireccion());
        verificarPV.getLblTelC().setText(cliente.getTelefono());
        verificarPV.getLblCredito().setText(String.valueOf(cliente.getCreditoCompra()));
        
        verificarPV.getLblCodTO().setText(tiendaO.getCodigo());
        verificarPV.getLblNameTO().setText(tiendaO.getNombre());
        verificarPV.getLblTelTO().setText(tiendaO.getTelefono1());
        
        verificarPV.getLblCodTD().setText(tiendaD.getCodigo());
        verificarPV.getLblNameTD().setText(tiendaD.getNombre());
        verificarPV.getLblTelTD().setText(tiendaD.getTelefono1());
        
    }
}
