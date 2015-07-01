package br.furb.cg.n4.modelo;

public abstract class Peca extends ObjetoGrafico
{
	private boolean encaixada = false;
	
	public Peca()
	{
		//setIluminacao(true);
	}
	
	@Override
	public void setSelecionado(boolean selecionado)
	{
		super.setSelecionado(selecionado);
		
		//if (isEncaixada())			
		//	transladar(0, 0, (selecionado) ? 10 : 0);
		//else
		//	transladar(0, 0, (selecionado) ? 10 : -10);
	}
	
	public boolean isEncaixada()
	{
		return encaixada;
	}

	public void setEncaixada(boolean encaixada)
	{
		this.encaixada = encaixada;
	}
	
	public abstract boolean verificaEncaixe(Mosaico mosaico);
	public abstract void encaixar(Mosaico mosaico);
	public abstract void desencaixar(Mosaico mosaico);

}
