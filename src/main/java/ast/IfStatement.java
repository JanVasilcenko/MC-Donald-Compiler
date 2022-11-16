

package ast;


public class IfStatement
        extends Statement {
    public Expression exp;
    public Statements thenPart;
    public Statements elsePart;


    public IfStatement(Expression exp, Statements thenPart, Statements elsePart) {
        this.exp = exp;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIfStatement(this, arg);
    }
}