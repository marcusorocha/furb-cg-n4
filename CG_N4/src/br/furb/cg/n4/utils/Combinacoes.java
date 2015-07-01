package br.furb.cg.n4.utils;

import java.util.ArrayList;
import java.util.List;

import br.furb.cg.n4.modelo.FormaType;
import br.furb.cg.n4.modelo.Mosaico;
import br.furb.cg.n4.modelo.Peca;
import br.furb.cg.n4.modelo.PecaDupla;
import br.furb.cg.n4.modelo.PecaTripla;
import br.furb.cg.n4.modelo.Ponto;

public class Combinacoes
{
	public static int[][] getMosaico(int i)
	{
		int[][] m = new int[Mosaico.BLOCOS_H][Mosaico.BLOCOS_V];
		
		/*
		 * O O + + + O T
		 * O T T T + T +
		 * T O + O O T O
		 * T O + T + + O
		 */
				
		m[0][0] = FormaType.QUADRADO.ordinal();
		m[0][1] = FormaType.QUADRADO.ordinal();
		m[0][2] = FormaType.CIRCULO.ordinal();
		m[0][3] = FormaType.CIRCULO.ordinal();
		
		m[1][0] = FormaType.CIRCULO.ordinal();		
		m[1][1] = FormaType.CIRCULO.ordinal();
		m[1][2] = FormaType.QUADRADO.ordinal();
		m[1][3] = FormaType.CIRCULO.ordinal();
		
		m[2][0] = FormaType.CRUZ.ordinal();
		m[2][1] = FormaType.CRUZ.ordinal();
		m[2][2] = FormaType.QUADRADO.ordinal();
		m[2][3] = FormaType.CRUZ.ordinal();
		
		m[3][0] = FormaType.QUADRADO.ordinal();
		m[3][1] = FormaType.CIRCULO.ordinal();
		m[3][2] = FormaType.QUADRADO.ordinal();
		m[3][3] = FormaType.CRUZ.ordinal();
				
		m[4][0] = FormaType.CRUZ.ordinal();
		m[4][1] = FormaType.CIRCULO.ordinal();
		m[4][2] = FormaType.CRUZ.ordinal();
		m[4][3] = FormaType.CRUZ.ordinal();
		
		m[5][0] = FormaType.CRUZ.ordinal();
		m[5][1] = FormaType.QUADRADO.ordinal();
		m[5][2] = FormaType.QUADRADO.ordinal();
		m[5][3] = FormaType.CIRCULO.ordinal();

		m[6][0] = FormaType.CIRCULO.ordinal();
		m[6][1] = FormaType.CIRCULO.ordinal();
		m[6][2] = FormaType.CRUZ.ordinal();
		m[6][3] = FormaType.QUADRADO.ordinal();			
		
		return m;
	}
	
	public static List<Peca> getPecas()
	{
		List<Peca> pecas = new ArrayList<Peca>();
		
		pecas.add(new PecaDupla(FormaType.CIRCULO, FormaType.CIRCULO));
		pecas.add(new PecaDupla(FormaType.QUADRADO, FormaType.QUADRADO));
		pecas.add(new PecaTripla(FormaType.QUADRADO, FormaType.QUADRADO, FormaType.CIRCULO));
		pecas.add(new PecaDupla(FormaType.CIRCULO, FormaType.CIRCULO));
		pecas.add(new PecaDupla(FormaType.CRUZ, FormaType.CRUZ));
		pecas.add(new PecaTripla(FormaType.QUADRADO, FormaType.CRUZ, FormaType.CRUZ));
		pecas.add(new PecaDupla(FormaType.QUADRADO, FormaType.CIRCULO));
		pecas.add(new PecaDupla(FormaType.CRUZ, FormaType.CIRCULO));
		pecas.add(new PecaTripla(FormaType.CIRCULO, FormaType.CRUZ, FormaType.QUADRADO));
		pecas.add(new PecaDupla(FormaType.CRUZ, FormaType.CRUZ));
		pecas.add(new PecaDupla(FormaType.QUADRADO, FormaType.CRUZ));
		pecas.add(new PecaTripla(FormaType.QUADRADO, FormaType.CIRCULO, FormaType.CIRCULO));
		
		/*
		 * 1 3 5 5 8 8 1
		 * 1 3 3 7 9 9 1
		 * 2 4 6 7 9 2 2
		 * 2 4 6 6 0 0 2
		 */
		
		/*
		 * O O + + + O T
		 * O T T T + T +
		 * T O + O O T O
		 * T O + T + + O
		 */
		
		espalharPecas(pecas);
		
		return pecas;
	}
	
	public static void espalharPecas(List<Peca> pecas)
	{
		double raio = 180; // Número mágico 
		
		Ponto centro = new Ponto(0, 0);
		
		List<Ponto> posicoes = FuncoesGeometricas.gerarCirculoDePontos(centro, raio, pecas.size());
		
		for (int i = 0; i < pecas.size(); i++)
		{
			Peca peca = pecas.get(i);
			Ponto posicao = posicoes.get(i);
			posicao.setZ(10);
			
			peca.transladar(posicao);
		}
	}
}