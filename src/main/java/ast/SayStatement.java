
 
package ast;


public class SayStatement
	extends Statement
{
	public Expression exp;
	
	
	public SayStatement( Expression exp )
	{
		this.exp = exp;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitSayStatement(this, arg);
	}
}