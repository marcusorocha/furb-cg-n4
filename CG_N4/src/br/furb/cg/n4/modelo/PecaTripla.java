package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class PecaTripla extends Peca
{
	private List<Ponto> borda;
	
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
		
		b1 = new Bloco(fb1);
		b1.transladar(pb1);
		b1.escalar(2);
		
		b2 = new Bloco(fb2);
		b2.transladar(pb2);
		b2.escalar(2);
		
		b3 = new Bloco(fb3);
		b3.transladar(pb3);
		b3.escalar(2);
		
		getObjetos().add(b1);
		getObjetos().add(b2);
		getObjetos().add(b3);
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
		Ponto pMin = getMatrizObjeto().transformPoint(new Ponto(xMin, yMin));
		Ponto pMax = getMatrizObjeto().transformPoint(new Ponto(xMax, yMax));
		
		return (p.getX() > pMin.getX()) && 
			   (p.getX() < pMax.getX()) && 
			   (p.getY() > pMin.getY()) && 
			   (p.getY() < pMax.getY());
	}

	@Override
	public boolean verificaEncaixe(Mosaico mosaico)
	{
		Ponto pa1 = getMatrizObjeto().transformPoint(pb1);
		Bloco bm1 = mosaico.obtemBlocoDePonto(pa1);
		
		Ponto pa2 = getMatrizObjeto().transformPoint(pb2);		
		Bloco bm2 = mosaico.obtemBlocoDePonto(pa2);
		
		Ponto pa3 = getMatrizObjeto().transformPoint(pb3);		
		Bloco bm3 = mosaico.obtemBlocoDePonto(pa3);
		
		if (bm1 != null && bm2 != null && bm3 != null)
		{
			return bm1.getTipoForma() == b1.getTipoForma() &&
				   bm2.getTipoForma() == b2.getTipoForma() &&
				   bm3.getTipoForma() == b3.getTipoForma();
		}
				
		return false;
	}

	@Override
	public void encaixar(Mosaico mosaico)
	{
		Ponto pa2 = getMatrizObjeto().transformPoint(pb2);
		Bloco bm2 = mosaico.obtemBlocoDePonto(pa2);
		
		if (bm2 != null)
		{			
			Ponto pEncaixe = bm2.getMatrizObjeto().transformPoint(new Ponto(0, 0));
			
			pEncaixe.setX(pEncaixe.getX() - 20);
			pEncaixe.setY(pEncaixe.getY() - 20);
			
			limparTranslacao();
			transladar(pEncaixe);
		}
	}
	
}