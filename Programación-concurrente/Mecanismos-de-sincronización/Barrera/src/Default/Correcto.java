package Default;

import java.util.Random;
import java.util.concurrent.CountDownLatch;	//Es un tipo de barrera de cuenta regresiva


//MÉTODO SINCRONIZADO (Usa thread-safe)
/*
 * Generalmente, los hilos al ejecutarse, tratan de realizar todo lo más rápidamente posible,
 * este proyecto ejemplifica la sicronización entre hilos a través de una barrera (que es un mecanismo de sinc)
 * 
 * 
 * 
 * Esta aplicación envía a varios hilos a realizar un cálculo complejo,
 * recupera todos los resultados y presenta el total. 
 */
public class Correcto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int NUM_TH = 4;	//CUATRO HILOS
		Operacion[] oper = new Operacion[NUM_TH];
		double resultado = 0;
		
		CountDownLatch barrera = new CountDownLatch(NUM_TH);	//Este objeto es el que permitirá hacer la sincronización
		
		for(int i=0; i<NUM_TH; i++) {
			oper[i] = new Operacion(barrera);	//Creando instancias en el arreglo oper
		}
		
		for(int i=0; i<NUM_TH; i++) {
			oper[i].start();	//Inicializamos
		}
		
		//DE AQUÍ NO VA A AVANZAR EL HILO PRINCIPAL HASTA QUE EL CONTADOR (CountDownLatch) SE DECREMENTE HASTA A CERO		
		try {
			barrera.await();
		} catch (InterruptedException e) {	//Significa que algo quiere interrumpir (generalmente terminar) este hilo
			e.printStackTrace();	//Escribe el seguimiento de la pila en System.errPrintStream
		}
		
		
		for(int i=0; i<NUM_TH; i++) {
			resultado = resultado + oper[i].getValue();	//Llama al método getValue del objeto Operacion de cada hilo y lo acumula
		}
		System.out.println("Total: " + resultado);
	}
	
	
	
	private static class Operacion extends Thread{	//Una clase Operacion que se tratará como hilo
		private double value = 0;
		private CountDownLatch barrera;
		
		Operacion(CountDownLatch barrera){
			//Este es un objeto compartido sincronizado
			this.barrera = barrera;
		}
		
		
		@Override
		public void run() {
			
			Random rnd = new Random();
			for(int i=0; i<1; i++) {	//
				double tmpVal = rnd.nextDouble();	//double aleatorio
				tmpVal = tmpVal;//*rnd.nextFloat();
				value = value + cbrt(tmpVal);	//raíz cúbica
			}
			
			System.out.println(Thread.currentThread().getName() + ": " + getValue() );
			
			barrera.countDown();	//Cuando se ejecutó la tarea run() prosigue un countDown...
			//Es decir, reduce en uno la fila de espera en el contador de la barrera
		}
		
		
		public double getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		
		private double atan(double value) {
			return Math.atan(value);
		}
		
		private double cbrt(double value) {
			return Math.cbrt(value);
		}
	}

}
