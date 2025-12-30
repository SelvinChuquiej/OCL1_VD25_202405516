/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
import java.util.List;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class MethodCallStmt extends Expr {

    private String idLista;
    private String metodo;
    private List<Expr> argumentos;

    public MethodCallStmt(String idLista, String metodo, List<Expr> argumentos, int line, int column) {
        super(line, column);
        this.idLista = idLista;
        this.metodo = metodo;
        this.argumentos = argumentos;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Simbolo simb = tabla.obtenerVariable(idLista);

        if (simb == null) {
            ManejadorErrores.agregar("Semántico", "La variable '" + idLista + "' no existe", linea, columna);
            return null;
        }

        Object valorEstructura = simb.getValor();

        switch (metodo.toLowerCase()) {
            case "find":
                return ejecutarFind(valorEstructura, tabla);
            case "append":
                ejecutarAdd(valorEstructura, tabla);
                return new Resultado(TipoDato.VOID, null);
            case "remove":
                return ejecutarRemove(valorEstructura, tabla);
        }
        return new Resultado(TipoDato.VOID, null);
    }

    private Resultado ejecutarFind(Object estructura, TablaSimbolos tabla) {
        Resultado buscado = argumentos.get(0).evaluar(tabla);
        boolean encontrado = false;

        if (estructura instanceof List) {
            encontrado = ((List) estructura).contains(buscado.getValor());
        } else if (estructura instanceof Object[]) {
            for (Object item : (Object[]) estructura) {
                if (item.equals(buscado.getValor())) {
                    encontrado = true;
                    break;
                }
            }
        }
        return new Resultado(TipoDato.BOOLEANO, encontrado);
    }

    private void ejecutarAdd(Object estructura, TablaSimbolos tabla) {
        if (estructura instanceof List) {
            Resultado res = argumentos.get(0).evaluar(tabla);
            ((List<Object>) estructura).add(res.getValor());
        } else {
            ManejadorErrores.agregar("Semántico", "Solo se puede usar .add() en listas", linea, columna);
        }
    }

    private Resultado ejecutarRemove(Object estructura, TablaSimbolos tabla) {
        if (estructura instanceof List) {
            Resultado res = argumentos.get(0).evaluar(tabla);
            int indice = (int) Double.parseDouble(res.getValor().toString());
            List<Object> lista = (List<Object>) estructura;

            if (indice >= 0 && indice < lista.size()) {
                Object eliminado = lista.remove(indice);
                return new Resultado(TipoDato.ENTERO, eliminado);
            } else {
                // Error si el índice está fuera de rango en lugar de devolver VOID silenciosamente
                ManejadorErrores.agregar("Semántico", "Índice fuera de rango: " + indice, linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
        }
        return new Resultado(TipoDato.ERROR, null);
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        this.evaluar(tabla);
        return ControlStmt.normal();
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoMethodCall" + this.hashCode();
        // Ejemplo: lista.add(valor)
        String etiqueta = "METODO: " + this.idLista + "." + this.metodo;
        dot.append(nombreNodo).append("[label=\"").append(etiqueta).append("\"];\n");

        // Graficamos los argumentos
        if (argumentos != null && !argumentos.isEmpty()) {
            for (Expr arg : argumentos) {
                dot.append(nombreNodo).append(" -> ").append(arg.getDot(dot)).append("[label=\"arg\"];\n");
            }
        }
        return nombreNodo;
    }
}
