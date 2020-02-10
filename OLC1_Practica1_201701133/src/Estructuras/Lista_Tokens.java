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
public class Lista_Tokens {
    private int ID;
    private String Lexema;
    private String Descripcion;
    private int Fila;
    private int Columna;

    public Lista_Tokens(int id,String lexema,String descripcion,int fila,int columna) {
        this.ID=id;
        this.Lexema=lexema;
        this.Descripcion=descripcion;
        this.Fila=fila;
        this.Columna=columna;
    }
    //metodos de acceso
    public int getID(){
        return ID;
    }
    public String getLexema(){
        return Lexema;
    }
    public String getDescripcion(){
        return Descripcion;
    }
    public int getFila(){
        return Fila;
    }
    public int getColumna(){
        return Columna;
    }
}
