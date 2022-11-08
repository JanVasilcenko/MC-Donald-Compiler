
package ast;


public class IntLitExpression
	extends Expression
{
	public IntegerLiteral literal;
	
	
	public IntLitExpression( IntegerLiteral literal )
	{
		this.literal = literal;
	}
}