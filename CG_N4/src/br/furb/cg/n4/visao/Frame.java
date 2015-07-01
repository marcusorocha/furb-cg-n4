package br.furb.cg.n4.visao;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

import br.furb.cg.n4.controle.AmbienteGrafico;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private int janelaLargura  = 700, janelaAltura = 700;
	
	public Frame() 
	{		
		super("CG-N4");
		
		setBounds(300, 150, janelaLargura, janelaAltura + 22);  // 500 + 22 da borda do título da janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
				
		AmbienteGrafico renderer = new AmbienteGrafico(this);

		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.addMouseWheelListener(renderer);
		
		add(canvas, BorderLayout.CENTER);
	}	
	
	public static void main(String[] args) 
	{
		new Frame().setVisible(true);
	}
	
}
