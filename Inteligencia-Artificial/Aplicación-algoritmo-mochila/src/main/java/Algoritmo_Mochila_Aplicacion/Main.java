package Algoritmo_Mochila_Aplicacion;

public class Main{
    private static boolean mensaje=false;
public static void main(String[] args) {
        
        Ventilador[] Ventiladores = {
            new Ventilador(18, 3),
            new Ventilador(20, 1),
            new Ventilador(8, 8),
            new Ventilador(1, 7),
            new Ventilador(3, 7),
            new Ventilador(2, 8),
            new Ventilador(16, 6),
            new Ventilador(7,9),
            new Ventilador(11, 7)
        };
 
        Cuarto m_base = new Cuarto(21, Ventiladores.length);
        Cuarto m_opt = new Cuarto(21, Ventiladores.length);
        
        int a=0;
        String cadena="";
        for (Ventilador elemento : Ventiladores) {
            if (elemento != null) {
                a++;
                cadena +="Ventilador #"+a+".\t"+ elemento;
                }
        }
        System.out.println("Se intentará ventilar un cuarto de la mejor manera usando la siguiente lista de ventiladores..."
        + "\n"+cadena+"\n");
        
        System.out.println("Este cuarto tiene como área "+m_opt.getVentilacionMaxima()+" m2.\n");
        llenarCuarto(m_base, Ventiladores, false, m_opt);
        System.out.println(m_opt);
    }
     /**
     * Aquí sucede la magia
     * @param cuarto_main
     * @param Ventiladores
     * @param esta_lleno
     * @param cuarto_complemento
     */
    public static void llenarCuarto(Cuarto cuarto_main, Ventilador[] Ventiladores, boolean esta_lleno, Cuarto cuarto_complemento) {
      if (esta_lleno) {  //fin=cierto    
          if(cuarto_main.getEfic() == cuarto_complemento.getEfic()){ //Si main y com tienen la misma EFICIENCIA
                if(!mensaje){
                    System.out.println("Existen combinaciones equivalentes en eficiencia.");
                    mensaje=true;
                }
                if ((cuarto_main.getVentilacion() > cuarto_complemento.getVentilacion())) {//si main tiene mas VENTILACIÓN que comp
                    Ventilador[] VentiladoresACopiar = cuarto_main.getElementos(); //instanciamos Ventiladores de cuarto base
                    cuarto_complemento.clear();      //vaciamos el cuarto complemento (quitamos elementos)
                    for (Ventilador e : VentiladoresACopiar) {//metemos en el los Ventiladores instanciados
                        if (e != null) {
                            cuarto_complemento.añadirElemento(e);
                        }
                    }
                }
            }
            if ((cuarto_main.getEfic() > cuarto_complemento.getEfic())) {//si main tiene mas EFICIENCIA eléctrica que comp
                Ventilador[] VentiladoresACopiar = cuarto_main.getElementos(); //instanciamos Ventiladores de cuarto base
                cuarto_complemento.clear();      //vaciamos el cuarto complemento (quitamos elementos)
                for (Ventilador e : VentiladoresACopiar) {//metemos en el los Ventiladores instanciados
                    if (e != null) {
                        cuarto_complemento.añadirElemento(e);
                    }
                }
            }
        }
        else {    //Iniciamos proceso de llenado en Cuarto Main
            for (Ventilador elemento : Ventiladores) {         //Recorre los Ventiladores propios del método
                if (!cuarto_main.existeElemento(elemento)) {//si dicho ventilador NO está instalado ya en el cuarto
                    if (cuarto_main.getVentilacionMaxima() >= cuarto_main.getVentilacion() + elemento.getVentilacion()) {
                    //Si el cuarto aguanta la ventilación del objeto...
                        cuarto_main.añadirElemento(elemento); //añadimos el elemento presente
                        llenarCuarto(cuarto_main, Ventiladores, false, cuarto_complemento);   
                        //reinvocamos el algoritmo con los cuartos main, opt y la lista de Ventiladores
                        cuarto_main.eliminarElemento(elemento); // eliminamos el mismo elemento
                    } 
                    else {//Si NO la aguanta
                        llenarCuarto(cuarto_main, Ventiladores, true, cuarto_complemento);    
                        //reinvocamos el algortmo diciendo que está llena
                    }
                }
            }
        }
    }
}
