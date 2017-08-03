/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;


import Controlador.productosControlador;
import Modelo.Producto;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.*;


/**
 *
 * @author alexi
 */
public class dialogProductos extends javax.swing.JDialog {

    /**
     * Creates new form dialogProductos
     *
     * @param parent
     * @param modal
     */
    productosControlador pco;
    String nombre;
    int stock;
    String descripcion;
    double precioCompra, precioVenta;
    String ubicacion, imagen, categoria;
           
    public dialogProductos(java.awt.Frame parent, boolean modal, productosControlador pc) {
        super(parent, modal);
        initComponents();
        pco = pc;
    }

    public void seleccionarImagen(dialogProductos frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath()); //lofgile
//            frmm.jtfImagen.setText(selectedFile.getAbsolutePath());
            jtxImagen.setText(selectedFile.getAbsolutePath());
        }
    }

    private void limpiarCampos() {
        jtxNombreProducto.setText("");
        jtxStock.setText("");
        jtxDescripcion.setText("");
        jtxPrecioCom.setText("");
        jtcPrecioVent.setText("");
        jtxNombreProducto.setText("");
        jtxImagen.setText("");
        jcbCategorias.setSelectedIndex(0);
        
    }

    private void leer_datos() {
        this.nombre = jtxNombreProducto.getText();
        this.stock = Integer.parseInt(jtxStock.getText());
        this.descripcion = jtxDescripcion.getText();
        this.precioCompra = Double.parseDouble(jtxPrecioCom.getText());
        this.precioVenta = Double.parseDouble(jtcPrecioVent.getText());
        this.ubicacion = jtxNombreProducto.getText();
//        this.imagen = jtxImagen.getText();
        this.categoria = jcbCategorias.getSelectedItem().toString();

        String path = "src/imagenes/productos/";
        File source = new File(jtxImagen.getText());
        String[] parts = jtxImagen.getText().split("\\\\");
        String filename =  parts[parts.length - 1];
        System.out.println("Nombre de la imagen: " + parts[parts.length - 1]);
        File dest = new File(System.getProperty("user.dir") + "\\src\\imagenes\\productos\\"+filename);
 
        try {
            FileUtils.copyFile(source, dest);
            this.imagen = path+filename;
            System.out.println("Imagen movida: " + path+filename);
        } catch (IOException ex) {
            Logger.getLogger(dialogProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlbIdProducto = new javax.swing.JLabel();
        jtxProveedor1 = new javax.swing.JTextField();
        jtxNombreProducto = new javax.swing.JTextField();
        jlbNombreProveedor1 = new javax.swing.JLabel();
        jlbDomicilioProveedor1 = new javax.swing.JLabel();
        jlbTelefonoProveedor1 = new javax.swing.JLabel();
        jlbEmailProveedor1 = new javax.swing.JLabel();
        jtxUbicacion = new javax.swing.JTextField();
        jtxDescripcion = new javax.swing.JTextField();
        jtxStock = new javax.swing.JTextField();
        jlbRfcProveedor2 = new javax.swing.JLabel();
        jlbRfcProveedor1 = new javax.swing.JLabel();
        jtxPrecioCom = new javax.swing.JTextField();
        jbnExaminar = new javax.swing.JButton();
        jtxImagen = new javax.swing.JTextField();
        jlbRfcProveedor3 = new javax.swing.JLabel();
        jcbCategorias = new javax.swing.JComboBox<>();
        jlbRfcProveedor4 = new javax.swing.JLabel();
        jtcPrecioVent = new javax.swing.JTextField();
        jbnAgregarProveedorNuevo = new javax.swing.JButton();
        jbnCancelarProveedorNuevo1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Producto");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Producto"));

        jlbIdProducto.setDisplayedMnemonic('b');
        jlbIdProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbIdProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIdProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ID Card_25px_2.png"))); // NOI18N
        jlbIdProducto.setText("ID producto:");
        jlbIdProducto.setToolTipText("");
        jlbIdProducto.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbIdProducto.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jtxProveedor1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxProveedor1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxProveedor1.setBorder(null);
        jtxProveedor1.setEnabled(false);

        jtxNombreProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNombreProducto.setForeground(new java.awt.Color(102, 102, 102));
        jtxNombreProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNombreProducto.setBorder(null);

        jlbNombreProveedor1.setDisplayedMnemonic('b');
        jlbNombreProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbNombreProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Business Building_25px_5.png"))); // NOI18N
        jlbNombreProveedor1.setText("Nombre:");
        jlbNombreProveedor1.setToolTipText("");
        jlbNombreProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jlbDomicilioProveedor1.setDisplayedMnemonic('d');
        jlbDomicilioProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbDomicilioProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbDomicilioProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbDomicilioProveedor1.setText("Stock:");
        jlbDomicilioProveedor1.setToolTipText("");
        jlbDomicilioProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbDomicilioProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jlbTelefonoProveedor1.setDisplayedMnemonic('b');
        jlbTelefonoProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbTelefonoProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoProveedor1.setText("Descripcion:");
        jlbTelefonoProveedor1.setToolTipText("");
        jlbTelefonoProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jlbEmailProveedor1.setDisplayedMnemonic('b');
        jlbEmailProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbEmailProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEmailProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mail_25px.png"))); // NOI18N
        jlbEmailProveedor1.setText("Ubicacion:");
        jlbEmailProveedor1.setToolTipText("");
        jlbEmailProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEmailProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jtxUbicacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxUbicacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxUbicacion.setBorder(null);

        jtxDescripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxDescripcion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxDescripcion.setBorder(null);

        jtxStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxStock.setBorder(null);

        jlbRfcProveedor2.setDisplayedMnemonic('b');
        jlbRfcProveedor2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbRfcProveedor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcProveedor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcProveedor2.setText("Precio Compra");
        jlbRfcProveedor2.setToolTipText("");
        jlbRfcProveedor2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcProveedor2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jlbRfcProveedor1.setDisplayedMnemonic('b');
        jlbRfcProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbRfcProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcProveedor1.setText("Imagen:");
        jlbRfcProveedor1.setToolTipText("");
        jlbRfcProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jtxPrecioCom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxPrecioCom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxPrecioCom.setBorder(null);

        jbnExaminar.setText("Examinar");
        jbnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnExaminarActionPerformed(evt);
            }
        });

        jtxImagen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxImagen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxImagen.setBorder(null);

        jlbRfcProveedor3.setDisplayedMnemonic('b');
        jlbRfcProveedor3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbRfcProveedor3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcProveedor3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcProveedor3.setText("Categoria:");
        jlbRfcProveedor3.setToolTipText("");
        jlbRfcProveedor3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcProveedor3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jcbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una categoria", "Arpones", "Anzuelos", "Canas", "Redes", "Misc" }));

        jlbRfcProveedor4.setDisplayedMnemonic('b');
        jlbRfcProveedor4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbRfcProveedor4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcProveedor4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcProveedor4.setText("Precio Venta");
        jlbRfcProveedor4.setToolTipText("");
        jlbRfcProveedor4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcProveedor4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jtcPrecioVent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtcPrecioVent.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtcPrecioVent.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jlbIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlbNombreProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jlbRfcProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlbTelefonoProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbDomicilioProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbEmailProveedor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jlbRfcProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbnExaminar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxDescripcion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jtxStock, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jlbRfcProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxPrecioCom, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlbRfcProveedor4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtcPrecioVent, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbIdProducto)
                    .addComponent(jtxProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jlbNombreProveedor1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbRfcProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jtxNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jlbDomicilioProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jtxStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlbRfcProveedor2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbRfcProveedor4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtcPrecioVent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jtxPrecioCom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jlbTelefonoProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jbnExaminar)
                                        .addComponent(jtxImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbRfcProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jlbEmailProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jbnAgregarProveedorNuevo.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarProveedorNuevo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarProveedorNuevo.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarProveedorNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarProveedorNuevo.setText("Agregar producto");
        jbnAgregarProveedorNuevo.setBorderPainted(false);
        jbnAgregarProveedorNuevo.setContentAreaFilled(false);
        jbnAgregarProveedorNuevo.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarProveedorNuevo.setSelected(true);
        jbnAgregarProveedorNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarProveedorNuevoActionPerformed(evt);
            }
        });

        jbnCancelarProveedorNuevo1.setBackground(new java.awt.Color(145, 36, 36));
        jbnCancelarProveedorNuevo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnCancelarProveedorNuevo1.setForeground(new java.awt.Color(255, 255, 255));
        jbnCancelarProveedorNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnCancelarProveedorNuevo1.setText("Cancelar");
        jbnCancelarProveedorNuevo1.setBorderPainted(false);
        jbnCancelarProveedorNuevo1.setContentAreaFilled(false);
        jbnCancelarProveedorNuevo1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnCancelarProveedorNuevo1.setSelected(true);
        jbnCancelarProveedorNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCancelarProveedorNuevo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jbnAgregarProveedorNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbnCancelarProveedorNuevo1)
                .addGap(157, 157, 157))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnAgregarProveedorNuevo)
                    .addComponent(jbnCancelarProveedorNuevo1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnExaminarActionPerformed
               seleccionarImagen(this);

    }//GEN-LAST:event_jbnExaminarActionPerformed

    private void jbnAgregarProveedorNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarProveedorNuevoActionPerformed
        Producto prod = pco.obternetObjProd();
        leer_datos();
        int code = prod.altaProducto(nombre, stock, descripcion, precioCompra, precioVenta, ubicacion, imagen, categoria);
        if (code == 0) {
            JOptionPane.showMessageDialog(null, "Registro Exitoso");
            limpiarCampos();
            this.dispose();
        }
    }//GEN-LAST:event_jbnAgregarProveedorNuevoActionPerformed

    private void jbnCancelarProveedorNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCancelarProveedorNuevo1ActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        this.dispose();
    }//GEN-LAST:event_jbnCancelarProveedorNuevo1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       limpiarCampos();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(dialogProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dialogProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dialogProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dialogProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                dialogProductos dialog = new dialogProductos(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbnAgregarProveedorNuevo;
    private javax.swing.JButton jbnCancelarProveedorNuevo1;
    private javax.swing.JButton jbnExaminar;
    private javax.swing.JComboBox<String> jcbCategorias;
    private javax.swing.JLabel jlbDomicilioProveedor1;
    private javax.swing.JLabel jlbEmailProveedor1;
    private javax.swing.JLabel jlbIdProducto;
    private javax.swing.JLabel jlbNombreProveedor1;
    private javax.swing.JLabel jlbRfcProveedor1;
    private javax.swing.JLabel jlbRfcProveedor2;
    private javax.swing.JLabel jlbRfcProveedor3;
    private javax.swing.JLabel jlbRfcProveedor4;
    private javax.swing.JLabel jlbTelefonoProveedor1;
    private javax.swing.JTextField jtcPrecioVent;
    private javax.swing.JTextField jtxDescripcion;
    public javax.swing.JTextField jtxImagen;
    private javax.swing.JTextField jtxNombreProducto;
    private javax.swing.JTextField jtxPrecioCom;
    private javax.swing.JTextField jtxProveedor1;
    private javax.swing.JTextField jtxStock;
    private javax.swing.JTextField jtxUbicacion;
    // End of variables declaration//GEN-END:variables
}
