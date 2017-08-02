package Modelo;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
private int stok;
private String descripcionProd;
private double precioCompra;
private double precioVenta;
private String ubicacion;
int contadorcito =0;

private String imagen;
private String cbarras;
private String categoria;

Conexion obj= new Conexion();


public Producto(){
    

}

    public Producto(int idProducto, String nombreProducto, String pedimento, int stok, String descripcionProd, double precioCompra, double precioVenta, String ubicacion, String imagen, String cbarras, String categoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.pedimento = pedimento;
        this.stok = stok;
        this.descripcionProd = descripcionProd;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.cbarras = cbarras;
        this.categoria = categoria;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCbarras() {
        return cbarras;
    }

    public void setCbarras(String cbarras) {
        this.cbarras = cbarras;
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
        return stok;
    }

    public void setStock(int stok) {
        this.stok = stok;
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

    public int altaProducto(String nombre, int stock, String desp, double precioCom, 
            double precioVent, String ubi, String image, String cat ) {

        PreparedStatement  conectar;
        obj.conectar();
        try {
            conectar = obj.conexion.prepareStatement("INSERT INTO producto VALUES (null,?,null,?,?,?,?,?,null,?,?,null)");
            conectar.setString(1, nombre);
            conectar.setInt(2, stock);
            conectar.setString(3, desp);
            conectar.setDouble(4, precioCom);
            conectar.setDouble(5, precioVent);
            conectar.setString(6, ubi);
            conectar.setString(7, image);
            conectar.setString(8, cat);

            //ejecutar sentencia
            conectar.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return 1;
        }
    }
    
 public void bajaProducto(){

 }

 
    public void consultaProductoID(int idc) {
        try {
            PreparedStatement conectar;
            obj.conectar();
            conectar = obj.conexion.prepareStatement("SELECT * FROM producto WHERE idProducto = ?");
            conectar.setInt(1, idc);
            System.out.println("Id a buscar: "+idc);
            //ejecutar sentencia
            ResultSet rs = conectar.executeQuery();
            while (rs.next()) {
                this.nombreProducto = rs.getString("nombreProducto");
                this.idProducto = rs.getInt("idProducto");
                this.pedimento = rs.getString("pedimento");
                this.stok = rs.getInt("stock");
                this.descripcionProd = rs.getString("descripcion");
                this.precioCompra = rs.getDouble("precioCompra");
                this.precioVenta = rs.getDouble("precioVenta");
                this.ubicacion = rs.getString("ubicacion");
                this.imagen = rs.getString("imagen");
                this.cbarras = rs.getString("cbarras");
                this.categoria = rs.getString("categoria");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
 public void modificarProducto(){
     
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
            stok=resultado.getInt("stock");
            precioVenta=resultado.getDouble("precioVenta");
            
            }else{
            
            JOptionPane.showMessageDialog(null,"No se puede realizar la consulta");
            idProducto=resultado.getInt("idProducto");
            nombreProducto=resultado.getString("nombreProducto");
            stok=resultado.getInt("stock");
            precioVenta=resultado.getDouble("precioVenta");
            
            }// fin del else 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }// fin del try-cach
    }



   
}
