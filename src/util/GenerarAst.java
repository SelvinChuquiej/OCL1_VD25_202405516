/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import AST.NodoAST;
import AST.RaizAST;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Selvi
 */
public class GenerarAst {

    public static void generar(RaizAST raiz) {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");
        dot.append("  node [shape=box, style=filled, fillcolor=white];\n");

        if (raiz != null) {
            raiz.getDot(dot);
        }

        dot.append("}\n");

        // Escribir el archivo .dot
        try (PrintWriter pw = new PrintWriter(new FileWriter("ast.dot"))) {
            pw.print(dot.toString());
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo DOT: " + e.getMessage());
        }

        // OPCIONAL: Intentar generar el PNG automáticamente si tienes Graphviz instalado
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "-o", "ast.png", "ast.dot");
            pb.redirectErrorStream(true);
            pb.start();
        } catch (Exception e) {
            System.err.println("No se pudo generar el PNG. Asegúrate de tener Graphviz instalado.");
        }
    }
}
