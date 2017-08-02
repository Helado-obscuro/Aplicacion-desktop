
package Ventanas;

import javax.swing.DefaultComboBoxModel;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sun.invoke.empty.Empty;
import Modelo.Producto;

public class frmAgregarProducto extends javax.swing.JDialog {
 //Modelo del combobox
 DefaultComboBoxModel comboTamano = new DefaultComboBoxModel();
  Date date = new Date();    
 java.util.Date dt = new java.util.Date();
 java.text.DecimalFormat formato = new java.text.DecimalFormat("#.##");
//Instancia de las clase pizza
frmMenu objMenu; 
//Variable auxiliar
 private int categoria=0; 
 
 Producto objProducto = new Producto();
 //Variables par el contsto total
 
 public frmAgregarProducto(java.awt.Frame parent, boolean modal,int id,  frmMenu f) {
        
        super(parent, modal);
        this.objProducto.setIdProducto(id);
        objMenu = f;
//        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.png")).getImage());
        initComponents();
//        
        jtxPrecioProducto.setText(String.valueOf(objProducto.getIdProducto()));
        objProducto.consultaDatosProducto();
        jlbNombreProducto.setText(String.valueOf(objProducto.getNombreProducto()));
        jtxPrecioProducto.setText(String.valueOf(objProducto.getPrecioVenta()));
        
//        //LLenado del combobox
//        objPizzas.ConsultarTamano(comboTamano, this.categoria);  
//        
 }
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jlbNombreProducto = new javax.swing.JLabel();
        jlbFoto = new javax.swing.JLabel();
        jlbCostoPizza = new javax.swing.JLabel();
        jlbCantidadProducto = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jbnAgregar = new javax.swing.JButton();
        jtxCantidadProducto = new javax.swing.JTextField();
        jlbPrecioProducto = new javax.swing.JLabel();
        jlbCostoPizza1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jtxPrecioProducto = new javax.swing.JTextField();

        jLabel3.setText("jLabel3");

        jLabel6.setText("jLabel6");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(22, 114, 185));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jlbNombreProducto.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jlbNombreProducto.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(jlbNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jlbNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 50));

        jlbFoto.setOpaque(true);
        jPanel1.add(jlbFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 160, 140));

        jlbCostoPizza.setFont(new java.awt.Font("Century Gothic", 0, 30)); // NOI18N
        jlbCostoPizza.setForeground(new java.awt.Color(255, 102, 0));
        jPanel1.add(jlbCostoPizza, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 140, 40));

        jlbCantidadProducto.setDisplayedMnemonic('c');
        jlbCantidadProducto.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlbCantidadProducto.setLabelFor(jtxCantidadProducto);
        jlbCantidadProducto.setText("Catidad:");
        jPanel1.add(jlbCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, -1));

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 160, 20));

        jbnAgregar.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregar.setBorderPainted(false);
        jbnAgregar.setContentAreaFilled(false);
        jbnAgregar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregar.setSelected(true);
        jbnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(jbnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 60, 50));

        jtxCantidadProducto.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jtxCantidadProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCantidadProducto.setBorder(null);
        jtxCantidadProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxCantidadProductoActionPerformed(evt);
            }
        });
        jtxCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxCantidadProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxCantidadProductoKeyTyped(evt);
            }
        });
        jPanel1.add(jtxCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 160, 30));

        jlbPrecioProducto.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlbPrecioProducto.setText("Precio");
        jPanel1.add(jlbPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        jlbCostoPizza1.setFont(new java.awt.Font("Century Gothic", 0, 30)); // NOI18N
        jlbCostoPizza1.setForeground(new java.awt.Color(255, 102, 0));
        jlbCostoPizza1.setText("$");
        jPanel1.add(jlbCostoPizza1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 20, 40));

        jSeparator4.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 160, 20));

        jtxPrecioProducto.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jtxPrecioProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxPrecioProducto.setBorder(null);
        jtxPrecioProducto.setEnabled(false);
        jtxPrecioProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxPrecioProductoActionPerformed(evt);
            }
        });
        jtxPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxPrecioProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxPrecioProductoKeyTyped(evt);
            }
        });
        jPanel1.add(jtxPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 160, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 300));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarActionPerformed
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        
//        if(objMenu.cotadorPizza==0){
//         codiguito=getCadenaAlfanumAleatoria(8);
//         objOrdenes.setIdCliente(31);
//         objOrdenes.setCosto(0);
//         objOrdenes.setStatus("Completado");
//         objOrdenes.setFecha((java.sql.Date.valueOf(dateFormat.format(date))) );
//         objOrdenes.setCodigoTransaccion(codiguito);
//         objOrdenes.setMetodoPago("efectivo");
//         objOrdenes.altaOrden();
//         objMenu.cotadorPizza++;
//        }
//        
//        objDetalle.setIdOrden(objOrdenes.MaximoidOrden());
//        objDetalle.setCantidad(Integer.parseInt(jtxCantidadProducto.getText()));
//        objDetalle.setPrecio(objPizzas.ConsultarPrecio((String) jcmTamano.getSelectedItem(), categoria));
//        objDetalle.setTotal(Precio());
//        objDetalle.setNombrePizza(jlbNombrePizza.getText());
//        objDetalle.setTamanoPizza((String) jcmTamano.getSelectedItem());
//        objDetalle.setCodigoTransaccion(objOrdenes.consultaCodigo(objOrdenes.MaximoidOrden()));
//        objDetalle.altaDetalle();
//
//        objMenu.jlbPasosVenta1.setText("Paso 2  Click al carro de venta");
//        objMenu.jlbPasosVenta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Circled 2 C_48px.png")));
//        //Control de pizzas y cantidad con consulta a base de datos
//        objMenu.jlbTotal.setText(formato.format(objDetalle.TotalOrden(objOrdenes.MaximoidOrden())));
//        objMenu.jbnContadorPizza.setText(String.valueOf(objDetalle.CantidadDetalleOrden(objOrdenes.MaximoidOrden())));
    }//GEN-LAST:event_jbnAgregarActionPerformed

    private void jtxCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxCantidadProductoKeyTyped
         //CONVERTIR LA TECLA PRESIONADA EN TIPO CHAR
        char letra = evt.getKeyChar();

        //VALIDAR SI ES NUMERO
        if(!Character.isDigit(letra)){
            getToolkit().beep();//Emite un sonido de alerta
            evt.consume(); // evita que el caracter aparezca en la caja  de texto
        }
    }//GEN-LAST:event_jtxCantidadProductoKeyTyped

    private void jtxCantidadProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxCantidadProductoKeyPressed
           if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          
           jlbCostoPizza.setText(String.valueOf(Precio()));
           }
    }//GEN-LAST:event_jtxCantidadProductoKeyPressed

    private void jtxCantidadProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxCantidadProductoActionPerformed
        jbnAgregar.requestFocus();
    }//GEN-LAST:event_jtxCantidadProductoActionPerformed

    private void jtxPrecioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxPrecioProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxPrecioProductoActionPerformed

    private void jtxPrecioProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxPrecioProductoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxPrecioProductoKeyPressed

    private void jtxPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxPrecioProductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxPrecioProductoKeyTyped


  private String getCadenaAlfanumAleatoria (int longitud){
    String cadenaAleatoria = "";
    long milis = new java.util.GregorianCalendar().getTimeInMillis();
    Random r = new Random(milis);
    int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
        if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
            cadenaAleatoria += c;
              i ++;
        }
    }
        return cadenaAleatoria;
    }
         
 public double Precio(){
         //Variables para el costo del detalle
         int sum1=0;
         double  sum3=0, sum2=0;
           sum1=Integer.parseInt(jtxCantidadProducto.getText());
           //sum2=objPizzas.ConsultarPrecio((String) jcmTamano.getSelectedItem(), categoria);
           sum3=sum1*sum2;
           
          return sum3;       
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                frmPizza dialog = new frmPizza(new javax.swing.JFrame(), true, 0, 0);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton jbnAgregar;
    private javax.swing.JLabel jlbCantidadProducto;
    private javax.swing.JLabel jlbCostoPizza;
    private javax.swing.JLabel jlbCostoPizza1;
    private javax.swing.JLabel jlbFoto;
    public javax.swing.JLabel jlbNombreProducto;
    private javax.swing.JLabel jlbPrecioProducto;
    private javax.swing.JTextField jtxCantidadProducto;
    private javax.swing.JTextField jtxPrecioProducto;
    // End of variables declaration//GEN-END:variables
}
