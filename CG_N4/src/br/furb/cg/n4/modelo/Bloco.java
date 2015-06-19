package br.furb.cg.n4.modelo;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Bloco extends ObjetoGrafico
{
	/**
	 * Constante definindo o tamanho padrão do objeto gráfico <code>Bloco</code>
	 */
	public static final int TAMANHO = 40;
	
	private Forma forma;
	
	public Bloco(Forma forma)
	{
		this.forma = forma;
	}
	
	public Bloco(FormaType tipo)
	{
		this.forma = FormaFactory.criarForma(tipo);
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{	
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL.GL_LINE_LOOP);
		{
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
		return false;
	}	

}