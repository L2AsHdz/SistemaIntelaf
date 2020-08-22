package com.l2ashdz.sistemaintelaf.controller.pedido;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.pedido.VerificarPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
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

    private TiempoTraslado tiempo;
    private TiempoTrasladoDAO tiempoDAO;

    private String tiendaActual;
    LocalDate fecha;

    public VerificarPedidoController(VerificarPedidoView verificarPV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
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
            limpiarCampos();
        } else {
            System.out.println("ya esta visible tiempo");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Marca el pedido a la espera del cliete
        if (verificarPV.getBtnEspera() == e.getSource()) {
            pedidoDAO.setEstado(pedido.getCodigo(), 2);
            pedidoDAO.setFecha(pedido.getCodigo(), LocalDate.now().toString(), 1);
            limpiarCampos();

        } else if (verificarPV.getBtnRetrasado() == e.getSource()) {
            pedidoDAO.setEstado(pedido.getCodigo(), 1);
            limpiarCampos();
            
        } else if (verificarPV.getBtnRecogido() == e.getSource()) {
            /**
             * Si fecha verificacion es mayor a fecha + tiempo traslado entonces
             *      si porcentaje pagado es igual a 1 añadir a credito compra total*0.05
             *      si no agregar a credito total*0.02
             * si no cliente debe pagar total-anticipo
             * 
             * cambiar estado pedido a 3
             * registrar venta
             * 
             * */
        }
    }

    private void limpiarCampos() {
        cargarPedidos();
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
        verificarPV.getLblAvisoAtrasado().setText("");
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

        //obtencion de objetos para la informacion
        pedido = pedidoDAO.getObject(codPedido);
        cliente = clienteDAO.getObject(pedido.getNitCliente());
        tiendaD = tiendaDAO.getObject(pedido.getTiendaDestino());
        tiendaO = tiendaDAO.getObject(pedido.getTiendaOrigen());
        anticipo = pedido.getTotal() * pedido.getPorcentajePagado();
        pagoPendiente = pedido.getTotal() - anticipo;
        if (tiempoDAO.getTiempoT(pedido.getTiendaDestino(), pedido.getTiendaOrigen()) == null) {
            tiempo = tiempoDAO.getTiempoT(pedido.getTiendaOrigen(), pedido.getTiendaDestino());
        } else {
            tiempo = tiempoDAO.getTiempoT(pedido.getTiendaDestino(), pedido.getTiendaOrigen());
        }

        //informacion del pedido
        verificarPV.getLblCodPedido().setText(String.valueOf(pedido.getCodigo()));
        verificarPV.getLblFechaP().setText(pedido.getFecha().toString());
        verificarPV.getLblCantProd().setText(String.valueOf(pedido.getCantProductos()));
        verificarPV.getLblAnticipo().setText(String.valueOf(anticipo));
        verificarPV.getLblTotal().setText(String.valueOf(pedido.getTotal()));
        verificarPV.getLblPagoPendiente().setText(String.valueOf(pagoPendiente));

        //informacion del cliente
        verificarPV.getLblNitC().setText(cliente.getNit());
        verificarPV.getLblNameC().setText(cliente.getNombre());
        verificarPV.getLblDirC().setText(cliente.getDireccion());
        verificarPV.getLblTelC().setText(cliente.getTelefono());
        verificarPV.getLblCredito().setText(String.valueOf(cliente.getCreditoCompra()));

        //informcaion de la tienda origen
        verificarPV.getLblCodTO().setText(tiendaO.getCodigo());
        verificarPV.getLblNameTO().setText(tiendaO.getNombre());
        verificarPV.getLblTelTO().setText(tiendaO.getTelefono1());

        //informacion de la tienda destino
        verificarPV.getLblCodTD().setText(tiendaD.getCodigo());
        verificarPV.getLblNameTD().setText(tiendaD.getNombre());
        verificarPV.getLblTelTD().setText(tiendaD.getTelefono1());

        //Se le suma a la fecha los dias de traslado
        fecha = pedido.getFecha();
        fecha = fecha.plusDays(tiempo.getTiempo());

        setEnableBtn(pedido.getEstadoP());

        //Activa un aviso si el pedido ya esta atrasado        
        if (fecha.isBefore(LocalDate.now()) && pedido.getEstadoP() == 0) {
            verificarPV.getLblAvisoAtrasado().setText("El pedido esta atrasado, la fecha de llegada era " + fecha);
            verificarPV.getBtnRetrasado().setEnabled(true);
            verificarPV.getBtnEspera().setEnabled(false);
        } else {
            verificarPV.getLblAvisoAtrasado().setText("");
        }
    }

    //habilita los botones dependiendo de el estado del pedido
    private void setEnableBtn(int estado) {
        switch (estado) {
            case 0:
                verificarPV.getBtnEspera().setEnabled(true);
                break;
            case 1:
                verificarPV.getBtnEspera().setEnabled(true);
                break;
            case 2:
                verificarPV.getBtnRecogido().setEnabled(true);
        }
    }
}
