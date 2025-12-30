/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Stmt;
import expresiones.CallExpr;
import java.util.List;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class StartStmt extends Stmt {

    private String id;
    private List<Expr> argumentos;

    public StartStmt(String id, List<Expr> argumentos, int line, int column) {
        super(line, column);
        this.id = id;
        this.argumentos = argumentos;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        CallExpr llamada = new CallExpr(id, argumentos, linea, columna);
        llamada.evaluar(tabla);
        return null;
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoStart" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"START WITH: " + id + "\", shape=doubleoctagon, fillcolor=orange, style=filled];\n");

        if (argumentos != null) {
            for (Expr arg : argumentos) {
                dot.append(nombreNodo).append(" -> ").append(arg.getDot(dot)).append(" [label=\"param\"];\n");
            }
        }
        return nombreNodo;
    }

}
