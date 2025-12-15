/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Stmt;
import AST.Expr;
import AST.Resultado;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;
import util.Consola;

/**
 *
 * @author Selvi
 */
public class PrintStmt extends Stmt {

    private Expr expresion;

    public PrintStmt(Expr expresion, int linea, int columna) {
        super(linea, columna);
        this.expresion = expresion;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Resultado res = expresion.evaluar(tabla);
        if (res.getTipo() == TipoDato.ERROR || res.getValor() == null) {
            return ControlStmt.normal();
        }
        Consola.print(String.valueOf(res.getValor()));
        return ControlStmt.normal();
    }

}
