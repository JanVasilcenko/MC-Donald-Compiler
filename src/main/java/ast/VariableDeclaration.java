
 
package ast;


import encoder.Address;

public class VariableDeclaration
	extends Declaration
{
	public Identifier id;

	public Address address;
	
	
	public VariableDeclaration( Identifier id )
	{
		this.id = id;
	}

	public Object visit(Visitor visitor, Object arg) {
		return visitor.visitVariableDeclaration(this, arg);
	}
}