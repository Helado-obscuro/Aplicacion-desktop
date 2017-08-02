package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Producto {

private int idProducto;
private String nombreProducto;
private String pedimento;
private int stock;
private String descripcionProd;
private double precioCompra;
private double precioVenta;
private String ubicacion;
Conexion obj = new Conexion();
int contadorcito =0;
public Producto(){

}
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPedimento() {
        return pedimento;
    }

    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stok) {
        this.stock = stock;
    }

    public String getDescripcionProd() {
        return descripcionProd;
    }

    public void setDescripcionProd(String descripcionProd) {
        this.descripcionProd = descripcionProd;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

 public void altaProducto(){
 
 }
 public void bajaProducto(){

 }
 public void consultaProducto(DefaultTableModel tablaProducto){
Object[] obj1= new Object[4];
 PreparedStatement com;
 ResultSet res;
 obj.conectar();
 
     if(contadorcito ==0){
     tablaProducto.addColumn("idProducto");
     tablaProducto.addColumn("Producto");
     tablaProducto.addColumn("stock");
     tablaProducto.addColumn("Precio");    
     contadorcito++; 
     }
                
    try {
        com=obj.conexion.prepareCall("Select * from vista_producto");
        res=com.executeQuery();
                
        while(res.next()){
            
            for (int i = 0; i < 4; i++) {
            obj1[i] = res.getObject(i+1);
            }
        tablaProducto.addRow(obj1);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,"No se puede realizar la consulta por este momento");
    }  
 }
public void consultaDatosProducto(){

            PreparedStatement comando;
            ResultSet resultado;
            obj.conectar();
        try {    
            comando= obj.conexion.prepareCall("Select * from producto WHERE idProducto=?");
            comando.setInt(1,idProducto);
            resultado=comando.executeQuery();
            
            if(resultado.first()){
            idProducto=resultado.getInt("idProducto");
            nombreProducto=resultado.getString("nombreProducto");
            stock=resultado.getInt("stock");
            precioVenta=resultado.getDouble("precioVenta");
            
            }else{
            
            JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
            idProducto=resultado.getInt("idProducto");
            nombreProducto=resultado.getString("nombreProducto");
            stock=resultado.getInt("stock");
            precioVenta=resultado.getDouble("precioVenta");
            
            }// fin del else 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach
    }


   
}
