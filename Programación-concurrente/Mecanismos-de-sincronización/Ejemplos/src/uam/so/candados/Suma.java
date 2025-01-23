package uam.so.candados;

class Suma implements Runnable{ 
	static final int N=200000;
	static int suma=0;
	private String nombre; 

	public Suma(String n) 
	{ 
		
		nombre = n; 
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		for(int i=0;i<N;i++)
		{ 
			
				suma++;

		} 
		
	} 
} 