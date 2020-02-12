/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Aragon Perez
 */
class Nodo_Arbol{
    public String Indentificador;
    public String Anulable;
    public LinkedList Primeros;
    public LinkedList Ultimos;
    public Nodo_Arbol Izquierda;
    public Nodo_Arbol Derecha;

    public Nodo_Arbol(String Id) {
        this.Indentificador=Id;
    }
    
}
public class Arbol {
    Nodo_Arbol Raiz;
    public String CadenaImprimir="";
    public Arbol() {
        Raiz=null;
    }
    public void Insertar_Arbol_Simple(String Dato1,String operador){
        Nodo_Arbol nuevo=new Nodo_Arbol(operador);
        Nodo_Arbol dato1=new Nodo_Arbol(Dato1);
        nuevo.Derecha=dato1;
        nuevo.Izquierda=this.Raiz;
        this.Raiz=nuevo;
        
    }    
    public void Insertar_Arbol_Doble(String Dato1,String Dato2,String operador){
        Nodo_Arbol dato2=new Nodo_Arbol(Dato2);
        Nodo_Arbol nuevo=new Nodo_Arbol(operador);
        Nodo_Arbol dato1=new Nodo_Arbol(Dato1);
        nuevo.Derecha=dato2;
        nuevo.Izquierda=dato1;
        this.Raiz=nuevo;
        
    }
    public void Insertar_Arbol_Unica(String operador){
        Nodo_Arbol nuevo=new Nodo_Arbol(operador);
        nuevo.Izquierda=this.Raiz;
        this.Raiz=nuevo;
        
    }
    public void GraficarArbol() throws IOException{
        
        String ruta = "Arbol.dot";
        File archivo = new File(ruta);
        BufferedWriter Lect;
        Lect = new BufferedWriter(new FileWriter(archivo));
        this.CadenaImprimir = "digraph ARBOL { " + '\n';
        this.CadenaImprimir += "rankdir=TB" + '\n';
        this.CadenaImprimir += "node[shape=record,style=filled] " + '\n';

        this.CadenaImprimir += '\n' + "}";
        DatosArbol(Raiz);
        Lect.write(this.CadenaImprimir);
        Lect.close();
        try {
            String cmd = "dot -Tpng Arbol.dot -o Arbol.png"; 
            Runtime.getRuntime().exec(cmd); 
            
        }catch (IOException ioe) {
            //en caso de error
            System.out.println (ioe);
        }
        
    }
    
    public void DatosArbol(Nodo_Arbol nodoraiz){
        this.CadenaImprimir += "\"" + nodoraiz.Indentificador  + "\"" + "[label =\"<C0>|<C1>" + "Nombre: " + nodoraiz.Indentificador + "|<C2>\"]; \n";

        if(nodoraiz.Izquierda !=null ){
            this.DatosArbol(nodoraiz.Izquierda);
            this.CadenaImprimir += "\""+ nodoraiz.Indentificador +"\":C0->"+"\""+nodoraiz.Izquierda.Indentificador+"\"; \n";
        }
        if(nodoraiz.Derecha !=null ){
            this.DatosArbol(nodoraiz.Derecha);
            this.CadenaImprimir += "\""+ nodoraiz.Indentificador +"\":C2->"+"\""+nodoraiz.Derecha.Indentificador+"\"; \n";
        }
    }
    
}
