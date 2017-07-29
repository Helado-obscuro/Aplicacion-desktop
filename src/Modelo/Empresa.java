package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Empresa {
 private int idEmp=0;
 private String nombreEmpresa=""; 
 private String rfcEmpresa="";
 private String telefono="";
 private String localidad="";
 private String domicilio="";
 private String email="";
 private int noEmpleados=0;
 
 Conexion obj= new Conexion();
 
 public Empresa(){
 
 }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

   
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRfcEmpresa() {
        return rfcEmpresa;
    }

    public void setRfcEmpresa(String rfcEmpresa) {
        this.rfcEmpresa = rfcEmpresa;
    }

   
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNoEmpleados() {
        return noEmpleados;
    }

    public void setNoEmpleados(int noEmpleados) {
        this.noEmpleados = noEmpleados;
    }
 
 public void altaEmpresa(){
 
 }
 public void bajaEmpresa(){

 }
 public void consultaEmpresa(int id){
           PreparedStatement comando;
            ResultSet resultado;
            obj.conectar();
        try {    
         
           comando= obj.conexion.prepareCall("select * from empresa where idEmp="+id);
         
            resultado=comando.executeQuery();
            
            if(resultado.first()){
            idEmp=resultado.getInt("idEmp");
            nombreEmpresa=resultado.getString("nombreEmpresa");
            rfcEmpresa=resultado.getString("rfcEmpresa");
            domicilio=resultado.getString("domicilio");
            telefono=resultado.getString("telefono");
            email=resultado.getString("email");
            localidad=resultado.getString("localidad");
            noEmpleados=resultado.getInt("noEmpleados");
                
            }else{
           
            JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
            idEmp=0;
            nombreEmpresa="";
            rfcEmpresa="";
            domicilio="";
            telefono="";
            email="";
            localidad="";
            noEmpleados=0;
            
            }// fin del else 
        } catch (SQLException ex) {
            
        }// fin del try-cach
    }
 
 public void modificarEmpresa(){

     PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE empresa SET nombreEmpresa=?,rfcEmpresa=?,domicilio=?, telefono=?,email=?, localidad=?, noEmpleados=? WHERE idEmp=?");
          
            conectar.setString(1, nombreEmpresa);
            conectar.setString(2, rfcEmpresa);
            conectar.setString(3, domicilio);
            conectar.setString(4, telefono);
            conectar.setString(5, email);
            conectar.setString(6, localidad);
            conectar.setInt(7,noEmpleados);
            conectar.setInt(8,idEmp);
             
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, resp + " Se actualizó registro de forma exitosa");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar datos");
          
        }
     
 }
 
 
 public void consultaNombre(){
 
 
 }



    
}
