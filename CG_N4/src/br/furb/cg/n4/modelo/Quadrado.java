package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

public class Quadrado extends Forma
{
	private static final int RAIO_QUADRADO = 5;
	
	List<Ponto> vertices;
	
	public Quadrado()
	{
		vertices = new ArrayList<Ponto>();
		
		criarVertices();
	}
	
	private void criarVertices()
	{
		vertices.add(new Ponto(-RAIO_QUADRADO, +RAIO_QUADRADO));
		vertices.add(new Ponto(+RAIO_QUADRADO, +RAIO_QUADRADO));
		vertices.add(new Ponto(+RAIO_QUADRADO, -RAIO_QUADRADO));
		vertices.add(new Ponto(-RAIO_QUADRADO, -RAIO_QUADRADO));
	}

	@Override
	List<Ponto> getVerticesForma()
	{
		return vertices;
	}
	
}
