/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.Error.TipoError;
import errores.ManejadorErrores;
import java.util.List;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;

/**
 *
 * @author Selvi
 */
public class Vector2DAsigStmt extends Stmt {

    private String id;
    private Expr indiceF;
    private Expr indiceC;
    private Expr expresion;

    public Vector2DAsigStmt(String id, Expr indiceF, Expr indiceC, Expr expresion, int line, int column) {
        super(line, column);
        this.id = id;
        this.indiceF = indiceF;
        this.indiceC = indiceC;
        this.expresion = expresion;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Simbolo simbolo = tabla.obtenerVariable(this.id);
        if (simbolo == null) {
            return ControlStmt.normal();
        }

        Resultado resF = indiceF.evaluar(tabla);
        Resultado resC = indiceC.evaluar(tabla);
        Resultado resVal = expresion.evaluar(tabla);

        try {
            List<List<Object>> matriz = (List<List<Object>>) simbolo.getValor();
            int f = (int) resF.getValor();
            int c = (int) resC.getValor();

            matriz.get(f).set(c, resVal.getValor());

        } catch (Exception e) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Error de límites en matriz", linea, columna);
        }

        return ControlStmt.normal();
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoVec2DAsig" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"ASIG. MATRIZ: " + id + "\", shape=box, color=darkgreen];\n");

        // Conexión al índice de la fila
        dot.append(nombreNodo).append(" -> ").append(indiceF.getDot(dot)).append(" [label=\"fila\"];\n");

        // Conexión al índice de la columna
        dot.append(nombreNodo).append(" -> ").append(indiceC.getDot(dot)).append(" [label=\"columna\"];\n");

        // Conexión a la expresión (el valor a asignar)
        dot.append(nombreNodo).append(" -> ").append(expresion.getDot(dot)).append(" [label=\"valor\"];\n");

        return nombreNodo;
    }
}
