package lexer;

import java_cup.runtime.Symbol;
import parser.sym;

%%
%class Lexer
%unicode
%cup
%public

%line
%column

%{

    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

%}

/* Expresiones regulares */
DIGITO      = [0-9]
LETRA       = [a-zA-Z_]
IDRESTO     = [a-zA-Z0-9_]
ESPACIO     = [ \t\r\n\f]+

CADENA      = \"([^\"\\]|\\[\"\'ntbr\\])*\"  
CARACTER    = \'([^\'\\]|\\[\"\'ntbr\\])+\'  

COMENT_LINEA = "//".*  
COMENT_MULTI = "/*"([^*]|\*+[^/])*\*+"/"
%%

{ESPACIO}           { /* ignorar */ }
{COMENT_LINEA}      { /* ignorar comentario de una línea */ }
{COMENT_MULTI}      { /* ignorar comentario multi-línea */ }

"int"       { return symbol(sym.INT); }
"double"    { return symbol(sym.DOUBLE); }
"bool"      { return symbol(sym.BOOL); }
"char"      { return symbol(sym.CHAR); }
"string"    { return symbol(sym.STRING); }

"true"      { return symbol(sym.TRUE); }
"false"     { return symbol(sym.FALSE); }
"print"     { return symbol(sym.PRINT); }

"+"         { return symbol(sym.MAS); }
"-"         { return symbol(sym.MENOS); }
"*"         { return symbol(sym.POR); }
"/"         { return symbol(sym.DIV); }
"%"         { return symbol(sym.MOD); }
"^"         { return symbol(sym.POT); }

"=="        { return symbol(sym.IGUAL_IGUAL); }
"!="        { return symbol(sym.DIFERENTE); }
"<="        { return symbol(sym.MENOR_IGUAL); }
">="        { return symbol(sym.MAYOR_IGUAL); }
"<"         { return symbol(sym.MENOR); }
">"         { return symbol(sym.MAYOR); }

"&&"        { return symbol(sym.AND); }
"||"        { return symbol(sym.OR); }
"!"         { return symbol(sym.NOT); }

"("         { return symbol(sym.PAREN_A); }
")"         { return symbol(sym.PAREN_C); }
";"         { return symbol(sym.PUNTO_COMA); }

{CADENA}    { 
                String text = yytext();
                text = text.substring(1, text.length() - 1);
                return symbol(sym.CADENA, text);
            }

{CARACTER}  {
                String text = yytext();
                char c = text.charAt(1); // 'A' → A
                return symbol(sym.CARACTER, c);
            }

{DIGITO}+ "." {DIGITO}+   { 
                return symbol(sym.DECIMAL, yytext());
            }

{DIGITO}+      {
                return symbol(sym.ENTERO, yytext());
            }

{LETRA}{IDRESTO}* {
                return symbol(sym.ID, yytext());
            }

.              { 
                System.err.println("Caracter no reconocido: " + yytext() +
                                   " en línea " + (yyline+1) + ", columna " + (yycolumn+1));
              }
