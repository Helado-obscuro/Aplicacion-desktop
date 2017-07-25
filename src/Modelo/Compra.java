package Modelo;

import java.util.Date;

public class Compra {
private int idCompra;
private Date fechaCompra;
private double total;
private double subtotal;
private double iva;
private Date fechaRecep;

public Compra(){

}

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
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

    public Date getFechaRecep() {
        return fechaRecep;
    }

    public void setFechaRecep(Date fechaRecep) {
        this.fechaRecep = fechaRecep;
    }
   

 public void altaCompra(){
 
 }
 public void bajaCompra(){

 }
 public void consultaCompra(){

 }
 public void modificarCompra(){

 }
    
}
