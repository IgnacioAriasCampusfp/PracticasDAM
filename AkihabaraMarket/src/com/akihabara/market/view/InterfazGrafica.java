package com.akihabara.market.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import com.akihabara.market.dao.*;
import com.akihabara.market.llm.*;
import com.akihabara.market.model.*;

public class InterfazGrafica extends JFrame {
	
    ProductoDAO dao = new ProductoDAO();
    ClienteDAO clienteDao = new ClienteDAO();
    PedidoDAO pedidoDAO = new PedidoDAO();
    DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
    
    public InterfazGrafica() {
        setTitle("Akihabara Market");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout central
        CardLayout cardLayout = new CardLayout();
        JPanel panelCentral = new JPanel(cardLayout);

        // Botones de navegación superior
        JButton productosBoton = new JButton("Productos");
        JButton clientesBoton = new JButton("Clientes");
        JPanel panelBotonesTop = new JPanel();
        panelBotonesTop.add(productosBoton);
        panelBotonesTop.add(clientesBoton);

        // ---------------- PANEL PRODUCTOS ----------------
        JButton verProductos = new JButton("Ver Productos");
        JButton agregarProductos = new JButton("Añadir productos");
        JButton editarProductos = new JButton("Editar productos");
        JButton eliminarProductos = new JButton("Eliminar producto");
        JButton sugerirProductos = new JButton("Sugerir nombre producto");

        verProductos.addActionListener(e -> {
            List<ProductoOtaku> pro = dao.obtenerTodosLosProductos();
            String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
            List<String[]> datos = new ArrayList<>();
            try {
                for (ProductoOtaku p : pro) {
                    datos.add(new String[]{
                            String.valueOf(p.getId()),
                            p.getNombre(),
                            p.getCategoria(),
                            String.valueOf(p.getPrecio()),
                            String.valueOf(p.getStock())
                    });
                }
                JTable tabla = new JTable(datos.toArray(new String[0][]), columnas);
                JScrollPane scrollPane = new JScrollPane(tabla);
                tabla.setFillsViewportHeight(true);
                JOptionPane.showMessageDialog(null, scrollPane, "Productos", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        agregarProductos.addActionListener(e -> {	
            String[] categorias = {"Figura", "Manga", "Póster", "Llavero", "Ropa"};
            JTextField nombreField = new JTextField();
            JComboBox<Object> categoriaBox = new JComboBox<>(categorias);
            JTextField precioField = new JTextField();
            JTextField stockField = new JTextField();
            Object[] campos = {
                    "Nombre:", nombreField,
                    "Categoría:", categoriaBox,
                    "Precio:", precioField,
                    "Stock:", stockField
            };
            int result = JOptionPane.showConfirmDialog(null, campos, "Nuevo producto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nombre = nombreField.getText();
                    String cat = (String) categoriaBox.getSelectedItem();
                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    dao.agregarProducto(new ProductoOtaku(0, nombre, cat, precio, stock));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio y stock deben ser valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editarProductos.addActionListener(e -> {
            String[] categorias = {"Figura", "Manga", "Póster", "Llavero", "Ropa"};
            JTextField idField = new JTextField();
            JTextField nombreField = new JTextField();
            JComboBox<Object> categoriaBox = new JComboBox<>(categorias);
            JTextField precioField = new JTextField();
            JTextField stockField = new JTextField();
            Object[] campos = {
                    "ID:", idField,
                    "Nombre:", nombreField,
                    "Categoría:", categoriaBox,
                    "Precio:", precioField,
                    "Stock:", stockField
            };
            int result = JOptionPane.showConfirmDialog(null, campos, "Editar producto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String nombre = nombreField.getText();
                    String cat = (String) categoriaBox.getSelectedItem();
                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    dao.actualizarProducto(new ProductoOtaku(id, nombre, cat, precio, stock));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Datos numéricos no válidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarProductos.addActionListener(e -> {
            String idstr = JOptionPane.showInputDialog("Introduce el ID del producto que quieres eliminar.");
            if (idstr != null && !idstr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idstr);
                    dao.eliminarProducto(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sugerirProductos.addActionListener(e -> {
            String[] tipos = {"Figura", "Manga", "Póster", "Llavero", "Ropa"};
            String[] franquicias = {"Naruto", "One Piece", "Dragon Ball", "My Hero Academia", "Attack on Titan"};
            JComboBox<String> tipoBox = new JComboBox<>(tipos);
            JComboBox<String> franquiciaBox = new JComboBox<>(franquicias);
            Object[] campos = {
                    "Tipo de producto:", tipoBox,
                    "Franquicia:", franquiciaBox
            };
            int result = JOptionPane.showConfirmDialog(null, campos, "Sugerencia de nombre", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String sugerencia = LlmService.sugerirNombreProducto(
                        (String) tipoBox.getSelectedItem(),
                        (String) franquiciaBox.getSelectedItem()
                );
                JOptionPane.showMessageDialog(null, sugerencia, "Nombre sugerido", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel panelProductos = new JPanel(new GridLayout(5, 1, 10, 10));
        eliminarProductos.setBackground(Color.red);
        panelProductos.add(verProductos);
        panelProductos.add(agregarProductos);
        panelProductos.add(editarProductos);
        panelProductos.add(eliminarProductos);
        panelProductos.add(sugerirProductos);

        // ---------------- PANEL CLIENTES ----------------
        JButton verClientes = new JButton("Ver Clientes");
        JButton agregarClientes = new JButton("Añadir Cliente");
        JButton editarClientes = new JButton("Editar Cliente");
        JButton eliminarClientes = new JButton("Eliminar Cliente");
        eliminarClientes.setBackground(Color.red);


        verClientes.addActionListener(e -> {
            List<ClienteOtaku> clientes = clienteDao.obtenerTodosLosClientes();
            String[] columnas = {"DNI", "Nombre", "Correo", "Teléfono"};
            List<String[]> datos = new ArrayList<>();
            try {
                for (ClienteOtaku c : clientes) {
                    datos.add(new String[]{
                            String.valueOf(c.getDni()),
                            c.getNombre(),
                            c.getEmail(),
                            c.getTelefono()
                    });
                }
                JTable tabla = new JTable(datos.toArray(new String[0][]), columnas);
                JScrollPane scrollPane = new JScrollPane(tabla);
                tabla.setFillsViewportHeight(true);
                JOptionPane.showMessageDialog(null, scrollPane, "Clientes", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        agregarClientes.addActionListener(e -> {
            JTextField dniField = new JTextField();

            JTextField nombreField = new JTextField();
            JTextField correoField = new JTextField();
            JTextField telefonoField = new JTextField();
            Object[] campos = {
            		"DNI",dniField,
                    "Nombre:", nombreField,
                    "Correo:", correoField,
                    "Teléfono:", telefonoField
            };
            int result = JOptionPane.showConfirmDialog(null, campos, "Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
            	String dni = dniField.getText();
                String nombre = nombreField.getText();
                String correo = correoField.getText();
                String telefono = telefonoField.getText();
                Date fechaRegistro = new Date(); // actual
                clienteDao.agregarCliente(new ClienteOtaku(dni, nombre, correo, telefono, new java.sql.Date(fechaRegistro.getTime())));
            }
        });

        editarClientes.addActionListener(e -> {
            JTextField dniField = new JTextField();
            JTextField nombreField = new JTextField();
            JTextField correoField = new JTextField();
            JTextField telefonoField = new JTextField();
            Object[] campos = {
                    "DNI:", dniField,
                    "Nombre:", nombreField,
                    "Correo:", correoField,
                    "Teléfono:", telefonoField
            };
            int result = JOptionPane.showConfirmDialog(null, campos, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String dni = dniField.getText();
                    String nombre = nombreField.getText();
                    String correo = correoField.getText();
                    String telefono = telefonoField.getText();
                    clienteDao.actualizarCliente(new ClienteOtaku(dni, nombre, correo, telefono, null));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarClientes.addActionListener(e -> {
            String dni = JOptionPane.showInputDialog("Introduce el ID del cliente que quieres eliminar.");
            if (dni != null && !dni.isEmpty()) {
                try {
                    clienteDao.eliminarCliente(dni);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        // ---------------- PANEL PEDIDOS ----------------
        JButton verPedidos = new JButton("Ver Pedidos por Cliente");
        JButton agregarPedido = new JButton("Crear Pedido");
        JButton eliminarPedido = new JButton("Eliminar Pedido");
        eliminarPedido.setBackground(Color.red);

        JButton verDetalles = new JButton("Ver Detalles de Pedido");
        // Eliminamos el botón de agregar detalle manualmente:
        // JButton agregarDetalle = new JButton("Agregar Detalle de Pedido");

        verPedidos.addActionListener(e -> {
            String dni = JOptionPane.showInputDialog("Introduce el DNI del cliente:");
            if (dni != null && !dni.isEmpty()) {
                List<Pedido> pedidos = pedidoDAO.obtenerPedidosPorCliente(Integer.parseInt(dni));
                String[] columnas = {"ID Pedido", "DNI Cliente", "Fecha"};
                List<String[]> datos = new ArrayList<>();
                for (Pedido p : pedidos) {
                    datos.add(new String[]{
                        String.valueOf(p.getIdPedido()),
                        p.getDniCliente(),
                        p.getFecha().toString()
                    });
                }
                JTable tabla = new JTable(datos.toArray(new String[0][]), columnas);
                JScrollPane scrollPane = new JScrollPane(tabla);
                JOptionPane.showMessageDialog(null, scrollPane, "Pedidos del Cliente", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        agregarPedido.addActionListener(e -> {
            String dni = JOptionPane.showInputDialog("Introduce el DNI del cliente:");
            if (dni != null && !dni.isEmpty()) {
                Pedido nuevoPedido = new Pedido(0, dni, new Date());

                List<DetallePedido> listaDetalles = new ArrayList<>();
                boolean agregarMas = true;

                while (agregarMas) {
                    String idProductoStr = JOptionPane.showInputDialog("Introduce el ID del producto para el detalle (o deja vacío para terminar):");
                    if (idProductoStr == null || idProductoStr.isEmpty()) {
                        // El usuario quiere terminar de agregar productos
                        agregarMas = false;
                        break;
                    }

                    String cantidadStr = JOptionPane.showInputDialog("Introduce la cantidad del producto:");

                    try {
                        int idProducto = Integer.parseInt(idProductoStr);
                        int cantidad = Integer.parseInt(cantidadStr);

                        ProductoOtaku producto = dao.obtenerProductoPorId(idProducto);
                        if (producto == null) {
                            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                            continue; // Volver a pedir producto
                        }

                        double precio = producto.getPrecio();

                        DetallePedido detalle = new DetallePedido(0, 0, idProducto, cantidad, precio);
                        listaDetalles.add(detalle);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Datos inválidos para producto o cantidad", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (listaDetalles.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se agregaron productos al pedido.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Insertar el pedido con la lista de detalles
                int idPedido = pedidoDAO.agregarPedidoConDetalles(nuevoPedido, listaDetalles);

                if (idPedido > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido creado con ID: " + idPedido + " con " + listaDetalles.size() + " productos.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el pedido con detalle");
                }
            }
        });

        eliminarPedido.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Introduce el ID del pedido a eliminar:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    boolean eliminado = pedidoDAO.eliminarPedido(id);
                    String msg = eliminado ? "Pedido eliminado." : "No se pudo eliminar.";
                    JOptionPane.showMessageDialog(null, msg);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verDetalles.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Introduce el ID del pedido para ver detalles:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    List<DetallePedido> detalles = detalleDAO.obtenerDetallesPorPedido(id);
                    String[] columnas = {"ID Detalle", "ID Pedido", "ID Producto", "Cantidad"};
                    List<String[]> datos = new ArrayList<>();
                    for (DetallePedido d : detalles) {
                        datos.add(new String[]{
                                String.valueOf(d.getIdDetalle()),
                                String.valueOf(d.getIdPedido()),
                                String.valueOf(d.getIdProducto()),
                                String.valueOf(d.getCantidad())
                        });
                    }
                    JTable tabla = new JTable(datos.toArray(new String[0][]), columnas);
                    JScrollPane scrollPane = new JScrollPane(tabla);
                    JOptionPane.showMessageDialog(null, scrollPane, "Detalles del pedido", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Nuevo panel pedidos: solo botones para ver, agregar, eliminar y ver detalles
        JPanel panelPedidos = new JPanel(new GridLayout(4, 1, 10, 10));
        panelPedidos.add(verPedidos);
        panelPedidos.add(agregarPedido);
        panelPedidos.add(eliminarPedido);
        panelPedidos.add(verDetalles);

        // ---------------- PANEL CLIENTES ----------------
        JPanel panelClientes = new JPanel(new GridLayout(4, 1, 10, 10));
        panelClientes.add(verClientes);
        panelClientes.add(agregarClientes);
        panelClientes.add(editarClientes);
        panelClientes.add(eliminarClientes);


        // Añadimos paneles al panel central
        panelCentral.add(panelProductos, "productos");
        panelCentral.add(panelClientes, "clientes");
        panelCentral.add(panelPedidos, "pedidos");

        // Navegación top
        productosBoton.addActionListener(e -> cardLayout.show(panelCentral, "productos"));
        clientesBoton.addActionListener(e -> cardLayout.show(panelCentral, "clientes"));

        // Agregar botón para pedidos en navegación
        JButton pedidosBoton = new JButton("Pedidos");
        panelBotonesTop.add(pedidosBoton);
        pedidosBoton.addActionListener(e -> cardLayout.show(panelCentral, "pedidos"));

        getContentPane().add(panelBotonesTop, BorderLayout.NORTH);
        getContentPane().add(panelCentral, BorderLayout.CENTER);
    }
}
