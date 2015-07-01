package br.furb.cg.n4.modelo;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Mundo extends ObjetoGrafico
{
	private float sruX = 150;
	private float sruY = 150;
	private float sruZ = 150;
	
	private Camera camera;
	
	public Mundo()
	{
		camera = new Camera();
	}

	/**
	 * Retorna uma referência a instância da câmera
	 * @return Uma câmera
	 */
	public Camera getCamera()
	{
		return camera;
	}
	
	/**
	 * Faz a chamada à função de posicionamento da câmera 
	 * @param gl
	 * @param glu
	 */
	public void posicionaCamera(GL gl, GLU glu, boolean ambiente3D)
	{
		camera.posicionar(gl, glu, ambiente3D);
	}
	
	/**
	 * Desenha os eixos de referências (SRU)
	 * @param gl
	 * @param glu
	 */
	public void desenhaSRU(GL gl, GLU glu)
	{
		gl.glLineWidth(1.0f);

		// Eixo X - Vermelho
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex3f(-sruX, 0.0f, 0.0f);
			gl.glVertex3f(+sruX, 0.0f, 0.0f);
		}
		gl.glEnd();

		// Eixo Y - Verde
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex3d(0.0, -sruY, 0.0f);
			gl.glVertex3d(0.0, +sruY, 0.0f);
		}
		gl.glEnd();
		
		// Eixo Z - Azul
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex3f(0.0f, 0.0f, -sruZ);
			gl.glVertex3f(0.0f, 0.0f, +sruZ);
		}
		gl.glEnd();
	}
	
	/**
	 * Localizar algum objeto gráfico que se encontra no ponto de coordenadas (x, y, z)
	 * @param p
	 * @return Um objeto gráfico
	 */
	public ObjetoGrafico localizarObjeto(Ponto p)
	{
		return localizarObjetoR(getObjetos(), p);
	}
	
	/**
	 * Função recursiva para varrer a estrutura de objetos gráficos em busca 
	 * de um objeto localizado no ponto de coordenadas (x, y, z) 
	 * @param listaObjetos
	 * @param p
	 * @return Um objeto gráfico
	 */
	private ObjetoGrafico localizarObjetoR(List<ObjetoGrafico> listaObjetos, Ponto p)
	{
		for (ObjetoGrafico o : listaObjetos)
		{			
			if (o.isSelecionavel())
			{								
				if (o.contemPonto(p))
					return o;
				
				ObjetoGrafico s = localizarObjetoR(o.getObjetos(), p);
				
				if (s != null) return s;
			}
		}
		
		return null;
	}
	
	/**
	 * Função para buscar o "Proprietario" de um objeto gráfico
	 * 
	 * @param o
	 * 		Objeto gráfico contido em algum <code>ObjetoGrafico</code>
	 * 
	 * @return Um <code>ObjetoGraficoContainer</code> de um objeto gráfico
	 */
	public ObjetoGrafico getProprietario(ObjetoGrafico o)
	{
		return getProprietarioR(this, o);
	}
	
	/**
	 * Função recursiva para varrer a estrutura de objetos gráficos em busca 
	 * do "Proprietario" de um objeto gráfico
	 * 
	 * @param prop
	 * 		<code>ObjetoGrafico</code> que pode conter outro <code>ObjetoGrafico</code>
	 * 
	 * @param og
	 * 		<code>ObjetoGrafico</code> contido em algum <code>ObjetoGrafico</code>
	 * 
	 * @return O <code>ObjetoGrafico</code> que contém o outro objeto gráfico
	 */
	private ObjetoGrafico getProprietarioR(ObjetoGrafico prop, ObjetoGrafico og)
	{		
		for (ObjetoGrafico o : prop.getObjetos())
		{
			if (o.equals(og))
				return this;
			
			ObjetoGrafico proprietario = getProprietarioR(o, og);
			
			if (proprietario != null) return proprietario;
		}
		
		return null;
	}

	@Override
	void desenhar(GL gl, GLU glu)
	{
		//desenhaSRU(gl, glu);
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
