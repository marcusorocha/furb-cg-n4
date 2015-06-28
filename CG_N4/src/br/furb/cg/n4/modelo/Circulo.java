package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import br.furb.cg.n4.utils.FuncoesGeometricas;

public class Circulo extends Forma
{
	private static final double RAIO_CIRCULO = 5;
	private static final int QTD_PONTOS_CIRCULO = 360;	
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
		Ponto posicao = new Ponto(X_INICIAL, Y_INICIAL);
		
		vertices.addAll(FuncoesGeometricas.gerarCirculoDePontos(posicao, RAIO_CIRCULO, QTD_PONTOS_CIRCULO));
	}

	@Override
	List<Ponto> getVerticesForma()
	{
		return vertices;
	}

	@Override
	FormaType getTipoForma()
	{
		return FormaType.CIRCULO;
	}

}
