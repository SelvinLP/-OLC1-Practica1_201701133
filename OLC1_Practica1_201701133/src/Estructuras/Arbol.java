/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.io.*;
import java.util.*;
import java.util.Set;

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
    
    //no tocar
    int maximo=0;
    public String NOMBRE_EXPRESIONREGULAR;
    int CantNodos;
    //Para colocar el nomrbe del estado
    int CantidadEstados=0;
    //listas para guardar valores como tablas o para insertar
    ArrayList<String>NodoHijos;
    //Lista de Transiciones
    String [][] Tabla=new String[100][100];
    ArrayList<Lista_Siguientes> L_Siguientes;
    ArrayList<Lista_TablaTransiciones> L_Transiciones;
    public ArrayList<String> Tokens; 
    //Nodo raiz arbol
    Nodo_Arbol Raiz;
    //Cadena para graficar
    public String CadenaImprimir="";
    public Arbol() {
        Raiz=null;
        Tokens=new ArrayList<>();
        L_Siguientes=new ArrayList<>();
        L_Transiciones=new ArrayList<>();
        NodoHijos=new ArrayList<>();
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
        //validaciones para Tablade Estados
        Collections.sort(NodoHijos);
        TablaEstados();
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
            //NO TOCAR Agregamos titulos para la trabla de transiciones 
            if(Rz.Indentificador.equals("#")){
                //No lo agrega al encabezado
                maximo=Rz.Id_Hoja;
            }else{
                NodoHijos.add(Rz.Indentificador);
            }
            
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
        
        String CadenaImprimir="<html>"+ "<body>"+ "<h1 align='center'> Tabla Siguientes: "+Nombre+"</h1></br>"+ "<table cellpadding='10' border = '1' align='center'>"+'\n';

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
        
        String ruta = "T_Siguientes"+CantidadHTML+".html";
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
//        try {
//
//            Runtime.getRuntime().exec("cmd /C Reporte_Siguientes"+CantidadHTML+".html");  
//            
//        }catch (IOException ioe) {
//            //en caso de error
//            System.out.println (ioe);
//        }
    }
    
    public void TablaEstados(){
        int fila=1;
        
        //Eliminacion de repetidos de los encaezados
        Set<String> hashSet2 = new HashSet<String>(NodoHijos);
        NodoHijos.clear();
        NodoHijos.addAll(hashSet2);
        //inserto en matriz
        Tabla[0][0]=" ";
        for(int i=0;i<NodoHijos.size();i++){
            Tabla[0][i+1]=NodoHijos.get(i);
        }
        
        
        
        //Primera iteracion de Estados
        String s="";
        s+="S"+CantidadEstados+"{";
        Lista_TablaTransiciones nuevo=new Lista_TablaTransiciones("S"+CantidadEstados);
        for(int x=0;x<Raiz.Primeros.size();x++){
            nuevo.setIdEstado(Raiz.Primeros.get(x));
            if(x==0){
                s+=Raiz.Primeros.get(x);
            }else{
                s+=","+Raiz.Primeros.get(x);
            }
            
        }
        Collections.sort(nuevo.IdEstado);
        s+="}";
        Tabla[1][0]=s;
        L_Transiciones.add(nuevo);
        CantidadEstados++;
        
        
        
        
        //fin de primer estado
        //para los demas estados creacion de los demas estados
        int y=1;
        while(y!=0){
            y--;
            //recorre la lista
            for(int x=0;x<L_Transiciones.size();x++){
                
                
                //Recorre los primeros
                for(int x2=0;x2<L_Transiciones.get(x).IdEstado.size();x2++){
                    //creamos uno nuevo por cada posicion de primeros
                    
                    
                    Lista_TablaTransiciones nuevo2=new Lista_TablaTransiciones("S"+CantidadEstados);                    
                    int posicionPrimeros=L_Transiciones.get(x).IdEstado.get(x2);
                    String Conc="";
                    Conc+="S"+CantidadEstados+"{";;
                    
                    
                    for(int x3=0;x3<L_Siguientes.get(posicionPrimeros-1).Siguientes.size();x3++){
                        //Inseerto los siguientes
                        nuevo2.setIdEstado(L_Siguientes.get(posicionPrimeros-1).Siguientes.get(x3));
                        if(x3==0){
                            Conc+=L_Siguientes.get(posicionPrimeros-1).Siguientes.get(x3);
                        }else{
                            Conc+=","+L_Siguientes.get(posicionPrimeros-1).Siguientes.get(x3);
                        }
                        
                    }

                    

                    
                    
                    //validaciones de la creacion
                    //Eliminar repetidos de los primeros en los estados
                    Set<Integer> hashSet = new HashSet<Integer>(nuevo2.IdEstado);
                    nuevo2.IdEstado.clear();
                    nuevo2.IdEstado.addAll(hashSet);
                    Collections.sort(nuevo2.IdEstado);
                    //Comparacion de si el nuevo nodo ya existe en caso de que no exista insertar
                    boolean repetido=false;
                    int posicionrepetido=0;
                    for(int i=0;i<L_Transiciones.size();i++){
                       //valores de la lista
                       repetido = L_Transiciones.get(i).IdEstado.equals(nuevo2.IdEstado);
                       if(repetido){
                           posicionrepetido=i;
                           break;
                       }
                        
                    }
                    if(repetido){
                        //no se inserta porque ya existe un valor
                        for(int i=0;i<NodoHijos.size();i++){
                            if(NodoHijos.get(i).equals(L_Siguientes.get(posicionPrimeros-1).getNombre())){
                                Tabla[x+1][i+1]="S"+posicionrepetido;
                            }
                        }

                    }else{
                        //si viene vacio
                        if(nuevo2.IdEstado.size()==0){
                            
                        }else{
                            //eliminar concatenacion de estado final
                            //creacion nueva posicion tabla
                            Tabla[x+2][0]=Conc+"}";
                            //creacion de nueva transaccion tabla
                            for(int i=0;i<NodoHijos.size();i++){
                                if(NodoHijos.get(i).equals(L_Siguientes.get(posicionPrimeros-1).getNombre())){
                                    Tabla[x+1][i+1]="S"+CantidadEstados;
                                }
                            }
                            y++;
                            L_Transiciones.add(nuevo2);
                            CantidadEstados++;
                            
                        }
                          
                    }

                }

            }
            
            
        }
        
    }

    public void GraficarTablaEstados(int CantidadHTML){
        String Nombre=NOMBRE_EXPRESIONREGULAR;
        
        String CadenaImprimir="<html>"+ "<body>"+ "<h1 align='center'> Tabla Transiciones: "+Nombre+"</h1></br>"+ "<table cellpadding='10' border = '1' align='center'>"+'\n';
        //Escribimos titulos
        CadenaImprimir+=" <tr><td><b>Estado</b></td><td><b>Terminales</b></td></tr>"+'\n';
        //imprimimos 

        for(int x2=0;x2<CantidadEstados+1;x2++){
            CadenaImprimir+="<tr>";
            for(int x=0;x<NodoHijos.size()+1;x++){
                if(Tabla[x2][x]==null){
                    CadenaImprimir+="<td><b></b></td>";
                }else{
                    CadenaImprimir+="<td><b>"+Tabla[x2][x]+"</b></td>";
                }
                
            }
            CadenaImprimir+="</tr>"+'\n';

            
        }

        CadenaImprimir+="</table></body></html>";
        
        String ruta = "T_Transiciones"+CantidadHTML+".html";
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
    }
    
    public void GraficarGrafo(int Cantidad) throws IOException{
        String Nombre=NOMBRE_EXPRESIONREGULAR;
        String ruta = "AFD"+Cantidad+".dot";
        File archivo = new File(ruta);
        BufferedWriter Lect;
        Lect = new BufferedWriter(new FileWriter(archivo));
        this.CadenaImprimir = "digraph AFD { " + '\n';
        this.CadenaImprimir+="graph [label=\"AFD: "+Nombre+"\", labelloc=t, fontsize=20]; ";
        DatosGrafo();
        this.CadenaImprimir += '\n' + "}";
        
        
        Lect.write(this.CadenaImprimir);
        Lect.close();
        try {
            String cmd = "dot -Tpng AFD"+Cantidad+".dot -o AFD"+Cantidad+".png"; 
            Runtime.getRuntime().exec(cmd); 
            
        }catch (IOException ioe) {
            //en caso de error
            System.out.println (ioe);
        }
        


    }
    
    public void DatosGrafo(){
        CadenaImprimir+="rankdir=LR;";
        CadenaImprimir+="edge [color=blue];";
        CadenaImprimir+="node [color = mediumseagreen];";
        for(int i=0;i<L_Transiciones.size();i++){
            CadenaImprimir+="\""+L_Transiciones.get(i).getNombreEstado()+"\""+"[ label="+L_Transiciones.get(i).getNombreEstado()+"]"+'\n';
        }
        CadenaImprimir+="secret_node [style=invis];\n";
        CadenaImprimir+="secret_node -> S0 [label=\"inicio\"];";
        //agregamos estado de finalizacion
        for(int z=0;z<L_Transiciones.size();z++){
            for(int z2=0;z2<L_Transiciones.get(z).IdEstado.size();z2++){
                System.out.println("FINAL---- "+maximo);
                if(L_Transiciones.get(z).IdEstado.get(z2)==maximo){
                    System.out.println("Valido"+z);
                    CadenaImprimir+=L_Transiciones.get(z).getNombreEstado()+"[peripheries=2];\n";
                }
            }
        }
        //creacion de transicions 
       for(int x=0;x<L_Transiciones.size();x++){
           //recorro hacia abajo en los estados
            for(int y=0;y<NodoHijos.size();y++){
                //recorro hacia la derecha
                
                if(Tabla[x+1][y+1]==null){
                    //no hay estado de transicion
                }else{
                    //quitar errore de graphviz
                    String Escape="";
                    //validaciones para quitar errores de graphviz
                    String tem=Tabla[0][y+1].replaceAll("\"", "");
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
                    CadenaImprimir+="\""+L_Transiciones.get(x).getNombreEstado()+"\""+"->\""+Tabla[x+1][y+1]+"\""+"[label=\""+Escape+tem2+"\"];"+'\n';
                }
                
            }
       }
    }
    
    public void GraficarArbol(int Cantidad) throws IOException{
        
        String ruta = "Arbol"+Cantidad+".dot";
        File archivo = new File(ruta);
        BufferedWriter Lect;
        Lect = new BufferedWriter(new FileWriter(archivo));
        this.CadenaImprimir = "digraph ARBOL { " + '\n';
        this.CadenaImprimir+="graph [label=\"Arbol: "+NOMBRE_EXPRESIONREGULAR+"\", labelloc=t, fontsize=20]; ";
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
    
    //validaciones para Lexema
    public boolean ValidarLexema(String Lexema,ArrayList<Lista_Conjuntos> L_Conj){
        boolean bandera=true;
        int Estado=1;
        for(int pos=0;pos<Lexema.length();pos++){
            //variable para ver si inserto en cualquiera de los casos
            int Insertado=0;
            //1 si
            //0 no

            //validaciones
            System.out.println("Nuevo");
            for(int x=0;x<NodoHijos.size();x++){
                
                if(Tabla[Estado][x+1]==null){
                    // No comparamos estados 
                }else{
                    //comparamos si pertenece o tiene una transicion a alguno de estos casos
                    char var=Tabla[0][x+1].charAt(0);
                    String tem=Tabla[0][x+1].replaceAll("\"", "");
                    String Nombre="";
                    for(int i=0;i<tem.length();i++){
                        if(tem.charAt(i)==(char)123    ||tem.charAt(i)==(char)125){
                        }else{Nombre+=tem.charAt(i);}
                    }
        
                    //Cadena
                    if(var == (char) 34){
                        String cad=String.valueOf(Lexema.charAt(pos));
                        for(int con=1;con<Nombre.length();con++){
                            pos++;
                            cad+=Lexema.charAt(pos);
                        }
                        if(cad.equals(Nombre)){
                            System.out.println("-valido Cadena");
                            Insertado=1;
                            Estado=Character.getNumericValue(Tabla[Estado][x+1].charAt(1))+1; 
                            System.out.println(Estado);
                            break;
                            
                        }else{
                            System.out.println("-No valido Cadena");
                            pos=pos-(Nombre.length()-1);
                            Insertado=0;
                        }
                    }
                    //Conjunto
                    if(var == (char) 123){
                        //identificamos y obtenemos el nodo de la lista de conjuntos
                        String Encontrado="";
                        for(int ListC=0;ListC<L_Conj.size();ListC++){
                            if(L_Conj.get(ListC).getNombre().equals(Nombre)){
                                Encontrado=L_Conj.get(ListC).getContenido();
                            }
                        }
                        //Luego de encontrarlo empezamos a validar
                        //L~D
                        if(Encontrado.charAt(1)==(char)126){
                            int a=(int)Encontrado.charAt(0);
                            int b=(int)Encontrado.charAt(2);
                            int valor=(int)Lexema.charAt(pos);
                            if(valor>=a && valor<=b){
                                //cumple
                                System.out.println("-valido Conjunto Rango");
                                Insertado=1;
                                Estado=Character.getNumericValue(Tabla[Estado][x+1].charAt(1))+1; 
                                System.out.println(Estado);
                                break;
                            }else{
                                System.out.println(Lexema.charAt(pos)+"Rango");
                                System.out.println("-No valido Conjunto Rango");
                                Insertado=0;
                            }
                            
                            
                            
                            
                        }else{
                            //conjunto por comas {,,,}
                            System.out.println("validandoconjcomas");
                            System.out.println(Lexema.charAt(pos));
                            String v=String.valueOf(Lexema.charAt(pos));
                            String[] valores = Encontrado.split(",");
                            int enc=0;
                            for (String it : valores){
                                System.out.println("---------------"+it);
                                if(it.equals(v)){
                                    enc=1;
                                }
                            }
                            if(enc==1){
                                System.out.println("-valido Conjunto ,");
                                Insertado=1;
                                Estado=Character.getNumericValue(Tabla[Estado][x+1].charAt(1))+1; 
                                System.out.println(Estado);
                                break;
                            }else{
                                System.out.println(Lexema.charAt(pos)+",");
                                System.out.println("-No valido Conjunto ,");
                                Insertado=0;
                            }
                            
                        }
                        
                        
                        
                        
                        
                    }//fin conjuntos




                }
            }
            //System.out.println(pos+"--"+token);
            //validamos si se inserto de lo contrario
            if(Insertado==0){
                bandera=false;
                break;
            }
            
            
            
            
            
            
        }
        return bandera;
    }
    
}
