package br.furb.cg.n4.modelo;


public class Transformador
{
	private ObjetoGrafico objeto;
	
	private Transformacao escala;
	private Transformacao rotacao;
	private Transformacao translacao;
	
	public Transformador(ObjetoGrafico objeto)
	{
		this.objeto = objeto;
		this.escala = new Transformacao();
		this.rotacao = new Transformacao();
		this.translacao = new Transformacao();
	}
	
	/**
	 * Aplicar a transformação de "Escala" no objeto gráfico
	 * conforme a "Proporção" desejada. Essa proporção será
	 * aplicada nos eixos x e y em relação ao centro do 
	 * objeto gráfico.
	 * 
	 * @param proporcao A proporção da escala
	 */
	public void escalar(double proporcao)
	{
		double Sx = proporcao;
		double Sy = proporcao;
		
		Transformacao escalaAux = new Transformacao();
		
		escalaAux.MakeScale(Sx, Sy, 1.0);
		
		escala = escala.transformMatrix(escalaAux);			
		
		aplicarTransformacao();
	}
	
	/**
	 * Aplicar a tranformação de "Translação" no objeto gráfico
	 * adicionando os valores de x e y à posição atual do objeto.
	 * 
	 * @param x Valor para translação no eixo X
	 * @param y Valor para translação no eixo Y
	 */
	public void transladar(double x, double y, double z, boolean absoluto)
	{	
		Transformacao translacaoAux = new Transformacao();
		
		translacaoAux.MakeTranslation(new Ponto(x, y, z));
		
		if (absoluto)
			translacao = translacaoAux;
		else
			translacao = translacao.transformMatrix(translacaoAux);
		
		aplicarTransformacao();
	}
	
	public void transladar(double x, double y, double z)
	{
		transladar(x, y, z, false);
	}
	
	/**
	 * Aplicar a transformação de "Rotação" no objeto gráfico
	 * conforme a quantidade de "graus" desejada. A rotação
	 * do objeto será aplicado em relação ao centro do 
	 * objeto gráfico.
	 * 
	 * @param graus 
	 * 		Valor em graus para rotação. Se esse valor for 
	 * 		negativo a rotação será feita para o lado esquerdo
	 * 		do objeto gráfico, caso contrário, para o lado
	 * 		direito do objeto gráfico.
	 */
	public void rotacionar(double graus)
	{		
		Transformacao rotacaoAux = new Transformacao();
		
		rotacaoAux.MakeZRotation(Transformacao.RAS_DEG_TO_RAD * graus);
		
		rotacao = rotacao.transformMatrix(rotacaoAux);
		
		aplicarTransformacao();
	}
	
	/**
	 * Aplica a transformação no objeto gráfico.
	 * 
	 * @param transformacao 
	 * 		Transformação a ser aplicada no objeto gráfico
	 * 
	 * @param usaPontoFixo 
	 * 		Define se a transformação será aplicada 
	 * 		com relação ao centro do objeto ou com
	 * 		relação ao centro do ambiente gráfico.
	 */
	private void aplicarTransformacao()
	{
		Transformacao matrizGlobal = new Transformacao();

		Ponto pMedio = objeto.getBbox().getPontoMedio();
		Ponto pFixo = new Ponto(-pMedio.getX(), -pMedio.getY());
		
		Transformacao matrizPontoFixo = new Transformacao();
			
		matrizPontoFixo.MakeTranslation(pFixo);		
			
		matrizGlobal = matrizPontoFixo.transformMatrix(matrizGlobal);					
		matrizGlobal = escala.transformMatrix(matrizGlobal);
		matrizGlobal = rotacao.transformMatrix(matrizGlobal);
		matrizGlobal = translacao.transformMatrix(matrizGlobal);
		
		objeto.getMatrizObjeto().setData(matrizGlobal.getData());
	}

}
