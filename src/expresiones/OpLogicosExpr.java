/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import errores.ManejadorErrores;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class OpLogicosExpr extends Expr {

    public enum OpLog {
        AND, OR, NOT
    }

    private Expr izq;
    private Expr der;
    private OpLog op;

    public OpLogicosExpr(Expr izq, Expr der, OpLog op, int linea, int columna) {
        super(linea, columna);
        this.izq = izq;
        this.der = der;
        this.op = op;
    }

    public OpLogicosExpr(Expr izq, OpLog op, int linea, int columna) {
        super(linea, columna);
        this.izq = izq;
        this.der = null;
        this.op = op;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Resultado r1 = izq.evaluar(tabla);
        Object v1 = r1.getValor();
        TipoDato t1 = r1.getTipo();

        if (op == OpLog.NOT) {
            if (t1 != TipoDato.BOOLEANO) {
                ManejadorErrores.agregar("Semantico", "El operador NOT solo puede aplicarse a booleanos", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            return new Resultado(TipoDato.BOOLEANO, !((boolean) v1));
        }

        Resultado r2 = der.evaluar(tabla);
        Object v2 = r2.getValor();
        TipoDato t2 = r2.getTipo();

        if (t1 != TipoDato.BOOLEANO || t2 != TipoDato.BOOLEANO) {
            ManejadorErrores.agregar("Semantico", "Los operadores logicos requieren operadores booleanos", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        boolean b1 = (boolean) v1;
        boolean b2 = (boolean) v2;

        switch (op) {
            case AND:
                return new Resultado(TipoDato.BOOLEANO, b1 && b2);
            case OR:
                return new Resultado(TipoDato.BOOLEANO, b1 || b2);
            default:
                ManejadorErrores.agregar("Semantico", "Operador logico desconocido " + op, linea, columna);
                return new Resultado(TipoDato.ERROR, null);
        }
    }

}
