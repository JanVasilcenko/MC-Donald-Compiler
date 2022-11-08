
 
package ast;


public class VarExpression
	extends Expression
{
	public Identifier name;
	
	
	public VarExpression( Identifier name )
	{
		this.name = name;
	}
}