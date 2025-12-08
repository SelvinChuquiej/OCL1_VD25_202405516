/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errores;

import java.util.ArrayList;

/**
 *
 * @author Selvi
 */
public class ManejadorErrores {

    private static ArrayList<Error> errores = new ArrayList<>();

    public static void limpiar() {
        errores.clear();
    }

    public static void agregar(String tipo, String descripcion, int linea, int columna) {
        errores.add(new Error(tipo, descripcion, linea, columna));
        System.err.println("ERROR AGREGADO: " + tipo + " -> " + descripcion);
    }

    public static ArrayList<Error> getErrores() {
        return errores;
    }

    public static boolean hayErrores() {
        return !errores.isEmpty();
    }

    public static void generarReporteHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Reporte de Errores</title></head>");
        html.append("<style>table, th, td {border: 1px solid black; border-collapse: collapse; padding: 5px;}</style>");
        html.append("<body><h1>Reporte de Errores</h1>");
        html.append("<table>");
        html.append("<tr style='background-color: #f2f2f2;'><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr>");

        for (Error err : errores) {
            html.append("<tr>");
            html.append("<td>").append(err.getTipo()).append("</td>");
            html.append("<td>").append(err.getDescripcion()).append("</td>");
            html.append("<td>").append(err.getLinea()).append("</td>");
            html.append("<td>").append(err.getColumna()).append("</td>");
            html.append("</tr>");
        }

        html.append("</table></body></html>");

        try {
            java.io.FileWriter writer = new java.io.FileWriter("ReporteErrores.html");
            writer.write(html.toString());
            writer.close();
            System.out.println("Reporte de errores generado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
