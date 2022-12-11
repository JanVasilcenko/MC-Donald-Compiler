package ast;

public interface Visitor {
    Object visitProgram(Program program, Object arg);

    Object visitBlock(Block block, Object arg);

    Object visitDeclarations(Declarations declarations, Object arg);

    Object visitVariableDeclaration(VariableDeclaration variableDeclaration, Object arg);

    Object visitFunctionDeclaration(FunctionDeclaration functionDeclaration, Object arg);
    Object visitArrayDeclaration(ArrayDeclaration arrayDeclaration, Object arg);

    Object visitStatements(Statements statements, Object arg);

    Object visitExpressionStatement(ExpressionStatement expressionStatement, Object arg);

    Object visitIfStatement(IfStatement ifStatement, Object arg);

    Object visitWhileStatement(WhileStatement whileStatement, Object arg);

    Object visitSayStatement(SayStatement sayStatement, Object arg);

    Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg);

    Object visitVarExpression(VarExpression varExpression, Object arg);

    Object visitCallExpression(CallExpression callExpression, Object arg);
    Object visitArrayExpression(ArrayExpression arrayExpression, Object arg);

    Object visitUnaryExpression(UnaryExpression unaryExpression, Object arg);

    Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg);

    Object visitExpList(ExpList expList, Object arg);

    Object visitIdentifier(Identifier identifier, Object arg);

    Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg);

    Object visitOperator(Operator operator, Object arg);
}
