/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Stmt;
import expresiones.CallExpr;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class CallStmt extends Stmt {

    private CallExpr call;

    public CallStmt(CallExpr call, int line, int column) {
        super(line, column);
        this.call = call;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        this.call.evaluar(tabla);
        return null;
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoCallStmt" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"LLAMADA (Stmt)\"];\n");

        // Delegamos la graficación al objeto CallExpr que contiene
        dot.append(nombreNodo).append(" -> ").append(call.getDot(dot)).append(";\n");

        return nombreNodo;
    }
}
