package ast;

import encoder.Address;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

public class ArrayDeclaration extends Declaration {
    public Identifier name;
    public int size;

    public Declarations integerLiteral;

    public ArrayDeclaration(Identifier name, int size) {
        this.name = name;
        this.size = size;
        integerLiteral = new Declarations();
        for (int i = 0; i < size; i++) {
            integerLiteral.dec.add(new VariableDeclaration(new Identifier(name.spelling + Integer.valueOf(i).toString())));
        }
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitArrayDeclaration(this, arg);
    }
}
