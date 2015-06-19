package br.furb.cg.n4.modelo;

public class Ponto
{
	public static int RAIO_PONTO = 5;
	
	private double x;
	private double y;
	private double z;
	private double w;
	
	public Ponto()
	{
		this(0, 0, 0, 1.0);
	}
	
	public Ponto(double x, double y)
	{
		this(x, y, 0, 1.0);
	}
	
	public Ponto(double x, double y, double z)
	{
		this(x, y, z, 1.0);
	}
	
	public Ponto(double x, double y, double z, double w)
	{
		setX(x);
		setY(y);
		setZ(z);
		setW(w);
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
	}

	public double getW()
	{
		return w;
	}

	public void setW(double w)
	{
		this.w = w;
	}
	
	public boolean isPontoProximo(Ponto outro)
	{
		double xmax = getX() + RAIO_PONTO;
		double xmin = getX() - RAIO_PONTO;
		double ymax = getY() + RAIO_PONTO;
		double ymin = getY() - RAIO_PONTO;
		
		return outro.getX() <= xmax && 
			   outro.getX() >= xmin && 
			   outro.getY() <= ymax && 
			   outro.getY() >= ymin;
	}
	
	public void exibirCoordenadas()
	{
		System.out.println("Ponto: [" + toString() + "]");
	}
	
	@Override
	public String toString()
	{
		return String.format("x=%s, y=%s, z=%s, w=%s", getX(), getY(), getZ(), getW());
	}
}
