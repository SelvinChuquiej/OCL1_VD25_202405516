/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
import java.util.ArrayList;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class Vector2DDeclStmt extends Stmt {

    private String id;
    private TipoDato tipoBase;
    private List<List<Expr>> valores;

    public Vector2DDeclStmt(String id, TipoDato tipoBase, List<List<Expr>> valores, int line, int column) {
        super(line, column);
        this.id = id;
        this.tipoBase = tipoBase;
        this.valores = valores;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        List<List<Object>> matrizFinal = new ArrayList<>();

        for (List<Expr> filaExpr : valores) {
            List<Object> filaObjetos = new ArrayList<>();

            for (Expr exp : filaExpr) {
                Resultado res = exp.evaluar(tabla);

                if (res.getTipo() != tipoBase && res.getTipo() != TipoDato.ERROR) {
                    ManejadorErrores.agregar("Semántico",
                            "Tipo incompatible en matriz '" + id + "'. Se esperaba " + tipoBase + " pero se encontró " + res.getTipo(),
                            linea, columna);
                }

                filaObjetos.add(res.getValor());
            }
            matrizFinal.add(filaObjetos);
        }

        boolean exito = tabla.insertar(id, tipoBase, matrizFinal, linea, columna);

        if (!exito) {
            ManejadorErrores.agregar("Semántico", "La variable '" + id + "' ya ha sido declarada en este ámbito", linea, columna);
        }

        return null;
    }
}
