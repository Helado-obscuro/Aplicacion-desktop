package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DetalleVenta {

    private int idProducto = 0;
    private int idVenta = 0;
    private double cantidad = 0.0;
    private double monto = 0.0;

    int contador = 0;

    Conexion obj = new Conexion();

<<<<<<< HEAD
=======
    
    public DetalleVenta(int idProducto, int idVenta, double cantidad, double monto) {
        this.idProducto = idProducto;
        this.idVenta = idVenta;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public DetalleVenta() {
    }

    
>>>>>>> dnop
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
<<<<<<< HEAD

=======
    
    
>>>>>>> dnop
    public void altaDetalleVenta() {
        PreparedStatement conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into detalleVenta values(?,?,?,?)");
            conectar.setInt(1, idProducto);
            conectar.setInt(2, idVenta);
            conectar.setDouble(3, cantidad);
            conectar.setDouble(4, monto);

            //ejecutar sentencia
            int resp = conectar.executeUpdate();
<<<<<<< HEAD
            // JOptionPane.showMessageDialog(null,  "Operación exitosa");
=======
            JOptionPane.showMessageDialog(null, "Operación exitosa");
>>>>>>> dnop
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar " + ex);

        }
<<<<<<< HEAD

    }

    public void bajaDetalleVentaProducto() {
        PreparedStatement comando;
=======
 
 }
public void bajaDetalleVentaProducto(){
PreparedStatement comando;
>>>>>>> dnop
        obj.conectar();

        try {
            comando = obj.conexion.prepareStatement("Delete from detalleVenta where idVenta=? and idProducto=?");
            comando.setInt(1,idVenta);
            comando.setInt(2, idProducto);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp+"Se cancela ");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar" + ex);
        }

    }

    public void bajaDetalleVenta(int id) {
        PreparedStatement comando;
        obj.conectar();

        try {
            comando = obj.conexion.prepareStatement("Delete from detalleVenta where idVenta=?");
            comando.setInt(1, id);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp+"Se cancela ");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar" + ex);
        }

    }

    public void ConsultaDetalle(DefaultTableModel model, int id) {
        Object[] obj1 = new Object[4];
        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();

        if (contador == 0) {
            model.addColumn("idProducto");
            model.addColumn("idVenta");
            model.addColumn("Cantidad");
            model.addColumn("Monto");
            contador++;

        }
        try {
            comando = obj.conexion.prepareCall("Select * from detalleVenta where idProducto=?");
            comando.setInt(1, id);
            resultado = comando.executeQuery();

            while (resultado.next()) {
                for (int i = 0; i < 4; i++) {
                    obj1[i] = resultado.getObject(i + 1);
                }
                model.addRow(obj1);

<<<<<<< HEAD
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach

    }

    public void ConsultaVistaDetalle(DefaultTableModel model, int id) {
        Object[] obj1 = new Object[6];
        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();

        if (contador == 0) {
            model.addColumn("idProducto");
            model.addColumn("idVenta");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Cantidad");
            model.addColumn("Monto");
            contador++;

        }
        try {
            comando = obj.conexion.prepareCall("Select * from vista_detalleventa where idVenta=?");
            comando.setInt(1, id);
            resultado = comando.executeQuery();

            while (resultado.next()) {
                for (int i = 0; i < 6; i++) {
                    obj1[i] = resultado.getObject(i + 1);
                }
                model.addRow(obj1);

=======
public void ConsultaDetalle(DefaultTableModel model, int id){
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
            comando = obj.conexion.prepareCall("Select * from detalleVenta where idVenta=?");
            comando.setInt(1, id);
            resultado = comando.executeQuery();

            while (resultado.next()) {
                for (int i = 0; i < 4; i++) {
                    obj1[i] = resultado.getObject(i + 1);
                }
                model.addRow(obj1);
>>>>>>> dnop
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach

    }

    public void modificarDetalleVenta() {

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
            JOptionPane.showMessageDialog(null, "Actualización Exitosa", "Venta", JOptionPane.YES_NO_CANCEL_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos");

        }
    }

    public double TotalVenta(int id) {
        double canti = 0;
        ResultSet resultado;
        PreparedStatement comando;
        obj.conectar();
        try {
            comando = obj.conexion.prepareStatement("select sum(monto) from detalleVenta where idVenta=?");
            comando.setInt(1, id);
            resultado = comando.executeQuery();
            if (resultado.first()) {
                canti = resultado.getInt("sum(monto)");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar a la base de datos");

        }
        return canti;
    }

    public int TotalCantidad(int id) {
        int canti = 0;
        ResultSet resultado;
        PreparedStatement comando;
        obj.conectar();
        try {
            comando = obj.conexion.prepareStatement("select sum(cantidad) from detalleVenta where idVenta=?");
            comando.setInt(1, id);
            resultado = comando.executeQuery();
            if (resultado.first()) {
                canti = resultado.getInt("sum(cantidad)");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar a la base de datos");

        }
        return canti;
    }

    public void buscarDetalleVenta(int id,int idv) {

        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();

        try {
            comando = obj.conexion.prepareCall("Select * from detalleVenta where idProducto=? and idventa=?");
            comando.setInt(1, idProducto);
            comando.setInt(1, idVenta);
            
            resultado=comando.executeQuery();
            
            if(resultado.first()){
            idProducto=resultado.getInt("idProducto");
            idVenta=resultado.getInt("idVenta");
            cantidad=resultado.getDouble("rfcEmpresa");
            monto=resultado.getDouble("domicilio");
                
            }else{
           
            JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
            idProducto=0;
            idVenta=0;
            cantidad=0;
            monto=0;
            
            }// fin del else 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach
    }
     
    
}
