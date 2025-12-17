/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
import errores.Error.TipoError;
import errores.ManejadorErrores;
import simbolo.TablaSimbolos;
import simbolo.TipoDato;

/**
 *
 * @author Selvi
 */
public class OpAritmeticosExpr extends Expr {

    public enum OpBin {
        MAS, MENOS, POR, DIV, MOD, POT
    }

    private Expr izq;
    private Expr der;
    private OpBin op;

    public OpAritmeticosExpr(Expr izq, Expr der, OpBin op, int line, int column) {
        super(line, column);
        this.izq = izq;
        this.der = der;
        this.op = op;
    }

    @Override
    public Resultado evaluar(TablaSimbolos tabla) {
        Resultado r1 = izq.evaluar(tabla);
        Resultado r2 = der.evaluar(tabla);

        Object v1 = r1.getValor();
        Object v2 = r2.getValor();

        if (r1.getTipo() == TipoDato.ERROR || r2.getTipo() == TipoDato.ERROR) {
            return new Resultado(TipoDato.ERROR, null);
        }

        switch (op) {
            case MAS:

                //Cualquier cosa + String
                if (r1.getTipo() == TipoDato.CADENA || r2.getTipo() == TipoDato.CADENA) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //int + int
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = (int) v1;
                    int b = (int) v2;
                    return new Resultado(TipoDato.ENTERO, a + b);
                }
                //int + decimal || decimal + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else {
                        a = (Double) v1;
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else {
                        b = (Double) v2;
                    }
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //int + char || char + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = (int) v1;
                        b = (int) ((Character) v2).charValue();
                    } else {
                        a = (int) ((Character) v1).charValue();
                        b = (int) v2;
                    }
                    return new Resultado(TipoDato.ENTERO, a + b);
                }
                //decimal + decimal
                if (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.DECIMAL) {
                    double a = (double) v1;
                    double b = (double) v2;
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //decimal + char || char + decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.DECIMAL)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = (double) v1;
                    } else {
                        char c = v1.toString().charAt(0);
                        a = c;
                    }
                    if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = (double) v2;
                    } else {
                        char c = v2.toString().charAt(0);
                        b = c;
                    }
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //char + char
                if (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.CARACTER) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede sumar " + r1.getTipo() + " con " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            case MENOS:
                //int - int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = (int) v1;
                    int b = (int) v2;
                    return new Resultado(TipoDato.ENTERO, a - b);
                }
                //Decimal - Cualquier cosa (int, decimal, char)
                if (r1.getTipo() == TipoDato.DECIMAL || r2.getTipo() == TipoDato.DECIMAL) {
                    double a, b;

                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = (double) v1;
                    } else {
                        a = (double) ((Character) v1).charValue();
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = (double) v2;
                    } else {
                        b = (double) ((Character) v2).charValue();
                    }
                    return new Resultado(TipoDato.DECIMAL, a - b);
                }
                //int - char || char - int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = (int) v1;
                        b = (int) ((Character) v2).charValue();
                    } else {
                        a = (int) ((Character) v1).charValue();
                        b = (int) v2;
                    }
                    return new Resultado(TipoDato.ENTERO, a - b);
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede restar " + r1.getTipo() + " con " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            case POR:
                //int * int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = (int) v1;
                    int b = (int) v2;
                    return new Resultado(TipoDato.ENTERO, a * b);
                }
                //Decimal * Cualquier cosa (int, decimal, char)
                if (r1.getTipo() == TipoDato.DECIMAL || r2.getTipo() == TipoDato.DECIMAL) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = ((Double) v1).doubleValue();
                    } else {
                        a = (double) ((Character) v1).charValue();
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = ((Double) v2).doubleValue();
                    } else {
                        b = (double) ((Character) v2).charValue();
                    }
                    return new Resultado(TipoDato.DECIMAL, a * b);
                }
                //int * char || char * int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = (int) v1;
                        b = (int) ((Character) v2).charValue();
                    } else {
                        a = (int) ((Character) v1).charValue();
                        b = (int) v2;
                    }
                    return new Resultado(TipoDato.ENTERO, a * b);
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede multiplicar " + r1.getTipo() + " con " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);
            case DIV:
                boolean t1EsNumDIV = (r1.getTipo() == TipoDato.ENTERO || r1.getTipo() == TipoDato.DECIMAL || r1.getTipo() == TipoDato.CARACTER);
                boolean t2EsNumDIV = (r2.getTipo() == TipoDato.ENTERO || r2.getTipo() == TipoDato.DECIMAL || r2.getTipo() == TipoDato.CARACTER);
                if (t1EsNumDIV && t2EsNumDIV) {
                    double a, b;

                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = (double) v1;
                    } else {
                        a = (double) ((Character) v1).charValue();
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = (double) v2;
                    } else {
                        b = (double) ((Character) v2).charValue();
                    }
                    if (b == 0.0) {
                        ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede dividir por cero", linea, columna);
                        return new Resultado(TipoDato.ERROR, null);
                    }
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede dividir " + r1.getTipo() + " con " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);

            case POT:
                //int ** int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = (int) v1;
                    int b = (int) v2;
                    return new Resultado(TipoDato.ENTERO, (int) Math.pow(a, b));
                }
                //Decimal ** CualquierCosa(decimal, int)
                boolean t1EsNumPOT = (r1.getTipo() == TipoDato.ENTERO || r1.getTipo() == TipoDato.DECIMAL);
                boolean t2EsNumPOT = (r2.getTipo() == TipoDato.ENTERO || r2.getTipo() == TipoDato.DECIMAL);
                if (t1EsNumPOT && t2EsNumPOT) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else {
                        a = (double) v1;
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else {
                        b = (double) v2;
                    }
                    return new Resultado(TipoDato.DECIMAL, Math.pow(a, b));
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede potenciar entre " + r1.getTipo() + " y " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);

            case MOD:
                boolean t1EsNumMOD = (r1.getTipo() == TipoDato.ENTERO || r1.getTipo() == TipoDato.DECIMAL);
                boolean t2EsNumMOD = (r2.getTipo() == TipoDato.ENTERO || r2.getTipo() == TipoDato.DECIMAL);
                if (t1EsNumMOD && t2EsNumMOD) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = ((Integer) v1).doubleValue();
                    } else {
                        a = (double) v1;
                    }
                    if (r2.getTipo() == TipoDato.ENTERO) {
                        b = ((Integer) v2).doubleValue();
                    } else {
                        b = (double) v2;
                    }
                    if (b == 0.0) {
                        ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede realizar modulo con divisor cero", linea, columna);
                        return new Resultado(TipoDato.ERROR, null);
                    }
                    return new Resultado(TipoDato.DECIMAL, a % b);
                }
                ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "El operador modulo so puede aplciarse entre numeros, recibio " + r1.getTipo() + " y " + r2.getTipo(), linea, columna);
                return new Resultado(TipoDato.ERROR, null);
        }
        ManejadorErrores.agregar(TipoError.SEMANTICO.toString(), "No se puede sumar ", linea, columna);
        return new Resultado(TipoDato.ERROR, null);
    }
}
