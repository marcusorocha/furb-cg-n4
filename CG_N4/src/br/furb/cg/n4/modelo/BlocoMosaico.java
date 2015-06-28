package br.furb.cg.n4.modelo;

public class BlocoMosaico extends Bloco
{
	private boolean ocupado = false;
	
	public BlocoMosaico(Forma forma)
	{
		super(forma);
	}
		
	public boolean isOcupado()
	{
		return ocupado;
	}
	
	public void setOcupado(boolean ocupado)
	{
		this.ocupado = ocupado;
	}	
	
}
