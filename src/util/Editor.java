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
public class Editor {

    private static JTextArea editor;

    public static void setEditor(JTextArea editor) {
        Editor.editor = editor;
    }

    public static void clear() {
        if (editor != null) {
            editor.setText("");
        }
    }

}
