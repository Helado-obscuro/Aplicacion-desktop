package Modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

public class Pedido {

private int idPedido;
private Date fechaPedido;
private String estatus;

Conexion obj= new Conexion();
public Pedido(){
}

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

  public void altaPedido(){
      
      PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into pedido values(?,?,?,?,?,?)");
//            conectar.setInt(1, idPedido);
//            conectar.setString(2, codigoTransaccion);
//            conectar.setString(3, domicilio);
//            conectar.setString(4, statusPe);
//            conectar.setString(5, nombreCliente);
//            conectar.setString(6, hora);
//            
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se registra servicio a domicilio","Venta",JOptionPane.YES_NO_CANCEL_OPTION);
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al registrar");
          
        }  
 
     }
  public void bajaPedido(){

     }
  public void consultaPedido(){

 }
  public void modificarPedido(){

 }

    
}
