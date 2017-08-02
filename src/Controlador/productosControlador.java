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
import Ventanas.dialogDetalleProd;


/**
 *
 * @author cesaralx
 */

public class productosControlador {

    frmMenu menu;
    dialogProductos dprod = new dialogProductos(menu, true, this);
    Conexion obj = new Conexion();
    dialogDetalleProd ddp;
    
    

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
                panel.removeAll();
        panel.revalidate();
        panel.repaint();

        panel.setLayout(new GridLayout(4, 3, 5, 5)); //la ultima linea son las columnas
        llenarCategoria(); //cargar imagenes de productos en la bd

        panel.validate();
        panel.repaint();
        panel.setVisible(true);

        panel.revalidate();
    }

    public void llenarCategoria() throws IOException {
        obj.conectar();
        JPanel panel = menu.getPanelPord();//Make a panel
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        String query = "SELECT * FROM producto";

        try {
            Statement stmnt = obj.conexion.createStatement();
            //ejecutar sentencia
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {

                //Instantiates a new instance of JLabel for each record
                String path = rs.getString("imagen");
                String Finalpath = new File(path).getAbsolutePath();
                System.out.println("Ruta completa  de la imagen: " + Finalpath);
                Image image = ImageIO.read(new File(Finalpath));
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel(icon);
                label.setText(rs.getString("idProducto"));
                label.setName(rs.getString("idProducto"));

                //then adds the instance to the panel
                panel.add(label);

            }

            System.out.println("LLenada exitoso");
            asignarActionsLabels(panel);
        } catch (SQLException ex) {

            System.out.println(ex);
        }
    }
    
    public void asignarActionsLabels(JPanel panel) {
        Component[] component = panel.getComponents();

        for (int i = 0; i < component.length; i++) {
            if (component[i] instanceof JLabel) {
               final String id = component[i].getName();
                if (component[i].getName().equals(String.valueOf((i + 1)))) {
                    /*add a mouselistener instead and listen to mouse clicks*/
                    component[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JOptionPane.showMessageDialog(menu, "Si tu me tocaste "+ id);
                            try {
                                ddp = new dialogDetalleProd(menu, true, Integer.valueOf(id));
                            } catch (IOException ex) {
                                Logger.getLogger(productosControlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ddp.setVisible(true);
                        }
                    });
                    //fin de mouse event
                    System.out.println("Encontrado el " + (i + 1));
                } else {
                    System.out.println("Encontrado jlabel " + (i + 1) + " pero no con nombre de producto");

                }
            }
        }

    }

    public Producto obternetObjProd() {
        Producto prod = menu.getProducto();
        return prod;
    }

    public void agegarProducto() {
        dprod.setVisible(true);
    }

    public productosControlador getthisObj() {
        return this;
    }

    
    
}
