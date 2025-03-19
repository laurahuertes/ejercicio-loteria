package com.example.Loggin;

import java.util.ArrayList;
import java.util.List;

public class Persona {
    private String nombre;
    private List<int[]> apuestas = new ArrayList<>();

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<int[]> getApuestas() {
        return apuestas;
    }

    public void setApuesta(List<int[]> apuestas) {
        this.apuestas = apuestas;
    }

    public void addApuesta(int[] apuesta) {
        this.apuestas.add(apuesta);
    }
}
