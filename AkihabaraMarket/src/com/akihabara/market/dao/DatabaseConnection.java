package com.akihabara.market.dao;

//Importamos lo necesario de java.sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	//Ponemos los datos necesarios en cada variable para poder hacer la conexion
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/akihabara_db";
	static final String USER = "userAkihabara";
	static final String PASSWORD = "akihabara";
	
	private Connection conexion;

	//Realizamos la conexion a traves del DriverManager.
    public DatabaseConnection() {
        try {
            conexion = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            //Si no ha ocurrido nada a la hora de conectarse nos mostrara en pantalla un mensaje de exito
            System.out.println("Se ha conectado con la base de datos.");
        } catch (SQLException e) {
        	//Si da error a la hora de conectar con la base de datos nos mostrara un error.
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    //En este metodo nos devolvera la conexion realizada con la base de datos.
    public  Connection getConexion() {
        return conexion;
    }

    //En este metodo cerraremos la conexion para que no de error a la hora de ejecutar otra vez el programa
    public void cerrarConexion() {
    	//Si la conexion esta activa nos cerrara la base de datos.
        if (conexion != null) {
            try {
            	//Cerramos la conexion a la base de datos e imprimimos un mensaje de verificacion
                conexion.close();
                System.out.println("Cerrado la base de datos correctamente");
            } catch (SQLException e) {
            	//Si la conexion no ha sido cerrada correctamente nos mostrara un mensaje
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
