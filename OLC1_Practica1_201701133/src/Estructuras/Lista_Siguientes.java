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
public class Lista_Siguientes {
    private String Nombre_Nodo;
    private int Numero_Nodo;
    public ArrayList<Integer> Siguientes;
    public Lista_Siguientes(String Nom,int No) {
        Siguientes=new ArrayList<>();
        this.Nombre_Nodo=Nom;
        this.Numero_Nodo=No;
    }
    //Metodos de Acceso
    public String getNombre(){
        return Nombre_Nodo;
    }
    public int getNumeroNodo(){
        return Numero_Nodo;
    }
    public void setValor(int Datos){
        Siguientes.add(Datos);
    }
}
