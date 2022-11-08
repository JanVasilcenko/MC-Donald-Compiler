
 
package ast;


public class Block
	extends AST
{
	public Declarations decs;
	public Statements stats;
	
	
	public Block( Declarations decs, Statements stats )
	{
		this.decs = decs;
		this.stats = stats;
	}
}