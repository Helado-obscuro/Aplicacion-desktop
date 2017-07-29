package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

public class Persona {

protected String rfc="";
protected String nombre="";
protected String apellidos="";
protected String telefono="";
protected String fNac;
protected String estado="";
protected String municipio="";
protected String calleNumero="";
protected String colonia="";
protected int cp=0;
protected String referencias="";
protected String email="";
protected int idEmpresa=0;

Conexion obj= new Conexion();
public Persona(){

}

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getfNac() {
        return fNac;
    }

    public void setfNac(String fNac) {
        this.fNac = fNac;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 

 public void altaPersona(){
 //PreparedStatement conectar;
     CallableStatement conectar; 
    obj.conectar();
        try {
            conectar = obj.conexion.prepareCall("CALL altaPersona(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            conectar.setString("rfcPersonap", rfc);
            conectar.setString("nombrep", nombre);
            conectar.setString("apellidosp", apellidos);
            conectar.setString("fechaNacp", fNac);
            conectar.setString("telefonop", telefono);
            conectar.setString("estadop", estado);
            conectar.setString("municipiop",municipio );
            conectar.setString("calleNump", calleNumero);
            conectar.setString("coloniap", colonia);
            conectar.setInt("CPp", cp);
            conectar.setString("referenciasp", referencias);
            conectar.setString("emailp", email);
            conectar.setInt("idEmpp",idEmpresa);
            
            
            //ejecutar sentencia
            conectar.executeUpdate();
            JOptionPane.showMessageDialog(null,  "Registro Exitoso");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
          
        }  
 }
 public void bajaPersona(){

 }
 public void consultaPersona(){

 }
 public void modificarPersona(){
     
     PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE persona SET nombre=?, apellidos=?,fechaNac=?, telefono=?,estado=?, municipio=?, calleNum=?, colonia=?, CP=?, referencias=?, email=?, idEmp=? WHERE rfcPersona=?");
          
            conectar.setString(1, nombre);
            conectar.setString(2, apellidos);
            conectar.setString(3, fNac);
            conectar.setString(4, telefono);
            conectar.setString(5, estado);
            conectar.setString(6, municipio);
            conectar.setString(7, calleNumero);
            conectar.setString(8, colonia);
            conectar.setInt(9, cp);
            conectar.setString(10, referencias);
            conectar.setString(11,email);
            conectar.setInt(12, idEmpresa);
            conectar.setString(13, rfc);
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null,"Operación exitosa");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar datos");
          
        }

 }


}
