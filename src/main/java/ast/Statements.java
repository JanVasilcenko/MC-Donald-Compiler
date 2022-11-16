
package ast;


import java.util.Vector;


public class Statements
        extends AST {
    public Vector<Statement> stat = new Vector<Statement>();

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitStatements(this, arg);
    }
}