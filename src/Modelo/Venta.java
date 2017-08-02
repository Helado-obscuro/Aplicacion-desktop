package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Venta {

 private int idVenta;
 private Date fechaVenta;
 private double total;
 private double subtotal;
 private double iva;
 
 
 private double totalaDescontar;
 private int idDescuento;
       
 
 Conexion  obj = new Conexion();

    public double getTotalaDescontar() {
        return totalaDescontar;
    }

    public void setTotalaDescontar(double totalaDescontar) {
        this.totalaDescontar = totalaDescontar;
    }

    public int getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }
 
 
    public Venta(){
    
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
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

 public void altaVenta(){
 PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into venta values(?,?,?,?,?,?,?)");
            conectar.setInt(1, idVenta);
            conectar.setDate(2, (java.sql.Date) fechaVenta);
            conectar.setDouble(3, subtotal);
            conectar.setDouble(4, iva);
            conectar.setDouble(5,totalaDescontar);
            conectar.setDouble(6,total);
            conectar.setInt(7, idDescuento);
            
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp + "Fila(s)afecta(s)");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al registrar"+ex);
          
        }  
 }
 public void bajaVenta(){
        PreparedStatement comando;
        obj.conectar();
            
        try {    
            comando=obj.conexion.prepareStatement("Delete from venta where idVenta=?");
            comando.setInt(1, idVenta);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se elimino con exito la venta");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar"+ex);
        }
 }
 public void consultaVenta(){
            
            PreparedStatement comando;
            ResultSet resultado;
            obj.conectar();
        try {    
            comando= obj.conexion.prepareCall("Select * from venta WHERE idVenta=?");
            comando.setInt(1,idVenta);
            resultado=comando.executeQuery();
            
            if(resultado.first()){
            fechaVenta=resultado.getDate("fechaVenta");
            subtotal=resultado.getDouble("subTotal");
            iva=resultado.getDouble("iva");
            totalaDescontar=resultado.getDouble("totalaDescontar");
            total=resultado.getDouble("total");
            idDescuento=resultado.getInt("idDescuento");
            
            }else{
            
            JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
            fechaVenta=null;
            subtotal=0.0;
            iva=0.0;
            totalaDescontar=0.0;
            total=0.0;
            idDescuento=0;
            
            
            }// fin del else 
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se puede realizar la consulta");
        }// fin del try-cach    
 }
 public void modificarOrden(){
   PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE venta SET fechaVenta=?,subTotal=?,iva=?,totalDescontar=?,total=?, idDescuento=? WHERE idDescuento=?");
          
            conectar.setDate(1,(java.sql.Date) fechaVenta);
            conectar.setDouble(2, subtotal);
            conectar.setDouble(3, iva);
            conectar.setDouble(4, totalaDescontar);
            conectar.setDouble(5, total);
            conectar.setInt(6,idDescuento);
            conectar.setInt(7,idVenta);
            
            
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Operación exitosa");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar datos "+ex);
          
        }
        
   }
 
 public int MaximoidOrden(){
 int i=0;   
 PreparedStatement comando;
 ResultSet resultado;
 obj.conectar();
 
    try {
        comando=obj.conexion.prepareCall("Select max(idVenta) from venta");
        resultado=comando.executeQuery();
        
         if(resultado.first()){
            i=resultado.getInt("max(idVenta)");
         }                
    }catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex);
    }


return i;
}

    
}
