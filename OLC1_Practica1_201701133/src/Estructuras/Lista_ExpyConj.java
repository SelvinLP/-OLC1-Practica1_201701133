/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;

/**
 *
 * @author Aragon Perez
 */
public class Lista_ExpyConj {
    private String Tipo;
    private String Nombre;
    private String Contenido;
    private ArrayList<String> ExpresionRegular;
    
    public Lista_ExpyConj(String tipo,String nombre, String contenido) {
        this.Tipo=tipo;
        this.Nombre=nombre;
        this.Contenido=contenido;
        ExpresionRegular=new ArrayList<>();
    }
    //Metodos de Acceso
    public String getTipo(){
        return Tipo;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getContenido(){
        return Contenido;
    }
    public ArrayList<String> getER(){
        return ExpresionRegular;
    }
    public void setER(ArrayList<String> Dato){
        this.ExpresionRegular=Dato;
    }
}
