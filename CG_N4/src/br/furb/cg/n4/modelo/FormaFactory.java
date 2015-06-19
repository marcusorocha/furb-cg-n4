package br.furb.cg.n4.modelo;

public final class FormaFactory
{
	private FormaFactory()
	{
		// Classes factory tem seu constructor privado, pois não podem ser instanciadas.
	}
	
	public static Forma criarForma(FormaType tipo)
	{
		switch(tipo)
		{
			case CIRCULO : return new Circulo();
			case QUADRADO : return new Quadrado();
			case CRUZ : return new Cruz();
		}
		
		return null;
	}
}