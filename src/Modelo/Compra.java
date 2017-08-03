package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Compra {
private int idCompra;
private String fechaCompra;
private double total;
private double subtotal;
private double iva;
private String fechaRecep;
private String estatus;
private int idEmpleado;
private int idProv;
public int contadorCompra=0;
public int compritas=0;
public Compra(){

}

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public int getIdProv() {
        return idProv;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getFechaRecep() {
        return fechaRecep;
    }

    public void setFechaRecep(String fechaRecep) {
        this.fechaRecep = fechaRecep;
    }
   
Conexion obj= new Conexion();
 public void altaCompra(){
PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into ordenCompra values(?,?,?,?,?,?,?,?,?)");
            conectar.setInt(1, idCompra);
            conectar.setString(2, fechaCompra);
            conectar.setDouble(3, total);
            conectar.setDouble(4,subtotal);
            conectar.setDouble(5, iva);
            conectar.setString(6, fechaRecep);
            conectar.setString(7,estatus);
            conectar.setInt(8, idEmpleado);
            conectar.setInt(9, idProv);
            //ejecutar sentencia
              
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, resp + "Registro Exitoso");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
        }
 }
 public void modificaCompra(){
      PreparedStatement conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE ordencompra SET estatus=? WHERE idCompra=?");
         
            conectar.setString(1, estatus);
            conectar.setInt(2, idCompra);
            
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualización exitosa");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar compra");
          
        } 
 }
 public void consultaCompra(){
PreparedStatement comando;
                ResultSet resultado;
                obj.conectar();
          
            try {    
             
                comando= obj.conexion.prepareCall("select * from ordencompra where idCompra=?");
                comando.setInt(1,idCompra);
                resultado=comando.executeQuery();
                
                if(resultado.first()){
                fechaCompra=resultado.getString("fechaCompra");
                total=resultado.getDouble("total");
                subtotal=resultado.getDouble("subtotal");
                iva=resultado.getDouble("iva");
                fechaRecep=resultado.getString("fechaRecep");
                estatus=resultado.getString("estatus");
                idEmpleado=resultado.getInt("idEmpleado");
                idProv=resultado.getInt("idProv");
                
                    
                }else{
               
                
               idCompra=0;
                fechaCompra=null;
                total=0.00;
                subtotal=0.00;
                iva=0.00;
                fechaRecep=null;
                estatus="";
                idEmpleado=0;
                idProv=0;
                
                }// fin del else 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }// fin del try-cach
         
    
 }
}

 