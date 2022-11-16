
 
package ast;


public class ExpressionStatement
	extends Statement
{
	public Expression exp;
	
	
	public ExpressionStatement( Expression exp )
	{
		this.exp = exp;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitExpressionStatement(this, arg);
	}
}