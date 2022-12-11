

package ast;


import encoder.Address;

public abstract class Declaration
        extends AST {
    public Address address;
    public abstract Object visit(Visitor visitor, Object arg);
}