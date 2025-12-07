/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AST;

import AST.NodoAST;
import AST.Resultado;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public abstract class Stmt extends NodoAST {

    public Stmt(int line, int column) {
        super(line, column);
    }

    public abstract Object ejecutar(TablaSimbolos tabla);
 
}
 