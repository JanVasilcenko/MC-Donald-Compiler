
 
package ast;


public class CallExpression
	extends Expression
{
	public Identifier name;
	public ExpList args;
	
	
	public CallExpression( Identifier name, ExpList args )
	{
		this.name = name;
		this.args = args;
	}
}