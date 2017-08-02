  package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Empleado extends Persona {
private int idEmpleado;
private String usuario;
private String contrasena;
private int nss;

private String id;

Conexion obj= new Conexion();

 int contador=0;
//private String email;

public Empleado (){

}

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getNss() {
        return nss;
    }

    public void setNss(int nss) {
        this.nss = nss;
    }

 public void altaEmpleado(){
     
     CallableStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareCall("CALL altaEmpleado(?,?,?,?,?)");
            conectar.setInt("idEmpp", idEmpleado);
            conectar.setString("usuariop", usuario);
            conectar.setString("contrasenap", contrasena);
            conectar.setInt("nssp", nss);
            conectar.setString("rfcEp", rfc);
            //ejecutar sentencia
           conectar.executeUpdate();
            JOptionPane.showMessageDialog(null,  "Registro Exitoso");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
    
    
        }  
 
 }
     public void buscarEmpleado(String buscar){
                PreparedStatement comando;
                ResultSet resultado;
                obj.conectar();
            try {     
                comando= obj.conexion.prepareCall("select * from vista_empleadopersona where (upper(idempleado) like '%"+buscar+"%') or (upper(usuario) like '%"+buscar+"%') or (upper(nombre) like '%"+buscar+"%') or (upper(apellidos) like '%"+buscar+"%') or (upper(telefono) like '%"+buscar+"%') or (upper(domicilio) like '%"+buscar+"%')");
             
                resultado=comando.executeQuery();
                
                if(resultado.first()){
                idEmpleado=resultado.getInt("idEmpleado");
                    
                }else{
               
                JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
                idEmpleado=0;
                
                }// fin del else 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }// fin del try-cach
         
            try {    
             
                comando= obj.conexion.prepareCall("select e.*,p.* from empleado e join persona p on e.rfcEmpleado=p.rfcPersona where idEmpleado=?");
                comando.setInt(1,idEmpleado);
                resultado=comando.executeQuery();
                
                if(resultado.first()){
                idEmpleado=resultado.getInt("idEmpleado");
                usuario=resultado.getString("usuario");
                contrasena=resultado.getString("contraseña");
                nss=resultado.getInt("nss");
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
               
                
                idEmpleado=0;
                usuario="";
                contrasena="";
                nss=0;
                rfc="";
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
// //Metodo para llenar tabla empleado
 public void consultaEmpleado(DefaultTableModel tablaEmpleado){
     
 Object[] obj1= new Object[6];

 PreparedStatement com;
 ResultSet res;
 obj.conectar();
 
     if(contador ==0){
     tablaEmpleado.addColumn("idEmpleado");
     tablaEmpleado.addColumn("usuario");
     tablaEmpleado.addColumn("Nombre");
     tablaEmpleado.addColumn("Apellidos");
     tablaEmpleado.addColumn("Teléfono");
     tablaEmpleado.addColumn("Domicilio");
     contador++;
    
     }
                
    try {
        com=obj.conexion.prepareCall("select * from vista_empleadopersona");
        res=com.executeQuery();
                
        while(res.next()){
            
            for (int i = 0; i < 6; i++) {
            obj1[i] = res.getObject(i+1);
            }
        tablaEmpleado.addRow(obj1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }  
 }
 
 public void modificarEmpleado(){
     PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE empleado SET usuario=?,contrasena=?,nss=?, rfcEmpleado=? where idEmpleado=?");
          
            conectar.setString(1, usuario);
            conectar.setString(2, contrasena);
            conectar.setInt(3, nss);
            conectar.setString(4, rfc);
            conectar.setInt(5, idEmpleado);
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, resp + "Fila(s)afecta(s)");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
          
        }

 }
 public int validarUsuario(){    
 int i=0, j=0;   
 PreparedStatement comando;
 ResultSet resultado;
 obj.conectar();
 
    try {
        comando=obj.conexion.prepareCall("Select * from empleado where usuario=?");
        comando.setString(1,usuario);
        resultado=comando.executeQuery();
        
         if(resultado.first()){
             
             if((getContrasena()).equals(resultado.getString("contrasena"))){
               this.id =  resultado.getString("rfcEmpleado");
                i=1;
             }else
                 i=2;
            } 
         else{
            JOptionPane.showMessageDialog(null,"Usuario ingresado no existe"); 
            }
           
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }

    return i;

 }
 
 public String getID(){
     return this.id;
 }

public void bajaEmpleado(){
        PreparedStatement comando;
        obj.conectar();
            
        try {    
            comando=obj.conexion.prepareStatement("Delete from empleado where idEmpleado=?");
            comando.setInt(1, idEmpleado);
            int resp = comando.executeUpdate();
            JOptionPane.showMessageDialog(null, resp+"Fila(s) afectada(s)");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }

        try {    
            comando=obj.conexion.prepareStatement("Delete from persona where rfcPersona=?");
            comando.setString(1, rfc);
            int resp = comando.executeUpdate();
            JOptionPane.showMessageDialog(null, resp+"Fila(s) afectada(s)");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }

}
}
