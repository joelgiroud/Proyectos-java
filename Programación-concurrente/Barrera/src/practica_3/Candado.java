package practica_3;

import java.util.concurrent.ConcurrentLinkedQueue;	
/*Es una cola segura bajo concurrencia, se basa en un bloqueo libre y, en general, 
 *puede proporcionar un mayor rendimiento en escenarios de acceso multiproceso comunes.*/
import java.util.concurrent.atomic.AtomicBoolean;
/*
 * Un dato booleano que se usará para operaciones atómicas (hace que los hilos se "formen"
 * para realizar operaciones)
 */
import java.util.concurrent.locks.LockSupport;
/*
 *Permite habilitar o deshabilitar un hilo del planificador; es un soporte para los candados
 *(habilitar o deshabilitar un hilo) 
 */



public class Candado{

	private ConcurrentLinkedQueue<Thread> cola = new ConcurrentLinkedQueue<Thread>();
	//Esta cola almacenará hilos
	
	//Los operadores atómicos pueden ser de varios tipos
	private final AtomicBoolean estado;	//Abierto -> verdadero o falso.
	@SuppressWarnings("unused")
	private Thread propietario=null;	//propietario -> quien tiene el candado.


	
	public Candado() {
		//Variable booleana desde el constructor
		estado =  new AtomicBoolean(true);	//Por default, el candado está abierto
	}
	
	public Candado(boolean valor) {
		//Constructor donde es posible inicializar el candado y acerrado
		estado =  new AtomicBoolean(valor);
	}

    /**
     *Trata de obtener el candado. Si lo obtiene regresa, si no lo obtiene se bloquea
     *
     * @param -----
     * @return ----
     * @throws ----
	 */

	public void lock() {

		boolean interrumpido = false;
		//Con esta instrucción se obtiene el hilo actual
		Thread hilo = Thread.currentThread();
		
		/*En este preciso momento de la ejecución, se desconoce si
		 *la ejecución continuará o quedará en standby, por lo que 
		 *el hilo debe agregarse a la cola concurrente
		 */
		
		cola.add(hilo);
		//Se forma el hilo en la cola concurrente; obtendría una referencia de sí mismo
		
		//System.out.println("Thread: "+hilo.getId()+"-> Lock 0");

		//El hilo se bloquea si el que está al frente (de la cola) es diferente del hilo actual
		//o bien si no puede adquirir el candado
		while (cola.peek() != hilo || !estado.compareAndSet(true, false)) {
		//La función peek regresa a quien está al frente de la cola
		//El que peek() == hilo no es garantía de que el próximo en pasar sea el hilo actual 
			//instancia.compareAndSet(valor esperado, si es el esperado sustituir con este valor)
			//si se cumple, compareAndSet regresa true
			
			/*
			* El método park() deshabilita el hilo actual del planificador para propósitos de programación de hilos.
			* El hilo se habilitara si y sólo si ocurre:
			*   1.- Otro hilo ejecuta el método unpark con el el valor del hilo actual
			*   2.- Otro hilo interumpe al hilo actual
			*   3.- Otra razon propia de eventos en la máquina virtal
			*/
			LockSupport.park( );

			//System.out.println("Thread: "+hilo.getId()+"-> Lock 1");
			if (Thread.interrupted()){ // Si el hilo fue interumpido...
				interrumpido= true;
			}
		}
		/*
		 * Si superamos este while, significa que 
		 * la sección crítica está libre y que 
		 * el proceso actual está al frente de la cola
		 */

		propietario=cola.remove(); //Guardamos el propitario del candado
	
		if (interrumpido) {          // reassert interrupt status on exit
			hilo.interrupt();      //Interrumpimos el hilo 
		}
		//System.out.println("Thread: "+hilo.getId()+"-> Lock 2");
	}



	public boolean tryLock() {
	
		boolean flag=estado.compareAndSet(true, false);	//Si el estado es verdadero, le pongo falso
		if(flag) {	//Si lo anterior se cumple...
			propietario=Thread.currentThread();	//"Yo soy el propietario"
			return true;	//Tuve acceso a la sección crítica
		}
		return false;	//No tuve acceso a la sección crítica
	}

	
	
	public void unlock() {
		estado.set(true);	//Ponemos verdadero a la variable booleana
		LockSupport.unpark(cola.peek());
		//El metodo unpark (Thread thread) habilita el hilo dado
		//Recordemos que unpark -> habilitar	y	park -> deshabilitar
	}

}
