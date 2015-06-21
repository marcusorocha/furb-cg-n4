package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

public class Circulo extends Forma
{
	private static final int QTD_PONTOS_CIRCULO = 360;
	private static final int RAIO_CIRCULO = 5;
	private static final int X_INICIAL = 0;
	private static final int Y_INICIAL = 0;
	
	private List<Ponto> vertices;
	
	public Circulo()
	{
		vertices = new ArrayList<Ponto>();
		
		criarVertices();
	}
	
	private void criarVertices()
	{	
		double a = 0;

		for (int i = 0; i < QTD_PONTOS_CIRCULO; i++)
		{
			a = (360 * i) / QTD_PONTOS_CIRCULO;

			vertices.add(criarPontoCirculo(a, RAIO_CIRCULO, X_INICIAL, Y_INICIAL));
		}
	}

	@Override
	List<Ponto> getVerticesForma()
	{
		return vertices;
	}
	
	private double RetornaX(double angulo, double raio)
	{
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	private double RetornaY(double angulo, double raio)
	{
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}

	private Ponto criarPontoCirculo(double angulo, double raio, double posX, double posY)
	{
		double x = RetornaX(angulo, raio) + posX;
		double y = RetornaY(angulo, raio) + posY;

		return new Ponto(x, y);
	}

	@Override
	FormaType getTipoForma()
	{
		return FormaType.CIRCULO;
	}

}
