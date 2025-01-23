package Paquete_main;

public class Main {
    
    public static void main(String[] args) {
     //<>
    Nodo A  = new Nodo("A");
    Nodo B  = new Nodo("B");
    Nodo C  = new Nodo("C");
    Nodo D  = new Nodo("D");
    Nodo E  = new Nodo("E");
    Nodo F  = new Nodo("F");
    Nodo G  = new Nodo("G");
    Nodo H  = new Nodo("H");//Estamos creando instancias de Nodo, el parámetro es el nombre (etiqueta)
    
    A.ligar(B);//Procedemos a hacer las ligaduras, parámetro (objeto nodo)
    A.ligar(E);
    A.ligar(F);
    B.ligar(A);
    B.ligar(G);
    C.ligar(D);
    C.ligar(E);
    C.ligar(H);
    D.ligar(C);
    E.ligar(A);
    E.ligar(C);
    F.ligar(A);
    F.ligar(G);
    G.ligar(B);
    G.ligar(F);
    H.ligar(C);

    ArbolBP Arb = new ArbolBP(); //Creamos instancia de Arbol de Búsqueda Ancho
    Arb.recorrer(A);//Aquí sucede la magia
    Arb.mostrar();//Método para Strings

    }
}  
