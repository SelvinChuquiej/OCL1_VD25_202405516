/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AST;

import AST.NodoAST;
import AST.Resultado;
import simbolo.TablaSimbolos;
import statements.ControlStmt;

/**
 *
 * @author Selvi
 */
public abstract class Expr extends Stmt {

    public Expr(int linea, int columna) {
        super(linea, columna);
    }

    public abstract Resultado evaluar(TablaSimbolos tabla);

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        this.evaluar(tabla);
        return null;
    }
}
