package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.n4.utils.Combinacoes;

public class Mosaico extends ObjetoGrafico
{
	public static final int BLOCOS_V = 4;
	public static final int BLOCOS_H = 7;
	
	private List<Ponto> borda;
	private Bloco[][] blocos;
	
	double xMin, xMax, yMin, yMax;
	
	public Mosaico()
	{
		criarBorda();
		criarBlocos();
	}
	
	private void criarBorda()
	{
		yMin = -((Bloco.TAMANHO * BLOCOS_V) / 2);
		yMax = +((Bloco.TAMANHO * BLOCOS_V) / 2);
		xMin = -((Bloco.TAMANHO * BLOCOS_H) / 2);
		xMax = +((Bloco.TAMANHO * BLOCOS_H) / 2);
		
		borda = new ArrayList<Ponto>(4);
		borda.add(new Ponto(xMin, yMin));
		borda.add(new Ponto(xMin, yMax));
		borda.add(new Ponto(xMax, yMax));
		borda.add(new Ponto(xMax, yMin));
	}
	
	private void criarBlocos()
	{
		blocos = new Bloco[BLOCOS_H][BLOCOS_V];
		
		int[][] formas = Combinacoes.getMosaico(0);
		
		for (int h = 0; h < BLOCOS_H; h++)
		{
			for (int v = 0; v < BLOCOS_V; v++)
			{
				double xPosBloco = ((Bloco.TAMANHO * h) + (Bloco.TAMANHO / 2)) + xMin;
				double yPosBloco = ((Bloco.TAMANHO * v) + (Bloco.TAMANHO / 2)) + yMin;
				
				Bloco b = new Bloco(FormaFactory.criarForma(FormaType.values()[formas[h][v]]));
				
				b.transladar(xPosBloco, yPosBloco);
				b.escalar(2);
				 
				blocos[h][v] = b;
				
				getObjetos().add(b);
			}
		}
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL.GL_LINE_LOOP);
		{
			for (Ponto v : borda)
				gl.glVertex3d(v.getX(), v.getY(), v.getZ());
		}
		gl.glEnd();
	}

	@Override
	boolean isSelecionavel()
	{
		return false;
	}

	@Override
	boolean contemPonto(Ponto p)
	{
		return false;
	}
	
	public Bloco obtemBlocoDePonto(Ponto p)
	{
		for (int h = 0; h < BLOCOS_H; h++)
		{
			for (int v = 0; v < BLOCOS_V; v++)
			{		
				Bloco b = blocos[h][v];
				
				if (b.contemPonto(p)) return b;
			}
		}
		
		return null;
	}

}
