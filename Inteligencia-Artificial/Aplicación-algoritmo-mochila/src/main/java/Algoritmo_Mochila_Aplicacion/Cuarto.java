package Algoritmo_Mochila_Aplicacion;

public class Cuarto {
    private int ventilacionMaxima;
    private Ventilador[] Ventiladores;
    private int ventilacion_actual;
    private int efic;
 
    public Cuarto(int ventilacionMaxima, int numElementos) {
        this.ventilacionMaxima = ventilacionMaxima;
        this.Ventiladores = new Ventilador[numElementos];
        this.efic = 0;
        this.ventilacion_actual = 0;
    }
 
    public Ventilador[] getElementos() {
        return Ventiladores;
    }
    
    public int getVentilacion() {
       return ventilacion_actual;
    }
    
    public void setVentilacion(int ventilacion) {
        this.ventilacion_actual = ventilacion;
    }
    
    public int getEfic() {
        return efic;
    }
    
    public void setEfic(int efic) {
        this.efic = efic;
    }
    
    public int getVentilacionMaxima() {
        return ventilacionMaxima;
    }
    
    public void setVentilacionMaxima(int ventilacionMaxima) {
        this.ventilacionMaxima = ventilacionMaxima;
    }
    
    /**
     * Añade un elemento (ventilador) al cuarto
     * @param e 
     */
    public void añadirElemento(Ventilador e) {
 
        for (int i = 0; i<this.Ventiladores.length; i++) { //Recorremos Ventiladores
            if (this.Ventiladores[i] == null) {    //Hasta encontrar un elemento vacío
                this.Ventiladores[i] = e; //lo añade
                this.efic=this.efic + e.getEfic(); // aumenta el gasto eléctrico
                this.ventilacion_actual=this.ventilacion_actual + e.getVentilacion(); // Aumenta la ventilacion_actual
                break;
            }
        }
    }
    /**
     * Vaciamos el cuarto
     */
    public void clear() {
        this.ventilacion_actual=0;
        this.efic=0;
        for (int i = 0; i<this.Ventiladores.length; i++) {
            this.Ventiladores[i] = null;
        }
    }
    /**
     * Elimina elemento dado
     * @param e 
     */
    public void eliminarElemento(Ventilador e) {
        for (int i = 0; i<this.Ventiladores.length; i++) {
            if (this.Ventiladores[i].equals(e)) {
                this.Ventiladores[i] = null; //el elemento fuera
                this.ventilacion_actual=this.ventilacion_actual - e.getVentilacion(); //Reduce la ventilacion_actual
                this.efic = this.efic - e.getEfic(); // reduce el gasto eléctrico
                break;
            }
        }
    }
    /**
     * Indica si existe un elemento
     * @param e
     * @return 
     */
    public boolean existeElemento(Ventilador e) {
        for (Ventilador elemento : this.Ventiladores) {
            if (elemento != null && elemento.equals(e)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Muestra el cuarto
     * @return 
     */
    @Override
    public String toString() {
        int a=0;
        int vent=0;
        int efi=0;
        String cadena="";
        for (Ventilador elemento : this.Ventiladores) {
            if (elemento != null) {
                a++;
                cadena +="Elemento #"+a+".\t"+ elemento;
                vent+=elemento.getVentilacion();
                efi+=elemento.getEfic();
                }
        }
        return "El algoritmo ha determinado que la mejor combinación de ventiladores"
                + " elegible es la siguiente...\n"+cadena+"\nTotal (Vent, Efic):\t"+
                "("+Integer.toString(vent)+", "+Integer.toString(efi)+")";
    }
}
