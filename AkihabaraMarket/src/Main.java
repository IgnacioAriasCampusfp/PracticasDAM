import com.akihabara.market.model.*;

import java.util.ArrayList;

import com.akihabara.market.dao.*;


public class Main {

	public static void main(String[] args) {

		ProductoDAO dao = new ProductoDAO();
		
		
		//Agregar produtos
		/**
		dao.agregarProducto(new ProductoOtaku(0,"Naruto Shippuden Manga Vol. 3","Manga",10,15));
		**/
		
		//Eliminar producto por id
		/**
				
		if(dao.eliminarProducto(17)) {
			System.out.println("Eliminado correctamente");
		}else {
			System.out.println("Error al eliminar");

		}
		
		**/
		//Mostrar datos
		ArrayList<ProductoOtaku> pos = 	(ArrayList<ProductoOtaku>) dao.obtenerTodosLosProductos();
		
		for(ProductoOtaku po : pos) {
			
			System.out.println(po);
		}
		
		
		//Actualizar producto
		/**
		ProductoOtaku producto1 = new ProductoOtaku(17,"Naruto Shippuden Manga Vol. 3","Manga",10,25);
				
		if(dao.actualizarProducto(producto1)) {
			System.out.println("Actualizado correctamente");
		}else {
			System.out.println("Error al actualizar");

		}
	**/
		
	}

}
