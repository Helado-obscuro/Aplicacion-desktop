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
//            frmm.jtfImagen.setText(selectedFile.getAbsolutePath());
            dprod.jtxImagen.setText(selectedFile.getAbsolutePath());
        }
    }

        public static void initUI() {
        JPanel pane = newPane("Nuevo producto");
        JFrame frame = new JFrame("Nuevo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(pane);
        frame.pack();
        frame.setVisible(true);

    }

    public static JPanel newPane(String labelText) {
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(newLabel(labelText));
        pane.add(newButton("Open dialog"), BorderLayout.SOUTH);
        return pane;
    }

    private static JButton newButton(String label) {
        final JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                Window parentWindow = SwingUtilities.windowForComponent(button);
                JDialog dialog = new JDialog(parentWindow);
                dialog.setLocationRelativeTo(button);
                dialog.setModal(true);
                dialog.add(newPane("Label in dialog"));
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        return button;
    }

    private static JLabel newLabel(String label) {
        JLabel l = new JLabel(label);
        l.setFont(l.getFont().deriveFont(24.0f));
        return l;
    }
    
    public void agegarProducto(){
        dprod.setVisible(true);
    }
}
