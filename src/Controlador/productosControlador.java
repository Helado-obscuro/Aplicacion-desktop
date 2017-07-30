/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import java.io.File;
import Ventanas.frmMenu;
import java.awt.*;
import javax.swing.*;
import Ventanas.dialogProductos;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;


/**
 *
 * @author cesaralx
 */

public class productosControlador {
    frmMenu menu;
    dialogProductos dprod = new dialogProductos(menu, true);
    Conexion obj= new Conexion();



    public productosControlador(frmMenu fmenu) {
        menu = fmenu;
        
    }
    
    public void seleccionarImagen(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath()); //lofgile
            dprod.jtxImagen.setText(selectedFile.getAbsolutePath());
        }
    }

    public void initUI() throws IOException {
        
        String path = "C:\\Users\\alexi\\Pictures\\logos\\portabilidad.png";
        Image image = ImageIO.read(new File(path));
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);

        JPanel panel = menu.getPanelPord();//Make a panel
        JPanel contentPane = new JPanel(null);
        panel.setLayout(new GridLayout(4, 3,5,5)); //la ultima linea son las columnas

        
        JLabel label1 = new JLabel(icon);
        label1.setSize(40, 40);
        JLabel label2 = new JLabel("Imagen");
        panel.add(label1);
        panel.add(label2);
        panel.add(label);
        
//         JScrollPane scrollPane = new JScrollPane(contentPane);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        scrollPane.setBounds(72, 72, panel.getWidth()-70, panel.getHeight()-70);
//
//        panel.add(scrollPane);


        panel.validate();
        panel.repaint();
        panel.setVisible(true);

        panel.revalidate();
        
    }

    public void agegarProducto(){
        dprod.setVisible(true);
    }
    
    public void llenarCategoria(){
    CallableStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareCall("select categoria from producto");
            
            //ejecutar sentencia
          ResultSet rs = conectar.executeQuery();
            while (rs.next()) {
                String lastName = rs.getString("categoria");
                System.out.println(lastName + "\n");
//                        JLabel label2 = new JLabel("Imagen");
            }

            System.out.println("Registro exitoso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

    
        }  
    }
}
