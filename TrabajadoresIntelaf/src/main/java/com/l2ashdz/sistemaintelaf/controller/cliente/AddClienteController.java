package com.l2ashdz.sistemaintelaf.controller.cliente;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.ValidacionesInterfaz.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAO;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.ui.cliente.AddClienteView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class AddClienteController extends MouseAdapter implements ActionListener {

    private AddClienteView addClienteV;
    private Cliente cliente;
    private List<Cliente> clientes;
    private ClienteDAO clienteDAO;

    private String nit;
    private String nombre;
    private String telefono;
    private String cui;
    private String correo;
    private String direccion;

    public AddClienteController(AddClienteView addClienteV) {
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        this.addClienteV = addClienteV;
        this.addClienteV.getBtnAgregar().addActionListener(this);
        this.addClienteV.getBtnActualizar().addActionListener(this);
        this.addClienteV.getBtnLimpiar().addActionListener(this);
        this.addClienteV.getBtnListarClientes().addActionListener(this);
        this.addClienteV.getTblClientes().addMouseListener(this);
    }

    public void iniciar(JPanel parent) {
        if (!addClienteV.isEnabled()) {
            parent.removeAll();
            parent.repaint();
            addClienteV.setSize(parent.getSize());
            addClienteV.setEnabled(true);
            addClienteV.setVisible(true);
            parent.add(addClienteV);
            parent.validate();
            limpiarCampos();
            limpiarFiltros();
        } else {
            System.out.println("Ya se esta mostrando addCliente");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Registra un nuevo cliente
        if (addClienteV.getBtnAgregar() == e.getSource()) {

            obtenerDatos();
            try {
                validarAddCliente(nombre, nit, telefono);
                clienteDAO.create(nuevoCliente(nit, nombre, cui, direccion, telefono, correo));
                JOptionPane.showMessageDialog(null, "Cliente registrado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                limpiarFiltros();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Actualiza los datos de un cliente existente
        } else if (addClienteV.getBtnActualizar() == e.getSource()) {

            obtenerDatos();
            try {
                validarUpdateCliente(nombre, telefono);
                clienteDAO.update(nuevoCliente(nit, nombre, cui, direccion, telefono, correo));
                JOptionPane.showMessageDialog(null, "Cliente actualizado", "Info", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                limpiarFiltros();
            } catch (UserInputException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
            }

            //Limpia los campos
        } else if (addClienteV.getBtnLimpiar() == e.getSource()) {
            limpiarCampos();
            limpiarFiltros();

            //Lista los clientes existentes
        } else if (addClienteV.getBtnListarClientes() == e.getSource()) {
            nit = addClienteV.getTxtFiltroNit().getText();
            nombre = addClienteV.getTxtFiltroNombre().getText();

            if (addClienteV.getRbNit().isSelected()) {
                clientes = clienteDAO.getFilteredList(nit, 1);
            } else if (addClienteV.getRbNombre().isSelected()) {
                clientes = clienteDAO.getFilteredList(nombre, 2);
            } else {
                clientes = clienteDAO.getListado();
            }

            //Actualiaz los datos de la tabla clientes
            addClienteV.getClienteObservableList().clear();
            addClienteV.getClienteObservableList().addAll(clientes);
        }
    }

    private void limpiarCampos() {
        addClienteV.getTxtNit().setText("");
        addClienteV.getTxtNit().setEditable(true);
        addClienteV.getTxtNombre().setText("");
        addClienteV.getTxtTelefono().setText("");
        addClienteV.getTxtCorreo().setText("");
        addClienteV.getTxtDireccion().setText("");
        addClienteV.getTxtCUI().setText("");
        addClienteV.getBtnActualizar().setEnabled(false);
        addClienteV.getTxtNit().requestFocus();

    }

    private void limpiarFiltros() {
        addClienteV.getClienteObservableList().clear();
        addClienteV.getBgFiltro().clearSelection();
        addClienteV.getTxtFiltroNit().setText("");
        addClienteV.getTxtFiltroNombre().setText("");
    }

    private void obtenerDatos() {
        nit = addClienteV.getTxtNit().getText();
        nombre = addClienteV.getTxtNombre().getText();
        telefono = addClienteV.getTxtTelefono().getText().trim();
        direccion = addClienteV.getTxtDireccion().getText();
        cui = addClienteV.getTxtCUI().getText();
        correo = addClienteV.getTxtCorreo().getText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = addClienteV.getTblClientes().getSelectedRow();
        nit = addClienteV.getTblClientes().getValueAt(fila, 0).toString();
        cliente = clienteDAO.getObject(nit);
        addClienteV.getTxtNit().setText(cliente.getNit());
        addClienteV.getTxtNit().setEditable(false);
        addClienteV.getTxtNombre().setText(cliente.getNombre());
        addClienteV.getTxtTelefono().setText(cliente.getTelefono());
        addClienteV.getTxtCorreo().setText(cliente.getCorreo());
        addClienteV.getTxtDireccion().setText(cliente.getDireccion());
        addClienteV.getTxtCUI().setText(cliente.getCui());
        addClienteV.getBtnActualizar().setEnabled(true);
    }

}
