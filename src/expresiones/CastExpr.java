/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import errores.Error.TipoError;
import errores.ManejadorErrores;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;
import static simbolo.TipoDato.CARACTER;
import static simbolo.TipoDato.DECIMAL;

/**
 *
 * @author Selvi
 */
public class CastExpr extends Expr {

    private TipoDato tipoDestino;
    private Expr expresion;

    public CastExpr(TipoDato tipoDestino, Expr expresion, int linea, int columna) {
        super(linea, columna);
        this.tipoDestino = tipoDestino;
        this.expresion = expresion;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Resultado res = expresion.evaluar(tabla);
        TipoDato tipoOrigen = res.getTipo();
        Object valor = res.getValor();

        if (!esCasteoPermitido(tipoOrigen, tipoDestino)) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se castear de " + tipoOrigen + " a " + tipoDestino, linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
        return realizarCasteo(valor, tipoOrigen, tipoDestino);

    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoCast" + this.hashCode();
        String etiqueta = "CAST (" + tipoDestino + ")";
        dot.append(nombreNodo).append("[label=\"").append(etiqueta).append("\"];\n");

        String hijo = expresion.getDot(dot);
        dot.append(nombreNodo).append(" -> ").append(hijo).append(";\n");

        return nombreNodo;

    }

    private boolean esCasteoPermitido(TipoDato origen, TipoDato destino) {
        return (origen == TipoDato.ENTERO && destino == TipoDato.DECIMAL)
                || (origen == TipoDato.ENTERO && destino == TipoDato.CADENA)
                || (origen == TipoDato.ENTERO && destino == TipoDato.CARACTER)
                || (origen == TipoDato.DECIMAL && destino == TipoDato.ENTERO)
                || (origen == TipoDato.DECIMAL && destino == TipoDato.CADENA)
                || (origen == TipoDato.CARACTER && destino == TipoDato.ENTERO)
                || (origen == TipoDato.CARACTER && destino == TipoDato.DECIMAL);
    }

    private Resultado realizarCasteo(Object valor, TipoDato origen, TipoDato destino) {
        if (destino == TipoDato.ENTERO) {
            if (origen == TipoDato.DECIMAL) {
                double d = (double) valor;
                return new Resultado(TipoDato.ENTERO, (int) d);
            } else if (origen == TipoDato.CARACTER) {
                char c = (char) valor;
                return new Resultado(TipoDato.ENTERO, (int) c);
            }
        }

        if (destino == TipoDato.DECIMAL) {
            if (origen == TipoDato.ENTERO) {
                int i = (int) valor;
                return new Resultado(TipoDato.DECIMAL, (double) i);
            } else if (origen == TipoDato.CARACTER) {
                char c = ((Character) valor).charValue();
                return new Resultado(TipoDato.DECIMAL, (double) c);
            }
        }

        if (destino == TipoDato.CADENA) {
            return new Resultado(TipoDato.CADENA, valor.toString());
        }

        if (destino == TipoDato.CARACTER && origen == TipoDato.ENTERO) {
            int i = (int) valor;
            return new Resultado(TipoDato.CARACTER, (char) i);
        }

        ManejadorErrores.agregar("Semantico", "Casteo no implementado de " + origen + " a " + destino, linea, columna);
        return new Resultado(TipoDato.ERROR, null);
    }

}
