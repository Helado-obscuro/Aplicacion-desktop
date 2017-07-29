/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Ventanas.frmMenu;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import Ventanas.frmMenu;

/**
 *
 * @author cesaralx
 */
public class productosControlador {
    frmMenu frmm;

    public productosControlador( frmMenu frmmenu) {
        frmm = frmmenu;
        
    }
    
    public void seleccionarImagen(JFrame frame){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); //lofgile
            frmm.jtfImagen.setText(selectedFile.getAbsolutePath());
        }
        
    }
    
    
}
