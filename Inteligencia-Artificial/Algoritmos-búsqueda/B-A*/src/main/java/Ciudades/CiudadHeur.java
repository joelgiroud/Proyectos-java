package Ciudades;
import java.util.ArrayList;

public class CiudadHeur implements Cloneable, Comparable<CiudadHeur> {
    private String Nombre;
    private int DistanciaADestino;
    private int Costo;
    ArrayList<CiudadHeur>ConectaCon;
    boolean Visitado;
    
    public CiudadHeur(){
        ConectaCon = new ArrayList<>();
        Visitado = false;
    }
    
    public CiudadHeur (String nombre, int DistDest){
        this.Nombre=nombre;
        this.DistanciaADestino=DistDest;
        Visitado = false;
        ConectaCon = new ArrayList<>();
    }
    
    @Override
    public CiudadHeur clone() {
        try {
            return (CiudadHeur) super.clone();
        }
        catch(CloneNotSupportedException e) {
            throw new IllegalStateException("A");
        }
    }
    
    public void AgregarCiudad (CiudadHeur c1, int costo){
        if(!ConectaCon.contains(c1)){
            c1.setCosto(costo);
            CiudadHeur c2 = c1.clone();
            ConectaCon.add(c1);
        }
        else
            System.out.println("La ciudad insertada ya se encuentra en la lista de conexiones");
    }
    
    public boolean isVisitado(){
        return Visitado;
    }

    public int getDistanciaADestino() {
        return DistanciaADestino;
    }

    public int getCosto() {
        return Costo;
    }
    @Override
    public String toString(){
        String ListaCiudades="\t\t";
        for (CiudadHeur c: ConectaCon) {
            if(ConectaCon.get(ConectaCon.size()-1)==c){
                ListaCiudades=ListaCiudades+c.getNombre()+".";
            }
            else{
                ListaCiudades=ListaCiudades+c.getNombre()+",\t";
            }
        }            
        return ("\t\t"+this.getNombre()+"\nDistancia al destino: \t\t"
                +Integer.toString(this.DistanciaADestino)+"\nCosto desde CiudadHeur inicial: \t"
                +Integer.toString(this.Costo)+"\nConecta con...\t"
                +ListaCiudades)+"\n";
    }

    @Override
    public int compareTo(CiudadHeur o) {
        return new Integer(this.getDistanciaADestino()+this.getCosto()).//La clave está aquí
        compareTo(new Integer(o.getDistanciaADestino()+o.getCosto()));  //Aquí entra la heurística
    }
    
    public void setCosto(int Costo) {
        this.Costo = Costo;
    }
    String getNombre() {
        return Nombre;
    }
}
