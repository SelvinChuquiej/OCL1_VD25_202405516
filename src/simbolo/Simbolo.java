/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

/**
 *
 * @author Selvi
 */
public class Simbolo {

    private String id;
    private TipoDato tipo;
    private Object valor;

    public Simbolo(String id, TipoDato tipo, Object valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Simbolo(String id, TipoDato tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public String getId() {
        return id;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
