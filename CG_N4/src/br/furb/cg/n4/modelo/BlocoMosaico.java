package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class BlocoMosaico extends Bloco
{
	private boolean ocupado = false;
	
	public BlocoMosaico(Forma forma)
	{
		super(forma);
		setIluminacao(true);
	}
		
	public boolean isOcupado()
	{
		return ocupado;
	}
	
	public void setOcupado(boolean ocupado)
	{
		this.ocupado = ocupado;
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{	
		List<Ponto> forma = new ArrayList<Ponto>();
		
		forma.addAll(getVerticesForma());
		
		// Define a cor				
		float corfundo[] = { 0.8f, 0.8f, 0.8f, 1.0f };		
		float corforma[] = { 0.95f, 0.95f, 0.95f, 1.0f };
		
		// Desenha o fundo do bloco
		gl.glBegin (GL.GL_QUADS );
		{
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corfundo, 0);
			
			gl.glNormal3f(0, 0, 1);
			
			for (Ponto p : getVerticesBorda())			
				gl.glVertex3d(p.getX(), p.getY(),  0);			
		}
		gl.glEnd();
		
		// Desenha o interior da forma
		gl.glBegin(GL.GL_QUAD_STRIP);
		{			
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corforma, 0);
			
			gl.glNormal3f(0, 0, 1);
			
			for (int i = 0; i <= forma.size(); i++)
			{
				int x = i % forma.size();
										
				Ponto d = forma.get(x);
										
				gl.glVertex3d(0, 0, 10.0);
				gl.glVertex3d(d.getX(), d.getY(), 10.0);
			}
		}
		gl.glEnd();
		
		// Desenha o interior da forma
		gl.glBegin(GL.GL_QUAD_STRIP);
		{		
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corforma, 0);
			
			gl.glNormal3f(1, 0, 0);
			
			for (int i = 0; i <= forma.size(); i++)
			{
				int x = i % forma.size();
										
				Ponto d = forma.get(x);

				gl.glVertex3d(d.getX(), d.getY(), 10.0);
				gl.glVertex3d(d.getX(), d.getY(), 0.0);
			}
		}
		gl.glEnd();
		
	}
	
}
