/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Param;
import AST.Stmt;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class MethodDeclStmt extends Stmt {

    public TipoDato tipoRetorno;
    public String id;
    public List<Param> parametros;
    public List<Stmt> instrucciones;

    public MethodDeclStmt(TipoDato tipoRetorno, String id, List<Param> parametros, List<Stmt> instrucciones, int line, int column) {
        super(line, column);
        this.tipoRetorno = tipoRetorno;
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    public TipoDato getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(TipoDato tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        TablaSimbolos global = tabla;
        while (global.getTablaAnterior() != null) {
            global = global.getTablaAnterior();
        }
        global.guardarFuncion(this.id, this);
        return null;
    }
}
