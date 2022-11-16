
package ast;


import java.util.Vector;


public class Declarations
        extends AST {
    public Vector<Declaration> dec = new Vector<Declaration>();

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitDeclarations(this, arg);
    }
}