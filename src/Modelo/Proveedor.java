package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

public class Proveedor {

private int idProveedor;
private String nombre;
public String rfc;
private String domicilio;
private String telefono;
private String email;
private int contador=0;
Conexion obj= new Conexion();

public Proveedor(){

}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
 public void altaProveedor(){
 PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into provedor values(?,?,?,?,?,?)");
            conectar.setInt(1, idProveedor);
            conectar.setString(2, nombre);
            conectar.setString(3, rfc);
            conectar.setString(4,domicilio);
            conectar.setString(5, telefono);
            conectar.setString(6, email);
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, resp + "Registro Exitoso");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al registrar");
          
        }  
 }
 public void bajaProveedor(){

     PreparedStatement comando;
        obj.conectar();    
        try {    
            comando=obj.conexion.prepareStatement("Delete from provedor where idProv=?");
            comando.setInt(1, idProveedor);
            int resp = comando.executeUpdate();
            JOptionPane.showMessageDialog(null, resp+"Fila(s) afectada(s)");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }
     
 }
 public void consultaProveedor(DefaultTableModel tablaProveedor){
 Object[] obj1= new Object[6];
 PreparedStatement com;
 ResultSet res;
 obj.conectar();
 
     if(contador ==0){
     tablaProveedor.addColumn("idProveedor");
     tablaProveedor.addColumn("Proveedor");
     tablaProveedor.addColumn("RFC");
     tablaProveedor.addColumn("Domicilio");
     tablaProveedor.addColumn("Teléfono");
     tablaProveedor.addColumn("Email");
     contador++; 
     }
                
    try {
        com=obj.conexion.prepareCall("Select * from provedor");
        res=com.executeQuery();
                
        while(res.next()){
            
            for (int i = 0; i < 6; i++) {
            obj1[i] = res.getObject(i+1);
            }
        tablaProveedor.addRow(obj1);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,"No se puede realizar la consulta por este momento");
    }  

 }
 public void modificarProveedor(){

    PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE provedor SET nombreProveedor=?,rfc=?,domicilio=?, telefono=?,email=? WHERE idProv=?");
          
            conectar.setString(1, nombre);
            conectar.setString(2, rfc);
            conectar.setString(3, domicilio);
            conectar.setString(4, telefono);
            conectar.setString(5, email);
            conectar.setInt(6, idProveedor);
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualización de datos exitosa");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar datos");
          
        }
     
 }
 public void consultaRfcProveedor(DefaultComboBoxModel rfcProveedor){
        PreparedStatement com;
 ResultSet res;
 obj.conectar();
 
                
    try {
        com=obj.conexion.prepareCall("Select rfc from provedor");
        res=com.executeQuery();
                
        while(res.next()){
            
          for(int i=0; i<1;i++){
              rfcProveedor.addElement(res.getObject("rfc"));
    }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,"No se puede realizar la consulta por este momento");
    } 
 }
 
 public void buscarProveedor(String buscadorcito){
        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();
        try {    
         
           comando= obj.conexion.prepareCall("select * from provedor where (upper(idProv) like '%"+buscadorcito+"%') or (upper(nombreProveedor) like '%"+buscadorcito+"%') or (upper(rfc) like '%"+buscadorcito+"%') or (upper(domicilio) like '%"+buscadorcito+"%') or (upper(telefono) like '%"+buscadorcito+"%') or (upper(email) like '%"+buscadorcito+"%')");
         
            resultado=comando.executeQuery();
            
            if(resultado.first()){
            idProveedor=resultado.getInt("idProv");
            nombre=resultado.getString("nombreProveedor");
            rfc=resultado.getString("rfc");
            domicilio=resultado.getString("domicilio");
            telefono=resultado.getString("telefono");
            email=resultado.getString("email");
                
            }else{
           
            JOptionPane.showMessageDialog(null,"Consulta no exitosa");
            idProveedor=0;
            nombre="";
            rfc="";
            domicilio="";
            telefono="";
            email="";
            
            }// fin del else 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach
 }
  public void compraProveedor(){
     PreparedStatement conectar;
        ResultSet resultado;
    obj.conectar();
    
        try {
            conectar = obj.conexion.prepareStatement("select idProv,nombreProveedor,domicilio,telefono,email from  provedor WHERE rfc=?");
         
            conectar.setString(1, rfc);
         
                resultado=conectar.executeQuery();
            if(resultado.first()){
            idProveedor=resultado.getInt("idProv");
            nombre=resultado.getString("nombreProveedor");
            domicilio=resultado.getString("domicilio");
            telefono=resultado.getString("telefono");
            email=resultado.getString("email");
            
                
            }else{
           
            JOptionPane.showMessageDialog(null,"Consulta no exitosa");
        idProveedor=0;
            nombre="";
            domicilio="";
            telefono="";
            email="";
            
            }// fin del else 
            
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al consultar");
          
        }
}
  public void compraProveedor2(){
    
    PreparedStatement conectar;
    ResultSet resultado;
    obj.conectar();
    
        try {
            conectar = obj.conexion.prepareStatement("select nombreProveedor,domicilio,telefono,email from  provedor WHERE idProv=?");
         
            conectar.setInt(1, idProveedor);
         
                resultado=conectar.executeQuery();
            if(resultado.first()){
            
            nombre=resultado.getString("nombreProveedor");
            domicilio=resultado.getString("domicilio");
            telefono=resultado.getString("telefono");
            email=resultado.getString("email");
            
                
            }else{
           
            JOptionPane.showMessageDialog(null,"Consulta no exitosa");
        
            nombre="";
            domicilio="";
            telefono="";
            email="";
            
            }// fin del else 
            
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al consultar");
          
        }
}
}
