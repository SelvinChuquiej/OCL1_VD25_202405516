/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import util.Consola;

/**
 *
 * @author Selvi
 */
public class AsigStmt extends Stmt {

    private String id;
    private Expr expresion;

    public AsigStmt(String id, Expr expresion, int line, int column) {
        super(line, column);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Resultado ejecutar(TablaSimbolos tabla) {
        Simbolo simbolo = tabla.obtenerVariable(this.id);

        if (simbolo == null) {
            Consola.print("Error semantico: Variable " + id + " no ha sido declarada. Linea: " + linea);
            return null;
        }
        Resultado res = expresion.evaluar(tabla);
        if (res == null) {
            return null;
        }
        if (simbolo.getTipo() != res.getTipo()) {
            Consola.print("Error Semántico: Tipos incompatibles. Intentas asignar "
                    + res.getTipo() + " a la variable '" + id
                    + "' que es de tipo " + simbolo.getTipo()
                    + ". (Línea: " + linea + ")");
            return null;
        }
        simbolo.setValor(res.getValor());
        return null;
    }
}
