package desuso;


public class ejemplo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num_threads = 4;
		Thread[] t = new Thread[num_threads];
		barrera bar = new barrera(num_threads);
		
		for(int i=0;i<num_threads;i++) {
			t[i] =  new Thread(new SumaConcurrente(bar, "T"+i));	//Le ponemos la barrera y el nombre del hilo
			t[i].start();
		}
		
		for(int i=0;i<num_threads;i++){

			try {
				t[i].join();

			} catch (InterruptedException e) {

				System.out.println("Error: en la espera del hilo");
			}
		}
		
		System.out.println("Resultado final (con nuestro candado):"+SumaConcurrente.suma);
		
	}

}
