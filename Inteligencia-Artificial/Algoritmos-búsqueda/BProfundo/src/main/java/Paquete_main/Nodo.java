package Paquete_main;
import java.util.ArrayList;

public class Nodo {
    String nombre;                      //Sólo para identificarlo
    Object contenido;                   //Clase objeto, no se implementa ahora mismo
    ArrayList<Nodo> ligas = new ArrayList<>();  //Aquí irán todos los nexos del nodo
    Boolean revisado;                   //Útil para el ArbolBA
    Boolean ocupado;                    //Útil para el ArbolBA
    Nodo papa;                          //Útil para el ArbolBA


    public Nodo (String a, Object o){           //CONSTRUCTOR
        this.nombre=a;
        this.contenido=o;
        this.ocupado=false;
        this.revisado=false;
    }
    
    public Nodo (){                             //CONSTRUCTOR vacío
        this.revisado=false;
        this.ocupado=false;
    }
    
    public Nodo (String a){                     //CONSTRUCTOR
        this.nombre=a;
        this.revisado=false;
        this.ocupado=false;
    }
    
    
    public void ligar(Nodo c){                  //Ligar a un Nodo
        this.ligas.add(c);
    }

    public void ligar(Nodo c, int index){       //Ligar a un Nodo en específico
        this.ligas.add(index, c);
    }      
      
    public boolean IsRevisado(){                //Trivial
        return this.revisado;
    }

    public String getNombre() {                 //A partir de aquí son Geters y Seters
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    public ArrayList<Nodo> getLigas() {             //Por si se necesita copiar los nexos a otra ubicación
        return ligas;
    }

    public void setRevisado(Boolean r) {
        revisado = r;
    }

    public void setPapa(Nodo papa) {
        this.papa = papa;
    }

    public Nodo getPapa() {
        return this.papa;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Boolean isOcupado() {
        return ocupado;
    } 
}
