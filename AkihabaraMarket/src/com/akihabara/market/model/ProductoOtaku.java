package com.akihabara.market.model;

public class ProductoOtaku {
	protected int id;
	protected String nombre;
	protected String categoria;
	protected double precio;
	protected int stock;
	
	public ProductoOtaku() {}
	
	public ProductoOtaku(int id, String nombre,String categoria,double precio,int stock) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}

	public int getId() {
		return this.id;
		
	}
	public String getNombre() {
		
		return this.nombre;
	}
	public String getCategoria() {
		
		return this.categoria;
	}
	public double getPrecio() {
		
		return this.precio;
	}
	public int getStock() {
		
		return this.stock;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCategoria(String categoria){
		this.categoria = categoria;
	}
	
	public void setPrecio(double precio){
		this.precio = precio;
	}

	public void setStock(int stock){
		this.stock = stock;
	}

	
	
	public String toString() {
		return  "ID: " + this.getId() +  ", Nombre: " + this.getNombre() + ", Categoria: " + this.getCategoria() + ", Precio: " + this.getPrecio() + ", Stock: " + this.getStock();
	}
}
