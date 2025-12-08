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
import util.Consola;

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
    public Resultado ejecutar(TablaSimbolos tabla) {
        Simbolo simbolo = tabla.obtenerVariable(this.id);

        if (simbolo == null) {
            ManejadorErrores.agregar("Semantico", "La variable +" + id + " no ha sido declarada", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
        Resultado res = expresion.evaluar(tabla);
        if (res.getTipo() == TipoDato.ERROR) {
            return res;
        }
        if (simbolo.getTipo() != res.getTipo()) {
            ManejadorErrores.agregar("Semantico", "Tipos imcompatible, no puedes asignar " + res.getTipo() + " a la variable "
                    + id + " de tipo " + simbolo.getTipo(), linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
        simbolo.setValor(res.getValor());
        return null;
    }
}
