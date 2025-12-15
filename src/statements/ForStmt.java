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
public class ForStmt extends Stmt {

    private Stmt inicializacion, actualizacion;
    private Expr condicion;
    private List<Stmt> bloque;

    public ForStmt(Stmt inicializacion, Stmt actualizacion, Expr condicion, List<Stmt> bloque, int line, int column) {
        super(line, column);
        this.inicializacion = inicializacion;
        this.actualizacion = actualizacion;
        this.condicion = condicion;
        this.bloque = bloque;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        TablaSimbolos tablaLocal = new TablaSimbolos(tabla);
        inicializacion.ejecutar(tablaLocal);

        while (true) {
            Resultado resCond = condicion.evaluar(tablaLocal);
            if (resCond.getTipo() == TipoDato.ERROR) {
                return ControlStmt.normal();
            }
            if (!(boolean) resCond.getValor()) {
                break;
            }

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
            actualizacion.ejecutar(tablaLocal);
        }
        return ControlStmt.normal();
    }
}
