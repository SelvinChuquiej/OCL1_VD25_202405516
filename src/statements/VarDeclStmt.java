/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.ManejadorErrores;
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

    public VarDeclStmt(TipoDato tipo, String id, int line, int column) {
        super(line, column);
        this.tipo = tipo;
        this.id = id;
        this.expresion = null;
    }

    @Override
    public Resultado ejecutar(TablaSimbolos tabla) {
        Object valorFinal = null;

        if (expresion != null) {
            Resultado res = expresion.evaluar(tabla);
            if (res == null || res.getTipo() == TipoDato.ERROR) {
                return new Resultado(TipoDato.ERROR, null);
            }

            if (this.tipo != res.getTipo()) {
                ManejadorErrores.agregar("Semantico", "No se puede asignar un valor de tipo "
                        + res.getTipo() + " a la variable " + id + " de tipo " + this.tipo, linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            }
            valorFinal = res.getValor();
        } else {
            valorFinal = valorPorDefecto(this.tipo);
        }
        Simbolo s = new Simbolo(this.id, this.tipo, valorFinal);
        tabla.declararVariable(this.id, s);

        return null;
    }

    private Object valorPorDefecto(TipoDato tipo) {
        switch (tipo) {
            case ENTERO:
                return 0;
            case DECIMAL:
                return 0.0;
            case CADENA:
                return "";
            case BOOLEANO:
                return false;
            case CARACTER:
                return '\0';
            default:
                return null;
        }
    }
}
