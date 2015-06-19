package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public abstract class ObjetoGrafico
{
	private BBox bbox;
	private Transformacao matrizObjeto;
	private Transformador transformador;	
	private List<ObjetoGrafico> objetos;
	
	private boolean selecionado;
	
	public ObjetoGrafico()
	{
		this.bbox = new BBox();
		this.objetos = new ArrayList<ObjetoGrafico>();
		this.matrizObjeto = new Transformacao();
		this.transformador = new Transformador(this);
	}
	
	public List<ObjetoGrafico> getObjetos()
	{
		return objetos;
	}
	
	abstract void desenhar(GL gl, GLU glu);
	
	public final void desenha(GL gl, GLU glu)
	{		
		gl.glPushMatrix();
		{
			gl.glMultMatrixd(matrizObjeto.getData(), 0);
			{
				desenhar(gl, glu);
				
				for (ObjetoGrafico f : getObjetos())
					f.desenha(gl, glu);
			}
		}
		gl.glPopMatrix();
	}

	/**
	 * Obtem uma referência da BBox do objeto gráfico
	 * 
	 * @return Referência da BBox do objeto gráfico
	 */
	public BBox getBbox()
	{	
		return bbox;
	}
	
	public void escalar(double proporcao)
	{		
		transformador.escalar(proporcao);
	}
	
	public void transladar(double x, double y)
	{
		transformador.transladar(x, y);
	}
	
	public void rotacionar(double graus)
	{		
		transformador.rotacionar(graus);
	}
	
	public Transformacao getMatrizObjeto()
	{
		return matrizObjeto;
	}
	
	public void exibeMatrizTransformacao()
	{
		this.matrizObjeto.exibeMatriz();
	}
	
	abstract boolean isSelecionavel();
	abstract boolean contemPonto(Ponto p);

	public boolean isSelecionado()
	{
		return selecionado;
	}

	public void setSelecionado(boolean selecionado)
	{
		this.selecionado = selecionado;
	}
	
	/*
	public boolean contemPonto(Ponto p)
	{
		if (!bbox.isPontoDentro(p))
			return false;
		
		if (getVerticeDePonto(p) != null)
			return true;
		
		return FuncoesGeometricas.pontoEmPoligono(vertices, p);		
	}
	*/
}
