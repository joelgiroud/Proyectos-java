package practica_3;

import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unused")
class SumaConcurrente implements Runnable{ 
	static int meta;	//Esto sería lo que le tocaría a cada hilo: 20*num_threads
	static int suma=0;
	private String nombre; 
	private Barrera b; 
	@SuppressWarnings("unused")
	private Candado c;
	
	public SumaConcurrente(Candado c, Barrera b, String n, int meta){ //Constructor 
		this.b = b;
		nombre = n;		//ID del hilo
		this.c = c;
		SumaConcurrente.meta = meta;
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		
		try {
			for(int i=0;i<meta;i++){
			
				//c.lock();	PARA FINES DE DEPURACIÓN
				//System.out.println("Suma antes de la barrera: " + suma);
				//c.unlock();
				
				b.bloquea();
				
				suma++;
			
				//c.lock();
				//System.out.println("Suma después de la barrera: " + suma);
				//c.unlock();

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	} 
} 
