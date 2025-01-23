package uam.so.candados;

import java.util.concurrent.locks.ReentrantLock; 

class SumaConcurrente implements Runnable{ 
	static final int N=200000;
	static int suma=0;
	private String nombre; 
	private ReentrantLock re; 
	public SumaConcurrente(ReentrantLock rt, String n) 
	{ 
		re = rt; 
		nombre = n; 
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		for(int i=0;i<N;i++)
		{ 
			re.lock();

			suma++;
			


			re.unlock(); 


		} 

	} 
} 
