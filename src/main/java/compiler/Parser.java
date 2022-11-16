package compiler;

import ast.*;

import static compiler.TokenKind.*;

public class Parser {
    private Scanner scan;

    private Token currentTerminal;

    public Parser(Scanner scan) {
        this.scan = scan;
        currentTerminal = scan.scan();
    }

    public Program parseProgram() {
        Block block = parseBlock();

        if (currentTerminal.kind != EOT)
            System.out.println("Tokens found after end of program");

        return new Program(block);
    }

    private Block parseBlock() {
        accept(IMLOVINIT);
        Declarations decs = parseDeclarations();
        accept(LEFTOPENING);
        Statements stats = parseStatements();
        accept(RIGHTOPENING);

        return new Block(decs, stats);
    }

    private Declarations parseDeclarations() {
        Declarations decs = new Declarations();

        while (currentTerminal.kind == NUGGETS ||
                currentTerminal.kind == ICE ||
                currentTerminal.kind == MEAL) {
            decs.dec.add(parseOneDeclaration());
        }

        return decs;
    }

    private Declaration parseOneDeclaration() {
        switch (currentTerminal.kind) {
            case NUGGETS:
                accept(NUGGETS);
                Identifier idNuggets = parseIdentifier();
                accept(SEMICOLON);

                return new VariableDeclaration(idNuggets);

            case ICE:
                accept(ICE);
                Identifier idIce = parseIdentifier();
                accept(SEMICOLON);

                return new VariableDeclaration(idIce);

            case MEAL:
                accept(MEAL);
                Identifier name = parseIdentifier();
                accept(LEFTPAREN);

                Declarations params = null;
                if (currentTerminal.kind == IDENTIFIER)
                    params = parseIdList();

                accept(RIGHTPAREN);
                Block block = parseBlock();
                accept(SERVE);
                Expression retExp = parseExpression();
                accept(SEMICOLON);

                return new FunctionDeclaration(name, params, block, retExp);

            default:
                System.out.println("nuggets, ice or meal expected");
                return null;
        }
    }

    private Declarations parseIdList() {
        Declarations list = new Declarations();

        list.dec.add(new VariableDeclaration(parseIdentifier()));

        while (currentTerminal.kind == COMMA) {
            accept(COMMA);
            list.dec.add(new VariableDeclaration(parseIdentifier()));
        }

        return list;
    }

    private Statements parseStatements() {
        Statements stats = new Statements();

        while (currentTerminal.kind == IDENTIFIER ||
                currentTerminal.kind == OPERATOR ||
                currentTerminal.kind == INTEGERLITERAL ||
                currentTerminal.kind == LEFTPAREN ||
                currentTerminal.kind == BURGER ||
                currentTerminal.kind == CHEESE ||
                currentTerminal.kind == SHAKE ||
                currentTerminal.kind == EATIN ||
                currentTerminal.kind == EATOUT) {
            System.out.println(currentTerminal.kind);
            stats.stat.add(parseOneStatement());
        }

        return stats;
    }

    private Statement parseOneStatement() {
        switch (currentTerminal.kind) {
            case IDENTIFIER:
            case INTEGERLITERAL:
            case OPERATOR:
            case LEFTPAREN:
                Expression exp = parseExpression();
                accept(SEMICOLON);
                System.out.println("1");

                return new ExpressionStatement(exp);

            case BURGER:
                accept(BURGER);
                Expression ifExp = parseExpression();
                accept(LEFTOPENING);
                Statements thenPart = parseStatements();
                accept(RIGHTOPENING);

                Statements elsePart = null;
                if (currentTerminal.kind == CHEESE) {
                    accept(CHEESE);
                    accept(LEFTOPENING);
                    elsePart = parseStatements();
                    accept(RIGHTOPENING);
                }

                accept(SEMICOLON);
                System.out.println("2");

                return new IfStatement(ifExp, thenPart, elsePart);

            case SHAKE:
                accept(SHAKE);
                accept(LEFTPAREN);
                Expression whileExp = parseExpression();
                accept(RIGHTPAREN);
                accept(LEFTOPENING);
                Statements stats = parseStatements();
                accept(RIGHTOPENING);

                System.out.println("3");

                return new WhileStatement(whileExp, stats);

            case EATOUT:
                accept(EATOUT);
                Expression eatOut = parseExpression();
                accept(SEMICOLON);
                System.out.println("4");

                return new SayStatement(eatOut);

            case EATIN:
                accept(EATIN);
                Expression eatIt = parseExpression();
                accept(SEMICOLON);
                System.out.println("5");

                return new SayStatement(eatIt);

            default:
                System.out.println("Error in statement");
                return null;
        }
    }

    private Expression parseExpression() {
        Expression res = parsePrimary();
        while (currentTerminal.kind == OPERATOR) {
            Operator op = parseOperator();
            Expression tmp = parsePrimary();

            res = new BinaryExpression(op, res, tmp);
        }

        return res;
    }

    private Expression parsePrimary() {
        switch (currentTerminal.kind) {
            case IDENTIFIER:
                Identifier name = parseIdentifier();

                if (currentTerminal.kind == LEFTPAREN) {
                    accept(LEFTPAREN);

                    ExpList args;

                    if (currentTerminal.kind == IDENTIFIER ||
                            currentTerminal.kind == INTEGERLITERAL ||
                            currentTerminal.kind == OPERATOR ||
                            currentTerminal.kind == LEFTPAREN)
                        args = parseExpressionList();
                    else
                        args = new ExpList();

                    accept(RIGHTPAREN);

                    return new CallExpression(name, args);
                } else
                    return new VarExpression(name);


            case INTEGERLITERAL:
                IntegerLiteral lit = parseIntegerLiteral();
                return new IntLitExpression(lit);

            case OPERATOR:
                Operator op = parseOperator();
                Expression exp = parsePrimary();
                return new UnaryExpression(op, exp);

            case LEFTPAREN:
                accept(LEFTPAREN);
                Expression res = parseExpression();
                accept(RIGHTPAREN);
                return res;

            default:
                System.out.println("Error in primary");
                return null;
        }
    }

    private ExpList parseExpressionList() {
        ExpList exps = new ExpList();

        exps.exp.add(parseExpression());
        while (currentTerminal.kind == COMMA) {
            accept(COMMA);
            exps.exp.add(parseExpression());
        }

        return exps;
    }

    private Identifier parseIdentifier() {
        if (currentTerminal.kind == IDENTIFIER) {
            Identifier res = new Identifier(currentTerminal.spelling);
            currentTerminal = scan.scan();

            return res;
        } else {
            System.out.println("Identifier expected at position: " + scan.getPosition());

            return new Identifier("???");
        }
    }


    private IntegerLiteral parseIntegerLiteral() {
        if (currentTerminal.kind == INTEGERLITERAL) {
            IntegerLiteral res = new IntegerLiteral(currentTerminal.spelling);
            currentTerminal = scan.scan();

            return res;
        } else {
            System.out.println("Integer literal expected");

            return new IntegerLiteral("???");
        }
    }


    private Operator parseOperator() {
        if (currentTerminal.kind == OPERATOR) {
            Operator res = new Operator(currentTerminal.spelling);
            currentTerminal = scan.scan();

            return res;
        } else {
            System.out.println("Operator expected");

            return new Operator("???");
        }
    }


    private void accept(TokenKind expected) {
        if (currentTerminal.kind == expected)
            currentTerminal = scan.scan();
        else
            System.out.println("Expected token of kind " + expected);
    }

}
