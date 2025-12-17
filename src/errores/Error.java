/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errores;

/**
 *
 * @author Selvi
 */
public class Error {
    private String tipo;
    private String descripcion;
    private int linea;
    private int columna;
    
    public enum TipoError {
        LEXICO, SINTACTICO, SEMANTICO
    }

    public Error(String tipo, String descripcion, int linea, int columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return "Error " + tipo + ", " + descripcion + ", linea= " + linea + ", columna= " + columna;
    }
}
