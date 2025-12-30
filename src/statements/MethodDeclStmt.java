/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Param;
import AST.Stmt;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class MethodDeclStmt extends Stmt {

    public TipoDato tipoRetorno;
    public String id;
    public List<Param> parametros;
    public List<Stmt> instrucciones;

    public MethodDeclStmt(TipoDato tipoRetorno, String id, List<Param> parametros, List<Stmt> instrucciones, int line, int column) {
        super(line, column);
        this.tipoRetorno = tipoRetorno;
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    public TipoDato getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(TipoDato tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        TablaSimbolos global = tabla;
        while (global.getTablaAnterior() != null) {
            global = global.getTablaAnterior();
        }
        global.guardarFuncion(this.id, this);
        return null;
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoMethodDecl" + this.hashCode();
        String label = "METODO: " + this.id + "\\nTipo: " + this.tipoRetorno;
        dot.append(nombreNodo).append("[label=\"").append(label).append("\", fillcolor=\"#ccffcc\", style=filled];\n");

        // 1. Graficar Parámetros
        if (parametros != null && !parametros.isEmpty()) {
            String nodoParams = "nodoParams" + this.hashCode();
            dot.append(nodoParams).append("[label=\"PARAMETROS\", shape=diamond];\n");
            dot.append(nombreNodo).append(" -> ").append(nodoParams).append(";\n");

            for (Param p : parametros) {
                String nodoP = "nodoP" + this.hashCode();
                dot.append(nodoP).append("[label=\"").append(p.id).append(" (").append(p.tipo).append(")\"];\n");
                dot.append(nodoParams).append(" -> ").append(nodoP).append(";\n");
            }
        }

        // 2. Graficar Instrucciones (Cuerpo)
        if (instrucciones != null && !instrucciones.isEmpty()) {
            String nodoCuerpo = "nodoCuerpoMetodo" + this.hashCode();
            dot.append(nodoCuerpo).append("[label=\"INSTRUCCIONES\", shape=folder];\n");
            dot.append(nombreNodo).append(" -> ").append(nodoCuerpo).append(";\n");

            for (Stmt s : instrucciones) {
                dot.append(nodoCuerpo).append(" -> ").append(s.getDot(dot)).append(";\n");
            }
        }

        return nombreNodo;
    }
}
