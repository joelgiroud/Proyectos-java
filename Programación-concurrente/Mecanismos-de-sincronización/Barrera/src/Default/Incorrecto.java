package Default;

import java.util.Random;


//MÉTODO NO SINCRONIZADO
//MÉTODO SINCRONIZADO (Usa thread-safe)
/*
* Generalmente, los hilos al ejecutarse, tratan de realizar todo lo más rápidamente posible,
* este proyecto ejemplifica la sicronización entre hilos a través de una barrera (que es un mecanismo de sinc)
* 
* En esta clase incorrecta el hilo principal no espera a los demás
* 
* Esta aplicación envía a varios hilos a realizar un cálculo complejo,
* recupera todos los resultados y presenta el total. 
*/
public class Incorrecto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int NUM_TH = 4;	//Número de hilos que trabajarán
		Operacion[] oper = new Operacion[NUM_TH];
		double resultado = 0;
		
		for (int i=0; i<NUM_TH; i++) {
		oper[i] = new Operacion();
		}
		
		for (int i=0; i<NUM_TH; i++) {
			oper[i].start();
		}
		
		for (int i=0; i<NUM_TH; i++) {
			resultado = resultado + oper[i].getValue();
		}
		
		System.out.println("Total: " + resultado);
	}
	
	//Operación de cálculo intensivo
	private static class Operacion extends Thread{
		private double value = 0;
		
		@Override
		public void run() {
			
			Random rnd = new Random();
			for(int i=0; i<1000000; i++) {	//1M
				double tmpVal = rnd.nextDouble();
				tmpVal = tmpVal*rnd.nextFloat();
				value = value + cbrt(tmpVal);
			}
			
			System.out.println(Thread.currentThread().getName() + ": " + getValue() );
		}
		
		public double getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		
		private double atan(double value) {
			return Math.atan(value);
		}
		
		private double tan(double value) {
			return Math.tan(value);
		}
		
		private double cbrt(double value) {
			return Math.cbrt(value);
		}
		
	}
}
