/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Resultado;

/**
 *
 * @author Selvi
 */
public class ControlStmt {

    public enum Tipo {
        NORMAL,
        BREAK,
        CONTINUE,
        RETURN
    }

    public final Tipo tipo;
    public final Resultado resultRetorno;

    public ControlStmt(Tipo tipo) {
        this.tipo = tipo;
        this.resultRetorno = null;
    }

    public ControlStmt(Tipo tipo, Resultado resultRetorno) {
        this.tipo = tipo;
        this.resultRetorno = resultRetorno;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Resultado getResultRetorno() {
        return resultRetorno;
    }

    public static ControlStmt normal() {
        return new ControlStmt(Tipo.NORMAL);
    }

    public static ControlStmt brk() {
        return new ControlStmt(Tipo.BREAK);
    }

    public static ControlStmt cont() {
        return new ControlStmt(Tipo.CONTINUE);
    }

    public static ControlStmt ret(Resultado r) {
        return new ControlStmt(Tipo.RETURN, r);
    }
}
