package com.akihabara.market.dao; // Paquete al que pertenece esta clase

import com.akihabara.market.model.ProductoOtaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
	
	// Crea una instancia de la conexión a la base de datos
	DatabaseConnection db = new DatabaseConnection();
	
	// Obtiene la conexión desde la instancia anterior
	private Connection conexion = db.getConexion();

	// Método para agregar un nuevo producto a la base de datos
	public void agregarProducto(ProductoOtaku producto) {
		String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try {
        	// Prepara la sentencia SQL con parámetros
        	PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());    
            stmt.setString(2, producto.getCategoria());  
            stmt.setDouble(3, producto.getPrecio());     
            stmt.setInt(4, producto.getStock());        
            stmt.executeUpdate();                       
            System.out.println("Producto agregado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
	}

	// Método para obtener un producto según su ID
	public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        try  {
        	PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) {
                // Si encuentra el producto, lo devuelve como objeto
                return new ProductoOtaku(
                	rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no encuentra nada, retorna null
    }

	// Método para obtener todos los productos de la base de datos
	public List<ProductoOtaku> obtenerTodosLosProductos() {
	    List<ProductoOtaku> lista = new ArrayList<>();
	    String sql = "SELECT * FROM producto";
	    try {
	        Statement stmt = conexion.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        // Recorre todos los resultados y los agrega a la lista
	        while (rs.next()) {
	            ProductoOtaku producto = new ProductoOtaku(
	                rs.getInt("id"),
	                rs.getString("nombre"),
	                rs.getString("categoria"),
	                rs.getDouble("precio"),
	                rs.getInt("stock")
	            );
	            lista.add(producto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista; 
	}

	// Método para actualizar los datos de un producto
	public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    // Método para eliminar un producto según su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try  {
        	PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar productos por nombre
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        try  {
        	PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, "%" + nombre + "%"); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para buscar productos por categoría exacta
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoria) {
        List<ProductoOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria = ?";
        try {
        	PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public void cerrarConexion() {
    	try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
