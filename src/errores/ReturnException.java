/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errores;

/**
 *
 * @author Selvi
 */
public class ReturnException extends RuntimeException{
    public Object valor;

    public ReturnException(Object valor) {
        this.valor = valor;
    }
}
