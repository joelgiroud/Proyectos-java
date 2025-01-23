package SistemaExperto;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Contador {
    private LinkedList<Pregunta> aprobadas;
    private LinkedList<Pregunta> respondidas;
    private LinkedList<Pregunta> reprobadas;
    private LinkedList<Pregunta> recuperadas;
    private LinkedList<Pregunta> Noeval;
    private LinkedList<String> rubros_explorados;
    private LinkedList<String> hab_exploradas;
    
    public Contador(){
        aprobadas = new LinkedList();           //Lista de preguntas aprobadas
        respondidas = new LinkedList();         //Lista de preguntas respondidas
        reprobadas = new LinkedList();          //Lista de preguntas reprobadas
        recuperadas = new LinkedList();         //Lista de preguntas reprobadas que se aprobaron
        Noeval = new LinkedList();              //Lista de preguntas no evaluadas
        rubros_explorados = new LinkedList();   //Lista de rubros ya explorados
        hab_exploradas = new LinkedList();      //Lista de habilidades ya exploradas
    }

    public void agregaaAprobadas(Pregunta a){
        if(!this.aprobadas.contains(a)){
            this.aprobadas.add(a);
        }
        System.out.println("Pregunta aprobada"); 
        for(int r=0; r<reprobadas.size(); r++){                             //Recorremos las reprobadas   
            if(a.getHabilidad().equals(reprobadas.get(r).getHabilidad())){  //Si algún elemento tiene la habilidad de la aprobada
                recuperadas.add(reprobadas.get(r));                         //Añadimos a la lista de recuperadas
                reprobadas.remove(reprobadas.get(r));                       //La quitamos de las reprobadas
            }
        }
        this.agregaaRespondidas(a);
    }
    
    public void agregaaReprobadas(Pregunta a){
        this.reprobadas.add(a);
        System.out.println("Pregunta reprobada"); //Temporal
        this.agregaaRespondidas(a);
    }   
    
    public void agregaaNoeval(Pregunta a){
        if(!Noeval.contains(a)){
            this.Noeval.add(a);
        }
        if(!hab_exploradas.contains(a.getHabilidad())){
            hab_exploradas.add(a.getHabilidad());
        }
        System.out.println("Pregunta no evaluada\n"); //Temporal
    } 
    
    public void agregaaRespondidas(Pregunta a){
        if(!this.respondidas.contains(a)){
            this.respondidas.add(a);
        }
        if(!this.rubros_explorados.contains(a.getAtributo())){
            this.rubros_explorados.add(a.getAtributo());
        }
        if(!hab_exploradas.contains(a.getHabilidad())){
            this.hab_exploradas.add(a.getHabilidad());
        }
        
        System.out.println("Pregunta añadida a la lista de respondidas\n");
    }  
    
    public Pregunta getReprobadaAleat(){    //Reprobada aleatoria de rubro aleatorio
        int s = reprobadas.size();
        Cuestionario lista_preg = new Cuestionario(true);
        String habilidad_elegida;
        Pregunta aux;
        
        if(s>1){
            habilidad_elegida = reprobadas.get(ThreadLocalRandom.current().nextInt
                (0, s)).getHabilidad();  // Habilidad aleatoria entre 0 y (s-1), ambos incluidos.
            do{
                aux = lista_preg.getPregunta();    //Aquí la variable rubro empieza desde 1
            }                       //Esto se repetirá hasta que el rubro de la pregunta obtenida sea el deseado
            while(!aux.getHabilidad().equals(habilidad_elegida));
            return aux;
        }
        else{
            if(s==1){
                habilidad_elegida = reprobadas.get(s-1).getHabilidad();
                do{
                aux = lista_preg.getPregunta();    //Aquí la variable rubro empieza desde 1, y eso está bien
                }                   //Esto se repetirá hasta que el rubro de la pregunta obtenida sea el deseado
                while(!aux.getHabilidad().equals(habilidad_elegida));
                return aux;
            }
            else{
            System.out.println("No hay ninguna pregunta reprobada.\nRevisar getReprobadaAleat(int)");
            return null;
            }
        }
    }
    
    public Pregunta getReprobada(int rubro){   //Pregunta de habilidad reprobada de rubro ordenada
        String srubro = convierteRubro(rubro);      //Convertimos int a String
        String habilidad_elegida;
        Pregunta aux;
        Cuestionario lista_preg = new Cuestionario(true);
        int s = reprobadas.size();              //Creamos un parámetro
        if(rubro==6){   //Nos aseguramos de evitar crasheos
            rubro=5;
        }
        if(s>1){
            habilidad_elegida = reprobadas.get(ThreadLocalRandom.current().nextInt//Hab aleatoria elegida de las reprobadas
                (0, s)).getHabilidad();  // Habilidad aleatoria entre 0 y (s-1), ambos incluidos.
            do{
                aux = lista_preg.getPregunta(rubro);    //Aquí la variable rubro empieza desde 1
            }       //Esto se repetirá hasta que el rubro de la pregunta obtenida sea el deseado
            while(!aux.getHabilidad().equals(habilidad_elegida));
            return aux;
        }   
        else{
            if(s==1){
                habilidad_elegida = reprobadas.get(s-1).getHabilidad();
                do{
                aux = lista_preg.getPregunta(rubro);    //Aquí la variable rubro empieza desde 1, y eso está bien
                }       //Esto se repetirá hasta que el rubro de la pregunta obtenida sea el deseado
                while(!aux.getHabilidad().equals(habilidad_elegida));
                return aux;
            }
            else{
            System.out.println("No hay ninguna pregunta reprobada.\nRevisar getReprobadaAleat(int)");
            return null;
            }
        }
    }    

    public LinkedList<Pregunta> getRecuperadas(){
        return this.recuperadas;
    }
    
    public LinkedList<Pregunta> getRespondidas(){
        return this.respondidas;
    }
    
    public LinkedList<Pregunta> getReprobadas(){
        return this.reprobadas;
    }
    
    public LinkedList<Pregunta> getAprobadas(){
        return this.aprobadas;
    }
    
    public LinkedList<Pregunta> getNoeval(){
        return this.Noeval;
    }
    
    public LinkedList<String> getRubrosExplorados(){
        return rubros_explorados;
    }
            
    public int getnumAprobadas() {
        return this.aprobadas.size();
    }

    public int getnumNoEval() {
        return this.Noeval.size();
    }

    public int getnumRespondidas() {
        return respondidas.size();
    }

    public int getnumReprobadas() {
        return reprobadas.size();
    }
        
    public int getnumHab(){
        return this.hab_exploradas.size();
    }
    
    public int getNumRubrosExplorados() {
        //this.Depura();
        return rubros_explorados.size();
    }

    public boolean preguntaRespondida (Pregunta a){
        return respondidas.contains(a);
    }
    
    private String convierteRubro (int num){
        String Rubro="";
        switch(num){
            case 1:
                Rubro="NP";
            break;
            
            case 2:
                Rubro="CT";
            break;

            case 3:
                Rubro="EI";
            break;
        
            case 4:
                Rubro="TAT";
            break;

            case 5:
                Rubro="AS";
            break;
        
            case 6:
                Rubro="ADO";
            break;
        
            default:
            System.out.println("Error en función convierteRubro; verifica que no sea cero"
                + "Esto no debería suceder");
            break;
        }
        return Rubro;
    }
}
