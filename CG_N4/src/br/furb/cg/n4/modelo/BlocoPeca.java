package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.n4.utils.FuncoesGeometricas;

public class BlocoPeca extends Bloco 
{	
	
	public BlocoPeca(Forma forma)
	{
		super(forma);
		setIluminacao(true);
	}
	
	public BlocoPeca(FormaType tipo)
	{
		super(tipo);
		setIluminacao(true);
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{
		double baseBloco = 0;
		
		if (isSelecionado())
			baseBloco = 10;
		
		double alturaBloco = baseBloco + 10;
		
		List<Ponto> borda = new ArrayList<Ponto>();
		List<Ponto> forma = new ArrayList<Ponto>();
		
		borda.addAll(getVerticesBorda());
		forma.addAll(getVerticesForma());
		
		// Define a cor da face				
		float cor[] = { 0.0f, 0.8f, 0.2f, 1.0f };
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);
		
		// Desenha a face superior				
		gl.glBegin(GL.GL_QUAD_STRIP);
		{		
			gl.glNormal3f(0, 0, 1);
			
			for (int i = 0; i <= forma.size(); i++)
			{
				int x = i % forma.size();

				Ponto d = forma.get(x);
				Ponto f = FuncoesGeometricas.pontoMaisProximo(borda, d);
				
				gl.glVertex3d(d.getX(), d.getY(), alturaBloco);
				gl.glVertex3d(f.getX(), f.getY(), alturaBloco);
			}
		}
		gl.glEnd();
		
		// Desenha a face inferior				
		gl.glBegin(GL.GL_QUAD_STRIP);
		{			
			gl.glNormal3f(0, 0, -1);
			
			for (int i = 0; i <= forma.size(); i++)
			{
				int x = i % forma.size();
										
				Ponto d = forma.get(x);
				Ponto f = FuncoesGeometricas.pontoMaisProximo(borda, d);
				
				gl.glVertex3d(d.getX(), d.getY(), baseBloco);
				gl.glVertex3d(f.getX(), f.getY(), baseBloco);
			}
		}
		gl.glEnd();
		
		// Desenha o interior da forma
		gl.glBegin(GL.GL_QUAD_STRIP);
		{			
			gl.glNormal3f(1, 0, 0);
			
			for (int i = 0; i <= forma.size(); i++)
			{
				int x = i % forma.size();
										
				Ponto d = forma.get(x);
										
				gl.glVertex3d(d.getX(), d.getY(), alturaBloco);
				gl.glVertex3d(d.getX(), d.getY(), baseBloco);
			}
		}
		gl.glEnd();
		
		// Desenha as laterais da borda
		gl.glBegin(GL.GL_QUAD_STRIP);
		{			
			gl.glNormal3f(1, 0, 0);
			
			for (int i = 0; i <= borda.size(); i++)
			{
				int x = i % borda.size();
										
				Ponto d = borda.get(x);
										
				gl.glVertex3d(d.getX(), d.getY(), alturaBloco);
				gl.glVertex3d(d.getX(), d.getY(), baseBloco);
			}
		}
		gl.glEnd();
	}

}
