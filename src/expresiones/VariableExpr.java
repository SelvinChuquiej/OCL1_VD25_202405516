/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import errores.Error.TipoError;
import errores.ManejadorErrores;
import simbolo.TablaSimbolos;
import simbolo.Simbolo;
import simbolo.TipoDato;
import statements.ControlStmt;

/**
 *
 * @author Selvi
 */
public class VariableExpr extends Expr {

    private String id;

    public VariableExpr(String id, int line, int column) {
        super(line, column);
        this.id = id;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Simbolo sim = tabla.obtenerVariable(id);
        
        if (sim == null) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "La variable '" + id + "' no existe en este entorno", linea, columna);
            return new Resultado(TipoDato.ERROR, null);
        }
        return new Resultado(sim.getTipo(), sim.getValor());
    }

}
