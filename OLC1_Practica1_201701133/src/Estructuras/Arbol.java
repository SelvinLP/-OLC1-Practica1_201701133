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
    public int No;
    public int Id_Hoja;
    public String Indentificador;
    public String Anulable;
    public ArrayList<Integer> Primeros;
    public ArrayList<Integer> Ultimos;
    public Nodo_Arbol Izquierda;
    public Nodo_Arbol Derecha;

    public Nodo_Arbol(int no,String Id) {
        this.No=no;
        this.Indentificador=Id;
        Ultimos=new ArrayList<>();
        Primeros=new ArrayList<>();
    }
    
}
public class Arbol {
    public String NOMBRE_EXPRESIONREGULAR;
    int CantNodos;
    //listas para guardar valores como tablas o para insertar
    ArrayList<Lista_Siguientes> L_Siguientes;
    public ArrayList<String> Tokens; 
    //Nodo raiz arbol
    Nodo_Arbol Raiz;
    //Cadena para graficar
    public String CadenaImprimir="";
    public Arbol() {
        Raiz=null;
        Tokens=new ArrayList<>();
        L_Siguientes=new ArrayList<>();
        CantNodos=1;
    }
    public void InsertarArraList(String Dato){
        Tokens.add(Dato);
    }
    public void AnalisarArbol() throws IOException{
        Raiz=new Nodo_Arbol(0,".");
        Raiz.Derecha=new Nodo_Arbol(1,"#");
        Raiz.Izquierda=new Nodo_Arbol(2,Tokens.get(0));
        //recorremos la lista
        for(int i=1;i<Tokens.size();i++){
            Nodo_Arbol nuevo=new Nodo_Arbol(i+3,Tokens.get(i));
            Insertar_Arbol(false,Raiz,nuevo);
            
        }
        RecorridoAnulables(Raiz);
        RecorridoPrimeros(Raiz);
        CantNodos=1;
        RecorridoUltimos(Raiz);
        RecorridoSiguientes(Raiz);
        
        CantNodos=1;

    }
    public boolean Insertar_Arbol(Boolean bandera,Nodo_Arbol Rz,Nodo_Arbol nuevo){
        

        if(Rz.Izquierda !=null ){
            bandera=Insertar_Arbol(bandera, Rz.Izquierda, nuevo);
        }
        if(!bandera){
            //miramos si se debe insertar porque si sigue cadena ya no puede tener hijos
            if(Rz.Indentificador.equals("+") || Rz.Indentificador.equals("*") || Rz.Indentificador.equals("|") || Rz.Indentificador.equals("?") || Rz.Indentificador.equals(".")){
                //comprobamos si es nodo final hio
                if(Rz.Izquierda==null && Rz.Derecha==null){
                    Rz.Izquierda=nuevo;
                    bandera=true;
                }else if((Rz.Indentificador.equals("+") || Rz.Indentificador.equals("*") ||Rz.Indentificador.equals("?")) && Rz.Izquierda==null ){
                    //insertamos izquierda
                    Rz.Izquierda=nuevo;
                    bandera=true;
                }else if(Rz.Indentificador.equals("|") || Rz.Indentificador.equals(".")){
                    if(Rz.Izquierda!=null && Rz.Derecha==null){
                        //insertamos derecha
                        Rz.Derecha=nuevo;
                        bandera=true;
                    }
                }
            }

        }
        if(Rz.Derecha !=null ){
            bandera=Insertar_Arbol(bandera, Rz.Derecha, nuevo);
        }
        return bandera;
    }    
    
    public void RecorridoAnulables(Nodo_Arbol Rz){
        if(Rz.Izquierda !=null ){
            RecorridoAnulables(Rz.Izquierda);
        }
        if(Rz.Derecha !=null ){
            RecorridoAnulables(Rz.Derecha);
        }
        //validacion de anulables
        if(Rz.Izquierda==null && Rz.Derecha==null){
            Rz.Anulable="No Anulable";
            
        }
        if((Rz.Izquierda!=null && Rz.Derecha==null)){
            if(Rz.Indentificador.equals("*")||Rz.Indentificador.equals("?")){
                Rz.Anulable="Anulable";
            }
            if(Rz.Indentificador.equals("+")){
                Rz.Anulable=Rz.Izquierda.Anulable;
            }
        }
        if((Rz.Izquierda!=null && Rz.Derecha!=null)){
            if(Rz.Indentificador.equals("|")){
                if(Rz.Izquierda.Anulable.equals("Anulable") || Rz.Derecha.Anulable.equals("Anulable")){
                    Rz.Anulable="Anulable";
                }else{
                    Rz.Anulable="No Anulable";
                }
            }
            if(Rz.Indentificador.equals(".")){
                if(Rz.Izquierda.Anulable.equals("Anulable") && Rz.Derecha.Anulable.equals("Anulable")){
                    Rz.Anulable="Anulable";
                }else{
                    Rz.Anulable="No Anulable";
                }
            }
        }
    }
    
    public void RecorridoPrimeros(Nodo_Arbol Rz){
        if(Rz.Izquierda !=null ){
            RecorridoPrimeros(Rz.Izquierda);
        }
        if(Rz.Derecha !=null ){
            RecorridoPrimeros(Rz.Derecha);
        }
        //Validacion de Primeros
        //Hojas
        if(Rz.Izquierda==null && Rz.Derecha==null){
            Rz.Primeros.add(CantNodos);
            //Insertamos a la tabla de siguientes para identificar los nodos hijos
            L_Siguientes.add(new Lista_Siguientes(Rz.Indentificador, CantNodos));
            Rz.Id_Hoja=CantNodos;
            CantNodos++;
        }
        //Operadores
        if(Rz.Indentificador.equals("*") || Rz.Indentificador.equals("+") || Rz.Indentificador.equals("?")){
            for(int i=0;i<Rz.Izquierda.Primeros.size();i++){
                Rz.Primeros.add(Rz.Izquierda.Primeros.get(i));
            }
        }
        if(Rz.Indentificador.equals("|")){
            //Izquierda 
            for(int i=0;i<Rz.Izquierda.Primeros.size();i++){
                Rz.Primeros.add(Rz.Izquierda.Primeros.get(i));
            }
            //Mas Derecha
            for(int i=0;i<Rz.Derecha.Primeros.size();i++){
                //validacion si existe sino no lo agrega
                Rz.Primeros.add(Rz.Derecha.Primeros.get(i));
            }
        }
        if(Rz.Indentificador.equals(".")){
            if(Rz.Izquierda.Anulable.equals("Anulable")){
                //se agregan los dos
                //Izquierda 
                for(int i=0;i<Rz.Izquierda.Primeros.size();i++){
                    Rz.Primeros.add(Rz.Izquierda.Primeros.get(i));
                }
                //Mas Derecha
                for(int i=0;i<Rz.Derecha.Primeros.size();i++){
                    //validacion si existe sino no lo agrega
                    Rz.Primeros.add(Rz.Derecha.Primeros.get(i));
                    
                }
            }else{
                for(int i=0;i<Rz.Izquierda.Primeros.size();i++){
                    Rz.Primeros.add(Rz.Izquierda.Primeros.get(i));
                }
            }
            
        }
        

    }
    
    public void RecorridoUltimos(Nodo_Arbol Rz){
        if(Rz.Izquierda !=null ){
            RecorridoUltimos(Rz.Izquierda);
        }
        if(Rz.Derecha !=null ){
            RecorridoUltimos(Rz.Derecha);
        }
        //Validacion de Primeros
        //Hojas
        if(Rz.Izquierda==null && Rz.Derecha==null){
            Rz.Ultimos.add(CantNodos);
            CantNodos++;
        }
        //Operadores
        if(Rz.Indentificador.equals("*") || Rz.Indentificador.equals("+") || Rz.Indentificador.equals("?")){
            for(int i=0;i<Rz.Izquierda.Ultimos.size();i++){
                Rz.Ultimos.add(Rz.Izquierda.Ultimos.get(i));
            }
        }
        if(Rz.Indentificador.equals("|")){
            //Izquierda 
            for(int i=0;i<Rz.Izquierda.Ultimos.size();i++){
                Rz.Ultimos.add(Rz.Izquierda.Ultimos.get(i));
            }
            //Mas Derecha
            for(int i=0;i<Rz.Derecha.Ultimos.size();i++){
                //validacion si existe sino no lo agrega
                Rz.Ultimos.add(Rz.Derecha.Ultimos.get(i));
            }
        }if(Rz.Indentificador.equals(".")){
            if(Rz.Derecha.Anulable.equals("Anulable")){
                //se agregan los dos
                //Izquierda 
                for(int i=0;i<Rz.Izquierda.Ultimos.size();i++){
                    Rz.Ultimos.add(Rz.Izquierda.Ultimos.get(i));
                }
                //Mas Derecha
                for(int i=0;i<Rz.Derecha.Ultimos.size();i++){
                    //validacion si existe sino no lo agrega
                    Rz.Ultimos.add(Rz.Derecha.Ultimos.get(i));
                }
            }else{
                for(int i=0;i<Rz.Derecha.Ultimos.size();i++){
                    Rz.Ultimos.add(Rz.Derecha.Ultimos.get(i));
                }
            }
            
        }
        //fin de validaciones
    }

    public void RecorridoSiguientes(Nodo_Arbol Rz){
        if(Rz.Izquierda !=null ){
            RecorridoSiguientes(Rz.Izquierda);
        }
        //comprobamos si a este nodo se le esta sacando siguientes o si pasa al otro
        if(Rz.Derecha !=null ){
            RecorridoSiguientes(Rz.Derecha);
        }
        //Validacion de Siguientes
        //agregamos siguientes
        if(Rz.Indentificador.equals(".")){
            //ultimos de c1 sus siguientes son los primeros de c2
            for(int i=0;i<Rz.Izquierda.Ultimos.size();i++){
                for(int i2=0;i2<Rz.Derecha.Primeros.size();i2++){
                    //posicion del valor
                    int valor=Rz.Izquierda.Ultimos.get(i);
                    valor--;
                    L_Siguientes.get(valor).setValor(Rz.Derecha.Primeros.get(i2));
                }
            }
        }
        if(Rz.Indentificador.equals("+") || Rz.Indentificador.equals("*")){
            for(int i=0;i<Rz.Izquierda.Ultimos.size();i++){
                //ultimos de c1 sus siguientes son los primeros de c1
                for(int i2=0;i2<Rz.Izquierda.Primeros.size();i2++){
                    //posicion del valor
                    int valor=Rz.Izquierda.Ultimos.get(i);
                    L_Siguientes.get(valor-1).setValor(Rz.Izquierda.Primeros.get(i2));
                }
            }
        }
        

    }
    
    public void GraficarSiguientes(int CantidadHTML){
        String Nombre=NOMBRE_EXPRESIONREGULAR;
        
        String CadenaImprimir="<html>"+ "<body>"+ "<h1 align='center'>"+Nombre+"</h1></br>"+ "<table cellpadding='10' border = '1' align='center'>"+'\n';

        CadenaImprimir+=" <tr><td><b>Nombre de Hoja</b></td><td><b>Id de Hoja</b></td><td><b>Siguientes</b></td></tr>"+'\n';
        for(int i=0;i<L_Siguientes.size();i++){
            //concatenacion de siguientes
            String sig="";
            for(int x=0;x<L_Siguientes.get(i).Siguientes.size();x++){
                if(x==0){
                    sig+=L_Siguientes.get(i).Siguientes.get(x);
                }else{
                    sig+=","+L_Siguientes.get(i).Siguientes.get(x);
                }
            }
            CadenaImprimir+="<tr><td>"+L_Siguientes.get(i).getNombre()+"</td>"+"<td>"+L_Siguientes.get(i).getNumeroNodo()+"</td>"+"<td>"+sig+"</tr>"+'\n';
        }

        CadenaImprimir+="</table></body></html>";
        
        String ruta = "Reporte_Siguientes"+CantidadHTML+".html";
        File archivo = new File(ruta);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            FileWriter Fw = new FileWriter(archivo);
            BufferedWriter Bw = new BufferedWriter(Fw);
            Bw.write(CadenaImprimir);
            Bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Runtime.getRuntime().exec("cmd /C Reporte_Siguientes"+CantidadHTML+".html");  
            
        }catch (IOException ioe) {
            //en caso de error
            System.out.println (ioe);
        }
    }
    
    public void GraficarArbol(int Cantidad) throws IOException{
        
        String ruta = "Arbol"+Cantidad+".dot";
        File archivo = new File(ruta);
        BufferedWriter Lect;
        Lect = new BufferedWriter(new FileWriter(archivo));
        this.CadenaImprimir = "digraph ARBOL { " + '\n';
        this.CadenaImprimir += "rankdir=TB" + '\n';
        this.CadenaImprimir += "node[shape=record,style=filled] " + '\n';
        DatosArbol(Raiz);
        this.CadenaImprimir += '\n' + "}";
        
        
        Lect.write(this.CadenaImprimir);
        Lect.close();
        try {
            String cmd = "dot -Tpng Arbol"+Cantidad+".dot -o Arbol"+Cantidad+".png"; 
            Runtime.getRuntime().exec(cmd); 
            
        }catch (IOException ioe) {
            //en caso de error
            System.out.println (ioe);
        }
        
    }
    
    public void DatosArbol(Nodo_Arbol nodoraiz){
        String Titulo;
        String Escape="";
        //validaciones para quitar errores de graphviz
        char Caracter=nodoraiz.Indentificador.charAt(0);
        if(Caracter == (char) 34){
            Titulo="Cadena";
        }else if(Caracter == (char) 123){
            Titulo="Conjunto";
        }else{
            Titulo="Operador";
        }
        String tem=nodoraiz.Indentificador.replaceAll("\"", "");
        String tem2="";
        for(int i=0;i<tem.length();i++){
            if(tem.charAt(i)==(char)123    ||tem.charAt(i)==(char)125){
            }else{tem2+=tem.charAt(i);}
        }
        Escape="";
        for(int i=0;i<tem2.length();i++){
            if(Character.isDigit(tem2.charAt(i)) ||Character.isLetter(tem2.charAt(i)) ||tem2.charAt(i)==(char)32){
            }else{
                Escape="\\";
                break;
            }
        }
        
        //Fin de errores Graphviz
        //concatenacion de primeros
        String L_Primeros="";
        L_Primeros+=nodoraiz.Primeros.get(0);
        for(int i=1;i<nodoraiz.Primeros.size();i++){
            L_Primeros+=" ,"+nodoraiz.Primeros.get(i);
            
        }
        String L_Ultimos="";
        L_Ultimos+=nodoraiz.Ultimos.get(0);
        for(int i=1;i<nodoraiz.Ultimos.size();i++){
            L_Ultimos+=" ,"+nodoraiz.Ultimos.get(i);
            
        }
        //fin de concatenacion de primeros 
        this.CadenaImprimir += "\"" + nodoraiz.No  + "\"" + "[label =\"<C0>|P: "+L_Primeros+"|{<C1>"+nodoraiz.Anulable+"|" +Titulo+": "+Escape+ tem2 + "}|U: "+L_Ultimos+"|<C2>\"]; \n";

        if(nodoraiz.Izquierda !=null ){
            this.DatosArbol(nodoraiz.Izquierda);
            this.CadenaImprimir += "\""+ nodoraiz.No  +"\":C0->"+"\""+nodoraiz.Izquierda.No+"\"; \n";
        }
        
        if(nodoraiz.Derecha !=null ){
            this.DatosArbol(nodoraiz.Derecha);
            this.CadenaImprimir += "\""+ nodoraiz.No  +"\":C2->"+"\""+nodoraiz.Derecha.No+"\"; \n";
        }
    }
    
}
