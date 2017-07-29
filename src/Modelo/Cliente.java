package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Cliente extends Persona{

 private int idCliente;
 private String contrasena;
 private int noCompras;
 private int idDescuento;
 
 private int contadorcitoCliente=0;
 
 Conexion obj= new Conexion();
 
 public Cliente(){
 
 }
  public int getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }
 

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getNoCompras() {
        return noCompras;
    }

    public void setNoCompras(int noCompras) {
        this.noCompras = noCompras;
    }
    
 
 public void altaCliente(){
 CallableStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareCall("CALL altaCliente(?,?,?,?,?)");
            conectar.setInt("idClientep", idCliente);
            conectar.setString("contrasenap", contrasena);
            conectar.setInt("noComprasp", 0);
            conectar.setString("rfcCp", rfc);
            conectar.setInt("idDescuentop", 0);
            //ejecutar sentencia
           conectar.executeUpdate();
            JOptionPane.showMessageDialog(null,  "Registro Exitoso");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
    
         }
 }
 public void bajaCliente(){
     PreparedStatement comando;
        obj.conectar();
            
        try {    
            comando=obj.conexion.prepareStatement("Delete from cliente where idCliente=?");
            comando.setInt(1, idCliente);
            int resp = comando.executeUpdate();
            JOptionPane.showMessageDialog(null, resp+"Eliminación exitosa");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }

        try {    
            comando=obj.conexion.prepareStatement("Delete from persona where rfcPersona=?");
            comando.setString(1, rfc);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp+"Fila(s) afectada(s)");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }

 }
 
 //metodo para llenar la tabla Cliente
 public void consultaCliente(DefaultTableModel tablaCliente){
     
     Object[] obj1= new Object[6];

 PreparedStatement com;
 ResultSet res;
 obj.conectar();
 
     if(contadorcitoCliente ==0){
     tablaCliente.addColumn("idCliente");
     tablaCliente.addColumn("Nombre");
     tablaCliente.addColumn("Apellidos");
     tablaCliente.addColumn("Teléfono");
     tablaCliente.addColumn("Email");
     tablaCliente.addColumn("Domicilio");
     contadorcitoCliente++;
    
     }
                
    try {
        com=obj.conexion.prepareCall("select * from vista_clientepersona");
        res=com.executeQuery();
                
        while(res.next()){
            
            for (int i = 0; i < 6; i++) {
            obj1[i] = res.getObject(i+1);
            }
        tablaCliente.addRow(obj1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }  

 }
 
 public void buscarCliente(String buscar){
  PreparedStatement comando;
                ResultSet resultado;
                obj.conectar();
            try {     
                comando= obj.conexion.prepareCall("select * from vista_clientepersona where (upper(idCliente) like '%"+buscar+"%') or (upper(nombre) like '%"+buscar+"%') or (upper(apellidos) like '%"+buscar+"%') or (upper(telefono) like '%"+buscar+"%') or (upper(email) like '%"+buscar+"%') or (upper(domicilio) like '%"+buscar+"%')");
             
                resultado=comando.executeQuery();
                
                if(resultado.first()){
                idCliente=resultado.getInt("idCliente");
                    
                }else{
               
                JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
                idCliente=0;
                
                }// fin del else 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }// fin del try-cach
         
            try {    
             
                comando= obj.conexion.prepareCall("select c.*,p.* from cliente c join persona p on c.rfcCliente=p.rfcPersona where idCliente=?");
                comando.setInt(1,idCliente);
                resultado=comando.executeQuery();
                
                if(resultado.first()){
                idCliente=resultado.getInt("idCliente");
                contrasena=resultado.getString("contraseña");
                noCompras=resultado.getInt("noCompras");                
                idDescuento=resultado.getInt("idDescuento");
                rfc=resultado.getString("rfcPersona");
                nombre=resultado.getString("nombre");
                apellidos=resultado.getString("apellidos");
                fNac=resultado.getString("fechaNac");
                telefono=resultado.getString("telefono");
                estado=resultado.getString("estado");
                municipio=resultado.getString("municipio");
                calleNumero=resultado.getString("calleNum");
                colonia=resultado.getString("colonia");
                cp=resultado.getInt("CP");
                referencias=resultado.getString("referencias");
                email=resultado.getString("email");
                idEmpresa=resultado.getInt("idEmp");
                    
                }else{
               
                
                idCliente=0;
                contrasena="";
                noCompras=0;
                rfc="";
                idDescuento=0;
                nombre="";
                apellidos="";
                fNac="";
                telefono="";
                estado="";
                municipio="";
                calleNumero="";
                colonia="";
                cp=0;
                referencias="";
                email="";
                idEmpresa=0;
                
                }// fin del else 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }// fin del try-cach
 }
 public void modificarCliente(){
PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE cliente SET contraseña=?, noCompras=?, rfcCliente=?, idDescuento=? where idCliente=?");
          
            conectar.setString(1, contrasena);
            conectar.setInt(2, noCompras);
            conectar.setString(3, rfc);
            conectar.setInt(4, idDescuento);
            conectar.setInt(5, idCliente);
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
           // JOptionPane.showMessageDialog(null, resp + "Fila(s)afecta(s)");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
          
        }
 }
    
    
}
