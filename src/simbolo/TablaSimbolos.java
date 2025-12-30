/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

import java.util.HashMap;
import java.util.Map;
import statements.MethodDeclStmt;

/**
 *
 * @author Selvi
 */
public class TablaSimbolos {

    private TablaSimbolos tablaAnterior;
    private Map<String, Simbolo> tablaActual;
    private Map<String, MethodDeclStmt> funciones;
    private String nombre;

    public TablaSimbolos() {
        this.tablaAnterior = null;
        this.tablaActual = new HashMap<>();
        this.funciones = new HashMap<>();
        this.nombre = "Global";
    }

    public TablaSimbolos(TablaSimbolos tablaAnterior, String nombre) {
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();
        this.funciones = new HashMap<>();
        this.nombre = nombre;
    }

    public boolean declararVariable(String id, Simbolo simbolo) {
        String clave = id.toLowerCase();
        if (tablaActual.containsKey(clave)) {
            return false;
        }
        tablaActual.put(clave, simbolo);
        return true;
    }

    public Simbolo obtenerVariable(String id) {
        String clave = id.toLowerCase();
        TablaSimbolos t = this;
        while (t != null) {
            if (t.tablaActual.containsKey(clave)) {
                return t.tablaActual.get(clave);
            }
            t = t.tablaAnterior;
        }
        return null;
    }

    public boolean asignarVariable(String id, Object valor) {
        Simbolo simbolo = obtenerVariable(id);
        if (simbolo == null) {
            return false;
        }
        simbolo.setValor(valor);
        return true;
    }

    public boolean insertar(String id, TipoDato tipo, Object valor, int linea, int columna) {
        String clave = id.toLowerCase();
        if (tablaActual.containsKey(clave)) {
            return false;
        }
        Simbolo s = new Simbolo(clave, tipo, valor, nombre, linea, columna);
        tablaActual.put(clave, s);
        return true;
    }

    
    public void guardarFuncion(String id, MethodDeclStmt funcion){
        funciones.put(id.toLowerCase(), funcion);
    }
    
    public MethodDeclStmt buscarFuncion(String id){
        String clave = id.toLowerCase();
        TablaSimbolos t = this;
        while(t != null){
            if(t.funciones.containsKey(clave)){
                return t.funciones.get(clave);
            }
            t = t.tablaAnterior;
        }
        return null;
    }
    
    public TablaSimbolos getTablaAnterior() {
        return tablaAnterior;
    }

    public void setTablaAnterior(TablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
    }

    public Map<String, Simbolo> getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(Map<String, Simbolo> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
