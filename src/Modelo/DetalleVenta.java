package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DetalleVenta {

    private int idProducto=0;
    private int idVenta=0;
    private double cantidad=0.0;
    private double monto=0.0;
    
    int contador=0;

    Conexion obj = new Conexion();
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    
 public void altaDetalleVenta(){
PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into detalleVenta values(?,?,?,?)");
            conectar.setInt(1, idProducto);
            conectar.setInt(2,idVenta);
            conectar.setDouble(3,cantidad);
            conectar.setDouble(4,monto);
            
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp + "Fila(s)afecta(s)");
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al registrar "+ex); 
 
 }
 
 }
public void bajaDetalleVenta(){
PreparedStatement comando;
        obj.conectar();
            
        try {    
            comando=obj.conexion.prepareStatement("Delete from detalleVenta where idProducto=?");
            comando.setInt(1, idProducto);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp+"Se cancela ");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar"+ex);
        }

}

public void ConsultaDetalle(DefaultTableModel model){
  Object[] obj1= new Object[4];
 PreparedStatement comando;
 ResultSet resultado;
 obj.conectar();
  
 if(contador ==0){
     model.addColumn("idProducto");
     model.addColumn("idVenta");
     model.addColumn("Cantidad");
     model.addColumn("Monto");
     contador++;
     
     }
    try {
        comando=obj.conexion.prepareCall("Select * from detalleVenta where idVenta=?");
        comando.setInt(1,idVenta);
        resultado=comando.executeQuery();
        
        while(resultado.next()){
           for (int i = 0; i < 4; i++){
                      obj1[i] = resultado.getObject(i+1);
            }
         model.addRow(obj1);

        }
    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach

}

public void modificarDetalleVenta(){

    PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE detalleVenta SET idVenta=?,cantidad=?,monto=? WHERE idProducto=?");
          
            
            conectar.setInt(1, idVenta);
            conectar.setDouble(2, cantidad);
            conectar.setDouble(3, monto);
            conectar.setInt(4, idProducto);
            
             //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualización Exitosa","Venta",JOptionPane.YES_NO_CANCEL_OPTION);
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al actualizar datos");
          
        }
}
    
}
