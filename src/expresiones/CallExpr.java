/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Param;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
import errores.ReturnException;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;
import statements.MethodDeclStmt;

/**
 *
 * @author Selvi
 */
public class CallExpr extends Expr {

    private String id;
    private List<Expr> argumentos;

    public CallExpr(String id, List<Expr> argumentos, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.argumentos = argumentos;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {

        if (this.id.equalsIgnoreCase("length")) {
            if (argumentos.size() != 1) {
                ManejadorErrores.agregar("Semántico", "La función length espera exactamente 1 argumento", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            Resultado resArg = argumentos.get(0).evaluar(tabla);
            Object valor = resArg.getValor();

            if (valor instanceof List) {
                return new Resultado(TipoDato.ENTERO, ((List<Object>) valor).size());
            } else if (valor != null && valor.getClass().isArray()) {
                // Esto captura int[], double[], Object[], etc.
                return new Resultado(TipoDato.ENTERO, java.lang.reflect.Array.getLength(valor));
            } else if (valor instanceof String) {
                return new Resultado(TipoDato.ENTERO, ((String) valor).length());
            } else {
                ManejadorErrores.agregar("Semántico", "La función length solo se aplica a vectores, listas o cadenas", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
        } else if (this.id.equalsIgnoreCase("round")) {
            if (argumentos.size() != 1) {
                ManejadorErrores.agregar("Semantico", "round espera 1 argumento", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            Resultado res = argumentos.get(0).evaluar(tabla);
            if (res.getTipo() == TipoDato.DECIMAL) {
                double val = (double) res.getValor();
                return new Resultado(TipoDato.ENTERO, (int) Math.round(val));
            } else if (res.getTipo() == TipoDato.ENTERO) {
                return res;
            } else {
                ManejadorErrores.agregar("Semantico", "round solo acepta numeros", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }

        } else if (this.id.equalsIgnoreCase("typeof")) {
            if (argumentos.size() != 1) {
                ManejadorErrores.agregar("Semantico", "typeof espera 1 argumento", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            Resultado res = argumentos.get(0).evaluar(tabla);
            return new Resultado(TipoDato.CADENA, res.getTipo().toString().toLowerCase());
        } else if (this.id.equalsIgnoreCase("toString")) {
            if (argumentos.size() != 1) {
                ManejadorErrores.agregar("Semantico", "toString espera 1 argumento", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            Resultado res = argumentos.get(0).evaluar(tabla);
            return new Resultado(TipoDato.CADENA, String.valueOf(res.getValor()));
        } else if (this.id.equalsIgnoreCase("find")) {
            if (argumentos.size() != 2) {
                ManejadorErrores.agregar("Semantico", "find espera 2 argumentos", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            Resultado resLista = argumentos.get(0).evaluar(tabla);
            Resultado resBuscado = argumentos.get(1).evaluar(tabla);

            if (resLista.getValor() instanceof List) {
                List<Object> lista = (List<Object>) resLista.getValor();
                boolean encontrado = lista.contains(resBuscado.getValor());
                return new Resultado(TipoDato.BOOLEANO, encontrado);
            } else {
                ManejadorErrores.agregar("Semantico", "El primer argumento de find debe ser un vector/lista", linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
        }

        MethodDeclStmt funcion = tabla.buscarFuncion(this.id);
        if (funcion == null) {
            ManejadorErrores.agregar("Semantico", "Funcion " + id + " no existe", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        if (argumentos.size() != funcion.parametros.size()) {
            ManejadorErrores.agregar("Semántico", "La función '" + id + "' esperaba " + funcion.parametros.size() + " parámetros", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }

        TablaSimbolos entornoLocal = new TablaSimbolos(obtenerGlobal(tabla), "Local-" + id);

        for (int i = 0; i < argumentos.size(); i++) {
            Resultado valorArg = argumentos.get(i).evaluar(tabla);
            Param p = funcion.parametros.get(i);
            entornoLocal.insertar(p.id, p.tipo, valorArg.getValor(), linea, columna);
        }

        try {
            for (Stmt s : funcion.instrucciones) {
                s.ejecutar(entornoLocal);
            }
        } catch (ReturnException e) {
            return new Resultado(funcion.tipoRetorno, e.valor);
        }

        if (funcion.tipoRetorno != TipoDato.VOID) {
            ManejadorErrores.agregar("Semántico",
                    "La función " + id + " debe retornar un valor tipo " + funcion.tipoRetorno,
                    this.linea,
                    this.columna);

            return new Resultado(TipoDato.ERROR, null);
        }

        return new Resultado(TipoDato.VOID, null);

    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodo" + this.hashCode();
        String etiqueta = "Llamada funcion: " + this.id;
        dot.append(nombreNodo).append("[label=\"").append(etiqueta).append("\"];\n");

        for (Expr arg : argumentos) {
            String nombreHijo = arg.getDot(dot); 
            dot.append(nombreNodo).append(" -> ").append(nombreHijo).append(";\n");
        }
        return nombreNodo;
    }

    private TablaSimbolos obtenerGlobal(TablaSimbolos tabla) {
        TablaSimbolos aux = tabla;
        while (aux.getTablaAnterior() != null) {
            aux = aux.getTablaAnterior();
        }
        return aux;
    }

}
