/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.Error.TipoError;
import errores.ManejadorErrores;
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
        String nombreEntorno = "For_" + this.linea;
        TablaSimbolos tablaLocal = new TablaSimbolos(tabla, nombreEntorno);
        inicializacion.ejecutar(tablaLocal);

        while (true) {
            Resultado resCond = condicion.evaluar(tablaLocal);
            if (resCond.getTipo() == TipoDato.ERROR) {
                return ControlStmt.normal();
            }
            if (!(resCond.getValor() instanceof Boolean)) {
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "La condicion del For debe ser booleana", this.linea, this.columna);
                return ControlStmt.normal();
            }
            if (!(boolean) resCond.getValor()) {
                break;
            }

            TablaSimbolos tablaIter = new TablaSimbolos(tablaLocal, nombreEntorno + "_iter");
            boolean rompePorContinue = false;

            for (Stmt s : bloque) {
                ControlStmt res = s.ejecutar(tablaIter);
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

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoFor" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"FOR\"];\n");

        // 1. Inicialización
        dot.append(nombreNodo).append(" -> ").append(inicializacion.getDot(dot)).append("[label=\"init\"];\n");

        // 2. Condición
        dot.append(nombreNodo).append(" -> ").append(condicion.getDot(dot)).append("[label=\"cond\"];\n");

        // 3. Actualización
        dot.append(nombreNodo).append(" -> ").append(actualizacion.getDot(dot)).append("[label=\"update\"];\n");

        // 4. Cuerpo del For
        String nodoCuerpo = "nodoCuerpoFor" + this.hashCode();
        dot.append(nodoCuerpo).append("[label=\"BLOQUE\", shape=circle];\n");
        dot.append(nombreNodo).append(" -> ").append(nodoCuerpo).append(";\n");
        for (Stmt s : bloque) {
            dot.append(nodoCuerpo).append(" -> ").append(s.getDot(dot)).append(";\n");
        }

        return nombreNodo;
    }
}
