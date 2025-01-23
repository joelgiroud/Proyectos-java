package Referencia;


import java.util.ArrayList;



public class Nodo implements Cloneable {
    public String nombre;                      //Sólo para identificarlo
    public Object contenido;                   //Clase objeto, no se implementa ahora mismo
    public ArrayList<Nodo> ligas = new ArrayList<>();  //Aquí irán todos los nexos del nodo
    public Boolean revisado;                   //Útil para el ArbolBA
    public Boolean ocupado;                    //Útil para el ArbolBA
    public Nodo papa;                          //Útil para el ArbolBA

    /**
     *
     * @param a 
     * @param o
     */
    public Nodo (String a, Object o){           //CONSTRUCTOR
        this.nombre=a;
        this.contenido=o;
        this.ocupado=false;
        this.revisado=false;
    }
    
    public Nodo (){                             //CONSTRUCTOR
        this.revisado=false;
        this.ocupado=false;
    }
    
    /**
     *
     * @param a
     */         
    public Nodo (String a){                     //CONSTRUCTOR
        this.nombre=a;
        this.revisado=false;
        this.ocupado=false;
    }
    
    
    public void ligar(Nodo c){                  //Ligar a un Nodo
        this.ligas.add(c);
        //c.setPapa(this.ligas.get(this.ligas.size()-1));
    }
      
    /**
     *
     * @param c
     * @param index
     */
    public void ligar(Nodo c, int index){       //Ligar a un Nodo en específico
        this.ligas.add(index, c);
    }      
      
    public boolean IsRevisado(){                //Trivial
        return this.revisado;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {                 //A partir de aquí son Geters y Seters
        return this.nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the contenido
     */
    public Object getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    /**
     * @return the ligas
     */
    public ArrayList<Nodo> getLigas() {             //Por si se necesita copiar los nexos a otra ubicación
        return ligas;
    }

    /**
     * @param r
     */
    public void setRevisado(Boolean r) {
        revisado = r;
    }

    /**
     * @param papa the papa to set
     */
    public void setPapa(Nodo papa) {
        this.papa = papa;
    }

    /**
     * @return the papa
     */
    public Nodo getPapa() {
        return this.papa;
    }

    /**
     * @param ocupado the ocupado to set
     */
    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    /**
     * @return the ocupado
     */
    public Boolean isOcupado() {
        return ocupado;
    }
    
    
}
