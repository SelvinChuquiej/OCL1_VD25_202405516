/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class DoWhileStmt extends Stmt {

    private Expr condicion;
    private List<Stmt> bloque;

    public DoWhileStmt(Expr condicion, List<Stmt> bloque, int line, int column) {
        super(line, column);
        this.condicion = condicion;
        this.bloque = bloque;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        do {
            TablaSimbolos tablaLocal = new TablaSimbolos(tabla);
            boolean rompePorContinue = false;

            for (Stmt s : bloque) {
                ControlStmt res = s.ejecutar(tablaLocal);
                
                if (res.getTipo() != ControlStmt.Tipo.NORMAL) {
                    if (res.getTipo() == ControlStmt.Tipo.BREAK) {
                        return ControlStmt.normal();
                    }
                    if (res.getTipo() == ControlStmt.Tipo.CONTINUE) {
                        rompePorContinue = true;
                        break;
                    }
                    if (res.getTipo() == ControlStmt.Tipo.RETURN) {
                        return res;
                    }
                }
            }
            Resultado resCond = condicion.evaluar(tabla);

            if (resCond.getTipo() == TipoDato.ERROR) {
                return ControlStmt.normal();
            }
            if (resCond.getTipo() != TipoDato.BOOLEANO) {
                return ControlStmt.normal();
            }
            if (!(boolean) resCond.getValor()) {
                break;
            }
        } while (true);
        return ControlStmt.normal();
    }
}
