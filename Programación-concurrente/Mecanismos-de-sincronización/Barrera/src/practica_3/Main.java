package practica_3;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num_threads = 4;
		Thread[] t = new Thread[num_threads];
		Barrera bar = new Barrera(num_threads);
		Candado c = new Candado();	//ESTE CANDADO NO ESTÁ EN USO ACTUALMENTE, SÓLO ES PARA FINES DE DEPURACIÓN
		final int meta = 20;
		
		for(int i=0;i<num_threads;i++) {
			t[i] =  new Thread(new SumaConcurrente(c, bar, "T"+i, meta));	//Le ponemos como meta 20
			t[i].start();
		}
		
		for(int i=0;i<num_threads;i++){

			try {
				t[i].join();

			} catch (InterruptedException e) {

				System.out.println("Error: en la espera del hilo");
			}
		}
		
		System.out.println("\nResultado final: "+SumaConcurrente.suma+" de "+meta*num_threads);
		
	}

}
