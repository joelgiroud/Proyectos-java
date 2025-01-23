package practica_3;
//package Barrera;

import java.util.concurrent.Semaphore;

public class Barrera {
    private int capacidad;
    private Semaphore semaforo, exclusion, counter;

    public Barrera(int capacity) {
        this.capacidad = capacity;
        counter = new Semaphore(0);
        semaforo = new Semaphore(0);
        exclusion = new Semaphore(1);
    }

    public void bloquea() throws InterruptedException {
        exclusion.acquire();	//Adquiere un permiso de este bloqueo de sem�foros hasta que todos est�n disponibles
        
        if (counter.availablePermits() < capacidad - 1) {	//Si el n�mero de permisos disponibles es menor a la capacidad -1...
            counter.release();	//Liberamos el sem�foro;
            exclusion.release();
            semaforo.acquire();	
        }    
        else {
            //System.out.println("Liberar procesos");
            for (int i = 0; i < capacidad; i++) {
                semaforo.release();
            }
        }
        exclusion.release();
    }

}
