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
public class VectorDeclStmt extends Stmt {

    private String id;
    private TipoDato tipoBase;
    private List<Expr> valoresIniciales;

    public VectorDeclStmt(String id, TipoDato tipoBase, List<Expr> valoresIniciales, int line, int column) {
        super(line, column);
        this.id = id;
        this.tipoBase = tipoBase;
        this.valoresIniciales = valoresIniciales;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        List<Object> vectorFinal = new ArrayList<>();
        for (Expr e : valoresIniciales) {
            Resultado res = e.evaluar(tabla);
            if (res.getTipo() != tipoBase) {
                ManejadorErrores.agregar("Semántico", "Tipo incompatible en vector", linea, columna);
            }
            vectorFinal.add(res.getValor());
        }
        tabla.insertar(id, tipoBase, vectorFinal, linea, columna);
        return null;
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoVecDecl" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"VECTOR: " + id + "\\nTipo: " + tipoBase + "\", shape=folder];\n");

        for (Expr e : valoresIniciales) {
            dot.append(nombreNodo).append(" -> ").append(e.getDot(dot)).append(";\n");
        }
        return nombreNodo;
    }

}
