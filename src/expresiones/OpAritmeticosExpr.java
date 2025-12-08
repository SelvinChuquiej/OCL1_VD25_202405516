/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import AST.Expr;
import AST.Resultado;
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

        switch (op) {
            case MAS:
                //int + int
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = Integer.parseInt(v1.toString());
                    int b = Integer.parseInt(v2.toString());
                    return new Resultado(TipoDato.ENTERO, a + b);
                }
                //int + decimal || decimal + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //int + char || char + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = Integer.parseInt(v1.toString());
                        b = v2.toString().charAt(0);
                    } else {
                        a = v1.toString().charAt(0);
                        b = Integer.parseInt(v2.toString());
                    }
                    return new Resultado(TipoDato.ENTERO, a + b);
                }
                //int + string || string + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CADENA)
                        || (r1.getTipo() == TipoDato.CADENA && r2.getTipo() == TipoDato.ENTERO)) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //decimal + decimal
                if (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.DECIMAL) {
                    double a = Double.parseDouble(r1.getValor().toString());
                    double b = Double.parseDouble(r2.getValor().toString());
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //decimal + char || char + decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.DECIMAL)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = Double.parseDouble(v1.toString());
                    } else {
                        char c = v1.toString().charAt(0);
                        a = c;
                    }
                    if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = Double.parseDouble(v2.toString());
                    } else {
                        char c = v2.toString().charAt(0);
                        b = c;
                    }
                    return new Resultado(TipoDato.DECIMAL, a + b);
                }
                //decimal + string || string + decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CADENA)
                        || (r1.getTipo() == TipoDato.CADENA && r2.getTipo() == TipoDato.DECIMAL)) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //booleano + string || string + booleano
                if ((r1.getTipo() == TipoDato.BOOLEANO && r2.getTipo() == TipoDato.CADENA)
                        || (r1.getTipo() == TipoDato.CADENA && r2.getTipo() == TipoDato.BOOLEANO)) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //char + char
                if (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.CARACTER) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //char + string || string + char
                if ((r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.CADENA)
                        || (r1.getTipo() == TipoDato.CADENA && r2.getTipo() == TipoDato.CARACTER)) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                //string + string
                if (r1.getTipo() == TipoDato.CADENA && r2.getTipo() == TipoDato.CADENA) {
                    String result = v1.toString() + v2.toString();
                    return new Resultado(TipoDato.CADENA, result);
                }
                break;
            case MENOS:
                //int - int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = Integer.parseInt(v1.toString());
                    int b = Integer.parseInt(v2.toString());
                    return new Resultado(TipoDato.ENTERO, a - b);
                }
                //int + decimal || decimal + int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a - b);
                }
                //int - char || char - int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = Integer.parseInt(v1.toString());
                        b = v2.toString().charAt(0);
                    } else {
                        a = v1.toString().charAt(0);
                        b = Integer.parseInt(v2.toString());
                    }
                    return new Resultado(TipoDato.ENTERO, a - b);
                }
                //decimal - decimal
                if (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.DECIMAL) {
                    double a = Double.parseDouble(r1.getValor().toString());
                    double b = Double.parseDouble(r2.getValor().toString());
                    return new Resultado(TipoDato.DECIMAL, a - b);
                }
                //decimal - char || char - decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.DECIMAL)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = Double.parseDouble(v1.toString());
                    } else {
                        char c = v1.toString().charAt(0);
                        a = c;
                    }
                    if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = Double.parseDouble(v1.toString());
                    } else {
                        char c = v2.toString().charAt(0);
                        b = c;
                    }
                    return new Resultado(TipoDato.DECIMAL, a - b);
                }
                break;
            case POR:
                //int ** int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = Integer.parseInt(v1.toString());
                    int b = Integer.parseInt(v2.toString());
                    return new Resultado(TipoDato.ENTERO, a * b);
                }
                //int ** decimal || decimal ** int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a * b);
                }
                //int ** char || char ** int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = Integer.parseInt(v1.toString());
                        b = v2.toString().charAt(0);
                    } else {
                        a = v1.toString().charAt(0);
                        b = Integer.parseInt(v2.toString());
                    }
                    return new Resultado(TipoDato.ENTERO, a * b);
                }
                //decimal ** decimal
                if (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.DECIMAL) {
                    double a = Double.parseDouble(r1.getValor().toString());
                    double b = Double.parseDouble(r2.getValor().toString());
                    return new Resultado(TipoDato.DECIMAL, a * b);
                }
                //decimal ** char || char ** decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.DECIMAL)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = Double.parseDouble(v1.toString());
                    } else {
                        char c = v1.toString().charAt(0);
                        a = c;
                    }
                    if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = Double.parseDouble(v1.toString());
                    } else {
                        char c = v2.toString().charAt(0);
                        b = c;
                    }
                    return new Resultado(TipoDato.DECIMAL, a * b);
                }
                break;
            case DIV:
                //int / int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                //int / decimal || decimal / int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                //int / char || char / int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.ENTERO)) {
                    int a, b;
                    if (r1.getTipo() == TipoDato.ENTERO) {
                        a = Integer.parseInt(v1.toString());
                        b = v2.toString().charAt(0);
                    } else {
                        a = v1.toString().charAt(0);
                        b = Integer.parseInt(v2.toString());
                    }
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                //decimal / decimal
                if (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.DECIMAL) {
                    double a = Double.parseDouble(r1.getValor().toString());
                    double b = Double.parseDouble(r2.getValor().toString());
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                //decimal / char || char / decimal
                if ((r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.CARACTER)
                        || (r1.getTipo() == TipoDato.CARACTER && r2.getTipo() == TipoDato.DECIMAL)) {
                    double a, b;
                    if (r1.getTipo() == TipoDato.DECIMAL) {
                        a = Double.parseDouble(v1.toString());
                    } else {
                        char c = v1.toString().charAt(0);
                        a = c;
                    }
                    if (r2.getTipo() == TipoDato.DECIMAL) {
                        b = Double.parseDouble(v1.toString());
                    } else {
                        char c = v2.toString().charAt(0);
                        b = c;
                    }
                    return new Resultado(TipoDato.DECIMAL, a / b);
                }
                break;
            case POT:
                //int ** int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    int a = Integer.parseInt(v1.toString());
                    int b = Integer.parseInt(v2.toString());
                    return new Resultado(TipoDato.ENTERO, (int) Math.pow(a, b));
                }
                //int ** decimal || decimal ** int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, Math.pow(a, b));
                }
                break;
            case MOD:
                //int % int 
                if (r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.ENTERO) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a % b);
                }
                //int % decimal || decimal % int
                if ((r1.getTipo() == TipoDato.ENTERO && r2.getTipo() == TipoDato.DECIMAL)
                        || (r1.getTipo() == TipoDato.DECIMAL && r2.getTipo() == TipoDato.ENTERO)) {
                    double a = Double.parseDouble(v1.toString());
                    double b = Double.parseDouble(v2.toString());
                    return new Resultado(TipoDato.DECIMAL, a % b);
                }
        }
        return new Resultado(null, null);
    }
}
