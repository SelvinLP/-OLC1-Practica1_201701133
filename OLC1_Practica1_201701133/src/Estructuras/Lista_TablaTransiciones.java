/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Aragon Perez
 */
class Transiciones{
    public String Encabezado;
    public String NombreEstado;
    public Transiciones(String Encabezado,String Nombr) {
        this.Encabezado=Encabezado;
        this.NombreEstado=Nombr;
    }
    
}
public class Lista_TablaTransiciones {
    
    private String NombreEstado;
    //primeros
    public ArrayList<Integer> IdEstado;
    //transiciones
    public ArrayList<Transiciones> Transicion;
    public Lista_TablaTransiciones(String Nombre){
        NombreEstado=Nombre;
        IdEstado=new ArrayList<>();
        Transicion=new ArrayList<>();
        
    }
    public String getNombreEstado(){
        return NombreEstado;
    }public void setNombreEstado(String Nm){
        this.NombreEstado=Nm;
    }
    public void setIdEstado(int pos){
        this.IdEstado.add(pos);
    }
    public void setTransicion(String Encabezado,String Estadonuevo){
        Transicion.add(new Transiciones(Encabezado, NombreEstado));
        Collections.sort(Transicion, new Comparator() {
                public int compare(Transiciones p1, Transiciones p2) {
                        return new String(p1.Encabezado).compareTo(new String(p2.Encabezado));
                }

            @Override
            public int compare(Object o1, Object o2) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    
}
