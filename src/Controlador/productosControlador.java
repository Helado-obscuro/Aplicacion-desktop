/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.File;
import Ventanas.frmMenu;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import Ventanas.dialogProductos;
import java.awt.event.ActionEvent;


/**
 *
 * @author cesaralx
 */
public class productosControlador {
    frmMenu menu;
    dialogProductos dprod = new dialogProductos(menu, true);

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

    public void initUI() {

        JPanel panel = menu.getPanelPord();//Make a panel
        panel.setLayout(new GridLayout(0, 3));
        JLabel label1 = new JLabel("test");
        JLabel label2 = new JLabel("Imagen");
        panel.add(label1);
        panel.add(label2);

        panel.validate();
        panel.repaint();
        panel.setVisible(true);

        panel.revalidate();

    }


    
    public void agegarProducto(){
        dprod.setVisible(true);
    }
}
