package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Pedido {

    private int idPedido;
    private Date fechaPedido;
    private String estatus;
    private int idCliente;

//variable para llevar control de titulos en la tabla
    int contadorPedido = 0;
    Conexion obj = new Conexion();
    Producto objProd = new Producto();
    DetallePedido objDpedido = new DetallePedido();

    public Pedido() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public void altaPedido() {

        PreparedStatement conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("insert into pedido values(?,?,?,?)");
            conectar.setInt(1, idPedido);
            conectar.setDate(2, (java.sql.Date) fechaPedido);
            conectar.setInt(3, idCliente);
            conectar.setString(4, estatus);

            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro exitoso", "Pedido", JOptionPane.YES_NO_CANCEL_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar");

        }

    }

    public void bajaPedido() {
        PreparedStatement comando;
        obj.conectar();

        try {
            comando = obj.conexion.prepareStatement("Delete from pedido where foliopedido=?");
            comando.setInt(1, idPedido);
            int resp = comando.executeUpdate();
            //JOptionPane.showMessageDialog(null, resp+"Fila(s) afectada(s)");    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "no se puede eliminar");
        }

    }

    public void consultaPedido(DefaultTableModel modeloPedido) {
        Object[] obj1 = new Object[6];
        final int idProd;
        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();
        if (contadorPedido == 0) {
            modeloPedido.addColumn("Folio");
            modeloPedido.addColumn("Fecha pedido");
            modeloPedido.addColumn("idCliene");
            modeloPedido.addColumn("Estatus");
            modeloPedido.addColumn("Producto");
            modeloPedido.addColumn("Cantidad");

            contadorPedido++;
        }
//        objDpedido.buscarDetallePedido(idProd);

        try {
            comando = obj.conexion.prepareCall("Select pedido.folioPedido, fechaPedido,idCliente,estatus, nombreProducto,"
                    + "cantidad from pedido, detallepedido, producto where pedido.folioPedido = detallepedido.folioPedido"
                    + " and producto.idProducto = detallepedido.idProducto");
            resultado = comando.executeQuery();
            while (resultado.next()) {

                for (int i = 0; i < 6; i++) {
                    obj1[i] = resultado.getObject(i + 1);
                }
                modeloPedido.addRow(obj1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }//Fin del try catch        
    }// fin del else

    public void consultaEstatus(DefaultTableModel modeloPedido) {

        Object[] obj1 = new Object[6];
        PreparedStatement comando;
        ResultSet resultado;
        obj.conectar();
        if (contadorPedido == 0) {
            modeloPedido.addColumn("Folio");
            modeloPedido.addColumn("Fecha pedido");
            modeloPedido.addColumn("idCliene");
            modeloPedido.addColumn("Estatus");
            modeloPedido.addColumn("Producto");
            modeloPedido.addColumn("Cantidad");
            contadorPedido++;
        }

        try {
            comando = obj.conexion.prepareCall("Select pedido.folioPedido, fechaPedido,idCliente,estatus, nombreProducto,"
                    + "cantidad from pedido, detallepedido, producto where pedido.folioPedido = detallepedido.folioPedido"
                    + " and producto.idProducto = detallepedido.idProducto and pedido.estatus=?");
            comando.setString(1, estatus);
            resultado = comando.executeQuery();

            while (resultado.next()) {

                for (int i = 0; i < 6; i++) {
                    obj1[i] = resultado.getObject(i + 1);
                }
                modeloPedido.addRow(obj1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }//fin del try-catch
    }

    public void modificarPedido() {

        PreparedStatement conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE pedido SET estatus=? WHERE folioPedido=?");

            conectar.setString(1, estatus);
            conectar.setInt(2, idPedido);

            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualización exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pedido");

        }
    }

    public void modificarEstatus(String status, Integer idPed) {
        PreparedStatement conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("UPDATE pedido SET estatus=? WHERE folioPedido=?");

            conectar.setString(1, status);
            conectar.setInt(2, idPed);

            //ejecutar sentencia
            int resp = conectar.executeUpdate();
            JOptionPane.showMessageDialog(null, resp + "Fila(s)afecta(s)");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar Status");

        }
    }

}
