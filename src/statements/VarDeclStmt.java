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
import simbolo.TipoDato;
import util.Consola;

/**
 *
 * @author Selvi
 */
public class VarDeclStmt extends Stmt {

    private TipoDato tipo;
    private String id;
    private Expr expresion;

    public VarDeclStmt(TipoDato tipo, String id, Expr expresion, int line, int column) {
        super(line, column);
        this.tipo = tipo;
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Resultado ejecutar(TablaSimbolos tabla) {
        Resultado res = expresion.evaluar(tabla);
        if (res == null) {
            return null;
        }
        if (this.tipo != res.getTipo()) {
            Consola.print("Error Semántico: No se puede asignar un " + res.getTipo()
                    + " a la variable '" + id + "' que es de tipo " + this.tipo
                    + ". Línea: " + linea);
            return null;
        }
        Simbolo nuevoSimbolo = new Simbolo(this.id, this.tipo, res.getValor());
        boolean creacionExitosa = tabla.declararVariable(this.id, nuevoSimbolo);

        if (!creacionExitosa) {
            Consola.print("Error semantico: Variable " + id + " ya existe. Linea: " + linea);
        }
        return null;
    }
}
