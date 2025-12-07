/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Selvi
 */
public class TablaSimbolos {

    private TablaSimbolos tablaAnterior;
    private Map<String, Simbolo> tablaActual;
    private String nombre;

    public TablaSimbolos() {
        this.tablaAnterior = null;
        this.tablaActual = new HashMap<>();
        this.nombre = "global";
    }

    public TablaSimbolos(TablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();
        this.nombre = "";
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
        TablaSimbolos actual = this;
        while (actual != null) {
            if (actual.tablaActual.containsKey(clave)) {
                return actual.tablaActual.get(clave);
            }
            actual = actual.tablaAnterior;
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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
