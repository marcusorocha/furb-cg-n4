package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.n4.utils.FuncoesGeometricas;

public class PecaDupla extends Peca
{
	private List<Ponto> borda;
	private BlocoMosaico[] blocosEncaixados;
	
	private Bloco b1;
	private Bloco b2;
	
	private Ponto pb1;
	private Ponto pb2;
	
	double yMin;
	double yMax;
	double xMin;
	double xMax;
	
	public PecaDupla(FormaType fb1, FormaType fb2)
	{
		this.blocosEncaixados = new BlocoMosaico[2];
		
		criarBorda();
		criarBlocos(fb1, fb2);
	}
	
	private void criarBorda()
	{
		yMin = -(Bloco.TAMANHO / 2);
		yMax = +(Bloco.TAMANHO / 2);
		xMin = -(Bloco.TAMANHO);
		xMax = +(Bloco.TAMANHO);
		
		borda = new ArrayList<Ponto>(4);
		borda.add(new Ponto(xMin, yMin));
		borda.add(new Ponto(xMin, yMax));
		borda.add(new Ponto(xMax, yMax));
		borda.add(new Ponto(xMax, yMin));
	}
	
	private void criarBlocos(FormaType fb1, FormaType fb2)
	{				
		pb1 = new Ponto(-20, 0);		
		pb2 = new Ponto(+20, 0);
		
		b1 = new BlocoPeca(fb1);
		b1.transladar(pb1);
		b1.escalar(2);
		
		b2 = new BlocoPeca(fb2);
		b2.transladar(pb2);
		b2.escalar(2);
		
		getObjetos().add(b1);
		getObjetos().add(b2);
	}

	@Override
	void desenhar(GL gl, GLU glu)
	{
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
		BlocoMosaico[] blocos = new BlocoMosaico[2];
		
		Ponto pa1 = getMatrizObjeto().transformPoint(pb1);
		blocos[0] = mosaico.obtemBlocoDePonto(pa1);
		
		Ponto pa2 = getMatrizObjeto().transformPoint(pb2);
		blocos[1] = mosaico.obtemBlocoDePonto(pa2);
		
		return blocos;
	}
	
	@Override
	public boolean verificaEncaixe(Mosaico mosaico)
	{
		BlocoMosaico[] blocos = obtemBlocosEcaixe(mosaico);
		
		if (blocos[0] != null && blocos[1] != null)
		{
			return !blocos[0].isOcupado() &&
				   !blocos[1].isOcupado() &&
				    blocos[0].getTipoForma() == b1.getTipoForma() &&
				    blocos[1].getTipoForma() == b2.getTipoForma();
		}
				
		return false;
	}

	@Override
	public void encaixar(Mosaico mosaico)
	{
		BlocoMosaico[] blocos = obtemBlocosEcaixe(mosaico);
		
		if (blocos[0] != null && blocos[1] != null)
		{			
			Ponto pb0 = blocos[0].getMatrizObjeto().transformPoint(new Ponto(0, 0));
			Ponto pb1 = blocos[1].getMatrizObjeto().transformPoint(new Ponto(0, 0));
			
			double x = (pb0.getX() + pb1.getX()) / 2;
			double y = (pb0.getY() + pb1.getY()) / 2;
			
			Ponto pEncaixe = new Ponto(x, y);
			
			transladar(pEncaixe, true);
			
			blocos[0].setOcupado(true);
			blocos[1].setOcupado(true);
			
			blocosEncaixados = blocos;
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
		}
	}

}
