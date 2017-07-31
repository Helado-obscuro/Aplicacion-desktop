package Modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Producto {

private int idProducto;
private String nombreProducto;
private String pedimento;
private int stok;
private String descripcionProd;
private double precioCompra;
private double precioVenta;
private String ubicacion;
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

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
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
 public void consultaProducto(){

 }
 public void modificarProducto(){

 }

   
}
