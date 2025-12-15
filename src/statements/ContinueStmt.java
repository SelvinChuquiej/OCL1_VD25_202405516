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
public class ContinueStmt extends Stmt {

    public ContinueStmt(int line, int column) {
        super(line, column);
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        return ControlStmt.cont();
    }

}
