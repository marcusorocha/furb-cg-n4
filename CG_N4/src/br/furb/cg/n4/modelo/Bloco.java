package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public abstract class Bloco extends ObjetoGrafico
{
	/**
	 * Constante definindo o tamanho padrão do objeto gráfico <code>Bloco</code>
	 */
	public static final int TAMANHO = 40;
	
	private Forma forma;
	private List<Ponto>	borda;
	
	double yMin;
	double yMax;
	double xMin;
	double xMax;
	
	public Bloco(Forma forma)
	{
		this.forma = forma;
		this.borda = new ArrayList<Ponto>(4);
		
		yMin = -10;
		yMax = +10;
		xMin = -10;
		xMax = +10;
				
		this.borda.add(new Ponto(xMin, yMax));
		this.borda.add(new Ponto(xMax, yMax));
		this.borda.add(new Ponto(xMax, yMin));
		this.borda.add(new Ponto(xMin, yMin));
	}
	
	public Bloco(FormaType tipo)
	{
		this(FormaFactory.criarForma(tipo));		
	}
	
	public FormaType getTipoForma()
	{
		return forma.getTipoForma();
	}
	
	protected final List<Ponto> getVerticesForma()
	{
		return forma.getVerticesForma();
	}
	
	protected final List<Ponto> getVerticesBorda()
	{
		return borda;
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{	
		gl.glBegin(GL.GL_LINE_LOOP);
		{
			gl.glColor3f(0.0f, 0.0f, 0.0f);
			
			for (Ponto v : forma.getVerticesForma())
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
		Ponto pMin = getMatrizObjeto().transformPoint(new Ponto(xMin, yMin));
		Ponto pMax = getMatrizObjeto().transformPoint(new Ponto(xMax, yMax));
		
		return (p.getX() > pMin.getX()) && 
			   (p.getX() < pMax.getX()) && 
			   (p.getY() > pMin.getY()) && 
			   (p.getY() < pMax.getY());
	}
}