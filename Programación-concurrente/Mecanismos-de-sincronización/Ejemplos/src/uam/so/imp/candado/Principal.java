package uam.so.imp.candado;

public class Principal{ 
	static final int MAX_T = 4; 
	public static void main(String[] args) 
	{ 
		Candado c = new Candado(); 

		Thread[] t = new Thread[MAX_T];
		
		
		
		for(int i=0;i<MAX_T;i++) {

			t[i]=  new Thread(new SumaConcurrente(c, "T"+i));
			t[i].start();
		}
		
		for(int i=0;i<MAX_T;i++){

			try {
				t[i].join();

			} catch (InterruptedException e) {

				System.out.println("Error: en la espera del hilo");
			}
		}
		
		System.out.println("Resultado final (con nuestro candado):"+SumaConcurrente.suma);
		

		
	} 
} 