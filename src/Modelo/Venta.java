package Modelo;

import java.util.Date;

public class Venta {

 private int idVenta;
 private Date fechaVenta;
 private double total;
 private double subtotal;
 private double iva;
         
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
 
 }
 public void bajaVenta(){

 }
 public void consultaVenta(){

 }
 public void modificarVenta(){

 }
    
}
