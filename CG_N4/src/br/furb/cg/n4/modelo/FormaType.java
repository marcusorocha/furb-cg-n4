package br.furb.cg.n4.modelo;

import java.util.Random;

public enum FormaType
{
	QUADRADO, CIRCULO, CRUZ;
	
	public static FormaType sortearTipo()
	{
		int indice = new Random().nextInt(values().length);
		
		return values()[indice];
	}
}