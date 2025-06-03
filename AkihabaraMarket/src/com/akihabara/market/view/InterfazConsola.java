package com.akihabara.market.view;

import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.dao.*;

import java.util.List;
import java.util.Scanner;

public class InterfazConsola {

    public static void menu() {
        int opcion;
        Scanner scan = new Scanner(System.in);
        ProductoDAO dao = new ProductoDAO();

        do {
            System.out.println("\n-----------------------------Menú----------------------------\n"
                    + "1. Añadir producto.\n"
                    + "2. Consultar producto por ID.\n"
                    + "3. Listar todos los productos.\n"
                    + "4. Listar productos por nombre.\n"
                    + "5. Listar productos por categoría.\n"
                    + "6. Actualizar producto.\n"
                    + "7. Eliminar producto.\n"
                    + "0. Salir del programa.");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt();

            try {
                switch (opcion) {
                    case 1: 
                    	scan.nextLine();
                    	//Le pedimos los datos del nuevo producto
                        System.out.print("Nombre del producto: ");
                        String nombre = scan.nextLine();

                        /**
                        Ya que en la base de datos es necesario que sea esas 5 opciones hacemos que se haga un switch case para la categoria
                        por lo que da esa opcion, si no es ninguna de esas opciones dadas le damos un valor como Otro para porder luego con un if
                        poder romper la ejecucion del añadir para que no de error en la base de datos.
                        **/
                         
                        System.out.println("Categoría (1 - Figura, 2 - Manga, 3 - Póster, 4 - Llavero, 5 - Ropa): ");
                        int opcion2 = scan.nextInt();
                        String cat = switch (opcion2) {
                            case 1 -> "Figura";
                            case 2 -> "Manga";
                            case 3 -> "Poster";
                            case 4 -> "Llavero";
                            case 5 -> "Ropa";
                            default -> {
                                System.out.println("Categoría no válida. Redirigiendo al menu.");
                                yield "Otro";
                            }
                        };
                        if(cat.equals("Otro")) {break;}

                        System.out.print("Precio: ");
                        double precio = scan.nextDouble();

                        System.out.print("Stock: ");
                        int stock = scan.nextInt();
                        //Llamamos a agregarProducto junto con el nuevo Producto
                        dao.agregarProducto(new ProductoOtaku(0, nombre, cat, precio, stock));
                        System.out.println("Producto añadido correctamente.");
                        break;

                    case 2: 
                    	//Pedimos el id
                        System.out.print("Ingrese el ID del producto: ");
                        int idConsulta = scan.nextInt();
                        //Obtenemos el producto y si este no el nulo lo imprime
                        ProductoOtaku p = dao.obtenerProductoPorId(idConsulta);
                        if (p != null) {
                            System.out.println(p);
                        } else {
                            System.out.println("Producto no encontrado.");
                        }
                        break;

                    case 3: 
                    	//Obtenemos todos los productos y los guardamos en una lista para luego hacer un foreach para que se muestren
                        List<ProductoOtaku> todos = dao.obtenerTodosLosProductos();
                        for(ProductoOtaku po : todos) {
                        	System.out.println(po);
                        }
                        break;

                    case 4: 
                    	 scan.nextLine();
                    	//Le pedimos el nombre y lo guardamos en una lista y hacemos un for para mostrar los datos
                        System.out.print("Ingrese el nombre a buscar: ");
                        String nombreBuscado = scan.nextLine();
                        List<ProductoOtaku> porNombre = dao.buscarProductosPorNombre(nombreBuscado);
                        for(ProductoOtaku po : porNombre) {
                        	System.out.println(po);
                        }                        break;

                    case 5:
                    	  scan.nextLine();
                    	//Le pedimos la y lo guardamos en una lista y hacemos un for para mostrar los datos
                        System.out.print("Ingrese la categoría a buscar: ");
                        String categoriaBuscada = scan.nextLine();
                        List<ProductoOtaku> porCategoria = dao.buscarProductoPorCategoria(categoriaBuscada);
                        for(ProductoOtaku po : porCategoria) {
                        	System.out.println(po);
                        }                    
                        break;

                    case 6:
                    	//Pedimos el id del producto que queremos actualizar, si este no existe se termina la operacion
                        System.out.print("Ingrese el ID del producto a actualizar: ");
                        int idActualizar = scan.nextInt();

                        ProductoOtaku existente = dao.obtenerProductoPorId(idActualizar);
                        if (existente == null) {
                            System.out.println("Producto no encontrado.");
                            break;
                        }
                        scan.nextLine();
                        //Si existe el producto le pedimos los datos nuevos mostrando los datos antiguos
                        System.out.print("Nuevo nombre (" + existente.getNombre() + "): ");
                        String nuevoNombre = scan.nextLine();

                        System.out.print("Nueva categoría (" + existente.getCategoria() + "): ");
                        

                        //Realizamos la misma operacion que hemos hecho en la opcion de añadir nuevo producto
                        System.out.println("Categoría (1 - Figura, 2 - Manga, 3 - Póster, 4 - Llavero, 5 - Ropa): ");
                        int opcion3 = scan.nextInt();
                        String nuevaCategoria = switch (opcion3) {
                            case 1 -> "Figura";
                            case 2 -> "Manga";
                            case 3 -> "Poster";
                            case 4 -> "Llavero";
                            case 5 -> "Ropa";
                            default -> {
                                System.out.println("Categoría no válida. Redirigiendo al menu.");
                                yield "Otro";
                            }
                        };
                        if(nuevaCategoria.equals("Otro")) {break;}

                        
                        System.out.print("Nuevo precio (" + existente.getPrecio() + "): ");
                        double nuevoPrecio = scan.nextDouble();

                        System.out.print("Nuevo stock (" + existente.getStock() + "): ");
                        int nuevoStock = scan.nextInt();

                        dao.actualizarProducto(new ProductoOtaku(idActualizar,nuevoNombre,nuevaCategoria,nuevoPrecio,nuevoStock));
                        System.out.println("Producto actualizado.");
                        break;

                    case 7:
                    	//Le pedimos el id del producto que queramos eliminar y llamamos a eliminar producto
                        System.out.print("Ingrese el ID del producto a eliminar: ");
                        int idEliminar = scan.nextInt();
                        dao.eliminarProducto(idEliminar);
                        System.out.println("Producto eliminado si existía.");
                        break;

                    case 0:
                        System.out.println("Cerrando programa");
                        dao.cerrarConexion();
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error: " + e.getMessage());
                scan.nextLine(); 
            }

        } while (opcion != 0);

        scan.close();
    }
}
