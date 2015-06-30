package br.furb.cg.n4.controle;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;

import br.furb.cg.n4.modelo.Mosaico;
import br.furb.cg.n4.modelo.Mundo;
import br.furb.cg.n4.modelo.ObjetoGrafico;
import br.furb.cg.n4.modelo.Operacao;
import br.furb.cg.n4.modelo.Peca;
import br.furb.cg.n4.modelo.Ponto;
import br.furb.cg.n4.utils.Combinacoes;
import br.furb.cg.n4.utils.EventosAdapter;

public class AmbienteGrafico extends EventosAdapter
{
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private Mundo mundo;
	private Mosaico mosaico;
	private ObjetoGrafico selecionado;
	private Ponto pontoSelecao;
	private boolean girarSelecionado = false;
	
	public AmbienteGrafico(Component owner)
	{
		selecionado = null;
	}

	public void init(GLAutoDrawable drawable)
	{
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		//gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		float pos[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
	    //gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_LIGHT0);
	    //gl.glEnable(GL.GL_DEPTH_TEST);
		
		mundo = new Mundo();
		
		iniciarJogo();
	}
	
	private void iniciarJogo()
	{
		mundo.getObjetos().clear();
		
		mosaico = new Mosaico(Combinacoes.getMosaico(0));
		
		mundo.getObjetos().add(mosaico);
		mundo.getObjetos().addAll(Combinacoes.getPecas());
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    
		gl.glViewport(0, 0, width, height);

//		glu.gluOrtho2D(-30.0f, 30.0f, -30.0f, 30.0f);
	    glu.gluPerspective(60, width/height, 100, 500);				// projecao Perpectiva 1 pto fuga 3D    
//		gl.glFrustum (-5.0, 5.0, -5.0, 5.0, 10, 100);				// projecao Perpectiva 1 pto fuga 3D
//	    gl.glOrtho(-30.0f, 30.0f, -30.0f, 30.0f, -30.0f, 30.0f);	// projecao Ortogonal 3D
	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		//gl.glMatrixMode(GL.GL_PROJECTION);
		//gl.glLoadIdentity();
		
		//mundo.posicionaCamera(gl, glu);
		 
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		mundo.posicionaCamera(gl, glu);
		
		drawAxis();
		
		mundo.desenha(gl, glu);

		gl.glFlush();
	}
	
	public void drawAxis()
	{
		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();
		
		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();
		
		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{		
		executarOperacao(Operacao.getOperacaoDeTeclado(e));
		
		redesenhar();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{	
		Ponto p = getPontoDeEventoMouse(e);
		
		ObjetoGrafico selecao = mundo.localizarObjeto(p);
		
		if (selecionado != null)
			if (!selecionado.equals(selecao))
				selecionado.setSelecionado(false);
				
		girarSelecionado = (selecao != null && selecao == selecionado);
		
		selecionado = selecao;
		
		if (selecionado != null)
		{			
			selecionado.setSelecionado(true);
			pontoSelecao = p;
			
		}
		
		redesenhar();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (selecionado != null)
		{
			if (selecionado instanceof Peca)
			{
				boolean encaixa = ((Peca)selecionado).verificaEncaixe(mosaico);
				
				if (encaixa)
				{
					((Peca)selecionado).encaixar(mosaico);
				}
				else
				{
					if (girarSelecionado)
					{
						selecionado.rotacionar(90);
						
						girarSelecionado = false;
					}
				}
				
				redesenhar();
				
				if (mosaico.todosBlocosEstaoOcupados())
				{
					JOptionPane.showMessageDialog(null, "Fim de jogo");
					iniciarJogo();
					redesenhar();
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (selecionado != null)
		{	
			girarSelecionado = false;
			
			Ponto p = getPontoDeEventoMouse(e);
			
			double translacaoX = p.getX() - pontoSelecao.getX();
			double translacaoY = p.getY() - pontoSelecao.getY();
			
			selecionado.transladar(translacaoX, translacaoY);
			
			pontoSelecao = p;
			
			if (selecionado instanceof Peca)
			{
				((Peca) selecionado).desencaixar(mosaico);
			}
			
			redesenhar();
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{	
		boolean positivo = e.getWheelRotation() > 0;
		double taxa = Math.abs(e.getWheelRotation());
		
		if (e.isControlDown())
		{		
			if (positivo)
				mundo.getCamera().aproximar(taxa);
			else
				mundo.getCamera().afastar(taxa);
		}
		else
		{		
			if (e.isShiftDown())
			{
				if (positivo)					
					mundo.getCamera().esquerda(taxa);
				else
					mundo.getCamera().direita(taxa);
			}
			else
			{
				if (positivo)					
					mundo.getCamera().acima(taxa);
				else
					mundo.getCamera().abaixo(taxa);					
			}
		}
		
		redesenhar();
	}
	
	private Ponto getPontoDeEventoMouse(MouseEvent e)
	{		
		double xTela = e.getComponent().getWidth();
		double yTela = e.getComponent().getHeight();
		
		return mundo.getCamera().convertePontoTela(e.getX(), e.getY(), xTela, yTela);		
	}
	
	private void redesenhar()
	{
		glDrawable.display();
	}
	
	private void executarOperacao(Operacao operacao)
	{
		switch (operacao)
		{	
			case NENHUM :
				return;
			
			case APROXIMAR_CAMERA :
				mundo.getCamera().aproximar();
				break;
				
			case AFASTAR_CAMERA :
				mundo.getCamera().afastar();
				break;
				
			case MOVER_CAMERA_ESQUERDA :
				mundo.getCamera().esquerda();
				break;
				
			case MOVER_CAMERA_DIRETA :
				mundo.getCamera().direita();
				break;
				
			case MOVER_CAMERA_ACIMA :
				mundo.getCamera().acima();
				break;
				
			case MOVER_CAMERA_ABAIXO :
				mundo.getCamera().abaixo();
				break;
			
			default : break;
		}
		
		if (selecionado != null)
		{
			switch (operacao)
			{
				case CANCELAR :							
					//selecionado.limparSelecao();
					selecionado = null;
					break;					

				case AMPLIAR :
					selecionado.escalar(2.0);
					break;
				
				case REDUZIR :
					selecionado.escalar(0.5);
					break;
				
				case MOVER_DIREITA :
					selecionado.transladar(+5, 0);
					break;
				
				case MOVER_ESQUERDA :
					selecionado.transladar(-5, 0);
					break;
				
				case MOVER_ACIMA :
					selecionado.transladar(0, +5);
					break;
				
				case MOVER_ABAIXO :
					selecionado.transladar(0, -5);
					break;
				
				case GIRAR_DIREITA :
					selecionado.rotacionar(90);
					break;
				
				case GIRAR_ESQUERDA :						
					selecionado.rotacionar(90);
					break;
				
				case IMPRIMIR_MATRIZ :						
					selecionado.exibeMatrizTransformacao();
					break;
				
				case IMPRIMIR_VERTICES :
					//selecionado.exibeVertices();
					break;
				
				default : break;
			}
		}
	}
}
