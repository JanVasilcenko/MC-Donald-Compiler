
 
package ast;


public abstract class Expression
	extends AST
{
	public abstract Object visit(Visitor visitor, Object arg);
}