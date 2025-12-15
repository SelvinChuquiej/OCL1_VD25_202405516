/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class WhileStmt extends Stmt {

    private Expr expresion;
    private List<Stmt> bloque;

    public WhileStmt(Expr expresion, List<Stmt> bloque, int line, int column) {
        super(line, column);
        this.expresion = expresion;
        this.bloque = bloque;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        TablaSimbolos scopeWhile = new TablaSimbolos(tabla);
        while (true) {
            Resultado rc = expresion.evaluar(scopeWhile);
            if (rc.getTipo() != TipoDato.BOOLEANO) {
                ManejadorErrores.agregar("Semantico", "La condicion del while debe ser booleana", linea, columna);
                return ControlStmt.normal();
            }
            boolean continuar = (Boolean) rc.getValor();
            if (!continuar) {
                break;
            }

            TablaSimbolos scopeIter = new TablaSimbolos(scopeWhile);

            for (Stmt s : bloque) {
                ControlStmt c = s.ejecutar(scopeIter);
                if (c == null) {
                    continue;
                }
                if (c.getTipo() == ControlStmt.Tipo.BREAK) {
                    return ControlStmt.normal();
                }

                if (c.getTipo() == ControlStmt.Tipo.CONTINUE) {
                    break;
                }

                if (c.getTipo() == ControlStmt.Tipo.RETURN) {
                    return c;
                }

            }

        }
        return ControlStmt.normal();

    }

}
