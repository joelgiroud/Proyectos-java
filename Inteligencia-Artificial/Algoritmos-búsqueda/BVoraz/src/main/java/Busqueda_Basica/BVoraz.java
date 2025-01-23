package Busqueda_Basica;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class BVoraz {
    NodoCosto nodoini;
    NodoCosto Actual;
    LinkedList<NodoCosto> EstadosAbiertos = new LinkedList<>();
    LinkedList<NodoCosto> EstadosCerrados = new LinkedList<>();
    ArrayList<NodoCosto> hijos = new ArrayList<>();
    
    
    public BVoraz(){
        
    }   //Constructor vacío
    
    public BVoraz(NodoCosto nodoini){
        this.nodoini=nodoini;
    }    
    
    public void busca(NodoCosto obj){
        EstadosAbiertos.add(nodoini);//Insertar Estado_inicial en Est_abiertos
        Actual=EstadosAbiertos.getFirst();//Actual = Primero Est_abiertos 
        while((!Actual.nombre.equals(obj.nombre))&&(EstadosAbiertos.isEmpty()==false)){
            //mientras Actual no es_final? y Est_abiertos no vacía? hacer{
            EstadosAbiertos.removeFirst();//Quitar_primero de Est_abiertos
            EstadosCerrados.add(Actual);//Insertar Actual en Est_cerrados
            generarSucesores(Actual);//hijos ← generar_sucesores_ordenados_por_costo (Actual)
            filtrarRepetidos(hijos);//hijos ← tratar_repetidos (Hijos, Est_cerrados, Est_abiertos)
            añadirAEstadosAbiertos(hijos);//Insertar Hijos en Est_abiertos
            Collections.sort(EstadosAbiertos);  //ESTE PASO ES FUNDAMENTAL
            Actual=EstadosAbiertos.getFirst();//Actual ← Primero Est_abiertos
        }
        //¿POR QUÉ TENGO QUE REPETIR UNA VEZ MÁS EL BUCLE PARA QUE ME DÉ LOS RESULTADOS DESEADOS?
        EstadosAbiertos.remove(0);//Quitar_primero de Est_abiertos
        EstadosCerrados.add(Actual);//Insertar Actual en Est_cerrados
        generarSucesores(Actual);//hijos ← generar_sucesores_ordenados_por_costo (Actual)
        filtrarRepetidos(hijos);//hijos ← tratar_repetidos (Hijos, Est_cerrados, Est_abiertos)
        añadirAEstadosAbiertos(hijos);//Insertar Hijos en Est_abiertos
    }
    
    public void muestra(){
        int i=0;
        for(NodoCosto c : EstadosCerrados){
            i++;
            System.out.println("\nNodo "+i+":");
            System.out.println("Nombre: "+c.getNombre()+".");
            System.out.println("");
        }
    }

    void generarSucesores (NodoCosto actual){//hijos ← generar_sucesores_ordenados_por_costo (Actual)
        Collections.sort(actual.destinos);     //Ordenamos por costo
        for(int l=0; l<actual.destinos.size(); l++){
            //Recorremos destinos de actual
            hijos.add(actual.destinos.get(l));         ///hijos ← generar_sucesores_por_costo (Actual)
        }
    }
    
    private void filtrarRepetidos(ArrayList<NodoCosto> listaclon){
        for(int b=0; b<listaclon.size(); b++){  //Recorremos recordando posición
            for(int a=0; a<listaclon.size(); a++){          //Recorremos recordando posición
                if(a!=b){
                    if(listaclon.get(a)==(listaclon.get(b))){   //Si el Objeto#b es igual al Objeto#a...
                        //                                          y no se trata de la misma entidad
                        listaclon.remove(a);                    //eliminamos el segundo
                    }
                }
            }
        }
        for(int x=0; x<listaclon.size(); x++){  //Recorremos recordando posición
            if(EstadosCerrados.contains(listaclon.get(x)))
                listaclon.remove(x);
        }        
        for(int x=0; x<listaclon.size(); x++){  //Recorremos recordando posición
            if(EstadosAbiertos.contains(listaclon.get(x)))
                listaclon.remove(x);
        } 
    }
    
   private void añadirAEstadosAbiertos(ArrayList<NodoCosto> hijos) {
        //boolean repetido=false;
        hijos.forEach(c -> {
            EstadosAbiertos.add(c);
        });
    }
}
