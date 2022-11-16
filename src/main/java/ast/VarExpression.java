

package ast;


public class VarExpression
        extends Expression {
    public Identifier name;
    public VariableDeclaration declaration;


    public VarExpression(Identifier name) {
        this.name = name;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitVarExpression(this, arg);
    }
}