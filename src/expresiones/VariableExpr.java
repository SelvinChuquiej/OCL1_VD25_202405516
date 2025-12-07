/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import simbolo.TablaSimbolos;
import simbolo.Simbolo;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */

public class VariableExpr extends Expr {

    private String id;

    public VariableExpr(String id, int line, int column) {
        super(line, column);
        this.id = id;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Simbolo sim = tabla.obtenerVariable(id);
        
        if(sim == null){
           return new Resultado(TipoDato.CADENA, "Error: variable no existe: " + id + "Linea: " + linea);
        }
        return new Resultado(sim.getTipo(), sim.getValor());
    }

}
