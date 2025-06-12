package com.akihabara.market.model;

import java.sql.Date;

public class ClienteOtaku {
    private String dni;
    private String nombre;
    private String email;
    private String telefono;
    private Date fechaRegistro;

    // Constructor completo
    public ClienteOtaku(String dni, String nombre, String email, String telefono, Date fechaRegistro2) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro2;
    }



    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
