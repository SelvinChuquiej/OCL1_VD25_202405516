/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AST;

import errores.Error.TipoError;
import errores.ManejadorErrores;
import errores.ReturnException;
import java.util.List;
import simbolo.TablaSimbolos;
import statements.ControlStmt;

/**
 *
 * @author Selvi
 */
public class RaizAST extends Stmt {

    private List<Stmt> instrucciones;

    public RaizAST(List<Stmt> instrucciones) {
        super(0, 0);
        this.instrucciones = instrucciones;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        try {
            for (Stmt s : instrucciones) {
                if (s != null) {
                    ControlStmt res = s.ejecutar(tabla);

                    // 1. Validar que no existan break/continue en el ámbito global
                    if (res != null && res.getTipo() != ControlStmt.Tipo.NORMAL) {
                        if (res.getTipo() == ControlStmt.Tipo.BREAK || res.getTipo() == ControlStmt.Tipo.CONTINUE) {
                            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(),
                                    "Sentencia de control fuera de un ciclo", s.linea, s.columna);
                        }
                        // Si es un RETURN, la excepción lo atrapará abajo, 
                        // pero si no usas excepciones para return, aquí podrías manejarlo.
                    }
                }
            }
        } catch (ReturnException e) {
            // 2. Manejar el return global (fuera de funciones)
            ManejadorErrores.agregar(TipoError.SEMANTICO.toString(),
                    "La sentencia 'return' no está permitida fuera de una función", 0, 0);
        } catch (Exception ex) {
            System.err.println("Error fatal en la ejecución: " + ex.getMessage());
        }

        return ControlStmt.normal();
    }

    @Override
    public String getDot(StringBuilder dot) {
        String nombreNodo = "NODO_RAIZ";
        dot.append(nombreNodo).append("[label=\"PROGRAMA\", shape=doubleoctagon, fillcolor=lightblue, style=filled];\n");

        for (Stmt s : instrucciones) {
            if (s != null) {
                dot.append(nombreNodo).append(" -> ").append(s.getDot(dot)).append(";\n");
            }
        }
        return nombreNodo;
    }
}
