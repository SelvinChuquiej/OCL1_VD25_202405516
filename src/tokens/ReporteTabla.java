/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokens;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import simbolo.Simbolo;

/**
 *
 * @author Selvi
 */
public class ReporteTabla {

    public static ArrayList<Simbolo> listaSimbolos = new ArrayList<>();

    public static void limpiar() {
        listaSimbolos.clear();
    }

    public static void agregar(Simbolo s) {
        for (Simbolo existente : listaSimbolos) {
            if (existente.getId().equals(s.getId()) && existente.getEntorno().equals(s.getEntorno())) {
                existente.setValor(s.getValor());
                return;
            }
        }
        listaSimbolos.add(s);
    }

    public static void generarReporte() {
        StringBuilder html = new StringBuilder();

        html.append("<html><head><title>Reporte de Símbolos</title></head>");
        html.append("<style>table, th, td {border: 1px solid black; border-collapse: collapse; padding: 5px;}</style>");

        html.append("<body><h1>Tabla de Símbolos</h1>");
        html.append("<table>");
        html.append("<tr style='background-color: #f2f2f2;'>");
        html.append("<th>#</th>");
        html.append("<th>Id</th>");
        html.append("<th>Tipo</th>");      
        html.append("<th>Tipo Dato</th>"); 
        html.append("<th>Entorno</th>");
        html.append("<th>Valor</th>");
        html.append("<th>Línea</th>");
        html.append("<th>Columna</th>");
        html.append("</tr>");

        int contador = 1;
        for (Simbolo s : listaSimbolos) {
            String valorStr = (s.getValor() != null) ? s.getValor().toString() : "null";
            valorStr = valorStr.replace("<", "&lt;").replace(">", "&gt;");

            html.append("<tr>");
            html.append("<td>").append(contador++).append("</td>");
            html.append("<td><b>").append(s.getId()).append("</b></td>");
            html.append("<td>Variable</td>");
            html.append("<td>").append(s.getTipo()).append("</td>");
            html.append("<td>").append(s.getEntorno()).append("</td>");
            html.append("<td>").append(valorStr).append("</td>");
            html.append("<td>").append(s.getLinea()).append("</td>");
            html.append("<td>").append(s.getColumna()).append("</td>");
            html.append("</tr>");
        }

        html.append("</table></body></html>");

        try {
            java.io.FileWriter writer = new FileWriter("ReporteSimbolos.html");
            writer.write(html.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
