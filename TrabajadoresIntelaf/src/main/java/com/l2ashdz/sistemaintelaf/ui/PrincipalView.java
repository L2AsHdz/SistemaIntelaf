/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.l2ashdz.sistemaintelaf.ui;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author asael
 */
public class PrincipalView extends javax.swing.JFrame {

    /**
     * Creates new form PrincipalUI
     */
    public PrincipalView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlDesk = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnInicio = new javax.swing.JLabel();
        Opciones = new javax.swing.JPanel();
        btnTiendas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnVentas = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnEmpleados = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnReportes = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnProductos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnClientes = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnPedidos = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        pnlInfoTienda = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNombreT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Intelaf");
        setSize(new java.awt.Dimension(800, 500));

        pnlPrincipal.setBackground(new java.awt.Color(39, 44, 52));

        pnlDesk.setBackground(new java.awt.Color(39, 44, 52));

        javax.swing.GroupLayout pnlDeskLayout = new javax.swing.GroupLayout(pnlDesk);
        pnlDesk.setLayout(pnlDeskLayout);
        pnlDeskLayout.setHorizontalGroup(
            pnlDeskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlDeskLayout.setVerticalGroup(
            pnlDeskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlMenu.setBackground(new java.awt.Color(239, 121, 34));

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Empresa-iloveimg-cropped.png"))); // NOI18N

        Opciones.setBackground(new java.awt.Color(214, 100, 15));

        btnTiendas.setBackground(new java.awt.Color(214, 100, 15));
        btnTiendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTiendasMousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tiendas");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-tienda-24.png"))); // NOI18N

        javax.swing.GroupLayout btnTiendasLayout = new javax.swing.GroupLayout(btnTiendas);
        btnTiendas.setLayout(btnTiendasLayout);
        btnTiendasLayout.setHorizontalGroup(
            btnTiendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnTiendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        btnTiendasLayout.setVerticalGroup(
            btnTiendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTiendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnTiendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnVentas.setBackground(new java.awt.Color(214, 100, 15));
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnVentasMousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ventas");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-canales-de-venta-24.png"))); // NOI18N

        javax.swing.GroupLayout btnVentasLayout = new javax.swing.GroupLayout(btnVentas);
        btnVentas.setLayout(btnVentasLayout);
        btnVentasLayout.setHorizontalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        btnVentasLayout.setVerticalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEmpleados.setBackground(new java.awt.Color(214, 100, 15));
        btnEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEmpleadosMousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Empleados");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-tarjeta-de-empleado-24.png"))); // NOI18N

        javax.swing.GroupLayout btnEmpleadosLayout = new javax.swing.GroupLayout(btnEmpleados);
        btnEmpleados.setLayout(btnEmpleadosLayout);
        btnEmpleadosLayout.setHorizontalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        btnEmpleadosLayout.setVerticalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReportes.setBackground(new java.awt.Color(214, 100, 15));
        btnReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnReportesMousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Reportes");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-reporte-de-negocios-24.png"))); // NOI18N

        javax.swing.GroupLayout btnReportesLayout = new javax.swing.GroupLayout(btnReportes);
        btnReportes.setLayout(btnReportesLayout);
        btnReportesLayout.setHorizontalGroup(
            btnReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        btnReportesLayout.setVerticalGroup(
            btnReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnProductos.setBackground(new java.awt.Color(214, 100, 15));
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnProductosMousePressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Productos");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-producto-24.png"))); // NOI18N

        javax.swing.GroupLayout btnProductosLayout = new javax.swing.GroupLayout(btnProductos);
        btnProductos.setLayout(btnProductosLayout);
        btnProductosLayout.setHorizontalGroup(
            btnProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        btnProductosLayout.setVerticalGroup(
            btnProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnClientes.setBackground(new java.awt.Color(214, 100, 15));
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnClientesMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clientes");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-usuario-masculino-24.png"))); // NOI18N

        javax.swing.GroupLayout btnClientesLayout = new javax.swing.GroupLayout(btnClientes);
        btnClientes.setLayout(btnClientesLayout);
        btnClientesLayout.setHorizontalGroup(
            btnClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        btnClientesLayout.setVerticalGroup(
            btnClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPedidos.setBackground(new java.awt.Color(214, 100, 15));
        btnPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPedidosMousePressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Pedidos");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-hacer-seguimiento-del-pedido-24.png"))); // NOI18N

        javax.swing.GroupLayout btnPedidosLayout = new javax.swing.GroupLayout(btnPedidos);
        btnPedidos.setLayout(btnPedidosLayout);
        btnPedidosLayout.setHorizontalGroup(
            btnPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        btnPedidosLayout.setVerticalGroup(
            btnPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout OpcionesLayout = new javax.swing.GroupLayout(Opciones);
        Opciones.setLayout(OpcionesLayout);
        OpcionesLayout.setHorizontalGroup(
            OpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTiendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        OpcionesLayout.setVerticalGroup(
            OpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpcionesLayout.createSequentialGroup()
                .addComponent(btnTiendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnInicio))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnInicio)
                .addGap(18, 18, 18)
                .addComponent(Opciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInfoTienda.setBackground(new java.awt.Color(57, 64, 76));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tienda actual:");

        lblCodigo.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setText("Codigo");

        lblNombreT.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNombreT.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreT.setText("Tienda X");

        javax.swing.GroupLayout pnlInfoTiendaLayout = new javax.swing.GroupLayout(pnlInfoTienda);
        pnlInfoTienda.setLayout(pnlInfoTiendaLayout);
        pnlInfoTiendaLayout.setHorizontalGroup(
            pnlInfoTiendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoTiendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(lblCodigo)
                .addGap(35, 35, 35)
                .addComponent(lblNombreT)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        pnlInfoTiendaLayout.setVerticalGroup(
            pnlInfoTiendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoTiendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lblCodigo)
                .addComponent(lblNombreT))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoTienda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDesk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlInfoTienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDesk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTiendasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTiendasMousePressed
        setColor(btnTiendas);
        resetColor(btnClientes);
        resetColor(btnEmpleados);
        resetColor(btnProductos);
        resetColor(btnReportes);
        resetColor(btnVentas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnTiendasMousePressed

    private void btnProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMousePressed
        setColor(btnProductos);
        resetColor(btnClientes);
        resetColor(btnEmpleados);
        resetColor(btnTiendas);
        resetColor(btnReportes);
        resetColor(btnVentas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnProductosMousePressed

    private void btnEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadosMousePressed
        setColor(btnEmpleados);
        resetColor(btnClientes);
        resetColor(btnTiendas);
        resetColor(btnProductos);
        resetColor(btnReportes);
        resetColor(btnVentas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnEmpleadosMousePressed

    private void btnClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMousePressed
        setColor(btnClientes);
        resetColor(btnTiendas);
        resetColor(btnEmpleados);
        resetColor(btnProductos);
        resetColor(btnReportes);
        resetColor(btnVentas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnClientesMousePressed

    private void btnVentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMousePressed
        setColor(btnVentas);
        resetColor(btnClientes);
        resetColor(btnEmpleados);
        resetColor(btnProductos);
        resetColor(btnReportes);
        resetColor(btnTiendas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnVentasMousePressed

    private void btnReportesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMousePressed
        setColor(btnReportes);
        resetColor(btnClientes);
        resetColor(btnEmpleados);
        resetColor(btnProductos);
        resetColor(btnTiendas);
        resetColor(btnVentas);
        resetColor(btnPedidos);
    }//GEN-LAST:event_btnReportesMousePressed

    private void btnPedidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedidosMousePressed
        setColor(btnPedidos);
        resetColor(btnClientes);
        resetColor(btnEmpleados);
        resetColor(btnProductos);
        resetColor(btnTiendas);
        resetColor(btnVentas);
        resetColor(btnReportes);
    }//GEN-LAST:event_btnPedidosMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Opciones;
    private javax.swing.JPanel btnClientes;
    private javax.swing.JPanel btnEmpleados;
    private javax.swing.JLabel btnInicio;
    private javax.swing.JPanel btnPedidos;
    private javax.swing.JPanel btnProductos;
    private javax.swing.JPanel btnReportes;
    private javax.swing.JPanel btnTiendas;
    private javax.swing.JPanel btnVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel lblCodigo;
    public static javax.swing.JLabel lblNombreT;
    private javax.swing.JPanel pnlDesk;
    public static javax.swing.JPanel pnlInfoTienda;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables

    public void setColor(JPanel panel){
        panel.setBackground(new Color(241, 137, 60));
    }
    
    public void resetColor (JPanel panel){
       panel.setBackground(new Color(214,100,15));
    }
    
    public JPanel getBtnClientes() {
        return btnClientes;
    }

    public JPanel getBtnEmpleados() {
        return btnEmpleados;
    }

    public JPanel getBtnProductos() {
        return btnProductos;
    }

    public JPanel getBtnReportes() {
        return btnReportes;
    }

    public JPanel getBtnTiendas() {
        return btnTiendas;
    }

    public JPanel getBtnVentas() {
        return btnVentas;
    }

    public JPanel getPnlDesk() {
        return pnlDesk;
    }

    public JPanel getPnlMenu() {
        return pnlMenu;
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }   

    public JPanel getBtnPedidos() {
        return btnPedidos;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblNombreT() {
        return lblNombreT;
    }

    public JLabel getBtnInicio() {
        return btnInicio;
    }
    
}
