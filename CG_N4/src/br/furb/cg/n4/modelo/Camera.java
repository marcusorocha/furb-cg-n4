package br.furb.cg.n4.modelo;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Camera
{	
	private static final double LARGURA_PADRAO = 400;
	private static final double ALTURA_PADRAO  = 400;
	private static final double LARGURA_MINIMA = 100;
	private static final double LARGURA_MAXIMA = 800;
	
	private double ortho2D_minX;
	private double ortho2D_maxX;
	private double ortho2D_minY;
	private double ortho2D_maxY;
	
	public Camera()
	{
		ortho2D_minX = -(LARGURA_PADRAO / 2);
		ortho2D_maxX = +(LARGURA_PADRAO / 2);
		ortho2D_minY = -(ALTURA_PADRAO / 2);
		ortho2D_maxY = +(ALTURA_PADRAO / 2);
	}
	
	public Camera(double largura, double altura)
	{
		ortho2D_minX = -(largura / 2);
		ortho2D_maxX = +(largura / 2);
		ortho2D_minY = -(altura / 2);
		ortho2D_maxY = +(altura / 2);
	}
	
	/**
	 * Obtem a largura total da câmera
	 * 
	 * @return Largura total da câmera 
	 */
	public double getLargura()
	{
		return ortho2D_maxX - ortho2D_minX;		
	}
	
	/**
	 * Obtem a altura total da câmera
	 * 
	 * @return Altura total da câmera
	 */
	public double getAltura()
	{
		return ortho2D_maxY - ortho2D_minY;
	}
	
	/**
	 * Atribui os valores máximos e minimos dos eixos X e Y ao "ortho". 
	 * 
	 * @param gl
	 * @param glu
	 * 
	 * @see javax.media.opengl.glu.GLU#gluOrtho2D(double, double, double, double)
	 */
	public void posicionar(GL gl, GLU glu)
	{			
		//glu.gluOrtho2D(ortho2D_minX, ortho2D_maxX, ortho2D_minY, ortho2D_maxY);
		
		float h = (float)getLargura() / (float)getAltura();
		
		glu.gluPerspective(60.0f, h, 5.0f, 100.0f);
	}
	
	/**
	 * Aumenta em 50 unidades os mínimos dos eixos X e Y e diminui 
	 * em 50 unidades os valores máximos dos eixos X e Y de referência 
	 * do ortho2D para aproximar a câmera.
	 *
	 * @see #aproximar(double);
	 * @see #posicionar(GL, GLU)
	 */
	public void aproximar()
	{
		aproximar(50);
	}
	
	/**
	 * Aumenta em uma taxa de a unidades mínimos dos eixos X e Y e diminui 
	 * na mesma taxa as unidades máximas dos eixos X e Y de referência do 
	 * ortho2D para aproximar a câmera.
	 *
	 * @param taxa Taxa de aproximação que será utilizada para ajustar os mínimos e máximos do Ortho2D
	 *
	 * @see #posicionar(GL, GLU)
	 */
	public void aproximar(double taxa)
	{	
		if (getLargura() > LARGURA_MINIMA)
		{
			ortho2D_minX += taxa;
			ortho2D_maxX -= taxa;
			ortho2D_minY += taxa;
			ortho2D_maxY -= taxa;
		}
	}
	
	/**
	 * Diminui em 50 unidades os valores mínimos dos eixos X e Y e aumenta 
	 * em 50 unidades os valores máximos dos eixos X e Y de referência do 
	 * ortho2D para afastas a câmera.
	 * 
	 * @see #afastar(double);
	 * @see #posicionar(GL, GLU)
	 */
	public void afastar()
	{
		afastar(50);
	}
	
	/**
	 * Diminui em uma taxa de a unidades mínimos dos eixos X e Y e aumenta 
	 * na mesma taxa as unidades máximas dos eixos X e Y de referência do 
	 * ortho2D para afastar a câmera.
	 *
	 * @param taxa Taxa de aproximação que será utilizada para ajustar os mínimos e máximos do Ortho2D
	 *
	 * @see #posicionar(GL, GLU)
	 */
	public void afastar(double taxa)
	{
		if (getLargura() < LARGURA_MAXIMA)
		{
			ortho2D_minX -= taxa;
			ortho2D_maxX += taxa;
			ortho2D_minY -= taxa;
			ortho2D_maxY += taxa;
		}
	}
	
	/**
	 * Aumenta em 50 unidades o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a esquerda
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void esquerda()
	{
		esquerda(50);
	}
	
	/**
	 * Aumenta em uma taxa o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a esquerda
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @param taxa Taxa que será utilizada para aumentar o mínimo X e máximo X do Ortho2D
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void esquerda(double taxa)
	{
		ortho2D_minX += taxa;
		ortho2D_maxX += taxa;
	}
	
	/**
	 * Diminui em 50 unidades o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a direita
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void direita()
	{
		direita(50);
	}
	
	/**
	 * Diminui em taxa o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a direita
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @param taxa Taxa que será utilizada para diminuir o mínimo X e máximo X do Ortho2D
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void direita(double taxa)
	{
		ortho2D_minX -= taxa;
		ortho2D_maxX -= taxa;
	}
	
	/**
	 * Diminui em 50 unidades o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a acima
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void acima()
	{
		acima(50);
	}
	
	/**
	 * Diminui em uma taxa o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a acima
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @param taxa Taxa que será utilizada para diminiuir o mínimo Y e máximo Y do Ortho2D
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void acima(double taxa)
	{
		ortho2D_minY -= taxa;
		ortho2D_maxY -= taxa;
	}
	
	/**
	 * Aumenta em 50 unidades o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a abaixo do 
	 * centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */	
	public void abaixo()
	{
		abaixo(50);
	}
	
	/**
	 * Aumenta em uma taxa o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a abaixo
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @param taxa Taxa que será utilizada para aumentar o mínimo Y e máximo Y do Ortho2D
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void abaixo(double taxa)
	{
		ortho2D_minY += taxa;
		ortho2D_maxY += taxa;
	}
	
	/**
	 * Converte as coordenadas de x e y do clique para as coordenadas do ambiente
	 * onde se encontra a câmera.
	 * 
	 * @param xPonto Coordenada X do clique
	 * @param yPonto Coordenada Y do clique
	 * @param xTela Largura total do componente de apresentação do ambiente
	 * @param yTela Altura total do componente de apresentação do ambiente
	 * 
	 * @return Um <code>Ponto</code> com as coordenadas convertidas.
	 */
	public Ponto convertePontoTela(double xPonto, double yPonto, double xTela, double yTela)
	{
		double xTotal = ortho2D_maxX - ortho2D_minX;
		double yTotal = ortho2D_maxY - ortho2D_minY;
		
		double escalaX = xTotal / xTela;
		double escalaY = yTotal / yTela;

		double x = +((xPonto * escalaX) + ortho2D_minX);
		double y = -((yPonto * escalaY) - ortho2D_maxY);
		
		return new Ponto(x, y);
	}
	
	public void exibirOrthos()
	{
		System.out.println("Camera: [" + toString() + "]");
	}
	
	@Override
	public String toString()
	{
		return String.format("maxY=%s, minY=%s, maxX=%s, minX=%s", ortho2D_maxY, ortho2D_minY, ortho2D_maxX, ortho2D_minX);
	}
}
