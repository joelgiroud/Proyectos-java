package uam.so.imp.candado;


class SumaConcurrente implements Runnable{ 
	static final int N=20;
	static int suma=0;
	private String nombre; 
	private Candado c; 
	public SumaConcurrente(Candado c, String n) 
	{ 
		this.c = c; 
		nombre = n; 
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		for(int i=0;i<N;i++)
		{ 
			c.lock();

			suma++;
			


			c.unlock(); 


		} 

	} 
} 
