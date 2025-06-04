package com.akihabara.market.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;


//Hacemos que esta clase extienda de JFrame
public class InterfazGrafica extends JFrame{
    ProductoDAO dao = new ProductoDAO();

	//Creamos una constructo para llamarlo despues
    public InterfazGrafica() {
    	
    	//Ponemos datos inicales de la ventana grafica
    	setTitle("Akihabara Market");
    	setSize(500,500);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Creamos los botones necesarios
        JButton verProductos = new JButton("Ver Productos");
        JButton agregarProductos = new JButton("Añadir productos");
        JButton editarProductos = new JButton("Editar productos");
        JButton eliminarProductos = new JButton("Eliminar producto");
        
        
        //Realizamos una actionListener para a la hora de clicar el boton
        verProductos.addActionListener( e -> {
        	
        	//Guardamos los productos en una lista
        	List<ProductoOtaku> pro = dao.obtenerTodosLosProductos();
        	String[] columnas = {"ID","Nombre","Categoria","Precio","Stock"};
        	
        	List<String[]> datos = new ArrayList<>();
        	
        	try {
        		//Por cada producto lo guardamos en sus variables respectivas
        		for(ProductoOtaku p : pro) {
        			String id = p.getId() + "";
        			String nombre = p.getNombre();
        			String cat = p.getCategoria();
        			String precio = p.getPrecio() + "";
        			String stock = p.getStock() + "";
        			datos.add(new String[] {id,nombre,cat,precio,stock});
        			
        		}
        		//Creamos una matriz que guarde todos los datos recogidos
        		String[][] data = new String[datos.size()][];
        		data = datos.toArray(data);
        		
        		//Creamos la tabla con los datos recogidos y el nombre de las columnas
        		JTable tabla = new JTable(data,columnas);
                JScrollPane scrollPane = new JScrollPane(tabla);
        		tabla.setFillsViewportHeight(true);
        		
        		JOptionPane.showMessageDialog(null,scrollPane,"Productos",JOptionPane.INFORMATION_MESSAGE);
        		
        		
        	}catch(Exception e1){
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	
        }
        		
       );
        //Realizamos un actionListener
        agregarProductos.addActionListener(e -> {
        	//Creamos los datos que estaran para el agregar
            String[] categorias = {"Figura", "Manga", "Póster", "Llavero", "Ropa"};
            JTextField nombreField = new JTextField();
            JComboBox<Object> categoriaBox = new JComboBox<>(categorias);
            categoriaBox.setSelectedIndex(0);
            JTextField precioField = new JTextField();
            JTextField stockField = new JTextField();

            Object[] campos = {
                "Nombre:", nombreField,
                "Categoría:", categoriaBox,
                "Precio:", precioField,
                "Stock:", stockField
            };

            //Mostramos La pantalla con los datos que le vamos pasando rellenando y los guardamos en sus variables para llamar al dao
            int result = JOptionPane.showConfirmDialog(null, campos, "Nuevo producto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String nombre = nombreField.getText();
                String cat = (String) categoriaBox.getSelectedItem();
                try {
                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    dao.agregarProducto(new ProductoOtaku(0, nombre, cat, precio, stock));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio y stock deben ser valores numéricos válidos", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Realizamos un actionListener
        editarProductos.addActionListener(e -> {
        	//Creamos los datos que estaran para el agregar

            String[] categorias = {"Figura", "Manga", "Póster", "Llavero", "Ropa"};
            JTextField idField = new JTextField();
            JTextField nombreField = new JTextField();
            JComboBox<Object> categoriaBox = new JComboBox<>(categorias);
            categoriaBox.setSelectedIndex(0);
            JTextField precioField = new JTextField();
            JTextField stockField = new JTextField();

            Object[] campos = {
            	"ID",idField,
                "Nombre:", nombreField,
                "Categoría:", categoriaBox,
                "Precio:", precioField,
                "Stock:", stockField
            };
            
            //Mostramos La pantalla con los datos que le vamos pasando rellenando y los guardamos en sus variables para llamar al dao


            int result = JOptionPane.showConfirmDialog(null, campos, "Editar producto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
            	
                String nombre = nombreField.getText();
                String cat = (String) categoriaBox.getSelectedItem();
                try {
                	int id = Integer.parseInt(idField.getText());

                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    dao.actualizarProducto(new ProductoOtaku(id,nombre,cat,precio,stock));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio y stock deben ser valores numéricos válidos", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Llamamos al actionListener
        eliminarProductos.addActionListener(e ->{
        	//Creamos un panel que nos pida el id y si no esta vacio llamamos al dao
        	String idstr = JOptionPane.showInputDialog("Introduce el ID del producto que quieres eliminar.");
        	if(idstr != null) {
        		int id = Integer.parseInt(idstr);
        		dao.eliminarProducto(id);
        	}
        });
        
        //Inicilizamos el panel con los botones
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        eliminarProductos.setBackground(Color.red);
        eliminarProductos.setForeground(Color.black);
        panel.add(verProductos);
        panel.add(agregarProductos);
        panel.add(editarProductos);
        panel.add(eliminarProductos);
        add(panel, BorderLayout.CENTER);


        
    }
    

}
