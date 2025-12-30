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


    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoCase" + this.hashCode();
        String etiqueta = (valor == null) ? "DEFAULT" : "CASE";

        dot.append(nombreNodo).append("[label=\"").append(etiqueta).append("\"];\n");

        // Si es un CASE, graficar el valor a comparar
        if (valor != null) {
            dot.append(nombreNodo).append(" -> ").append(valor.getDot(dot)).append("[label=\"valor\"];\n");
        }

        // Graficar el bloque de instrucciones dentro del caso
        if (bloque != null && !bloque.isEmpty()) {
            String nodoCuerpo = "nodoCuerpoCase" + this.hashCode();
            dot.append(nodoCuerpo).append("[label=\"CUERPO\", shape=circle];\n");
            dot.append(nombreNodo).append(" -> ").append(nodoCuerpo).append(";\n");

            for (Stmt s : bloque) {
                dot.append(nodoCuerpo).append(" -> ").append(s.getDot(dot)).append(";\n");
            }
        }

        return nombreNodo;
    }
}
