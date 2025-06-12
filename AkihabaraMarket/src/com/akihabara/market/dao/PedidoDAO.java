package com.akihabara.market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akihabara.market.model.*;

public class PedidoDAO {
    
    DatabaseConnection db = new DatabaseConnection();
    private Connection conexion = db.getConexion();

    // Crear nuevo pedido con detalles automáticos
    public int agregarPedidoConDetalles(Pedido pedido, List<DetallePedido> detalles) {
        String sqlPedido = "INSERT INTO pedido (dni_cliente, fecha) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        int idPedido = -1;

        try {
            conexion.setAutoCommit(false);

            // Insertar pedido
            try (PreparedStatement stmtPedido = conexion.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setString(1, pedido.getDniCliente());
                stmtPedido.setDate(2, new java.sql.Date(pedido.getFecha().getTime()));
                stmtPedido.executeUpdate();

                ResultSet rs = stmtPedido.getGeneratedKeys();
                if (rs.next()) {
                    idPedido = rs.getInt(1);
                    pedido.setIdPedido(idPedido);
                }
            }

            // Insertar detalles
            try (PreparedStatement stmtDetalle = conexion.prepareStatement(sqlDetalle)) {
                for (DetallePedido detalle : detalles) {
                    stmtDetalle.setInt(1, idPedido);
                    stmtDetalle.setInt(2, detalle.getIdProducto());
                    stmtDetalle.setInt(3, detalle.getCantidad());
                    stmtDetalle.addBatch();
                }
                stmtDetalle.executeBatch();
            }

            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idPedido;
    }

    // Obtener pedidos por cliente
    public List<Pedido> obtenerPedidosPorCliente(int idPedido) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE dni_cliente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getString("dni_cliente"),
                    rs.getDate("fecha")
                );
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar pedido y sus detalles
    public boolean eliminarPedido(int idPedido) {
        try {
            conexion.setAutoCommit(false);

            String sqlDetalle = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
            try (PreparedStatement stmtDetalle = conexion.prepareStatement(sqlDetalle)) {
                stmtDetalle.setInt(1, idPedido);
                stmtDetalle.executeUpdate();
            }

            String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmtPedido = conexion.prepareStatement(sqlPedido)) {
                stmtPedido.setInt(1, idPedido);
                int filas = stmtPedido.executeUpdate();
                conexion.commit();
                return filas > 0;
            }

        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Cerrar conexión
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
