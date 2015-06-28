package br.furb.cg.n4.modelo;

public abstract class Peca extends ObjetoGrafico
{
	public abstract boolean verificaEncaixe(Mosaico mosaico);
	public abstract void encaixar(Mosaico mosaico);
	public abstract void desencaixar(Mosaico mosaico);
}
