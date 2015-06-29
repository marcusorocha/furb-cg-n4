package br.furb.cg.n4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class BlocoPeca extends Bloco 
{
	public BlocoPeca(Forma forma) 
	{
		super(forma);		
	}
	
	public BlocoPeca(FormaType tipo)
	{
		super(tipo);
	}
	
	@Override
	void desenhar(GL gl, GLU glu)
	{	
		/*
		//gl.glBegin(GL.GL_POLYGON);
		//gl.glBegin(GL.GL_LINE_STRIP);
		//gl.glBegin(GL.GL_TRIANGLES);
		//gl.glBegin(GL.GL_TRIANGLE_FAN);
		//gl.glBegin(GL.GL_TRIANGLE_STRIP);
		//gl.glBegin(GL.GL_QUADS);
		gl.glBegin(GL.GL_QUAD_STRIP);
		{
			gl.glColor3f(0.0f, 1.0f, 0.0f); // Verde
			
			//for (Ponto v : getVerticesBorda())
			//	gl.glVertex3d(v.getX(), v.getY(), v.getZ());
			
			
			yMin = -10;
			yMax = +10;
			xMin = -10;
			xMax = +10;
			
			gl.glVertex3d(xMin, yMax, 0);
			
			//gl.glVertex3d(xMin, yMin, 0);
														
			//for (Ponto v : getVericesForma())
			//	gl.glVertex3d(v.getX(), v.getY(), v.getZ());
			
			for (int i = 0; i < getVericesForma().size(); i++)
			{
				//int x = i % getVericesForma().size();
				int x = i;
				
				Ponto v = getVericesForma().get(x);
				
				gl.glVertex3d(v.getX(), v.getY(), v.getZ());
			}
			
			
			gl.glVertex3d(xMin, yMax, 0);
			gl.glVertex3d(xMax, yMax, 0);
			gl.glVertex3d(xMax, yMin, 0);
			gl.glVertex3d(xMin, yMin, 0);			
		}
		gl.glEnd();		
		*/
		
		/*
		float width = 2.0f;
		float r0 = 5.0f;
	    float r1 = 8.0f;
	    //float r2 = 4.35f;
	    int teeth = 30;
	            
	    float da = 2.0f * (float) Math.PI / teeth / 4.0f;
	            
	    //gl.glShadeModel(GL.GL_FLAT);

	    //gl.glNormal3f(0.0f, 0.0f, 1.0f);

	    // Draw front face
	    gl.glBegin(GL.GL_QUAD_STRIP);
	    for (int i = 0; i <= teeth; i++)
	    {
	        float angle = i * 2.0f * (float) Math.PI / teeth;
	        
	        gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5f);
	        gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
	        
	        if (i < teeth)
	        {
	            //gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5f);
	            //gl.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
	        }
	    }
	    gl.glEnd();
	    */
		
		switch (getTipoForma())
		{		
			case QUADRADO :
			{
				gl.glBegin(GL.GL_QUAD_STRIP);
				{
					gl.glColor3f(0.0f, 1.0f, 0.0f); // Verde
					
					for (int i = 0; i <= getVerticesForma().size(); i++)
					{
						int x = i % getVerticesForma().size();
						
						Ponto f = getVerticesBorda().get(x);
						Ponto d = getVerticesForma().get(x);
						
						gl.glVertex3d(d.getX(), d.getY(), d.getZ());
						gl.glVertex3d(f.getX(), f.getY(), f.getZ());
					}
				}
				gl.glEnd();
				
				break;
			}
			
			case CRUZ :
			{
				List<Ponto> borda = new ArrayList<Ponto>();
				List<Ponto> forma = new ArrayList<Ponto>();
				
				borda.add(new Ponto(-10, +10));
				borda.add(new Ponto(-7.5, +10.0));
				borda.add(new Ponto(+7.5, +10.0));
				borda.add(new Ponto(+10, +10));
				borda.add(new Ponto(+10, +7.5));
				borda.add(new Ponto(+10, -7.5));
				borda.add(new Ponto(+10, -10));
				borda.add(new Ponto(+7.5, -10));
				borda.add(new Ponto(-7.5, -10));
				borda.add(new Ponto(-10, -10));
				borda.add(new Ponto(-10, -7.5));
				borda.add(new Ponto(-10, +7.5));
									
				forma.add(new Ponto(-2.5, +2.5));
				forma.add(new Ponto(-2.5, +5.0));
				forma.add(new Ponto(+2.5, +5.0));
				forma.add(new Ponto(+2.5, +2.5));
				forma.add(new Ponto(+5.0, +2.5));
				forma.add(new Ponto(+5.0, -2.5));
				forma.add(new Ponto(+2.5, -2.5));
				forma.add(new Ponto(+2.5, -5.0));
				forma.add(new Ponto(-2.5, -5.0));
				forma.add(new Ponto(-2.5, -2.5));
				forma.add(new Ponto(-5.0, -2.5));
				forma.add(new Ponto(-5.0, +2.5));				

				//gl.glColor3f(0.0f, 1.0f, 0.0f); // Verde
				
				float green[] = { 0.0f, 0.8f, 0.2f, 1.0f };
												
				gl.glBegin(GL.GL_QUAD_STRIP);
				{
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, green, 0);
					
					for (int i = 0; i <= forma.size(); i++)
					{
						int x = i % forma.size();
						
						Ponto f = borda.get(x);
						Ponto d = forma.get(x);
						
						gl.glVertex3d(d.getX(), d.getY(), d.getZ());
						gl.glVertex3d(f.getX(), f.getY(), f.getZ());
					}
				}
				gl.glEnd();
				
				gl.glBegin(GL.GL_QUAD_STRIP);
				{
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, green, 0);
					
					for (int i = 0; i <= forma.size(); i++)
					{
						int x = i % forma.size();
												
						Ponto d = forma.get(x);
						
						gl.glVertex3d(d.getX(), d.getY(), -10.0);
						gl.glVertex3d(d.getX(), d.getY(), +10.0);
					}
				}
				gl.glEnd();
				
				break;
			}
				
			case CIRCULO : break;
		}
	}

}
