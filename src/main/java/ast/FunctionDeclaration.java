
 
package ast;

public class FunctionDeclaration
	extends Declaration
{
	public Identifier name;
	public Declarations params;
	public Block block;
	public Expression retExp;
	
	public FunctionDeclaration( Identifier name, Declarations params,
	                            Block block, Expression retExp )
	{
		this.name = name;
		this.params = params;
		this.block = block;
		this.retExp = retExp;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitFunctionDeclaration(this, arg);
	}
}