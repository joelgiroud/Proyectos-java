package Busqueda_Basica;

public class Main {
     //<>    
    public static void main(String[] args) {
        NodoCosto q0  = new NodoCosto("q0");
        NodoCosto q1  = new NodoCosto("q1");
        NodoCosto q2  = new NodoCosto("q2");
        NodoCosto q3  = new NodoCosto("q3");
        NodoCosto q4  = new NodoCosto("q4");
        //Estamos creando instancias de Nodo, el parámetro es el nombre (etiqueta)

        //Procedemos a hacer las ligaduras, parámetro (objeto nodo)
        q0.AgregaDest(q1, 10);
        q0.AgregaDest(q2, 60);
        q0.AgregaDest(q4, 80);
        q1.AgregaDest(q2, 40);
        q1.AgregaDest(q3, 20);
        q2.AgregaDest(q4, 30);
        q3.AgregaDest(q2, 10);
        q4.AgregaDest(q3, 30);
            
        BVoraz Busqueda_Voraz = new BVoraz(q0); //Creamos la instancia de BVoraz
        Busqueda_Voraz.busca(q4);               //Iniciamos el proceso de búsqueda
        Busqueda_Voraz.muestra();               //Mostramos resultados
    }
}  
 
