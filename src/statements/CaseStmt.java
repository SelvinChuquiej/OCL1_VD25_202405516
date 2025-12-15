/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Stmt;
import java.util.List;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class CaseStmt extends Stmt {

    private Expr valor;
    private List<Stmt> bloque;

    public CaseStmt(Expr valor, List<Stmt> bloque, int line, int column) {
        super(line, column);
        this.valor = valor;
        this.bloque = bloque;
    }

    public Expr getValor() {
        return valor;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        for (Stmt s : bloque) {
            ControlStmt res = s.ejecutar(tabla);
            if (res.getTipo() != ControlStmt.Tipo.NORMAL) {
                return res;
            }
        }
        return ControlStmt.normal();
    }
}
