package Busqueda_Basica;
import Referencia.Nodo;
import java.util.ArrayList;

public class NodoCosto extends Nodo implements Cloneable, Comparable<NodoCosto>  {
    ArrayList<NodoCosto> destinos = new ArrayList<>();  //Aquí irán todos los nexos del nodo
    int costo;

    
    public NodoCosto (String a, Object o){           //CONSTRUCTOR
        this.nombre=a;
        this.contenido=o;
        this.ocupado=false;
        this.revisado=false;
    } //Constructor
    
    public NodoCosto (){                             //CONSTRUCTOR
        this.revisado=false;
        this.ocupado=false;
    }                   //Constructor
           
    public NodoCosto (String a){                     //CONSTRUCTOR
        this.nombre=a;
        this.revisado=false;
        this.ocupado=false;
    }           //Constructor
    @Override
    public NodoCosto clone() {
        try {
            return (NodoCosto) super.clone();
        }
        catch(CloneNotSupportedException e) {
            throw new IllegalStateException("A");
        }
    }
    
    public void AgregaDest(NodoCosto d, int co) {
        if(!(destinos.contains(d))){
            NodoCosto b = d.clone();
            b.costo=co;
            this.destinos.add(b);
        }
        else{
            System.out.println("El nodo ya se encuentra en la lista de destinos");
        }
    }

    public NodoCosto getDestino(int index) {
        return destinos.get(index);
    }
    
    public int getCosto(){
        return costo;
    }

    public void setCosto(int costo) {
        this.costo=costo;
    }
    
    boolean ligaRepetida(NodoCosto nodo){
        boolean repetida=false;
        for(int e=0; e<ligas.size(); e++){
            if(ligas.get(e)==nodo){
            repetida = true;
            System.out.println("Ya existe el nodo "+nodo.getNombre()+" en la lista de ligas.");
            }
        }
        return repetida;
    }
    @Override
    public int compareTo(NodoCosto o) {
        return new Integer(this.getCosto()).compareTo(new Integer(o.getCosto()));
    }
}
