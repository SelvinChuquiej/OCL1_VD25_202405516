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
public class Vector2DAccessExpr extends Expr {

    private String id;
    private Expr indiceFila;
    private Expr indiceCol;

    public Vector2DAccessExpr(String id, Expr indiceFila, Expr indiceCol, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.indiceFila = indiceFila;
        this.indiceCol = indiceCol;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Simbolo sim = tabla.obtenerVariable(id);
        if (sim == null) {
            ManejadorErrores.agregar("Semántico", "La matriz '" + id + "' no existe", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        Resultado resF = indiceFila.evaluar(tabla);
        Resultado resC = indiceCol.evaluar(tabla);

        if (resF.getTipo() != TipoDato.ENTERO || resC.getTipo() != TipoDato.ENTERO) {
            ManejadorErrores.agregar("Semántico", "Los índices de la matriz deben ser enteros", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        int f = (int) resF.getValor();
        int c = (int) resC.getValor();

        try {
            List<List<Object>> matriz = (List<List<Object>>) sim.getValor();

            if (f < 0 || f >= matriz.size()) {
                ManejadorErrores.agregar("Semántico", "Índice de fila " + f + " fuera de rango", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }

            List<Object> fila = matriz.get(f);

            if (c < 0 || c >= fila.size()) {
                ManejadorErrores.agregar("Semántico", "Índice de columna " + c + " fuera de rango", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }

            return new Resultado(sim.getTipo(), fila.get(c));

        } catch (Exception e) {
            ManejadorErrores.agregar("Semántico", "Error de acceso en matriz: " + e.getMessage(), linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
    }


    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoMatAcc" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"ACCESO MATRIZ: ").append(id).append("\"];\n");
        dot.append(nombreNodo).append(" -> ").append(indiceFila.getDot(dot)).append("[label=\"Fila\"];\n");
        dot.append(nombreNodo).append(" -> ").append(indiceCol.getDot(dot)).append("[label=\"Columna\"];\n");
        return nombreNodo;
    }
}
