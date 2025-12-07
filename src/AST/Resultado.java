/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AST;

import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class Resultado {

    private TipoDato tipo;
    private Object valor;

    public Resultado(TipoDato tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

}
