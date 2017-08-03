package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DetallePedido {

    private int folioPedido=0;
    private int idProducto=0;
    private double cantidad=0.0;
    private  double monto=0.0;

    Conexion obj=  new Conexion();
    public int getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(int folioPedido) {
        this.folioPedido = folioPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
    
    public void altaDetallePedido(){
    
    PreparedStatement conectar;
    obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into detallePedido values(?,?,?,?)");
            conectar.setInt(1, folioPedido);
            conectar.setInt(2, idProducto);
            conectar.setDouble(3, cantidad);
            conectar.setDouble(4, monto);
            
            //ejecutar sentencia
            int resp = conectar.executeUpdate();
           JOptionPane.showMessageDialog(null, "Operación exitosa","Venta",JOptionPane.YES_NO_CANCEL_OPTION);
    
        } catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, "Error al registrar");
          
        }  
    }
    
    public void buscarDetallePedido(int folio){
                try {
            PreparedStatement conectar;
            obj.conectar();
            conectar = obj.conexion.prepareStatement("SELECT * FROM detallepedido WHERE folioPedido = ?");
            conectar.setInt(1, folio);
            System.out.println("Folio pedido a buscar: "+folio);
            //ejecutar sentencia
            ResultSet rs = conectar.executeQuery();
            while (rs.next()) {
                this.folioPedido = rs.getInt("folioPedido");
                this.idProducto = rs.getInt("idProducto");
                this.cantidad = rs.getDouble("cantidad");
                this.monto = rs.getDouble("monto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void buscarDetallePedidoFull(int folio){
                try {
            PreparedStatement conectar;
            obj.conectar();
            conectar = obj.conexion.prepareStatement("Select pedido.folioPedido, fechaPedido,idCliente,estatus, nombreProducto,"
                    + "cantidad from pedido, detallepedido, producto where pedido.folioPedido = detallepedido.folioPedido"
                    + " and producto.idProducto = detallepedido.idProducto and detallepedido.folioPedido = ?");
            conectar.setInt(1, folio);
            System.out.println("Folio pedido a buscar: "+folio);
            //ejecutar sentencia
            ResultSet rs = conectar.executeQuery();
            while (rs.next()) {
                this.folioPedido = rs.getInt("folioPedido");
                this.idProducto = rs.getInt("idProducto");
                this.cantidad = rs.getDouble("cantidad");
                this.monto = rs.getDouble("monto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void bajaDetallePedido(){
    
    
    
    
    }
   





    
}
