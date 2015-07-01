package br.furb.cg.n4.utils;

import java.util.ArrayList;
import java.util.List;

import br.furb.cg.n4.modelo.Ponto;
import br.furb.cg.n4.modelo.Reta;

public class FuncoesGeometricas
{	
	/**
	 * Função para verificar se o Ponto "p" se encontra no interior do 
	 * polígono formado pela lista de vértices "vertices"
	 * 
	 * @param vertices
	 * @param p
	 * 
	 * @return 
	 * 		Verdadeiro caso do ponto "p" esteja dentro do polígono e 
	 * 		Falso no caso contrário 
	 */
	public static boolean pontoEmPoligono(List<Ponto> vertices, Ponto p)
	{
		int nInt = 0;
		int nVertices = vertices.size();
		
		for (int iA = 0; iA < nVertices; iA++)
		{
			int iB = (iA + 1) % nVertices;
			
			Ponto pA = vertices.get(iA);
			Ponto pB = vertices.get(iB);
			
			if (pA.getY() != pB.getY())
			{
				Ponto pInt = pontoDeInterseccao(pA, pB, p.getY()); 
				if (pInt != null)
				{
					if (pInt.getX() >= p.getX() && 
						pInt.getY() >= Math.min(pA.getY(), pB.getY()) && 
						pInt.getY() <= Math.max(pA.getY(), pB.getY()))
					{
						nInt += 1;
					}
				}
			}
		}
		
		return !((nInt % 2) == 0);
	}
	
	/**
	 * Calcular o ponto de intersecção entre a coordenada y e 
	 * a reta formada pelos pontos p1 (x, y) e p2 (x, y).
	 * 
	 * @param p1
	 * @param p2
	 * @param y
	 * 
	 * @return
	 * 		O ponto (x, y) de intersecção caso essa intersecção exista
	 */
	public static Ponto pontoDeInterseccao(Ponto p1, Ponto p2, double y)
	{		
		double ti = (y - p1.getY()) / (p2.getY() - p1.getY());
		
		if (ti >= 0.0 && ti <= 1.0)
		{
			double x = p1.getX() + ((p2.getX() - p1.getX()) * ti);
			
			return new Ponto(x, y);
		}
				
		return null;
	}

	public static Ponto pontoDeRaioAngulo(double angulo, double raio, double posX, double posY)
	{
		double x = (raio * Math.cos(Math.PI * angulo / 180.0)) + posX;
		double y = (raio * Math.sin(Math.PI * angulo / 180.0)) + posY;

		return new Ponto(x, y);
	}
	
	public static List<Ponto> gerarCirculoDePontos(Ponto posicao, Double raio, int qtdePontos)
	{
		List<Ponto> pontos = new ArrayList<Ponto>(qtdePontos);
		
		double a = 0;

		for (int i = 0; i < qtdePontos; i++)
		{
			a = (360 * i) / qtdePontos;

			pontos.add(pontoDeRaioAngulo(a, raio, posicao.getX(), posicao.getY()));
		}
		
		return pontos;
	}
	
	/**
	 * Calcula a interseccao entre 2 retas no plano 2D ("XY" Z = 0)
	 * 
	 * @param k Ponto inicial da reta 1
	 * @param l Ponto final da reta 1
	 * @param m Ponto inicial da reta 2
	 * @param n Ponto final da reta 2
	 * 
	 * @return Ponto de intersecção entre as 2 retas
	 */
	public static Ponto interseccao(Ponto k, Ponto l, Ponto m, Ponto n)
	{
		double det;

		det = (n.getX() - m.getY()) * (l.getY() - k.getY())  -  (n.getY() - m.getY()) * (l.getX() - k.getX());

		if (det == 0.0)
			return null; // não há intersecção

		double x = ((n.getX() - m.getX()) * (m.getY() - k.getY()) - (n.getY() - m.getY()) * (m.getX() - k.getX())) / det ;
		double y = ((l.getX() - k.getX()) * (m.getY() - k.getY()) - (l.getY() - k.getY()) * (m.getX() - k.getX())) / det ;

		return new Ponto(x, y); // há intersecção
	}
	
	/**
	 * Calcula a interseccao entre 2 retas no plano 2D ("XY" Z = 0)
	 * 
	 * @param r1 Reta 1 contendo os pontos A e B
	 * @param r2 Reta 2 contendo os pontos A e B 
	 * 
	 * @return Ponto de intersecção entre as 2 retas
	 */	
	public static Ponto interseccao(Reta r1, Reta r2)
	{
		return interseccao(r1.getPontoA(), r1.getPontoB(), r2.getPontoA(), r2.getPontoB());
	}
	
	public static double distanciaEuclidiana(Ponto a, Ponto b)
	{
		double xAB = b.getX() - a.getX();
		double yAB = b.getY() - a.getY();
		
		xAB = xAB * xAB;
		yAB = yAB * yAB;
		
		return Math.sqrt(xAB + yAB);
	}
	
	public static Ponto pontoMaisProximo(List<Ponto> pontos, Ponto b)
	{
		double min = Integer.MAX_VALUE;
		double dis;
		Ponto p = null;
		
		for (Ponto a : pontos)
		{
			dis = distanciaEuclidiana(a, b);
			if (min > dis)
			{
				min = dis;
				p = a;
			}
		}
		
		return p;
	}

}
