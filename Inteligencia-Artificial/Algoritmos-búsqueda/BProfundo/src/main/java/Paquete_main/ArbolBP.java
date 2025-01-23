package Paquete_main;
import java.util.ArrayList;

public class ArbolBP{

    ArrayList<Nodo> hojas = new ArrayList<>();              //Aquí irán los nodos del árbol
    static Nodo aux;                                        //No se usa, es para fines experimentales
    int i;                                                  //No se usa, es para fines experimentales

    public ArbolBP(){
    }   //Constructor vacío

    public void recorrer(Nodo nodo){    //Esta vez trabajamos con Nodos, no con colas de nodos
        if(!(nodo.IsRevisado())){       //Si el nodo no ha sido revisado; es decir...
                                        //no se han examinado sus adyacentes
            this.hojas.add(nodo);          //Añadimos el nodo al árbol, en forma de hoja
            nodo.setOcupado(true);              //Lo marcamos como ocupado; es decir, que ya se agregó como hoja
            for(i=0; i<nodo.ligas.size(); i++){         //Contador del tamaño de #nodos adyacentes
                if(!(nodo.ligas.get(i).isOcupado())){   //Si el nodo ligado no está ocupado...
                    nodo.ligas.get(i).setPapa(nodo);  //Marcamos al nodo principal como papá de dicho nodo ligado
                    recorrer(nodo.ligas.get(i));      //Invocamos este mismo algoritmo, pero con dicho nodo ligado
                }
            }
            i=0;                //¿¿¿¿ALGUIEN ME PUEDE EXPLICAR POR QUÉ EL QUITARLE ESTO CAUSA PROBLEMAS????
            nodo.setRevisado(true); //Marcamos al nodo main como revisado (recorrido)
            System.out.println("El nodo "+nodo.getNombre()+" ha sido recorrido...");
        }
    }                                                       //Esto se repetirá hasta que la cola esté vacía
    
    public void mostrar(){
        System.out.println("\nA continuación se mostrará el contenido del Arbol de Búsqueda a lo Ancho...");
        for (int a=0; a<this.hojas.size(); a++){                //Contador del tamaño del arreglo hojas
            System.out.println("\nHoja "+(a+1)+":");            //Cabezal: Número de hoja
            System.out.println("\tNombre: "+hojas.get(a).nombre);   //Nombre de la hoja
            if(!(hojas.get(a).getPapa()==null)){                //Si la hoja tiene papá...
                Nodo Aux = hojas.get(a).getPapa();              //Copiamos a su papá en Aux 
                                    //(Lo hice así porque tuve problemas en la compilación)
                System.out.println("\tPapá: "+Aux.getNombre()+"\n");    //Imprimimos únicamente el nombre de su papá
            }
        }                                                  //Esto se repetirá para cada elemento del arreglo hojas
    }
}
