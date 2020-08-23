package com.l2ashdz.sistemaintelaf.controller.pedido;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isFecha;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.validarRecogerPedido;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.nuevaVenta;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAO;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.ProductoVentaDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAO;
import com.l2ashdz.sistemaintelaf.dao.venta.VentaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.model.Venta;
import com.l2ashdz.sistemaintelaf.ui.PrincipalView;
import com.l2ashdz.sistemaintelaf.ui.pedido.RecogerPedidoView;
import com.l2ashdz.sistemaintelaf.ui.pedido.VerificarPedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class VerificarPedidoController extends MouseAdapter implements ActionListener {

    private VerificarPedidoView verificarPV;
    private RecogerPedidoView recogerPedidoV;

    private Pedido pedido;
    private List<Pedido> pedidos;
    private PedidoDAO pedidoDAO;
    private List<ProductoPedido> productosP;
    private ProductoPedidoDAO prodPedidoDAO;

    private Cliente cliente;
    private ClienteDAO clienteDAO;

    private Tienda tiendaO;
    private Tienda tiendaD;
    private CRUD<Tienda> tiendaDAO;

    private TiempoTraslado tiempo;
    private TiempoTrasladoDAO tiempoDAO;

    private Venta venta;
    private VentaDAO ventaDAO;

    private List<ProductoVenta> productosV;
    private CRUD<ProductoVenta> prodVentaDAO;

    private String tiendaActual;
    LocalDate fecha;
    String fechaRetiro;
    String porcentajeC;
    String porcentajeE;
    float anticipo;
    float pagoPendiente;
    float credito;

    public VerificarPedidoController(VerificarPedidoView verificarPV) {
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
        ventaDAO = VentaDAOImpl.getVentaDAO();
        prodVentaDAO = ProductoVentaDAOImpl.getProductoVentaDAO();
        prodPedidoDAO = ProductoPedidoDAOImpl.getProductoPDAO();
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
            String fechaV = JOptionPane.showInputDialog(null, "Ingrese fecha de verificacion"
                    + "\nejemplo(2020-04-09)", "Verificar Pedido", JOptionPane.INFORMATION_MESSAGE);
            while (!isFecha(fechaV)) {
                fechaV = JOptionPane.showInputDialog(null, "El formato de la fecha no es correcto"
                        + "\nejemplo(2020-04-09)", "Verificar Pedido", JOptionPane.ERROR_MESSAGE);
            }

            pedidoDAO.setEstado(pedido.getCodigo(), 2);
            pedidoDAO.setFecha(pedido.getCodigo(), fechaV, 1);
            limpiarCampos();

        } else if (verificarPV.getBtnRetrasado() == e.getSource()) {
            pedidoDAO.setEstado(pedido.getCodigo(), 1);
            limpiarCampos();

        } else if (verificarPV.getBtnRecogido() == e.getSource()) {
            setVisibleRecogerP();
            /**
             * Si fecha verificacion es mayor a fecha + tiempo traslado entonces
             * si porcentaje pagado es igual a 1 añadir a credito compra
             * total*0.05 si no agregar a credito total*0.02 si no cliente debe
             * pagar total-anticipo
             *
             * cambiar estado pedido a 3 registrar venta
             */
            if (pedido.getFechaVerificacion().isAfter(fecha)) {
                if (pedido.getPorcentajePagado() == 1) {
                    float add = (float) (pedido.getTotal() * 0.05) * -1;
                    clienteDAO.restarCredito(pedido.getNitCliente(), add);
                } else {
                    float add = (float) (pedido.getTotal() * 0.02) * -1;
                    clienteDAO.restarCredito(pedido.getNitCliente(), add);
                }
            }

            int idVenta = ventaDAO.getIdVenta();
            ventaDAO.create(nuevaVenta(pedido.getNitCliente(), fechaRetiro, getPorcCredito(),
                    getPorcEfectivo(), pedido.getTiendaDestino()));

            productosP = prodPedidoDAO.getProductosInPedido(pedido.getCodigo());
            productosP.forEach(pp -> {
                prodVentaDAO.create(new ProductoVenta(pp, String.valueOf(idVenta),
                        String.valueOf(pp.getCantidad())));
            });
            clienteDAO.restarCredito(pedido.getNitCliente(), credito);
            pedidoDAO.setEstado(pedido.getCodigo(), 3);
            pedidoDAO.setFecha(pedido.getCodigo(), fechaRetiro, 2);
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "Pedido completado", "Info", JOptionPane.INFORMATION_MESSAGE);

            //Cierra interfaz recoger pedido
        } else if (recogerPedidoV.getBtnFinalizar() == e.getSource()) {
            obtenerDatos();
            try {
                validarRecogerPedido(fechaRetiro, porcentajeC, porcentajeE);
                recogerPedidoV.dispose();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
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

    private void obtenerDatos() {
        Date input = recogerPedidoV.getTxtFecha().getDate();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaRetiro = date.toString() == null ? "" : date.toString();
        porcentajeC = recogerPedidoV.getTxtPorcentajeC().getText();
        porcentajeE = recogerPedidoV.getTxtPorcentajeE().getText();
        if (!recogerPedidoV.getTxtCredito().getText().isEmpty()) {
            credito = Float.parseFloat(recogerPedidoV.getTxtCredito().getText());
        }
    }

    private void cargarPedidos() {
        tiendaActual = PrincipalView.lblCodigo.getText();
        pedidos = pedidoDAO.getPedidosSinVerificar(tiendaActual);
        verificarPV.getPedidosObservableList().clear();
        verificarPV.getPedidosObservableList().addAll(pedidos);
    }

    private String getPorcCredito() {
        float porcAnticipo = pedido.getPorcentajeCredito() * pedido.getPorcentajePagado();
        float porcPendiente = Float.parseFloat(porcentajeC) * (pagoPendiente / pedido.getTotal());
        Float porcTotalC = porcAnticipo + porcPendiente;
        return porcTotalC.toString();
    }

    private String getPorcEfectivo() {
        float porcAnticipo = pedido.getPorcentajeEfectivo() * pedido.getPorcentajePagado();
        float porcPendiente = Float.parseFloat(porcentajeE) * (pagoPendiente / pedido.getTotal());
        Float porcTotalE = porcAnticipo + porcPendiente;
        return porcTotalE.toString();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
        
        //Se le suma a la fecha los dias de traslado
        fecha = pedido.getFecha();
        fecha = fecha.plusDays(tiempo.getTiempo());

        //informacion del pedido
        verificarPV.getLblCodPedido().setText(String.valueOf(pedido.getCodigo()));
        verificarPV.getLblFechaP().setText(fecha.toString());
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

    private void setVisibleRecogerP() {
        recogerPedidoV = new RecogerPedidoView();
        recogerPedidoV.getBtnFinalizar().addActionListener(this);
        String date = pedido.getFechaVerificacion().toString();
        Date date2 = null;
        try {

            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
        }
        recogerPedidoV.getTxtFecha().setDate(date2);
        recogerPedidoV.getLblAnticipo().setText(String.valueOf(anticipo));
        recogerPedidoV.getLblCreditoCompra().setText(String.valueOf(cliente.getCreditoCompra()));
        recogerPedidoV.getLblTotal().setText(String.valueOf(pedido.getTotal()));
        recogerPedidoV.getLblPagoPendiente().setText(String.valueOf(pagoPendiente));
        recogerPedidoV.setLocationRelativeTo(null);
        recogerPedidoV.setVisible(true);
    }
}
