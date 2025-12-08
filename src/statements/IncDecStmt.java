/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class IncDecStmt extends Stmt {

    public enum OpIncDec {
        INCREMENTO, DECREMENTO
    }

    private String id;
    private OpIncDec op;

    public IncDecStmt(String id, OpIncDec op, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.op = op;
    }

    @Override
    public Resultado ejecutar(TablaSimbolos tabla) {
        Simbolo sim = tabla.obtenerVariable(id);
        if (sim == null) {
            throw new RuntimeException("Error semantico");
        }
        Object valorActual = sim.getValor();
        TipoDato tipo = sim.getTipo();

        if (tipo != TipoDato.ENTERO && tipo != TipoDato.DECIMAL) {
            throw new RuntimeException("Error semantico");
        }

        Object nuevoValor = null;
        if (tipo == TipoDato.ENTERO) {
            int v = (int) valorActual;
            if (op == OpIncDec.INCREMENTO) {
                v = v + 1;
            } else {
                v = v - 1;
            }
            nuevoValor = v;
        } else if (tipo == TipoDato.DECIMAL) {
            double v = (double) valorActual;
            if (op == OpIncDec.INCREMENTO) {
                v = v + 1.0;
            } else {
                v = v - 1.0;
            }
            nuevoValor = v;
        }
        sim.setValor(nuevoValor);
        return new Resultado(tipo, nuevoValor);
    }
}
