

package ast;


public abstract class Declaration
        extends AST {
    public abstract Object visit(Visitor visitor, Object arg);
}