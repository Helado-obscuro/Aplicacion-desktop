/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Empleado;
import Modelo.Producto;
import java.io.File;
import Ventanas.frmMenu;
import java.awt.*;
import javax.swing.*;
import Ventanas.dialogProductos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/**
 *
 * @author cesaralx
 */

public class productosControlador {
    frmMenu menu;
    dialogProductos dprod = new dialogProductos(menu, true, this);
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
            JPanel panel = menu.getPanelPord();//Make a panel

        String path = "C:\\Users\\alexi\\Pictures\\logos\\portabilidad.png";
        Image image = ImageIO.read(new File(path));
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
       

 
        panel.setLayout(new GridLayout(4, 3,5,5)); //la ultima linea son las columnas

        
        JLabel label1 = new JLabel(icon);
        label1.setSize(40, 40);
        JLabel label2 = new JLabel("Imagen");
        llenarCategoria();
//        panel.add(label1);
//        panel.add(label2);
//        panel.add(label);
        
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
    
    public void llenarCategoria() throws IOException {
        obj.conectar();
        JPanel panel = menu.getPanelPord();//Make a panel

        String query = "SELECT * FROM producto";

        try {
            Statement stmnt = obj.conexion.createStatement();
            //ejecutar sentencia
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                String lastName = rs.getString("nombreProducto");
                System.out.println(lastName + "\n");

                //Instantiates a new instance of JLabel for each record
                String path = rs.getString("imagen");
                Image image = ImageIO.read(new File(path));
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel(icon);

                //then adds the instance to the panel
                panel.add(label);

            }

            System.out.println("LLenada exitoso");
        } catch (SQLException ex) {

            System.out.println(ex);
        }
    }

    public Producto obternetObjProd() {
        Producto prod = menu.getProducto();
        return prod;
    }
    
    public productosControlador getthisObj(){
        return this;
    }

    
    
}
