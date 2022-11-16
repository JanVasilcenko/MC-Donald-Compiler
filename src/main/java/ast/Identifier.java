
 
package ast;


public class Identifier
	extends Terminal
{
	public Identifier( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitIdentifier(this, arg);
	}
}