/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Aragon Perez
 */
public class Lista_LexemasE {
    private String Nombre;
    private String Contenido;
    public Lista_LexemasE(String nombre,String contenido) {
        this.Nombre=nombre;
        this.Contenido=contenido;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getContenido(){
        return Contenido;
    }
}
