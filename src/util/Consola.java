/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.JTextArea;

/**
 *
 * @author Selvi
 */
public class Consola {

    private static JTextArea consola;

    public static void setConsola(JTextArea txt) {
        consola = txt;
    }

    public static void print(String texto) {
        if (consola != null) {
            consola.append(texto + "\n");
        }else {
            System.out.println("Error " + texto);
        }
    }

    public static void clear() {
        if (consola != null) {
            consola.setText("");
        }
    }

}
