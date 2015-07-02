package br.furb.cg.n4.controle;

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
import br.furb.cg.n4.visao.Frame;

public class AmbienteGrafico extends EventosAdapter
{
	private final boolean AMBIENTE_3D = true;
	
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Frame frame;
	private Mundo mundo;
	private Mosaico mosaico;
	private Peca selecionada;
	private Ponto pontoSelecao;
	private boolean girarSelecionado = false;
	
	public AmbienteGrafico(Frame frame)
	{
		this.frame = frame;
		selecionada = null;
	}

	public void init(GLAutoDrawable drawable)
	{
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		
		//gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		float pos[] = { 10.0f, 10.0f, 10.0f, 0.0f };
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
		gl.glEnable(GL.GL_LIGHT0);
		
		//gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_DEPTH_TEST);
		
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
		if (AMBIENTE_3D)
		{			
			gl.glMatrixMode(GL.GL_PROJECTION);
		    gl.glLoadIdentity();
		    
			gl.glViewport(0, 0, width, height);
		    
		    double proporcao = (double)width / (double)height;

		    glu.gluPerspective(80, proporcao, 100, 1000); // projecao Perpectiva 1 pto fuga 3D
		}
	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		if (!AMBIENTE_3D)
		{
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			
			mundo.posicionaCamera(gl, glu, AMBIENTE_3D);
		}
		else
		{
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		}
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		if (AMBIENTE_3D)
			mundo.posicionaCamera(gl, glu, AMBIENTE_3D);
			
		mundo.desenhaSRU(gl, glu);
		
		mundo.desenha(gl, glu);

		gl.glFlush();
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
		
		if (selecionada != null)
			if (!selecionada.equals(selecao))
				selecionada.setSelecionado(false);
				
		girarSelecionado = (selecao != null && selecao == selecionada);
					
		selecionada = (selecao instanceof Peca) ? (Peca)selecao : null;
		
		if (selecionada != null)
		{			
			selecionada.setSelecionado(true);
			pontoSelecao = p;
			
		}
	
		redesenhar();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (selecionada != null)
		{
			
			boolean encaixa = selecionada.verificaEncaixe(mosaico);
			
			if (encaixa)
			{
				selecionada.encaixar(mosaico);
				selecionada.setSelecionado(false);
				selecionada = null;
			}
			else
			{
				if (girarSelecionado && !selecionada.isEncaixada())
				{
					selecionada.rotacionar(90);
					
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
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (selecionada != null)
		{	
			girarSelecionado = false;
			
			Ponto p = getPontoDeEventoMouse(e);
			
			double translacaoX = p.getX() - pontoSelecao.getX();
			double translacaoY = p.getY() - pontoSelecao.getY();
			
			selecionada.transladar(translacaoX, translacaoY, 0);
			
			pontoSelecao = p;
			
			if (selecionada.isEncaixada())
				selecionada.desencaixar(mosaico);
			
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
				
			case MODO_TELA_CHEIA :
				frame.setFullscreen(true);
				break;
			
			case CANCELAR :
				frame.setFullscreen(false);
				break;
				
			case VISAO_DE_JOGO :
				mundo.getCamera().reinicializar();
				break;
			
			default : break;
		}
		
		if (selecionada != null)
		{
			switch (operacao)
			{
				case CANCELAR :
					selecionada.setSelecionado(false);
					selecionada = null;
					break;	
				
				case AMPLIAR :
					selecionada.escalar(2.0);
					break;
				
				case REDUZIR :
					selecionada.escalar(0.5);
					break;
				
				case MOVER_DIREITA :
					selecionada.transladar(+5, 0, 0);
					break;
				
				case MOVER_ESQUERDA :
					selecionada.transladar(-5, 0, 0);
					break;
				
				case MOVER_ACIMA :
					selecionada.transladar(0, +5, 0);
					break;
				
				case MOVER_ABAIXO :
					selecionada.transladar(0, -5, 0);
					break;
				
				case GIRAR_DIREITA :
					selecionada.rotacionar(90);
					break;
				
				case GIRAR_ESQUERDA :						
					selecionada.rotacionar(90);
					break;
				
				case IMPRIMIR_MATRIZ :						
					selecionada.exibeMatrizTransformacao();
					break;
					
				
				
				default : break;
			}
		}
	}
}
