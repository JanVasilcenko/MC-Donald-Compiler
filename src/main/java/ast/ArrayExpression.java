package ast;

public class ArrayExpression extends Expression {

    public Identifier name;

    public int index = -1;
    public ArrayDeclaration arrayDeclaration;
    public ExpList elements;

    public ArrayExpression(Identifier name, int index) {
        this.name = name;
        this.index = index;
        this.elements = new ExpList();
    }

    public void setupExpressions() {
        for (int i = 0; i < arrayDeclaration.integerLiteral.dec.size(); i++) {
            VariableDeclaration variableDeclaration = (VariableDeclaration) arrayDeclaration.integerLiteral.dec.get(i);
            elements.exp.add(new VarExpression(variableDeclaration.id, i));
        }
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitArrayExpression(this, arg);
    }
}
