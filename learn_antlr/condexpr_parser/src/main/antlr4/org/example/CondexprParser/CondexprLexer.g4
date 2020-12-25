// https://github.com/antlr/grammars-v4/blob/master/sql/mysql/Positive-Technologies/MySqlLexer.g4

lexer grammar CondexprLexer;

//=============================================================================================
// SKIP
SPACE:                               [ \t\r\n]+ -> skip;

//=============================================================================================
// Common Keywords
AND:                                 'AND';
AS:                                  'AS';
BETWEEN:                             'BETWEEN';
CAST:                                'CAST';
CHARACTER:                           'CHARACTER';
CONVERT:                             'CONVERT';
FALSE:                               'FALSE';
IS:                                  'IS';
IN:                                  'IN';
LIKE:                                'LIKE';
NOT:                                 'NOT';
NULL_LITERAL:                        'NULL';
OR:                                  'OR';
TRUE:                                'TRUE';
UNSIGNED:                            'UNSIGNED';
XOR:                                 'XOR';

//=============================================================================================
// DATA TYPE Keywords
TINYINT:                             'TINYINT';
SMALLINT:                            'SMALLINT';
INT:                                 'INT';
INTEGER:                             'INTEGER';
BIGINT:                              'BIGINT';
DECIMAL:                             'DECIMAL';
DATE:                                'DATE';
TIME:                                'TIME';
DATETIME:                            'DATETIME';
CHAR:                                'CHAR';
VARCHAR:                             'VARCHAR';
TEXT:                                'TEXT';
LONGTEXT:                            'LONGTEXT';

//=============================================================================================
// Common Keywords, but can be ID
ESCAPE:                              'ESCAPE';
SIGNED:                              'SIGNED';
UNKNOWN:                             'UNKNOWN';


//=============================================================================================
// Operators
// Operators. Assigns
VAR_ASSIGN:                          ':=';
PLUS_ASSIGN:                         '+=';
MINUS_ASSIGN:                        '-=';
MULT_ASSIGN:                         '*=';
DIV_ASSIGN:                          '/=';
MOD_ASSIGN:                          '%=';
AND_ASSIGN:                          '&=';
XOR_ASSIGN:                          '^=';
OR_ASSIGN:                           '|=';

// Operators. Arithmetics
STAR:                                '*';
DIVIDE:                              '/';
MODULE:                              '%';
PLUS:                                '+';
MINUSMINUS:                          '--';
MINUS:                               '-';
DIV:                                 'DIV';
MOD:                                 'MOD';

// Operators. Comparation
EQUAL_SYMBOL:                        '=';
GREATER_SYMBOL:                      '>';
LESS_SYMBOL:                         '<';
EXCLAMATION_SYMBOL:                  '!';

// Operators. Bit
BIT_NOT_OP:                          '~';
BIT_OR_OP:                           '|';
BIT_AND_OP:                          '&';
BIT_XOR_OP:                          '^';

// Constructors symbols
DOT:                                 '.';
LR_BRACKET:                          '(';
RR_BRACKET:                          ')';
COMMA:                               ',';
SEMI:                                ';';
AT_SIGN:                             '@';
ZERO_DECIMAL:                        '0';
ONE_DECIMAL:                         '1';
TWO_DECIMAL:                         '2';
SINGLE_QUOTE_SYMB:                   '\'';
DOUBLE_QUOTE_SYMB:                   '"';
REVERSE_QUOTE_SYMB:                  '`';
COLON_SYMB:                          ':';

//=============================================================================================
// Literal Primitives
STRING_LITERAL:                      DQUOTA_STRING | SQUOTA_STRING | BQUOTA_STRING;
DECIMAL_LITERAL:                     DEC_DIGIT+;

REAL_LITERAL:                        (DEC_DIGIT+)? '.' DEC_DIGIT+;
NULL_SPEC_LITERAL:                   '\\' 'N';

//=============================================================================================
// Identifiers
ID:                                  ID_LITERAL;

//=============================================================================================
// Fragments for Literal primitives
fragment ID_LITERAL:                 [A-Z_$0-9\u0080-\uFFFF]*?[A-Z_$\u0080-\uFFFF]+?[A-Z_$0-9\u0080-\uFFFF]*;
fragment DQUOTA_STRING:              '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
fragment SQUOTA_STRING:              '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
fragment BQUOTA_STRING:              '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
fragment DEC_DIGIT:                  [0-9];
