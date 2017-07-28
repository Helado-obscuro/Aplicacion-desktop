/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.swing.JFileChooser;
import java.io.File;       
import javax.swing.JFrame;
import Ventanas.frmMenu;
import Ventanas.frmLogin;

/**
 *
 * @author cesaralx
 */
public class controlProductos {
     frmLogin ologin;
     frmMenu omenuProducto;

    public controlProductos(frmLogin login, frmMenu menuProducto) {

     ologin = login;
      omenuProducto = menuProducto;
    }
    
    
    public void selectImagen(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); //lofgile
            omenuProducto.jtfImagen.setText(selectedFile.getAbsolutePath());
        }
    }
       
    
    
}
