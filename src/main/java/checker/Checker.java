package checker;

import ast.*;

import java.util.Vector;

public class Checker implements Visitor {

    private IdentificationTable identificationTable = new IdentificationTable();

    public void check(Program program) {
        program.visit(this, null);
    }

    @Override
    public Object visitProgram(Program program, Object arg) {
        identificationTable.openScope();

        program.block.visit(this, null);

        identificationTable.closeScope();

        return null;
    }

    @Override
    public Object visitBlock(Block block, Object arg) {
        block.decs.visit(this, null);
        block.stats.visit(this, null);
        return null;
    }

    @Override
    public Object visitDeclarations(Declarations declarations, Object arg) {
        for (Declaration declaration : declarations.dec) {
            declaration.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitVariableDeclaration(VariableDeclaration variableDeclaration, Object arg) {
        String id = (String) variableDeclaration.id.visit(this, null);

        identificationTable.enter(id, variableDeclaration);

        return null;
    }

    @Override
    public Object visitFunctionDeclaration(FunctionDeclaration functionDeclaration, Object arg) {
        String id = (String) functionDeclaration.name.visit(this, null);

        identificationTable.enter(id, functionDeclaration);
        identificationTable.openScope();

        if (functionDeclaration.params != null) {
            functionDeclaration.params.visit(this, null);
        }
        functionDeclaration.block.visit(this, null);
        functionDeclaration.retExp.visit(this, null);

        identificationTable.closeScope();
        return null;
    }

    @Override
    public Object visitArrayDeclaration(ArrayDeclaration arrayDeclaration, Object arg) {
        String id = (String) arrayDeclaration.name.visit(this, null);
        identificationTable.enter(id, arrayDeclaration);

        if (arrayDeclaration.integerLiteral != null) {
            arrayDeclaration.integerLiteral.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitStatements(Statements statements, Object arg) {
        for (Statement statement : statements.stat) {
            statement.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatement, Object arg) {
        expressionStatement.exp.visit(this, null);
        return null;
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatement, Object arg) {
        ifStatement.exp.visit(this, null);
        ifStatement.thenPart.visit(this, null);
        ifStatement.elsePart.visit(this, null);
        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatement, Object arg) {
        whileStatement.exp.visit(this, null);
        whileStatement.stats.visit(this, null);
        return null;
    }

    @Override
    public Object visitSayStatement(SayStatement sayStatement, Object arg) {
        sayStatement.exp.visit(this, null);
        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) {
        Type type1 = (Type) binaryExpression.operand1.visit(this, null);
        Type type2 = (Type) binaryExpression.operand2.visit(this, null);
        String operator = (String) binaryExpression.operator.visit(this, null);

        if (operator.equals("ordered") && type1.rvalueOnly) {
            System.out.println("Left-hand side of ordered must be a variable");
        }
        return new Type(true);
    }

    @Override
    public Object visitVarExpression(VarExpression varExpression, Object arg) {
        String id = (String) varExpression.name.visit(this, null);

        Declaration declaration = identificationTable.retrieve(id);
        if (declaration == null) {
            System.out.println("Id " + id + " is not declared!");
        } else if (!(declaration instanceof VariableDeclaration)) {
            System.out.println("Id " + id + " is not a variable!");
        } else {
            varExpression.declaration = (VariableDeclaration) declaration;
        }
        return new Type(false);
    }

    @Override
    public Object visitCallExpression(CallExpression callExpression, Object arg) {
        String id = (String) callExpression.name.visit(this, null);
        Vector<Type> type = (Vector<Type>) callExpression.args.visit(this, null);

        Declaration declaration = identificationTable.retrieve(id);
        if (declaration == null) {
            System.out.println("Id " + id + " is not declared!");
        } else if (!(declaration instanceof FunctionDeclaration)) {
            System.out.println("Id " + id + " is not a variable!");
        } else {
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declaration;
            callExpression.functionDeclaration = functionDeclaration;
            if (functionDeclaration.params != null && functionDeclaration.params.dec.size() != type.size()) {
                System.out.println("Incorrect number of arguments in call to " + id);
            }
        }
        return new Type(true);
    }

    @Override
    public Object visitArrayExpression(ArrayExpression arrayExpression, Object arg) {
        String id = (String) arrayExpression.name.visit(this, null);

        Declaration declaration = identificationTable.retrieve(id);
        if (declaration == null) {
            System.out.println("Id " + id + " is not declared!");
        } else if (!(declaration instanceof ArrayDeclaration)) {
            System.out.println("Id " + id + " is not a variable!");
        } else {
            ArrayDeclaration arrayDeclaration = (ArrayDeclaration) declaration;
            arrayExpression.arrayDeclaration = arrayDeclaration;
            arrayExpression.setupExpressions();
            Vector<Type> type = (Vector<Type>) arrayExpression.elements.visit(this, null);
            if (arrayDeclaration.size < 1 && type.size() < 1) {
                System.out.println("Array initialized with incorrect size!");
            }
            if (arrayExpression.index >= arrayDeclaration.size)
            {
                System.out.println("Array index of out bounds");
            }
        }
        return new Type(false);
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression unaryExpression, Object arg) {
        unaryExpression.operand.visit(this, null);
        String operator = (String) unaryExpression.operator.visit(this, null);

        if (!operator.equals("+") && !operator.equals("-")) {
            System.out.println("Only + or - is allowed as unary operator");
        }
        return new Type(true);
    }

    @Override
    public Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) {
        intLitExpression.literal.visit(this, null);
        return new Type(true);
    }

    @Override
    public Object visitExpList(ExpList expList, Object arg) {
        Vector<Type> types = new Vector<>();

        for (Expression expression : expList.exp) {
            types.add((Type) expression.visit(this, null));
        }
        return types;
    }

    @Override
    public Object visitIdentifier(Identifier identifier, Object arg) {
        return identifier.spelling;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg) {
        return null;
    }

    @Override
    public Object visitOperator(Operator operator, Object arg) {
        return operator.spelling;
    }
}
