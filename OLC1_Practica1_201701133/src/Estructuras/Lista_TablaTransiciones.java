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
class NodoEstado{
    public String IdEstado="";
    public String IdEncabezado="";

    public NodoEstado(String idEst, String Encabezado) {
        this.IdEstado=idEst;
        this.IdEncabezado=Encabezado;
    }
        

}
public class Lista_TablaTransiciones {
    
    private String NombreEstado;
    public ArrayList<Integer> IdEstado;
    public ArrayList<NodoEstado> Estados;
    public Lista_TablaTransiciones() {
        IdEstado=new ArrayList<>();
        Estados=new ArrayList<>();
    }
    public String getNombreEstado(){
        return NombreEstado;
    }public void setNombreEstado(String Nm){
        this.NombreEstado=Nm;
    }
    public void setIdEstado(int pos){
        this.IdEstado.add(pos);
    }
    public void setTransicion(String Nm,String Enc){
        this.Estados.add(new NodoEstado(Nm, Enc));
        
    }
    
}
