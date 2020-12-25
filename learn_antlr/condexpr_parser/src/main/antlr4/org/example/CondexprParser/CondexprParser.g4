// https://github.com/antlr/grammars-v4/blob/master/sql/mysql/Positive-Technologies/MySqlParser.g4

parser grammar CondexprParser;

options { tokenVocab=CondexprLexer; }

//=============================================================================================
//    DB Objects
fullId
    : uid
    ;

fullColumnName
    : uid
    ;

uid
    : simpleId
    ;

simpleId
    : ID
    ;

//=============================================================================================
// Literals
decimalLiteral
    : DECIMAL_LITERAL | ZERO_DECIMAL | ONE_DECIMAL | TWO_DECIMAL
    ;

stringLiteral
    : STRING_LITERAL+
    ;

booleanLiteral
    : TRUE | FALSE;

nullNotnull
    : NOT? (NULL_LITERAL | NULL_SPEC_LITERAL)
    ;

constant
    : stringLiteral | decimalLiteral
    | '-' decimalLiteral
    | booleanLiteral
    | REAL_LITERAL
    | NOT? nullLiteral=(NULL_LITERAL | NULL_SPEC_LITERAL)
    ;

//=============================================================================================
//    Data Types
dataType
    : typeName=(
      CHAR | CHARACTER | VARCHAR | TEXT | LONGTEXT
      )                                                             #stringDataType
    | typeName=(
        TINYINT | SMALLINT | INT | INTEGER | BIGINT
      )                                                             #dimensionDataType
    ;

convertedDataType
    : typeName=(DATE | DATETIME | TIME)
    | typeName=DECIMAL lengthTwoDimension?
    | (SIGNED | UNSIGNED) INTEGER?
    ;

lengthTwoDimension
    : '(' decimalLiteral ',' decimalLiteral ')'
    ;

//=============================================================================================
// Common Lists
expressions
    : expression (',' expression)*
    ;

//=============================================================================================
// Functions
functionCall
    : specificFunction                                              #specificFunctionCall
    | fullId '(' functionArgs? ')'                                  #udfFunctionCall
    ;

specificFunction
    : CAST '(' expression AS convertedDataType ')'                  #dataTypeFunctionCall
    | CONVERT '(' expression separator=',' convertedDataType ')'    #dataTypeFunctionCall
    ;

functionArgs
    : (constant | fullColumnName | functionCall | expression)
    (
      ','
      (constant | fullColumnName | functionCall | expression)
    )*
    ;

//=============================================================================================
// Expressions, predicates
expression
    : notOperator=(NOT | '!') expression                            #notExpression
    | expression logicalOperator expression                         #logicalExpression
    | predicate IS NOT? testValue=(TRUE | FALSE | UNKNOWN)          #isExpression
    | predicate                                                     #predicateExpression
    ;

predicate
    : predicate NOT? IN '(' expressions ')'                         #inPredicate
    | predicate IS nullNotnull                                      #isNullPredicate
    | left=predicate comparisonOperator right=predicate             #binaryComparasionPredicate
    | predicate NOT? BETWEEN predicate AND predicate                #betweenPredicate
    | predicate NOT? LIKE predicate (ESCAPE STRING_LITERAL)?        #likePredicate
    | expressionAtom                                                #expressionAtomPredicate
    ;

expressionAtom
    : constant                                                      #constantExpressionAtom
    | fullColumnName                                                #fullColumnNameExpressionAtom
    | functionCall                                                  #functionCallExpressionAtom
    | unaryOperator expressionAtom                                  #unaryExpressionAtom
    | '(' expression (',' expression)* ')'                          #nestedExpressionAtom
    ;

unaryOperator
    : '!' | '~' | '+' | '-' | NOT
    ;

comparisonOperator
    : '=' | '>' | '<' | '<' '=' | '>' '='
    | '<' '>' | '!' '=' | '<' '=' '>'
    ;

logicalOperator
    : AND | '&' '&' | XOR | OR | '|' '|'
    ;
