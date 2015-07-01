package br.furb.cg.n4.modelo;

public class Reta 
{
	private Ponto pontoA;
	private Ponto pontoB;
	
	public Reta()
	{
		this(null, null);
	}
	
	public Reta(Ponto pA, Ponto pB)
	{
		this.pontoA = pA;
		this.pontoB = pB;
	}
	
	public Ponto getPontoA() 
	{
		return pontoA;
	}
	
	public void setPontoA(Ponto pontoA) 
	{
		this.pontoA = pontoA;
	}
	
	public Ponto getPontoB() 
	{
		return pontoB;
	}
	
	public void setPontoB(Ponto pontoB) 
	{
		this.pontoB = pontoB;
	}
}
