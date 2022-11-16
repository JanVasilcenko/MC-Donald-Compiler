
package ast;


import java.util.Vector;


public class ExpList
	extends AST
{
	public Vector<Expression> exp = new Vector<Expression>();

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitExpList(this, arg);
	}
}