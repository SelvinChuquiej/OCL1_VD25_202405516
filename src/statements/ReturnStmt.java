/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ReturnException;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class ReturnStmt extends Stmt {

    private Expr expresion;

    public ReturnStmt(Expr expresion, int line, int column) {
        super(line, column);
        this.expresion = expresion;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Object valor = null;
        if (this.expresion != null) {
            Resultado result = this.expresion.evaluar(tabla);

            if (result != null) {
                valor = result.getValor();
            }
        }
        throw new ReturnException(valor);
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoReturn" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"RETURN\"];\n");

        // Solo graficamos la flecha si hay algo que retornar
        if (expresion != null) {
            dot.append(nombreNodo).append(" -> ").append(expresion.getDot(dot)).append(";\n");
        }

        return nombreNodo;
    }
}
