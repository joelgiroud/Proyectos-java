package desuso;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
/*Es una cola segura bajo concurrencia, se basa en un bloqueo libre y, en general, 
 *puede proporcionar un mayor rendimiento en escenarios de acceso multiproceso comunes.*/
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicInteger;
/*
 * Un dato booleano que se usará para operaciones atómicas (hace que los hilos se "formen"
 * para realizar operaciones)
 */
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
/*
 *Permite habilitar o deshabilitar un hilo del planificador; es un soporte para los candados
 *(habilitar o deshabilitar un hilo) 
 */


public class barrera {
	private ConcurrentLinkedQueue<Thread> cola = new ConcurrentLinkedQueue<Thread>();
	//Esta cola almacenará hilos
	
	private final int capacidad;			//De cuántos hilos estamos hablando
	private final AtomicBoolean estado;		//Nos avisará cuando todos los procesos lleguen a la barrera
	//private final AtomicBoolean isInList;	//Nos dirá si el hilo ya está en la barrera
	private final AtomicInteger contador;	//Es la cuenta de cuántos procesos llegaron a la barrera
	@SuppressWarnings("unused")
	//Semaphore semaforo;
	Semaphore semaforo2;
	ReentrantLock candado;

	public barrera(int capacidad) {
		//Constructor donde es posible inicializar el candado y acerrado
		this.capacidad =  capacidad;
		this.estado = new AtomicBoolean(false);
		this.contador = new AtomicInteger(0);
		//this.semaforo = new Semaphore(1);
		this.semaforo2 = new Semaphore(capacidad);
		this.candado = new ReentrantLock();
	}
	
	public void activar(){
		boolean interrumpido = false;
		
		//Con esta instrucción se obtiene el hilo actual
		Thread hilo = Thread.currentThread();
		/*En este preciso momento de la ejecución, se desconoce si
		 *la ejecución continuará o quedará en standby, por lo que 
		 *el hilo debe agregarse a la cola concurrente
		*/
		

		try {
			semaforo2.acquire();
	
			//El hilo se bloquea si el número de hilos es menor a la capacidad...
			
			while ( true ) {

				if(cola.add(hilo)) {	//Se forma el hilo en la cola concurrente; obtendría una referencia de sí mismo
					contador.getAndIncrement();
					System.out.println("Contador: "+contador.intValue());
				}
				
				

				/*
				 * El método park() deshabilita el hilo actual del planificador para propósitos de programación de hilos.
				 * El hilo se habilitara si y sólo si ocurre:
				 *   1.- Otro hilo ejecuta el método unpark con el el valor del hilo actual
				 *   2.- Otro hilo interumpe al hilo actual
				 *   3.- Otra razon propia de eventos en la máquina virtal
				 */
					candado.lock();
					if(contador.intValue()<capacidad) {	//Si es múltiplo de la capacidad
						System.out.println("Thread: "+hilo.getId()+"-> Lock 1");
						candado.unlock();
						cola.add(hilo);
						contador.getAndIncrement();
						LockSupport.park( );
						//semaforo.release();
					}
					else {
						desactivar(contador.intValue());	//Aquí hay hilos que no cumplen el if
						//semaforo.release();
						candado.unlock();
						break;
						
					}
					if (Thread.interrupted()){ // Si el hilo fue interumpido...
						interrumpido= true;
					}
					semaforo2.release();

				}
		} catch (InterruptedException e) {
						e.printStackTrace();
		 }

		/*
		 * Si superamos este while, significa que la barrera se llenó
		*/
		if (interrumpido) {          // reassert interrupt status on exit
			hilo.interrupt();      //Interrumpimos el hilo 
		}

		//System.out.println("Thread: "+hilo.getId()+"-> Lock 2");
		
		//} catch (InterruptedException e) {
			//e.printStackTrace();
		 // }

	}
	
	private void desactivar(int cont) {	//Esto debería ser una función monohilo
		if(cont>0) {
			System.out.println("Rompiendo ciclo, somos: "+cont);
			
			if( (cont==capacidad) ){	//Si el contador es menor o igual a la capacidad
				for(int i=0; i<capacidad ; i++){
					LockSupport.unpark(cola.poll());
				}
				contador.set(0);
				estado.set(true);	//Verdadero -> libre
				//El metodo unpark (Thread thread) habilita el hilo dado
				//Recordemos que unpark -> habilitar	y	park -> deshabilitar
			}
			else if (cont>capacidad){
				for(int i=0; i<capacidad; i++){
					LockSupport.unpark(cola.poll());
					contador.decrementAndGet();
				}
				//contador.set(cont-capacidad);
			}
			System.out.println("Ciclo roto, somos: "+ contador.intValue());
		}
		else {
			System.out.println("Contador es cero, no se liberará nada");
		}
	}
}
