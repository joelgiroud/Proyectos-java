package uam.so.barrera;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock; 

public class Principal{ 
	static final int MAX_T = 4; 
	public static void main(String[] args) 
	{ 
		ReentrantLock rel = new ReentrantLock(); 
		CyclicBarrier barrera = new CyclicBarrier(MAX_T);
		Thread[] t = new Thread[MAX_T];
		
		
		for(int i=0;i<MAX_T;i++) {

			t[i]=  new Thread(new SumaConcurrente(rel,barrera,"T"+i));
			t[i].start();
		}
		
		for(int i=0;i<MAX_T;i++){

			try {
				t[i].join();

			} catch (InterruptedException e) {

				System.out.println("Error: en la espera del hilo");
			}
		}
		


		
	} 
} 