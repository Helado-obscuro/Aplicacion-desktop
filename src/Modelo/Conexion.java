package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

Connection conexion;

public Connection conectar(){

     try{
    Class.forName("com.mysql.jdbc.Driver");
    
    }catch (ClassNotFoundException ex){
        JOptionPane.showMessageDialog(null, "Error al cargar la clase Driver");
    }
    try{
    //conectar
    conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/redes","root","");
    }
    catch (SQLException ex){
    JOptionPane.showMessageDialog(null, "Error al conectar");
    }
     return conexion;

}







    
}
