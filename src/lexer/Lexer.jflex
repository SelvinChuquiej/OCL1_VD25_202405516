package lexer;

import java_cup.runtime.Symbol;
import parser.sym;
import errores.ManejadorErrores;
import errores.Error.TipoError;

%%
%class Lexer
%unicode
%cup
%public

%line
%column

%ignorecase

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

ENTERO      = {DIGITO}+
DECIMAL     = {DIGITO}+"."+{DIGITO}+

ID          = {LETRA}{IDRESTO}*
ESPACIO     = [ \t\r\n\f\u00A0]+

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
"bool"      { return symbol(sym.BOOLEAN); }
"char"      { return symbol(sym.CHAR); }
"string"    { return symbol(sym.STRING); }
"var"         { return symbol(sym.VAR); }

"true"      { return symbol(sym.TRUE); }
"false"     { return symbol(sym.FALSE); }
"print"     { return symbol(sym.PRINT); }

"if"        { return symbol(sym.IF); }   
"else"      { return symbol(sym.ELSE); }  
"switch"    { return symbol(sym.SWITCH); } 
"case"      { return symbol(sym.CASE); }  
"default"   { return symbol(sym.DEFAULT); }  
"break"     { return symbol(sym.BREAK); }  
"continue"  { return symbol(sym.CONTINUE); }
"while"     { return symbol(sym.WHILE); }
"for"       { return symbol(sym.FOR); }
"do"        { return symbol(sym.DO); }

"++"        { return symbol(sym.INCREMENTO); }
"--"        { return symbol(sym.DECREMENTO); }
"**"        { return symbol(sym.POT); }
"=="        { return symbol(sym.IGUAL_IGUAL); }
"="         { return symbol(sym.IGUAL); }
"!="        { return symbol(sym.DIFERENTE); }
"<="        { return symbol(sym.MENOR_IGUAL); }
">="        { return symbol(sym.MAYOR_IGUAL); }
"<"         { return symbol(sym.MENOR); }
">"         { return symbol(sym.MAYOR); }

"+"         { return symbol(sym.MAS); }
"-"         { return symbol(sym.MENOS); }
"*"         { return symbol(sym.POR); }
"/"         { return symbol(sym.DIV); }
"%"         { return symbol(sym.MOD); }

"&&"        { return symbol(sym.AND); }
"||"        { return symbol(sym.OR); }
"!"         { return symbol(sym.NOT); }
"^"         { return symbol(sym.XOR); }

"("         { return symbol(sym.PAREN_A); }
")"         { return symbol(sym.PAREN_C); }
"{"         { return symbol(sym.LLAVE_A); }
"}"         { return symbol(sym.LLAVE_C); }
";"         { return symbol(sym.PUNTO_COMA); }
":"         { return symbol(sym.DOS_PUNTOS); }



{DECIMAL}   { return symbol(sym.DECIMAL, yytext()); }
{ENTERO}    { return symbol(sym.ENTERO, yytext()); }

{ID}        { return symbol(sym.ID, yytext()); }


{CADENA}    { 
                String text = yytext();
                text = text.substring(1, text.length() - 1);
                text = text.replace("\\n", "\n"); 
                text = text.replace("\\t", "\t");   
                text = text.replace("\\r", "\r");    
                text = text.replace("\\\"", "\"");  
                text = text.replace("\\'", "'");     
                text = text.replace("\\\\", "\\");
                return symbol(sym.CADENA, text);
            }

{CARACTER}  {
                String text = yytext();
                text = text.substring(1, text.length() - 1);
                char c;
                if (text.length() > 1 && text.startsWith("\\")) {
                    switch(text.charAt(1)) {
                        case 'n': c = '\n'; break;
                        case 't': c = '\t'; break;
                        case 'r': c = '\r'; break;
                        case '\\': c = '\\'; break;
                        case '\'': c = '\''; break;
                        default: c = text.charAt(1); // Fallback
                    }
                } else {
                    c = text.charAt(0);
                }
                return symbol(sym.CARACTER, c);
            }

\" [^\n\"]* \n  { 
                ManejadorErrores.agregar(TipoError.LEXICO.toString(), "Cadena no cerrada (falta comilla de cierre)", yyline + 1, yycolumn + 1);
                }

.           { 
                String mensaje = "Caracter no reconocido: '" + yytext() + "'";
                ManejadorErrores.agregar(TipoError.LEXICO.toString(), mensaje, yyline + 1, yycolumn + 1);
            }

