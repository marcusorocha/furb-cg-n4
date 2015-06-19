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
		b1 = new Bloco(fb1);
		b1.transladar(-20, +20);
		b1.escalar(2);
		
		b2 = new Bloco(fb2);
		b2.transladar(+20, +20);
		b2.escalar(2);
		
		b3 = new Bloco(fb3);
		b3.transladar(+20, -20);
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
		return (p.getX() > xMin) && 
			   (p.getX() < xMax) && 
			   (p.getY() > yMin) && 
			   (p.getY() < yMax);
	}

}