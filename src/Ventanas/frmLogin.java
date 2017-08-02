
package Ventanas;

import java.awt.MouseInfo;
import java.awt.Point;
import Modelo.Empleado;
import java.awt.Toolkit;
import java.awt.event.KeyEvent; 
import javax.swing.JOptionPane;


public class frmLogin extends javax.swing.JFrame {

 private frmMenu menu;
 private frmSplash splash;
 
 
 //Instancia de la clase
 Empleado objEmpleado =new Empleado();
    
 //Variable para mover la pantalla de un lado a otro
 int x=0, y=0;
 //variable para validar contraseña o usuario 
 int validar=0;
       public frmLogin() {
        initComponents();
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpContenido = new javax.swing.JPanel();
        jlbOlvidaste = new javax.swing.JLabel();
        jlbInicioSesion = new javax.swing.JLabel();
        jlbPAssword = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jtxUsuario = new javax.swing.JTextField();
        jpsContrasena = new javax.swing.JPasswordField();
        jbnAceptar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlbUsuario1 = new javax.swing.JLabel();
        jbnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jpContenido.setBackground(new java.awt.Color(22, 114, 185));
        jpContenido.setBorder(new javax.swing.border.MatteBorder(null));
        jpContenido.setForeground(new java.awt.Color(255, 255, 255));
        jpContenido.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpContenidoMouseDragged(evt);
            }
        });
        jpContenido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpContenidoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpContenidoMouseReleased(evt);
            }
        });
        jpContenido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbOlvidaste.setBackground(new java.awt.Color(204, 204, 204));
        jlbOlvidaste.setDisplayedMnemonic('O');
        jlbOlvidaste.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jlbOlvidaste.setForeground(new java.awt.Color(255, 255, 255));
        jlbOlvidaste.setText("¿Olvidaste tu contraseña?");
        jlbOlvidaste.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbOlvidasteMouseClicked(evt);
            }
        });
        jpContenido.add(jlbOlvidaste, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 500, 170, -1));

        jlbInicioSesion.setBackground(new java.awt.Color(0, 0, 0));
        jlbInicioSesion.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jlbInicioSesion.setForeground(new java.awt.Color(255, 255, 255));
        jlbInicioSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbInicioSesion.setText("Inicio de Sesión");
        jpContenido.add(jlbInicioSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 210, 30));

        jlbPAssword.setBackground(new java.awt.Color(255, 255, 255));
        jlbPAssword.setDisplayedMnemonic('C');
        jlbPAssword.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jlbPAssword.setForeground(new java.awt.Color(255, 255, 255));
        jlbPAssword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbPAssword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Forgot Password_38px_1.png"))); // NOI18N
        jlbPAssword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpContenido.add(jlbPAssword, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 160, -1));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jpContenido.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 260, 10));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jpContenido.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 260, 10));

        jtxUsuario.setBackground(new java.awt.Color(22, 114, 185));
        jtxUsuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jtxUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jtxUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxUsuario.setText("Ingresa tu usuario");
        jtxUsuario.setBorder(null);
        jtxUsuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtxUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxUsuarioFocusGained(evt);
            }
        });
        jtxUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtxUsuarioMouseClicked(evt);
            }
        });
        jtxUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxUsuarioActionPerformed(evt);
            }
        });
        jpContenido.add(jtxUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 260, 30));

        jpsContrasena.setBackground(new java.awt.Color(22, 114, 185));
        jpsContrasena.setForeground(new java.awt.Color(255, 255, 255));
        jpsContrasena.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpsContrasena.setText("12345");
        jpsContrasena.setToolTipText("");
        jpsContrasena.setBorder(null);
        jpsContrasena.setDisabledTextColor(new java.awt.Color(142, 36, 36));
        jpsContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jpsContrasenaFocusGained(evt);
            }
        });
        jpsContrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpsContrasenaMouseClicked(evt);
            }
        });
        jpsContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpsContrasenaActionPerformed(evt);
            }
        });
        jpsContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jpsContrasenaKeyTyped(evt);
            }
        });
        jpContenido.add(jpsContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 260, 30));

        jbnAceptar.setBackground(new java.awt.Color(255, 255, 255));
        jbnAceptar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jbnAceptar.setText("Aceptar");
        jbnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAceptarActionPerformed(evt);
            }
        });
        jbnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbnAceptarKeyTyped(evt);
            }
        });
        jpContenido.add(jbnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, 100, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jpContenido.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 330, 170));

        jlbUsuario1.setBackground(new java.awt.Color(255, 255, 255));
        jlbUsuario1.setDisplayedMnemonic('U');
        jlbUsuario1.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jlbUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        jlbUsuario1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbUsuario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/User_38px.png"))); // NOI18N
        jlbUsuario1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpContenido.add(jlbUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 120, 50));

        jbnCerrar.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Close Window_50px_1.png"))); // NOI18N
        jbnCerrar.setBorderPainted(false);
        jbnCerrar.setContentAreaFilled(false);
        jbnCerrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Close Window_60px.png"))); // NOI18N
        jbnCerrar.setSelected(true);
        jbnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarActionPerformed(evt);
            }
        });
        jpContenido.add(jbnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpContenido, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpContenido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAceptarActionPerformed
    validarlog();
    }//GEN-LAST:event_jbnAceptarActionPerformed

    private void jpsContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpsContrasenaActionPerformed
        jbnAceptar.requestFocus();
    }//GEN-LAST:event_jpsContrasenaActionPerformed

    private void jpsContrasenaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpsContrasenaMouseClicked
        jpsContrasena.setText("");
    }//GEN-LAST:event_jpsContrasenaMouseClicked

    private void jpsContrasenaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpsContrasenaFocusGained
        jpsContrasena.setText("");
    }//GEN-LAST:event_jpsContrasenaFocusGained

    private void jtxUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxUsuarioActionPerformed
        jpsContrasena.requestFocus();
    }//GEN-LAST:event_jtxUsuarioActionPerformed

    private void jtxUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxUsuarioMouseClicked
        jtxUsuario.setText("");
    }//GEN-LAST:event_jtxUsuarioMouseClicked

    private void jtxUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxUsuarioFocusGained
        //jtxUsuario.setText("");
    }//GEN-LAST:event_jtxUsuarioFocusGained

    private void jbnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarActionPerformed

    private void jpContenidoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpContenidoMousePressed
         setOpacity((float)0.8);
         x=evt.getX();
         y=evt.getY();
    }//GEN-LAST:event_jpContenidoMousePressed

    private void jpContenidoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpContenidoMouseReleased
        setOpacity((float)1.0);
        
    }//GEN-LAST:event_jpContenidoMouseReleased

    private void jpContenidoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpContenidoMouseDragged
        Point point= MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jpContenidoMouseDragged

    private void jpsContrasenaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpsContrasenaKeyTyped
        System.out.println("Codigo"+evt.getKeyChar());
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("enter pressed");
            validarlog();
        }


    }//GEN-LAST:event_jpsContrasenaKeyTyped

    private void jbnAceptarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbnAceptarKeyTyped
               System.out.println("Codigo"+evt.getKeyCode());
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("enter pressed");
            validarlog();
        }
    }//GEN-LAST:event_jbnAceptarKeyTyped

    private void jlbOlvidasteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbOlvidasteMouseClicked
       
     
    }//GEN-LAST:event_jlbOlvidasteMouseClicked

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
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }
    
    private void validarlog(){
        objEmpleado.setUsuario(jtxUsuario.getText());
        objEmpleado.setContrasena(jpsContrasena.getText());
        validar = objEmpleado.validarUsuario();

        menu = new frmMenu(this);
        //if para validar usuario si es 1 usuario y contraseña correctos
        if (validar == 1) {
            splash = new frmSplash(this, menu);
            splash.setVisible(true);
            this.dispose();
//          menu.jlbLogin.setText(jtxUsuario.getText());
//          menu.jlbTitulo.setText("Venta");
//          menu.jlbTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Buy_48px_1.png")));
        }
        //if para validar datos si es 2 la contraseña es la incorrecta
        if (validar == 2) {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta");
//
        }
//
//        if(!jtxUsuario.getText().equals("admin")){
//            menu.jbnConfiguracion.setEnabled(false);
//            menu.jbnConfiguracion.setToolTipText("No tienes permisos para acceder a esta opción");
//
        //}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton jbnAceptar;
    private javax.swing.JButton jbnCerrar;
    private javax.swing.JLabel jlbInicioSesion;
    private javax.swing.JLabel jlbOlvidaste;
    private javax.swing.JLabel jlbPAssword;
    private javax.swing.JLabel jlbUsuario1;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPasswordField jpsContrasena;
    private javax.swing.JTextField jtxUsuario;
    // End of variables declaration//GEN-END:variables
}
