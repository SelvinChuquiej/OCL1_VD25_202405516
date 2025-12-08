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
public class OpRelacionalesExpr extends Expr {

    public enum OpRel {
        IGUAL_IGUAL, DIFERENTE, MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL
    }

    private Expr izq;
    private Expr der;
    private OpRel op;

    public OpRelacionalesExpr(Expr izq, Expr der, OpRel op, int linea, int columna) {
        super(linea, columna);
        this.izq = izq;
        this.der = der;
        this.op = op;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Resultado r1 = izq.evaluar(tabla);
        Resultado r2 = der.evaluar(tabla);

        Object v1 = r1.getValor();
        Object v2 = r2.getValor();

        TipoDato t1 = r1.getTipo();
        TipoDato t2 = r2.getTipo();

        //  Solo para Strings (== y !=)
        if (t1 == TipoDato.CADENA && t2 == TipoDato.CADENA) {
            String s1 = v1.toString();
            String s2 = v2.toString();
            switch (op) {
                case IGUAL_IGUAL:
                    return new Resultado(TipoDato.BOOLEANO, s1.equals(s2));
                case DIFERENTE:
                    return new Resultado(TipoDato.BOOLEANO, !s1.equals(s2));
                default:
                    ManejadorErrores.agregar("Semantico", "No se puede aplicar el operador relacional sobre cadenas", linea, columna);
                    return new Resultado(TipoDato.ERROR, null);
            }
        }

        // Solo para numeros
        if (esNumerico(t1) && esNumerico(t2)) {
            double d1 = aDouble(v1, t1);
            double d2 = aDouble(v2, t2);

            boolean res = false;
            switch (op) {
                case IGUAL_IGUAL:
                    res = d1 == d2;
                    break;
                case DIFERENTE:
                    res = d1 != d2;
                    break;
                case MENOR:
                    res = d1 < d2;
                    break;
                case MENOR_IGUAL:
                    res = d1 <= d2;
                    break;
                case MAYOR:
                    res = d1 > d2;
                    break;
                case MAYOR_IGUAL:
                    res = d1 >= d2;
                    break;
                default:
                    ManejadorErrores.agregar("Semantico", "Operador realacional desconocido", linea, columna);
                    return new Resultado(TipoDato.ERROR, null);
            }
            return new Resultado(TipoDato.BOOLEANO, res);
        }

        if (t1 == TipoDato.BOOLEANO && t2 == TipoDato.BOOLEANO) {
            boolean b1 = (boolean) v1;
            boolean b2 = (boolean) v2;
            switch (op) {
                case IGUAL_IGUAL:
                    return new Resultado(TipoDato.BOOLEANO, b1 == b2);
                case DIFERENTE:
                    return new Resultado(TipoDato.BOOLEANO, b1 != b2);
                default:
                    // Si intentan hacer true > false, cae aquí
                    ManejadorErrores.agregar("Semantico", "No se pueden aplicar operadores de orden (<, >, <=, >=) a valores booleanos", linea, columna);
                    return new Resultado(TipoDato.ERROR, null);
            }
        }

        ManejadorErrores.agregar("Semantico", "Tipos imcompatibles para comparar", linea, columna);
        return new Resultado(TipoDato.ERROR, null);

    }

    private boolean esNumerico(TipoDato tipo) {
        return tipo == TipoDato.ENTERO || tipo == TipoDato.DECIMAL || tipo == TipoDato.CARACTER;
    }

    private double aDouble(Object valor, TipoDato tipo) {
        switch (tipo) {
            case ENTERO:
                return ((Integer) valor).doubleValue();
            case DECIMAL:
                return (Double) valor;
            case CARACTER:
                return (double) ((Character) valor).charValue();
            default:
                ManejadorErrores.agregar("Semantico", "No se pueden convertir el tipo " + tipo + "a numero para comparar", linea, columna);
                return 0;
        }
    }

}
