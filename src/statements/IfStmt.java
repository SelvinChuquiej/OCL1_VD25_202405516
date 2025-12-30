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
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class IfStmt extends Stmt {

    private Expr expresion;
    private List<Stmt> thenBlock;
    private List<Stmt> elseBlock;

    public IfStmt(Expr expresion, List<Stmt> thenBlock, List<Stmt> elseBlock, int line, int column) {
        super(line, column);
        this.expresion = expresion;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        Resultado result = expresion.evaluar(tabla);

        if (result.getTipo() != TipoDato.BOOLEANO) {
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "La condicion if debe ser booleana", linea, columna);
            return ControlStmt.normal();
        }

        boolean valorCondicion = (Boolean) result.getValor();
        List<Stmt> bloque = valorCondicion ? thenBlock : elseBlock;
        if (bloque == null) {
            return ControlStmt.normal();
        }
        String nombreEntorno = "If_" + this.linea;
        TablaSimbolos local = new TablaSimbolos(tabla, nombreEntorno);
        for (Stmt s : bloque) {
            ControlStmt c = s.ejecutar(local);
            if (c != null && c.tipo != ControlStmt.Tipo.NORMAL) {
                return c;
            }
        }
        return ControlStmt.normal();
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "nodoIf" + this.hashCode();
        dot.append(nombreNodo).append("[label=\"IF\"];\n");

        // Rama de la condición
        dot.append(nombreNodo).append(" -> ").append(expresion.getDot(dot)).append("[label=\"condicion\"];\n");

        // Rama Then (Verdadero)
        String nodoThen = "nodoThen" + this.hashCode();
        dot.append(nodoThen).append("[label=\"THEN\", shape=circle, width=0.5];\n");
        dot.append(nombreNodo).append(" -> ").append(nodoThen).append(";\n");
        for (Stmt s : thenBlock) {
            dot.append(nodoThen).append(" -> ").append(s.getDot(dot)).append(";\n");
        }

        // Rama Else (Falso) - Solo si existe
        if (elseBlock != null && !elseBlock.isEmpty()) {
            String nodoElse = "nodoElse" + this.hashCode();
            dot.append(nodoElse).append("[label=\"ELSE\", shape=circle, width=0.5];\n");
            dot.append(nombreNodo).append(" -> ").append(nodoElse).append(";\n");
            for (Stmt s : elseBlock) {
                dot.append(nodoElse).append(" -> ").append(s.getDot(dot)).append(";\n");
            }
        }
        return nombreNodo;
    }
}
