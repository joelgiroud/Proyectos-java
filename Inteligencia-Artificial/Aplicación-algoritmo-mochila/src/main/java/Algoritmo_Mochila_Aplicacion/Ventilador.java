package Algoritmo_Mochila_Aplicacion;

public class Ventilador {
    private int ventilacion;
    private int efic;
 
    public Ventilador(int ventilacion, int efic) {
        this.ventilacion = ventilacion;
        this.efic = efic;
    }
 
    public int getVentilacion() {
        return ventilacion;
    }
 
    public void setVentilacion(int ventilacion) {
        this.ventilacion = ventilacion;
    }
 
    public int getEfic() {
        return efic;
    }
 
    public void setEfic(int efic) {
        this.efic = efic;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Ventilador other = (Ventilador) obj;
        if (this.ventilacion != other.ventilacion) {
            return false;
        }
        if (this.efic != other.efic) {
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return "Ventilación: "+ventilacion+" m2,"+" Eficiencia Eléctrica: "+efic+"\n";
    }
        
}
