package scanner;

import static scanner.TokenKind.*;

public class Parser {
    private Scanner scan;

    private Token currentTerminal;

    public Parser(Scanner scan) {
        this.scan = scan;
        currentTerminal = scan.scan();
    }

    public void parseProgram()
    {
        parseBlock();

        if( currentTerminal.kind != EOT )
            System.out.println( "Tokens found after end of program" );
    }

    private void parseBlock()
    {
        accept( IMLOVINIT );
        parseDeclarations();
        accept( LEFTOPENING );
        parseStatements();
        accept( RIGHTOPENING );
    }

    private void parseDeclarations()
    {
        while( currentTerminal.kind == NUGGETS ||
                currentTerminal.kind == ICE ||
                currentTerminal.kind == MEAL )
            parseOneDeclaration();
    }

    private void parseOneDeclaration()
    {
        switch( currentTerminal.kind ) {
            case NUGGETS:
                accept( NUGGETS );
                accept( IDENTIFIER );
                accept( SEMICOLON );
                break;

            case ICE:
                accept( ICE );
                accept( IDENTIFIER );
                accept( SEMICOLON );
                break;

            case MEAL:
                accept( MEAL );
                accept( IDENTIFIER );
                accept( LEFTPAREN );

                if( currentTerminal.kind == IDENTIFIER )
                    parseIdList();

                accept( RIGHTPAREN );
                parseBlock();
                accept( SERVE );
                parseExpression();
                accept( SEMICOLON );
                break;

            default:
                System.out.println( "nuggets, ice or meal expected" );
                break;
        }
    }

    private void parseIdList()
    {
        accept( IDENTIFIER );

        while( currentTerminal.kind == COMMA ) {
            accept( COMMA );
            accept( IDENTIFIER );
        }
    }

    private void parseStatements()
    {
        while( currentTerminal.kind == IDENTIFIER ||
                currentTerminal.kind == OPERATOR ||
                currentTerminal.kind == INTEGERLITERAL ||
                currentTerminal.kind == LEFTPAREN ||
                currentTerminal.kind == BURGER ||
                currentTerminal.kind == CHEESE ||
                currentTerminal.kind == SHAKE ||
                currentTerminal.kind == EATIN ||
                currentTerminal.kind == EATOUT)
        {
            System.out.println(currentTerminal.kind);
            parseOneStatement();
        }
    }

    private void parseOneStatement()
    {
        switch( currentTerminal.kind ) {
            case IDENTIFIER:
            case INTEGERLITERAL:
            case OPERATOR:
            case LEFTPAREN:
                parseExpression();
                accept( SEMICOLON );
                System.out.println("1");
                break;

            case BURGER:
                accept( BURGER );
                parseExpression();
                accept( LEFTOPENING );
                parseStatements();
                accept( RIGHTOPENING );

                if( currentTerminal.kind == CHEESE ) {
                    accept( CHEESE );
                    accept( LEFTOPENING );
                    parseStatements();
                    accept( RIGHTOPENING );
                }

                accept( SEMICOLON );
                System.out.println("2");
                break;

            case SHAKE:
                accept( SHAKE );
                accept(LEFTPAREN);
                parseExpression();
                accept(RIGHTPAREN);
                accept( LEFTOPENING );
                parseStatements();
                accept( RIGHTOPENING );

                System.out.println("3");
                break;

            case EATOUT:
                accept( EATOUT );
                parseExpression();
                accept( SEMICOLON );
                System.out.println("4");
                break;

            case EATIN:
                accept( EATIN );
                parseExpression();
                accept( SEMICOLON );
                System.out.println("5");
                break;

            default:
                System.out.println( "Error in statement" );
                break;
        }
    }

    private void parseExpression()
    {
        parsePrimary();
        while( currentTerminal.kind == OPERATOR ) {
            accept( OPERATOR );
            parsePrimary();
        }
    }

    private void parsePrimary()
    {
        switch( currentTerminal.kind ) {
            case IDENTIFIER:
                accept( IDENTIFIER );

                if( currentTerminal.kind == LEFTPAREN ) {
                    accept( LEFTPAREN );

                    if( currentTerminal.kind == IDENTIFIER ||
                            currentTerminal.kind == INTEGERLITERAL ||
                            currentTerminal.kind == OPERATOR ||
                            currentTerminal.kind == LEFTPAREN )
                        parseExpressionList();


                    accept( RIGHTPAREN );
                }
                break;

            case INTEGERLITERAL:
                accept( INTEGERLITERAL );
                break;

            case OPERATOR:
                accept( OPERATOR );
                parsePrimary();
                break;

            case LEFTPAREN:
                accept( LEFTPAREN );
                parseExpression();
                accept( RIGHTPAREN );
                break;

            default:
                System.out.println( "Error in primary" );
                break;
        }
    }

    private void parseExpressionList()
    {
        parseExpression();
        while( currentTerminal.kind == COMMA ) {
            accept( COMMA );
            parseExpression();
        }
    }


    private void accept( TokenKind expected )
    {
        if( currentTerminal.kind == expected )
            currentTerminal = scan.scan();
        else
            System.out.println( "Expected token of kind " + expected );
    }

}
