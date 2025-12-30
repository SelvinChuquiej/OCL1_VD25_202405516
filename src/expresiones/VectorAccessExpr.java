/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import errores.ManejadorErrores;
import java.util.List;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class VectorAccessExpr extends Expr {

    private String id;
    private Expr indice;

    public VectorAccessExpr(String id, Expr indice, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.indice = indice;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Simbolo sim = tabla.obtenerVariable(id);
        if (sim == null) {
            ManejadorErrores.agregar("Semántico", "El vector '" + id + "' no existe", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        Resultado resIdx = indice.evaluar(tabla);
        if (resIdx.getTipo() != TipoDato.ENTERO) {
            ManejadorErrores.agregar("Semántico", "El índice del vector debe ser de tipo entero", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        int idx = (int) resIdx.getValor();

        try {
            List<Object> lista = (List<Object>) sim.getValor();

            if (idx < 0 || idx >= lista.size()) {
                ManejadorErrores.agregar("Semántico", "Índice " + idx + " fuera de rango para el vector '" + id + "'", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }

            Object valorElemento = lista.get(idx);
            TipoDato tipoRetorno = sim.getTipo();

            if (sim.getTipo() == TipoDato.LISTA) {
                if (valorElemento instanceof Integer) {
                    tipoRetorno = TipoDato.ENTERO;
                } else if (valorElemento instanceof Double) {
                    tipoRetorno = TipoDato.DECIMAL;
                } else if (valorElemento instanceof String) {
                    tipoRetorno = TipoDato.CADENA;
                } else if (valorElemento instanceof Boolean) {
                    tipoRetorno = TipoDato.BOOLEANO;
                } else if (valorElemento instanceof Character) {
                    tipoRetorno = TipoDato.CARACTER;
                }
            } else {
                tipoRetorno = sim.getTipo();
            }

            return new Resultado(tipoRetorno, valorElemento);

        } catch (Exception e) {
            ManejadorErrores.agregar("Semántico", "Error al acceder al vector: " + e.getMessage(), linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
    }
}
