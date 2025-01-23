package uam.so.semaforos;

import java.util.concurrent.Semaphore;

class SumaConcurrente implements Runnable{ 
	static final int N=200000;
	static int suma=0;
	private String nombre; 
	private Semaphore s=null;
	public SumaConcurrente( Semaphore s, String n) 
	{ 
		this.s = s; 
		nombre = n; 
	} 
	public void run() 
	{ 
		System.out.println("Hilo: "+nombre);
		for(int i=0;i<N;i++)
		{ 
			try {
				s.acquire();//wait
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


				suma++;



			s.release();//signal
			

		} 
		
	} 
} 