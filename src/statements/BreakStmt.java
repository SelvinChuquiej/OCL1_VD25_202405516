/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Stmt;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class BreakStmt extends Stmt {

    public BreakStmt(int line, int column) {
        super(line, column);
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        return ControlStmt.brk();
    }


    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoBreak" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"BREAK\", fillcolor=\"#ffcccc\"];\n");
        return nombreNodo;
    }

}
