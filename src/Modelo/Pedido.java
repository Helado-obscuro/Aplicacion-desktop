package Modelo;

import java.util.Date;

public class Pedido {

private int idPedido;
private Date fechaPedido;
private String estatus;

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
 
     }
  public void bajaPedido(){

     }
  public void consultaPedido(){

 }
  public void modificarPedido(){

 }

    
}
