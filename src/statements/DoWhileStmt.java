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

        String nombreEntorno = "DoWhile_" + this.linea;
        TablaSimbolos tablaLoop = new TablaSimbolos(tabla, nombreEntorno);

        do {
            TablaSimbolos tablaIter = new TablaSimbolos(tablaLoop, nombreEntorno + "_iter");
            for (Stmt s : bloque) {

                ControlStmt res = s.ejecutar(tablaIter);

                if (res.getTipo() != ControlStmt.Tipo.NORMAL) {
                    if (res.getTipo() == ControlStmt.Tipo.BREAK) {
                        return ControlStmt.normal();
                    }
                    if (res.getTipo() == ControlStmt.Tipo.CONTINUE) {
                        break;
                    }
                    if (res.getTipo() == ControlStmt.Tipo.RETURN) {
                        return res;
                    }
                }
            }
            Resultado resCond = condicion.evaluar(tablaLoop);

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
    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoDoWhile" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"DO WHILE\"];\n");

        // Nodo intermedio para el cuerpo del bucle
        String nodoCuerpo = "nodoCuerpoDo" + this.hashCode();
        dot.append(nodoCuerpo).append("[label=\"CUERPO\", shape=point];\n");
        dot.append(nombreNodo).append(" -> ").append(nodoCuerpo).append(";\n");

        for (Stmt s : bloque) {
            dot.append(nodoCuerpo).append(" -> ").append(s.getDot(dot)).append(";\n");
        }

        // Condición al final
        dot.append(nombreNodo).append(" -> ").append(condicion.getDot(dot)).append("[label=\"condicion\"];\n");

        return nombreNodo;
    }
}
