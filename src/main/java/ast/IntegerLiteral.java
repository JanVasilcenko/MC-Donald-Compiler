
 
package ast;


public class IntegerLiteral
	extends Terminal
{
	public IntegerLiteral( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitIntegerLiteral(this, arg);
	}
}