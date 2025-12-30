/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import errores.Error.TipoError;
import errores.ManejadorErrores;
import java.util.List;
import simbolo.Simbolo;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class VectorAsigStmt extends Stmt {

    private String id;
    private Expr indice;
    private Expr expresion;

    public VectorAsigStmt(String id, Expr indice, Expr expresion, int line, int column) {
        super(line, column);
        this.id = id;
        this.indice = indice;
        this.expresion = expresion;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Simbolo simbolo = tabla.obtenerVariable(this.id);

        if (simbolo == null) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "El vector " + id + " no existe", linea, columna);
            return ControlStmt.normal();
        }

        Resultado resIdx = indice.evaluar(tabla);
        if (resIdx.getTipo() != TipoDato.ENTERO) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "El índice debe ser entero", linea, columna);
            return ControlStmt.normal();
        }

        Resultado resVal = expresion.evaluar(tabla);
        if (simbolo.getTipo() != resVal.getTipo()) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Tipo incompatible para el vector " + id, linea, columna);
            return ControlStmt.normal();
        }

        try {
            List<Object> lista = (List<Object>) simbolo.getValor();
            int pos = (int) resIdx.getValor();

            if (pos < 0 || pos >= lista.size()) {
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Índice fuera de límites", linea, columna);
                return ControlStmt.normal();
            }

            lista.set(pos, resVal.getValor());

        } catch (Exception e) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Error al asignar al vector", linea, columna);
        }

        return ControlStmt.normal();
    }

}
