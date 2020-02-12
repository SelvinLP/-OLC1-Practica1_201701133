/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Aragon Perez
 */
public class Lista_ER {
    private String Nombre;
    private ArrayList<String> ExpresionRegular;
    
    public Lista_ER(String nombre) {
        this.Nombre=nombre;
        ExpresionRegular=new ArrayList<>();
    }
    //Metodos de Acceso
    public String getNombre(){
        return Nombre;
    }
    public ArrayList getER(){
        return ExpresionRegular;
    }
    public void setER(String Datos){
        ExpresionRegular.add(Datos);
    }
}
