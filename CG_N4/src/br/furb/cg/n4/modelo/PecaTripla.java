package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.n4.utils.FuncoesGeometricas;

public class PecaTripla extends Peca
{
	private List<Ponto> borda;
	private BlocoMosaico[] blocosEncaixados;
	
	private Bloco b1;
	private Bloco b2;
	private Bloco b3;
	
	private Ponto pb1;
	private Ponto pb2;
	private Ponto pb3;
	
	double yMin;
	double yMax;
	double xMin;
	double xMax;
	
	public PecaTripla(FormaType fb1, FormaType fb2, FormaType fb3)
	{
		blocosEncaixados = new BlocoMosaico[3];
		
		criarBorda();
		criarBlocos(fb1, fb2, fb3);
	}
	
	private void criarBorda()
	{
		yMin = -Bloco.TAMANHO;
		yMax = +Bloco.TAMANHO;
		xMin = -Bloco.TAMANHO;
		xMax = +Bloco.TAMANHO;
		
		borda = new ArrayList<Ponto>(4);
		borda.add(new Ponto(xMin, yMax));
		borda.add(new Ponto(xMax, yMax));
		borda.add(new Ponto(xMax, yMin));
		borda.add(new Ponto(0, yMin));
		borda.add(new Ponto(0, 0));
		borda.add(new Ponto(xMin, 0));
	}
	
	private void criarBlocos(FormaType fb1, FormaType fb2, FormaType fb3)
	{
		pb1 = new Ponto(-20, +20);
		pb2 = new Ponto(+20, +20);
		pb3 = new Ponto(+20, -20);
		
		b1 = new BlocoPeca(fb1);
		b1.transladar(pb1);
		b1.escalar(2);
		
		b2 = new BlocoPeca(fb2);
		b2.transladar(pb2);
		b2.escalar(2);
		
		b3 = new BlocoPeca(fb3);
		b3.transladar(pb3);
		b3.escalar(2);
		
		getObjetos().add(b1);
		getObjetos().add(b2);
		getObjetos().add(b3);
	}

	@Override
	void desenhar(GL gl, GLU glu)
	{
		/*
		if (isSelecionado())
			gl.glColor3f(1.0f, 0.0f, 0.0f);
		else
			gl.glColor3f(0.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL.GL_LINE_LOOP);
		{
			for (Ponto v : borda)
				gl.glVertex3d(v.getX(), v.getY(), v.getZ());
		}
		gl.glEnd();
		*/
	}

	@Override
	boolean isSelecionavel()
	{
		return true;
	}

	@Override
	boolean contemPonto(Ponto p)
	{
		List<Ponto> vertices = new ArrayList<Ponto>();
		
		for (Ponto b : borda)
			vertices.add(getMatrizObjeto().transformPoint(b));
		
		return FuncoesGeometricas.pontoEmPoligono(vertices, p);
	}
	
	private BlocoMosaico[] obtemBlocosEcaixe(Mosaico mosaico)
	{
		BlocoMosaico[] blocos = new BlocoMosaico[3];
		
		Ponto pa1 = getMatrizObjeto().transformPoint(pb1);
		blocos[0] = mosaico.obtemBlocoDePonto(pa1);
		
		Ponto pa2 = getMatrizObjeto().transformPoint(pb2);		
		blocos[1] = mosaico.obtemBlocoDePonto(pa2);
		
		Ponto pa3 = getMatrizObjeto().transformPoint(pb3);		
		blocos[2] = mosaico.obtemBlocoDePonto(pa3);
		
		return blocos;
	}

	@Override
	public boolean verificaEncaixe(Mosaico mosaico)
	{
		BlocoMosaico[] blocos = obtemBlocosEcaixe(mosaico);
		
		if (blocos[0] != null && blocos[1] != null && blocos[2] != null)
		{
			return !blocos[0].isOcupado() &&
				   !blocos[1].isOcupado() &&
				   !blocos[2].isOcupado() &&
				    blocos[0].getTipoForma() == b1.getTipoForma() &&
				    blocos[1].getTipoForma() == b2.getTipoForma() &&
				    blocos[2].getTipoForma() == b3.getTipoForma();
		}
				
		return false;
	}

	@Override
	public void encaixar(Mosaico mosaico)
	{
		BlocoMosaico[] blocos = obtemBlocosEcaixe(mosaico);
		
		if (blocos[0] != null && blocos[1] != null && blocos[2] != null)
		{			
			Ponto pb0 = blocos[0].getMatrizObjeto().transformPoint(new Ponto(0, 0));
			Ponto pb2 = blocos[2].getMatrizObjeto().transformPoint(new Ponto(0, 0));
			
			double x = (pb0.getX() + pb2.getX()) / 2;
			double y = (pb0.getY() + pb2.getY()) / 2;
			
			Ponto pEncaixe = new Ponto(x, y, 0);
			
			transladar(pEncaixe, true);
			
			blocos[0].setOcupado(true);
			blocos[1].setOcupado(true);
			blocos[2].setOcupado(true);
			
			blocosEncaixados = blocos;
			
			setEncaixada(true);
		}
	}
	
	@Override
	public void desencaixar(Mosaico mosaico)
	{
		for (int i = 0; i < blocosEncaixados.length; i++)
		{
			if (blocosEncaixados[i] != null)
			{		
				blocosEncaixados[i].setOcupado(false);
				blocosEncaixados[i] = null;
			}
			
			setEncaixada(false);
			
			transladar(0, 0, 10);
		}
	}
}