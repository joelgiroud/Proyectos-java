package SistemaExperto;

import Interfaces.Interface_Calzado;
import Interfaces.Interface_Main;

public class Main {
    public static void main(String[] args) {
        boolean bool=false;
        Interface_Main fondo = new Interface_Main();
        fondo.show();
        Interface_Calzado main = new Interface_Calzado();
        main.show();
        do{
            if(main.isVisible()==false){
                fondo.setVisible(false);
                fondo.dispose(); //Destroy the JFrame object
                bool=true;
            }
        }
        while(bool==false);
    }
}
