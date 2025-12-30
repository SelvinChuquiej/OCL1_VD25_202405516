/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class ValorExpr extends Expr {

    private TipoDato tipo;
    private Object valor;

    public ValorExpr(TipoDato tipo, Object valor, int line, int column) {
        super(line, column);
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        return new Resultado(tipo, valor);
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoValor" + this.hashCode();
        // Reemplazamos comillas para evitar errores en el archivo DOT
        String val = valor.toString().replace("\"", "\\\"");
        dot.append(nombreNodo).append("[label=\"LITERAL: ").append(val).append("\"];\n");
        return nombreNodo;
    }
}
