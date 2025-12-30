/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;
import tokens.ReporteTabla;
import errores.Error.TipoError;

/**
 *
 * @author Selvi
 */
public class AsigStmt extends Stmt {

    private String id;
    private Expr expresion;

    public AsigStmt(String id, Expr expresion, int line, int column) {
        super(line, column);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Simbolo simbolo = tabla.obtenerVariable(this.id);

        if (simbolo == null) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "La variable " + id + " no ha sido declarada", linea, columna);
            return ControlStmt.normal();
        }
        Resultado res = expresion.evaluar(tabla);
        if (res.getTipo() == TipoDato.ERROR) {
            return ControlStmt.normal();
        }
        if (simbolo.getTipo() != res.getTipo()) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Tipos imcompatible, no puedes asignar " + res.getTipo() + " a la variable "
                    + id + " de tipo " + simbolo.getTipo(), linea, columna);
            return ControlStmt.normal();
        }
        simbolo.setValor(res.getValor());
        ReporteTabla.agregar(simbolo);
        return ControlStmt.normal();
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoAsig" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"ASIGNACION: ").append(this.id).append("\"];\n");

        // Conectamos con el nodo de la expresión que se está asignando
        dot.append(nombreNodo).append(" -> ").append(expresion.getDot(dot)).append(";\n");

        return nombreNodo;
    }
}
