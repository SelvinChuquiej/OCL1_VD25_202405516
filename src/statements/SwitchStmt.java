/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Expr;
import AST.Resultado;
import AST.Stmt;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class SwitchStmt extends Stmt {

    private Expr expresion;
    private List<CaseStmt> casos;

    public SwitchStmt(Expr expresion, List<CaseStmt> casos, int line, int column) {
        super(line, column);
        this.expresion = expresion;
        this.casos = casos;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Resultado result = expresion.evaluar(tabla);
        if (result.getTipo() == TipoDato.ERROR) {
            return ControlStmt.normal();
        }

        Object valSwicth = result.getValor();
        TablaSimbolos local = new TablaSimbolos(tabla);

        int indiceInicio = -1;
        int indiceDefault = -1;

        for (int i = 0; i < casos.size(); i++) {
            CaseStmt c = casos.get(i);

            if (c.getValor() == null) {
                indiceDefault = i;
            } else {
                Resultado resultCase = c.getValor().evaluar(local);
                if (sonIguales(valSwicth, resultCase.getValor())) {
                    indiceInicio = i;
                    break;
                }
            }
        }

        if (indiceInicio == -1) {
            indiceInicio = indiceDefault;
        }
        if (indiceInicio == -1) {
            return ControlStmt.normal();
        }

        for (int i = indiceInicio; i < casos.size(); i++) {
            CaseStmt c = casos.get(i);
            ControlStmt res = c.ejecutar(local);

            if (res.getTipo() != ControlStmt.Tipo.NORMAL) {
                if (res.getTipo() == ControlStmt.Tipo.BREAK) {
                    return ControlStmt.normal();
                }
                return res;
            }
        }
        return ControlStmt.normal();
    }

    private boolean sonIguales(Object a, Object b) {
        if (a == null || b == null) {
            return false;
        }
        return a.toString().equals(b.toString());
    }
}
