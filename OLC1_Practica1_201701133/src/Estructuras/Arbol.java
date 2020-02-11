/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Aragon Perez
 */
class Nodo_Arbol{
    private String Indentificador;
    private String Anulable;
    private LinkedList Primeros;
    private LinkedList Ultimos;

    public Nodo_Arbol(String Id) {
        this.Indentificador=Id;
    }
    
}
public class Arbol {
    Nodo_Arbol Raiz;
    public Arbol() {
        Raiz=null;
    }
    public void Insertar_ER(ArrayList<String> L_Tokens){
        String Cadena="";
        for(int i = L_Tokens.size()-1; i >=0; i--){
            if(L_Tokens.get(i).equals("}")){
                Cadena=L_Tokens.get(i)+L_Tokens.get(i-1)+L_Tokens.get(i-2);
                i=i-2;
                Insertar_Arbol(Raiz,Cadena);
            }else{
                Cadena="";
                Insertar_Arbol(Raiz,L_Tokens.get(i));
            }
        }
        
    }
    public void Insertar_Arbol(Nodo_Arbol raiz,String Dato){
        
    }
    
}
