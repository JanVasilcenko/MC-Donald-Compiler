

package ast;


public abstract class Statement
        extends AST {
    public abstract Object visit(Visitor visitor, Object arg);
}