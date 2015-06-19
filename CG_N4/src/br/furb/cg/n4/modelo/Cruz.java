package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cruz extends Forma
{
	private List<Ponto> vertices;
	
	public Cruz()
	{
		vertices = new ArrayList<Ponto>();
		
		criarVertices();
	}
	
	/**
	 *          y
	 *        __|__     +5.0
	 *       |  |  |
	 *    ___|  |  |___ +2.5
	 *   |      |      | 
	 * --|-----0.0-----|---- x
	 *   |___   |   ___|-2.5
	 *       |  |  |
	 *       |__|__|    -5.0
	 *          |
	 *  -5 -2.5  +2.5 +5
	 */
	private void criarVertices()
	{
		vertices.add(new Ponto(-2.5, +2.5));
		vertices.add(new Ponto(-2.5, +5.0));
		vertices.add(new Ponto(+2.5, +5.0));
		vertices.add(new Ponto(+2.5, +2.5));
		vertices.add(new Ponto(+5.0, +2.5));
		vertices.add(new Ponto(+5.0, -2.5));
		vertices.add(new Ponto(+2.5, -2.5));
		vertices.add(new Ponto(+2.5, -5.0));
		vertices.add(new Ponto(-2.5, -5.0));
		vertices.add(new Ponto(-2.5, -2.5));
		vertices.add(new Ponto(-5.0, -2.5));
		vertices.add(new Ponto(-5.0, +2.5));		
	}
	
	@Override
	List<Ponto> getVerticesForma()
	{		
		return vertices;
	}
}
