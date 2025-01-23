package desuso;


class SumaConcurrente implements Runnable{ 
	static final int N=20;
	static int suma=0;
	private String nombre; 
	private barrera c; 
	
	public SumaConcurrente(barrera c, String n){ //Constructor 
		this.c = c;
		nombre = n;		//ID del hilo
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		for(int i=0;i<N;i++)
		{
			
			c.activar();
			
			System.out.println("Suma antes de la barrera: " + suma);
			
			c.activar();

			suma++;
			
			System.out.println("Suma después de la barrera: " + suma);

			//c.unlock(); 


		} 

	} 
} 
