package br.furb.cg.n4.modelo;

import java.awt.event.KeyEvent;

public enum Operacao
{
	// Transformações de objeto
	MOVER_DIREITA,
	MOVER_ESQUERDA,
	MOVER_ACIMA,
	MOVER_ABAIXO,	
	AMPLIAR,
	REDUZIR,	
	GIRAR_DIREITA,
	GIRAR_ESQUERDA,
	
	// Transformações de camera
	APROXIMAR_CAMERA,
	AFASTAR_CAMERA,
	MOVER_CAMERA_ESQUERDA,
	MOVER_CAMERA_DIRETA,
	MOVER_CAMERA_ABAIXO,
	MOVER_CAMERA_ACIMA,
	VISAO_DE_JOGO,
	
	// Genérico
	CANCELAR,
	MODO_TELA_CHEIA,	
	
	IMPRIMIR_VERTICES,
	IMPRIMIR_MATRIZ,
	
	NENHUM;
	
	
	public static Operacao getOperacaoDeTeclado(KeyEvent e)
	{
		if (e.isControlDown())
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_ENTER : return MODO_TELA_CHEIA;
				case KeyEvent.VK_SPACE : return VISAO_DE_JOGO;
			}
		}
		else if (e.isShiftDown())
		{
			switch(e.getKeyCode())
			{				
				case KeyEvent.VK_LEFT  : return GIRAR_ESQUERDA;
				case KeyEvent.VK_RIGHT : return GIRAR_DIREITA;	
			}
		}
		else
		{		
			switch(e.getKeyCode())
			{				
				case KeyEvent.VK_I: return AFASTAR_CAMERA;
				case KeyEvent.VK_O: return APROXIMAR_CAMERA;
				case KeyEvent.VK_E: return MOVER_CAMERA_ESQUERDA;
				case KeyEvent.VK_D: return MOVER_CAMERA_DIRETA;
				case KeyEvent.VK_B: return MOVER_CAMERA_ABAIXO;
				case KeyEvent.VK_C: return MOVER_CAMERA_ACIMA;
				case KeyEvent.VK_M: return IMPRIMIR_MATRIZ;
				case KeyEvent.VK_V: return IMPRIMIR_VERTICES;					
				case KeyEvent.VK_ESCAPE : return CANCELAR;
			}
		}
		
		return NENHUM;
	}
}