package Ciudades;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class BVorazHeur {
    CiudadHeur nodoini;
    CiudadHeur Actual;
    LinkedList<CiudadHeur> EstadosAbiertos = new LinkedList<>();
    LinkedList<CiudadHeur> EstadosCerrados = new LinkedList<>();
    ArrayList<CiudadHeur> hijos = new ArrayList<>();
    CiudadHeur aux;
    
    public BVorazHeur(CiudadHeur nodoini){
        this.nodoini=nodoini;
    }      //Constructor 
    
    public void busca(CiudadHeur Objetivo){
        /*
        Insertar Estado_inicial Est_abiertos
        Actual ← Primero Est_abiertos
        mientras Actual no es_final? y Est_abiertos no vacía? hacer
            Quitar_primero Est_abiertos
            Insertar Actual Est_cerrados
            hijos ← generar_sucesores_ordenados_por_peso (Actual)
            hijos ← tratar_repetidos (Hijos, Est_cerrados, Est_abiertos)
            Insertar Hijos Est_abiertos
            Actual ← Primero Est_abiertos
        */
        EstadosAbiertos.add(nodoini);//Insertar Estado_inicial en Est_abiertos
        Actual=EstadosAbiertos.get(0);//Actual = Primero Est_abiertos 
        
        while((Actual!=Objetivo)&&(EstadosAbiertos.isEmpty()==false)){//mientras Actual no es_final? y Est_abiertos no vacía? hacer{
            EstadosAbiertos.removeFirst();//Quitar_primero de Est_abiertos
            EstadosCerrados.add(Actual);//Insertar Actual en Est_cerrados
            generarSucesores(Actual);//hijos ← generar_sucesores_ordenados_por_distancia (Actual)
            filtrarRepetidos(hijos);//hijos ← tratar_repetidos (Hijos, Est_cerrados, Est_abiertos)
            añadirAEstadosAbiertos(hijos);//Insertar Hijos en Est_abiertos
            Collections.sort(EstadosAbiertos);//SI NO COLOCO ESTO, NO COMPILA CORRECTAMENTE
            Actual=EstadosAbiertos.getFirst();//Actual ← Primero Est_abiertos

        }
            //¿POR QUÉ TENGO QUE REPETIR UNA VEZ MÁS EL BUCLE PARA QUE ME DÉ LOS RESULTADOS DESEADOS?
            /*EstadosAbiertos.remove(0);//Quitar_primero de Est_abiertos
            EstadosCerrados.add(Actual);//Insertar Actual en Est_cerrados
            generarSucesores(Actual);//hijos ← generar_sucesores_ordenados_por_costo (Actual)
            filtrarRepetidos(hijos);//hijos ← tratar_repetidos (Hijos, Est_cerrados, Est_abiertos)
            añadirAEstadosAbiertos(hijos);//Insertar Hijos en Est_abiertos
            */            
        //Haré la sig. instrucción para que muestre el nodo destino
        EstadosCerrados.add(Objetivo);
    }
    
    public void muestra(){
        int i=1;
        System.out.println("Costo por heurística = Distancia al destino + Distancia al origen\n");
        //System.out.println("Ciudad Inicial:\t"+nodoini.toString());
        for(CiudadHeur c : EstadosCerrados){
            System.out.println("Ciudad "+i+":\t"+c.toString());
            i++;
        }
    }

    void generarSucesores (CiudadHeur actual){//hijos ← generar_sucesores_ordenados_por_distancia (Actual)
        //aux = actual;
        Collections.sort(actual.ConectaCon);     //Ordenamos por costo
        
        actual.ConectaCon.forEach(c -> {         //Recorremos ConectaCon de aux
        hijos.add(c);                   ///hijos ← generar_sucesores_ordenados_por_costo (Actual)
        });
    }

    private void filtrarRepetidos(ArrayList<CiudadHeur> listaclon){
        
        for(int b=0; b<listaclon.size(); b++){  //Recorremos recordando posición
            for(int a=0; a<listaclon.size(); a++){          //Recorremos recordando posición
                if(a!=b){
                    if(listaclon.get(a)==(listaclon.get(b))){   //Si el Objeto#b es igual al Objeto#a, y no se trata de la misma entidad
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
    
   private void añadirAEstadosAbiertos(ArrayList<CiudadHeur> hijos) {//Insertar Hijos en Est_abiertos
        //boolean repetido=false;
        for(int w=0; w<hijos.size(); w++){                  //Recorremos arreglo hijos
            if(!(EstadosAbiertos.contains(hijos.get(w)))){    //Recorremos arreglo EstadosAbiertos
                EstadosAbiertos.add(hijos.get(w));
            }
        }
    }
}
