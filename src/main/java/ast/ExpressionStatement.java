
 
package ast;


public class ExpressionStatement
	extends Statement
{
	public Expression exp;
	
	
	public ExpressionStatement( Expression exp )
	{
		this.exp = exp;
	}
}