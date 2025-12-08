/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
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
            throw new RuntimeException("Error semantico: No se puede castear " + tipoOrigen + " a " + tipoDestino);
        }
        return realizarCasteo(valor, tipoOrigen, tipoDestino);

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
                char c = (char) valor;
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

        throw new RuntimeException("Casteo no implementado");
    }
    /*try {

            switch (tipoDestino) {
                case ENTERO:
                    if (tipoOrigen == TipoDato.DECIMAL) {
                        double val = Double.parseDouble(valor.toString());
                        return new Resultado(TipoDato.ENTERO, (int) val);
                    }
                    if (tipoOrigen == TipoDato.CARACTER) {
                        char ch = (char) valor;
                        return new Resultado(TipoDato.ENTERO, (int) valor);
                    }
                    break;
                case DECIMAL:
                    if (tipoOrigen == TipoDato.ENTERO) {
                        int val = Integer.parseInt(valor.toString());
                        return new Resultado(TipoDato.DECIMAL, (double) val);
                    }
                    if (tipoOrigen == TipoDato.CARACTER) {
                        char ch = (char) valor;
                        return new Resultado(TipoDato.DECIMAL, (double) ch);
                    }
                    break;
                case CADENA:
                    if (tipoOrigen == TipoDato.ENTERO) {
                        return new Resultado(TipoDato.CADENA, valor.toString());
                    }
                    if (tipoOrigen == TipoDato.CARACTER) {
                        return new Resultado(TipoDato.CADENA, valor.toString());
                    }
                    break;
                case CARACTER:
                    if (tipoOrigen == TipoDato.DECIMAL) {
                        int val = Integer.parseInt(valor.toString());
                        return new Resultado(TipoDato.CARACTER, (char) val);
                    }
                    break;
            }
        } catch (Exception e) {
        }*/

}
