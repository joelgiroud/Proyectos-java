package uam.so.barrera;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock; 

class SumaConcurrente implements Runnable{ 
	static final int N=200000;
	static int suma=0;
	private String nombre; 
	private ReentrantLock re; 
	private CyclicBarrier barrera; 
	public SumaConcurrente(ReentrantLock rt,CyclicBarrier barrera,String n) 
	{ 
		re = rt; 
		this.barrera=barrera;
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
		try {
			barrera.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Resultado final con sincronizacion (barrera):"+suma);
		
	} 
} 
