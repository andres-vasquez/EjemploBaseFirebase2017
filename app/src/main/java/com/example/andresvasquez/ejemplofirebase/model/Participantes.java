package com.example.andresvasquez.ejemplofirebase.model;

/**
 * Created by andresvasquez on 10/6/16.
 */
public class Participantes {

    public long ci;
    public String nombre;
    public String email;
    public int edad;

    public Participantes() {
    }

    public Participantes(long ci, String nombre, String email, int edad) {
        this.ci = ci;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    public long getCi() {
        return ci;
    }

    public void setCi(long ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
