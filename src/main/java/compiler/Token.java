package compiler;

import static compiler.TokenKind.*;

public class Token {
    public TokenKind kind;
    public String spelling;

    public Token( TokenKind kind, String spelling )
    {
        this.kind = kind;
        this.spelling = spelling;

        if( kind == IDENTIFIER )
/*
			for( byte i = 0; i < SPELLINGS.length; ++i )
				if( spelling.equals( SPELLINGS[i] ) ) {
					this.kind = i;
					break;
				}
*/
            for( TokenKind tk: KEYWORDS )
                if( spelling.equals( tk.getSpelling() ) ) {
                    this.kind = tk;
                    break;
                }
    }


    public boolean isAssignOperator()
    {
        if( kind == OPERATOR )
            return containsOperator( spelling, ASSIGNOPS );
        else
            return false;
    }

    public boolean isAddOperator()
    {
        if( kind == OPERATOR )
            return containsOperator( spelling, ADDOPS );
        else
            return false;
    }

    public boolean isBooleanOperator()
    {
        if(kind == OPERATOR )
            return containsOperator( spelling, BOOLOPS );
        else
            return false;
    }

    public boolean isMulOperator()
    {
        if( kind == OPERATOR )
            return containsOperator( spelling, MULOPS );
        else
            return false;
    }


    private boolean containsOperator( String spelling, String OPS[] )
    {
        for( int i = 0; i < OPS.length; ++i )
            if( spelling.equals( OPS[i] ) )
                return true;

        return false;
    }

    private static final TokenKind[] KEYWORDS = { IMLOVINIT, LEFTOPENING, CHEESE, MEAL, BURGER, RIGHTOPENING, SERVE, EATOUT, EATIN, NUGGETS, ICE, SHAKE,HARDOPENINGLEFT,HARDOPENINGRIGHT,HAPPYMEAL };

    private static final String BOOLOPS[] =
            {
                    "A",
                    "O"
            };

    private static final String ASSIGNOPS[] =
            {
                    "=",
            };


    private static final String ADDOPS[] =
            {
                    "+",
                    "-",
            };


    private static final String MULOPS[] =
            {
                    "*",
                    "/",

            };

//    public Token(TokenKind kind, String spelling) {
//        this.kind = kind;
//        this.spelling = spelling;
//
//        if (kind == TokenKind.IDENTIFIER) {
//            for (TokenKind tokenKind : KEYWORDS) {
//                if (spelling.equals(tokenKind.getSpelling())) {
//                    this.kind = tokenKind;
//                    break;
//                }
//            }
//
//            IsOperator();
//        }
//    }
//
//    private void IsOperator() {
//        for (String operator : addOperators) {
//            if (spelling.equals(operator)) {
//                this.kind = TokenKind.OPERATOR;
//                break;
//            }
//        }
//        for (String operator : assignOperators) {
//            if (spelling.equals(operator)) {
//                this.kind = TokenKind.OPERATOR;
//                break;
//            }
//        }
//        for (String operator : multiplicativeOperators) {
//            if (spelling.equals(operator)) {
//                this.kind = TokenKind.OPERATOR;
//                break;
//            }
//        }
//    }
//
//    public boolean isAssignOperator() {
//        if (kind == TokenKind.OPERATOR) {
//            return containsOperator(spelling, assignOperators);
//        }
//        return false;
//    }
//
//    public boolean isAddOperator() {
//        if (kind == TokenKind.OPERATOR) {
//            return containsOperator(spelling, addOperators);
//        }
//        return false;
//    }
//
//    public boolean isMulOperator() {
//        if (kind == TokenKind.OPERATOR) {
//            return containsOperator(spelling, multiplicativeOperators);
//        }
//        return false;
//    }
//
//    private boolean containsOperator(String spelling, String[] ops) {
//        for (int i = 0; i < ops.length; i++) {
//            if (spelling.equals(ops[i])) {
//                return true;
//            }
//        }
//        return false;
//    }

 //   private static final TokenKind[] KEYWORDS = {TokenKind.BURGER, TokenKind.CHEESE, TokenKind.EATIN, TokenKind.EATOUT, TokenKind.SHAKE, TokenKind.MC};
//    private static final String[] assignOperators =
//            {
//                    "ordered",
//                    "doubleordered"
//            };
//
//
//    private static final String[] addOperators =
//            {
//                    "chicken",
//                    "vegan",
//            };
//
//
//    private static final String[] multiplicativeOperators =
//            {
//                    "beef",
//                    "fish",
//            };
}
