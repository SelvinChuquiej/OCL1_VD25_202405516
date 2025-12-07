/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Stmt;
import AST.Expr;
import AST.Resultado;
import simbolo.TablaSimbolos;
import util.Consola;

/**
 *
 * @author Selvi
 */
public class PrintStmt extends Stmt {

    private Expr expresion;

    public PrintStmt(Expr expresion, int line, int column) {
        super(line, column);
        this.expresion = expresion;
    }

    @Override
    public Resultado ejecutar(TablaSimbolos tabla) {
        Resultado res = expresion.evaluar(tabla);
        if (res != null) {
            Consola.print(res.getValor().toString());
        }
        return null;
    }

}
