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
public class Param {
    public TipoDato tipo;
    public String id;

    public Param(TipoDato tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }
    
}
