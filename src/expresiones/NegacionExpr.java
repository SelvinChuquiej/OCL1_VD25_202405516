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
public class NegacionExpr extends Expr {

    private Expr expresion;

    public NegacionExpr(Expr expresion, int linea, int columna) {
        super(linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Resultado res = expresion.evaluar(tabla);
        if (res.getTipo() == TipoDato.ERROR) {
            return res;
        }

        if (res.getTipo() == TipoDato.ENTERO) {
            int valor = (int) res.getValor();
            return new Resultado(TipoDato.ENTERO, -valor);
        } else if (res.getTipo() == TipoDato.DECIMAL) {
            double valor = (double) res.getValor();
            return new Resultado(TipoDato.DECIMAL, -valor);
        }

        ManejadorErrores.agregar("Semantico", "No se puede aplicar negacion aritmentica a un valor de tipo: " + res.getTipo(), linea, columna);
        return new Resultado(TipoDato.ERROR, null);
    }

}
