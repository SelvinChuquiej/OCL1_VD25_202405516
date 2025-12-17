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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class SwitchStmt extends Stmt {

    private Expr expresion;
    private List<CaseStmt> casos;
    private Stmt defaultStmt;

    public SwitchStmt(Expr expresion, List<CaseStmt> casos, Stmt defaultStmt, int line, int column) {
        super(line, column);
        this.expresion = expresion;
        this.casos = casos;
        this.defaultStmt = defaultStmt;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Resultado resultSwitch = expresion.evaluar(tabla);
        if (resultSwitch.getTipo() == TipoDato.ERROR) {
            return ControlStmt.normal();
        }

        Object valorSwitch = resultSwitch.getValor();

        String nombreEntorno = "Switch_" + this.linea;
        TablaSimbolos switchEnv = new TablaSimbolos(tabla, nombreEntorno);

        Set<Object> valoresVistos = new HashSet<>();
        boolean huboMatch = false;

        for (CaseStmt caso : casos) {
            if (caso.getValor() == null) {
                continue;
            }

            Resultado resCase = caso.getValor().evaluar(switchEnv);
            if (resCase.getTipo() == TipoDato.ERROR) {
                continue;
            }

            Object valorCase = resCase.getValor();

            if (valorSwitch.getClass() != valorCase.getClass()) {
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "Tipos incompatibles", caso.linea, caso.columna);
                continue;
            }
            if (valoresVistos.contains(valorCase)) {
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "El caso valor '" + valorCase + "' está duplicado.", caso.linea, caso.columna);
                continue;
            }
            valoresVistos.add(valorCase);

            if (valorSwitch.equals(valorCase) || huboMatch) {

                ControlStmt res = caso.ejecutar(switchEnv);

                if (res != null) {
                    if (res.getTipo() == ControlStmt.Tipo.BREAK) {
                        return ControlStmt.normal();
                    }
                    if (res.getTipo() == ControlStmt.Tipo.RETURN) {
                        return res;
                    }
                    if (res.getTipo() == ControlStmt.Tipo.CONTINUE) {
                        return res;
                    }
                }
                huboMatch = true;
            }
        }
        if (!huboMatch && defaultStmt != null) {
            ControlStmt res = defaultStmt.ejecutar(switchEnv);
            if (res != null) {
                if (res.getTipo() == ControlStmt.Tipo.BREAK) {
                    return ControlStmt.normal();
                }
                if (res.getTipo() == ControlStmt.Tipo.RETURN) {
                    return res;
                }
                if (res.getTipo() == ControlStmt.Tipo.CONTINUE) {
                    return res;
                }
            }
        }
        return ControlStmt.normal();
    }
}
