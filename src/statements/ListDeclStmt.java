/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package statements;

import AST.Stmt;
import errores.ManejadorErrores;
import java.util.ArrayList;
import java.util.List;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class ListDeclStmt extends Stmt {

    private TipoDato tipoContenido;
    private String id;

    public ListDeclStmt(TipoDato tipoContenido, String id, int line, int column) {
        super(line, column);
        this.tipoContenido = tipoContenido;
        this.id = id;
    }

    @Override
    public ControlStmt ejecutar(TablaSimbolos tabla) {
        List<Object> nuevaLista = new ArrayList<>();

        if (!tabla.insertar(id, TipoDato.LISTA, nuevaLista, linea, columna)) {
            ManejadorErrores.agregar("Semántico", "La variable '" + id + "' ya existe", linea, columna);
        }
        return null;
    }
}
