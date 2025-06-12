package com.akihabara.market.model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int idPedido;
    private String dniCliente;
    private Date fecha;
    private List<DetallePedido> detalles;

    public Pedido() {}

    public Pedido(int idPedido, String dniCliente, Date fecha) {
        this.idPedido = idPedido;
        this.dniCliente = dniCliente;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}
