

package ast;


public class VarExpression
        extends Expression {
    public Identifier name;
    public VariableDeclaration declaration;
    public int index = -1;


    public VarExpression(Identifier name) {
        this.name = name;
    }

    public VarExpression(Identifier name, int index)
    {
        this.name = name;
        this.index = index;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitVarExpression(this, arg);
    }
}