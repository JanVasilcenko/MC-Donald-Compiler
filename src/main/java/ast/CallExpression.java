

package ast;


public class CallExpression
        extends Expression {
    public Identifier name;
    public ExpList args;

    public FunctionDeclaration functionDeclaration;

    public CallExpression(Identifier name, ExpList args) {
        this.name = name;
        this.args = args;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitCallExpression(this, arg);
    }
}