package br.furb.cg.n4.visao;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

import br.furb.cg.n4.controle.AmbienteGrafico;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private int janelaLargura = 800; 
	private int janelaAltura = 600;
	private boolean fullscreen = false;	
	
	private GLCanvas canvas;
	
	public Frame() 
	{		
		super("CG-N4");
		
		setBounds(300, 150, janelaLargura, janelaAltura + 22);
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
		canvas = new GLCanvas(glCaps);
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.addMouseWheelListener(renderer);
		
		add(canvas, BorderLayout.CENTER);
	}
	
	public void init(boolean bFullScreen) 
	{
	    fullscreen = bFullScreen;
	    setUndecorated(bFullScreen);
	    setVisible(true);
	    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(bFullScreen ? this : null);
	    canvas.requestFocus();
	}
	
	public void setFullscreen(boolean bFullscreen) 
	{
	    if (fullscreen != bFullscreen) 
	    {
	        this.dispose();
	        init(bFullscreen);
	    }
	}
	
	public static void main(String[] args) 
	{
		new Frame().init(false);
	}
	
	
	
}
