package SistemaExperto;
import Rule.*;
import java.util.LinkedList;

public class SistemaExperto {
    Cuestionario lista = new Cuestionario(true);
    private Contador cont = new Contador();
    public LinkedList<Respuesta> respuestas_noev = new LinkedList();
    
    BooleanRuleBase base_reglas = new BooleanRuleBase("Base de Reglas");
    
    //Eventos lógicos
    RuleVariable SE;                //Es la abstracción del SE evaluador
    RuleVariable RubCompleto;       //Es un indicador de que todos los rubros fueron evaluados
    RuleVariable HabCompleto;       //Es una indicador de que todas las habilidades fueron evaluadas
    RuleVariable Escoge;            //Es un indicador del método de selección
    RuleVariable Pregunta_Acierta;  //Indicador booleano
    RuleVariable Pregunta_Falla;    //Indicador booleano
    RuleVariable ForzaEval;         //Indicador booleano
    RuleVariable Contador;          //El contador de TODAS las preguntas que se presentan
    RuleVariable Contador_Noeval;   //El contador de las preguntas que no se evaluan
    RuleVariable Contador_Fallas;   //El contador de preguntas respondidas erróneamente
    RuleVariable Contador_Aciertos; //El contador de preguntas respondidas asertivamente
    RuleVariable Contador_Hab;      //El contador de las habilidades evaluadas actualmente
    RuleVariable Contador_Rub;

    public Pregunta[] obtenPar(){                   //Devuelve el próximo par de preguntas al SE
        int rubro=cont.getNumRubrosExplorados();    //Obtenemos rubros explorados hasta el momento
        if(rubro>5){
            rubro=5;                                //Medida de seguridad
        }
        boolean forzar_evaluacion = false;          //creamos indicador booleano, falso por default
        Pregunta[] par = new Pregunta[2];           //Instanciamos las preguntas a devolver
        String[] resultado = new String[2];//String[0] Obtiene resultado de Escoge, String[1] Obtiene resultado de ForzaEval
        Base_de_Conocimiento();
        generaSeleccion(resultado);                 //Aquí hay encadenamiento lógico
        
        if(resultado[1].equals("si")){              //Verificamos si se forza la ev
            forzar_evaluacion=true;
        }
        else{
            forzar_evaluacion=false;
        }

        switch(resultado[0]){                       //Verificamos valores de Escoge
            case "caso_especial_1":
                if(forzar_evaluacion){                     //Si se forza la evaluación...
                    do{
                        par[0]=colecta("normal", rubro);   //Escogerá de manera normal
                    }
                    while((!par[0].isEvaluable())||(cont.getRespondidas().contains(par[0])));   //Mientras no sea elegible
                    
                    do{
                        par[1]=colecta("erro", rubro);     //Escogerá una de la lista de errores
                    }
                    while((!par[1].isEvaluable())||(cont.getRespondidas().contains(par[1])));   //Mientras no sea elegible
                }
                else{                                   
                    do{
                        par[0]=colecta("normal", rubro);
                    }
                    while(cont.getRespondidas().contains(par[0]));
                    par[1]=colecta("erro", rubro);
                }
                
            break;
            
            case "caso_especial_2":
                if(forzar_evaluacion){
                    do{
                        par[0]=colecta("normal", rubro);            //Escogerá una preg normal
                    }
                    while((!par[0].isEvaluable())||(cont.getRespondidas().contains(par[0])));
                    
                    do{
                        par[1]=colecta("normal_rubaleat", rubro);   //Escogerá una normal pero de cualquier rubro
                    }
                    while((!par[1].isEvaluable())||(cont.getRespondidas().contains(par[1])));
                }   
                else{
                    do{
                        par[0]=colecta("normal", rubro); 
                    }
                    while(cont.getRespondidas().contains(par[0]));
                    par[1]=colecta("normal_rubaleat", rubro);
                }
            break;
            
            case "no":  //*********************ESTO NO DEBERÍA OCURRIR
                System.out.println("\nEl sistema terminó de evaluar con error en método obtenPar");
            break;
            
            default:                                    //Implica casos en donde el par se obtiene por el mismo método
                if(forzar_evaluacion){
                    do{
                        par[0]=colecta(resultado[0], rubro);       //Obtenemos la pregunta próxima por función colecta
                    }
                    while((!par[0].isEvaluable())||(cont.getRespondidas().contains(par[0])));
                    do{ 
                        par[1]=colecta(resultado[0], rubro);       //Repetimos en par[1] hasta que sea diferente de par[2]...
                    }                                              //...sea evaluable y no se haya respondido antes
                    while ((par[1]==par[0])||(!par[1].isEvaluable())||(cont.getRespondidas().contains(par[1])));
                }
                else{
                    do{
                        par[0]=colecta(resultado[0], rubro);      //Obtenemos la pregunta próxima por función colecta
                    }
                    while(cont.getRespondidas().contains(par[0]));
                    do{ 
                        par[1]=colecta(resultado[0], rubro);  //Repetimos en par[1] hasta que sea diferente de par[2]
                    }
                    while ((par[1]==par[0])||(cont.getRespondidas().contains(par[1])));
                }
            break;
        }
        if((par[0]!=null)){                                  //Generalmente esto siempre debería ocurrir
            if(par[1]!=null){
                return par;
            }
            else{                                            //Váse como una medida de seguridad
                System.out.println("Segunda pregunta nula, reevisar método obtenPar");
                return par;
            }
        }
                                                             //Si llegamos a este paso hay problemas
        System.out.println("Par es nulo, revisar obtenPar"); //Esto es casi imposible que suceda
        return null;
    }
    
    private Pregunta colecta (String res, int rub){
        switch (res){
            case "normal":        //si se solicita pedir normalmente las preguntas                  OK
                return lista.getPregunta(rub);
        
            case "normal_rubaleat": //si se solicita pedir normalmente pero con rubro aleatorio     OK
                return lista.getPregunta();
            
            case "erro_rubaleat": //si se solicita pedir de las erróneas y de rubro aleatorio
                return cont.getReprobadaAleat();
                
            case "erro":          //si se solicita pedir de las erróneas y de rubro NO aleatorio
                return cont.getReprobada(rub);
            
            default:
                System.out.println("\nEsto no debería ocurrir. Revisa método llamaPregunta.");
            break;
        }
        return null;
    }

    //termina() y generaSeleccion() son métodos que comunican a la Base de conocimientos con el SE
    public boolean termina(){     //true = SE termina
        Contador.setValue(Integer.toString(cont.getnumRespondidas()+cont.getnumNoEval()));//Actualizamos contadores...
        Contador_Aciertos.setValue(Integer.toString(cont.getnumAprobadas()));             //...con datos reales
        base_reglas.forwardChain();
        return SE.getValue().equals("termina");                              //Si SE="Termina", entonces termina=true
    }
    
    private String[] generaSeleccion(String[] devuelve){  
        Base_de_Conocimiento();
        Contador.setValue(Integer.toString(cont.getnumRespondidas()+cont.getnumNoEval()));//Actualizamos contadores
        Contador_Fallas.setValue(Integer.toString(cont.getnumReprobadas()));    
        Contador_Aciertos.setValue(Integer.toString(cont.getnumAprobadas()));   
        Contador_Noeval.setValue(Integer.toString(cont.getnumNoEval()));
        Contador_Hab.setValue(Integer.toString(cont.getnumHab()));
        Contador_Rub.setValue(Integer.toString(cont.getNumRubrosExplorados()));
        base_reglas.forwardChain();                                             //Encadenamiento hacia adelante
        devuelve[0]=Escoge.getValue();                                          //Nos dice el método de selección
        if(devuelve[0]==null){
            devuelve[0]="normal";
        }
        devuelve[1]=ForzaEval.getValue();                  //Nos dice si se forza la selección de preguntas evaluables
        return devuelve;  
    }

    public void Base_de_Conocimiento(){
        SE = new RuleVariable(base_reglas,"Sistema Experto");    //Constructor:(BooleanRuleBase, Etiqueta)
        Contador = new RuleVariable(base_reglas,"Contador de Preguntas");
        RubCompleto = new RuleVariable(base_reglas,"Rúbricas Completas");
        HabCompleto = new RuleVariable(base_reglas,"Habilidades Completas");
        Contador_Noeval = new RuleVariable(base_reglas,"Contador de preguntas no evaluadas");
        Escoge = new RuleVariable(base_reglas,"Método de selección");
        Contador_Fallas = new RuleVariable(base_reglas,"Contador de Fallas");   
        Contador_Aciertos = new RuleVariable(base_reglas,"Contador de Aciertos");
        ForzaEval = new RuleVariable(base_reglas,"Forzar preguntas evaluables");
        Contador_Hab = new RuleVariable(base_reglas,"Contador de habilidades respondidas");
        Contador_Rub = new RuleVariable(base_reglas,"Contador de rubricas visitadas");
        
        //Las condiciones solamente pueden ser "=", ">", "<", "!=", así los trabaja rule.jar
        Condition igual = new Condition("=");     //Creamos condiciones lógicas y las bautisamos
        Condition menor = new Condition("<");
        Condition mayor = new Condition(">");
        Condition diferente= new Condition("!=");
        
        //Ahora definiremos las reglas lógicas
        Rule regla1 = new Rule(base_reglas, "regla 1",
        new Clause[]{
            new Clause(HabCompleto,igual,"si"),     //Si ya se contestó una pregunta de cada habilidad...
            new Clause(Contador_Fallas,menor,"2"),  //... y se tienen UNA o CERO fallas...
            new Clause(RubCompleto,igual,"no")},    //...y NO se ha contestado una pregunta de cada rubro...
        new Clause(Escoge,igual,"normal")           //Entonces escogerá preguntas de hab aleatorias, pero de rubro ordenado
        );   
        
        Rule regla2 = new Rule(base_reglas, "regla 2", 
        new Clause[]{
            new Clause(HabCompleto,igual,"no"),     //Si NO se ha contestado una pregunta de cada habilidad...
            new Clause(RubCompleto,igual,"no")},    //...y NO se ha contestado una pregunta de cada rubro... 
        new Clause(Escoge,igual,"normal")           //Entonces escogerá de manera normal
        );    //SEij

        Rule regla3 = new Rule(base_reglas, "regla 3", 
        new Clause[]{
            new Clause(HabCompleto,igual,"si"),     //Si ya se ha contestado una pregunta de cada habilidad...
            new Clause(RubCompleto,igual,"si"),     //...y ya se ha contestado una pregunta de cada rubro... 
            new Clause(Contador_Fallas,igual,"0")}, //...y NO se tiene ninguna falla...
        new Clause(Escoge,igual,"normal_rubaleat")  //Entonces escogerá preguntas aleatorios de hab. no evaluadas de rubros ordenados
        );    //SEiJ        
      
        Rule regla4 = new Rule(base_reglas, "regla 4", 
        new Clause[]{
            new Clause(Contador_Aciertos,igual,"6"), //Si se tienen SEIS aciertos...
            new Clause(HabCompleto,igual,"no"),      //... y NO se ha completado una pregunta de cada habilidad...
            new Clause(Contador_Fallas,igual,"1")},  //... y se tiene UNA falla...
        new Clause(Escoge,igual,"caso_especial_1")   //Entonces escogerá 1 preg normal y 1 preg de los errores //Implementado
        );

        Rule regla5 = new Rule(base_reglas, "regla 5", 
        new Clause[]{
            new Clause(Contador_Aciertos,igual,"6"), //Si se tienen SEIS aciertos...
            new Clause(HabCompleto,igual,"no"),      //... y no se ha contestado una pregunta de cada habilidad...
            new Clause(Contador_Fallas,igual,"0")},  //... y no se tiene ninguna falla...
        new Clause(Escoge,igual,"caso_especial_2")   //Entonces escogerá 1 preg normal y 1 preg normal_rubaleat
        );        
        
        /*Rule regla6 = new Rule(base_reglas, "regla 6",        //Esto genera repeticiones
        new Clause[]{
            new Clause(HabCompleto,igual,"si"),      //Si ya se contestó una pregunta de cada habilidad...
            new Clause(RubCompleto,igual,"no"),      //... y NO se ha contestado una pregunta de cada rubro...
            new Clause(Contador_Fallas,mayor,"1")},  //...y se tienen AL MENOS DOS fallas...
        new Clause(Escoge,igual,"erro")              //Entonces ecogerá preguntas erróneas de rubros ordenados
        );    //SEIj    */
        
        
        Rule regla7 = new Rule(base_reglas, "regla 7",
        new Clause[]{
            new Clause(HabCompleto,igual,"si"),      //Si ya se contestó una pregunta de cada habilidad...
            new Clause(RubCompleto,igual,"si"),      //... y ya se contestó una pregunta de cada rubro... 
            new Clause(Contador_Fallas,mayor,"1")},  //... y si te tienen AL MENOS DOS fallas...
        new Clause(Escoge,igual,"erro_rubaleat")     //Entonces escogerá preguntas erróneas de rubros aleatorios
        );    //SEIJ
        
        Rule regla8 = new Rule(base_reglas, "regla 8",
        new Clause[]{
            new Clause(Contador_Noeval,mayor,"4")},   //Si se tiene AL MENOS CINCO preguntas no evaluadas...
        new Clause(ForzaEval,igual,"si")              //Entonces evitará preguntas no evaluables
        );
        
        Rule regla9 = new Rule(base_reglas, "regla 9", 
        new Clause[]{
            new Clause(Contador_Hab,igual,"7")},      //Si el contador de habilidades respondidas llega a 7...
        new Clause(HabCompleto,igual,"si")            //Entonces ya se contestó una pregunta de cada habilidad
        );       
        
        Rule regla10 = new Rule(base_reglas, "regla 10",
        new Clause[]{
            new Clause(Contador_Hab,menor,"7")},      //Si el contador de habilidades es menor a 7...
        new Clause(HabCompleto,igual,"no")            //Entonces NO se ha contestado una pregunta de cada habilidad
        );
        
        Rule regla11 = new Rule(base_reglas, "regla 11", 
        new Clause[]{
                new Clause(Contador_Rub,igual,"6")},     //Si el contador de preguntas llega a 12 o más (CONTANDO NO EVAL)...
        new Clause(RubCompleto,igual,"si")            //Entonces ya se contestó una pregunta de cada rubro
        );         
        
        Rule regla12 = new Rule(base_reglas, "regla 12", 
        new Clause[]{
            new Clause(Contador_Rub,menor,"6")},         //Si el contador de preguntas es menor que 12...
        new Clause(RubCompleto,igual,"no")            //Entonces NO se ha contestado una pregunta de cada rubro
        );
        
        Rule regla13 = new Rule(base_reglas, "regla 13",
        new Clause[]{
            new Clause(Contador_Aciertos,menor,"12"), //Si el contador de preguntas acertadas es menor a 12
            new Clause(Contador,menor,"16")},         //...y el contador de preguntas es menor a 16
        new Clause(SE,igual,"continua")               //Entonces el SE continúa evaluando
        );
        
        Rule regla14 = new Rule(base_reglas, "regla 14",
        new Clause[]{               
            new Clause(Contador_Aciertos,mayor,"11")}, //Si el alumno ya supera los doce aciertos...
        new Clause(SE,igual,"termina")                 //Entonces el SE termina de evaluar
        );
        
        Rule regla15 = new Rule(base_reglas, "regla 15",
        new Clause[]{
            new Clause(Contador,mayor,"15")},          //Si el contador de preguntas llega a 16...
        new Clause(SE,igual,"termina")                 //Entonces el SE termina de evaluar
        );

  
        Rule regla16 = new Rule(base_reglas, "regla 16",
        new Clause[]{
            new Clause(SE,igual,"termina")},           //Si el SE termina de evaluar...
        new Clause(Escoge,igual,"no")                  //Entonces se anula todo método de selección (Puede causar crasheos)
        );
        
        Rule regla17 = new Rule(base_reglas, "regla 17",// Esta regla parcha los errores posibles entre @regla7 y @regla3
        new Clause[]{
            new Clause(HabCompleto,igual,"si"),        //Si ya se contestó una pregunta de cada habilidad...
            new Clause(RubCompleto,igual,"si"),        //... y ya se contestó una pregunta de cada rubro...
            new Clause(Contador_Fallas,igual,"1")},    //... y se tiene únicamente una falla...
        new Clause(Escoge,igual,"caso_especial_2")     //Entonces escogerá 1 preg normal y 1 preg normal_rubaleat
        );    
        
        /*Rule Default = new Rule(base_reglas, "regla deefault", 
        new Clause[]{
            new Clause(Escoge,igual,null)},           //Si, por algún error no visto, ecoge fuera 'null'...
        new Clause(Escoge,igual,"normal")             //Entonces escogerá preguntas de manera normal
        );*/
        
        Rule regla18 = new Rule(base_reglas, "regla 18",
        new Clause[]{
            new Clause(Contador_Noeval,menor,"5")},   //Si se tiene MENOS de CINCO preguntas no evaluadas...
        new Clause(ForzaEval,igual,"no")              //Entonces no se activa ForzaEval
        );
        
        /*Rule regla19 = new Rule(base_reglas, "regla 19", 
        new Clause[]{
            new Clause(Contador_Fallas,mayor,"1")},  //... y se tienen más de una falla
        new Clause(Escoge,igual,"erro")   //Entonces escogerá 1 preg normal y 1 preg de los errores //Implementado
        );*/
    }
    
    /*ESTO SE PUEDE IMPLEMENTAR CON LóGICA DE 1er ORDEN, POR CUESTIONES
    DE TIEMPO ME FUE IMPOSIBLE IMPLEMENTAR ESA PARTE*/
    //Se considera que las respuestas se escriben con minúsculas
    public void califica(Respuesta res){
        int rubro = convierteRubro(res.getPregunta_asoc().getAtributo());
        int hab = convierteHab(res.getPregunta_asoc().getHabilidad()); //ejercicio

        if(lista.lista_preg[rubro][hab].isEvaluable()){
            if(!cont.preguntaRespondida(lista.lista_preg[rubro][hab])){
                switch(rubro){
                    case 0: //Rubro 1
                        switch(hab){
                            case 0: //Hab 1 no evaluable
                                    
                            break;
                                
                            case 1: //Hab 2 
                                if(((res.getRespuesta().contains("do"))
                                        &&(res.getRespuesta().contains("ut")))
                                    ||((res.getRespuesta().contains("sí"))
                                        &&(res.getRespuesta().contains("se")))
                                    ||((res.getRespuesta().contains("si"))
                                        &&(res.getRespuesta().contains("se")))     
                                    ){ //Inicia proceso
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                    }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 2: //Hab 3
                                int p=0;
                                if((res.getRespuesta().contains("costumbr"))
                                    ||(res.getRespuesta().contains("conven"))
                                    ||(res.getRespuesta().contains("acuerdo"))
                                    ||(res.getRespuesta().contains("establ"))
                                    ||(res.getRespuesta().contains("form"))
                                    ||(res.getRespuesta().contains("manera"))
                                        ){
                                    p++;
                                } 
                                if((res.getRespuesta().contains("adopt"))
                                    ||(res.getRespuesta().contains("escri"))
                                    ||(res.getRespuesta().contains("denota"))
                                    ||(res.getRespuesta().contains("para"))
                                    ||(res.getRespuesta().contains("tiene"))
                                        ){
                                    p++;
                                }                                
                                if((res.getRespuesta().contains("simul"))
                                    ||(res.getRespuesta().contains("espaci"))
                                    ||(res.getRespuesta().contains("línea"))
                                    ||(res.getRespuesta().contains("linea"))
                                        ){
                                    p++;
                                }           
                                if((res.getRespuesta().contains("pentagram"))
                                    ||(res.getRespuesta().contains("extra"))
                                    ||(res.getRespuesta().contains("mas"))
                                    ||(res.getRespuesta().contains("más"))
                                    ||(res.getRespuesta().contains("exten"))
                                    ||(res.getRespuesta().contains("extiende"))
                                        ){
                                    p++;
                                if((res.getRespuesta().contains("nota"))
                                        ){
                                    p++;
                                                                    }
                                }                                
                                if(p>=3){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                
                            case 3: //Hab 4
                                if((res.getRespuesta().contains("sol"))
                                    ||(res.getRespuesta().contains("G"))
                                        ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4: //Hab 5
                                if(((res.getRespuesta().contains("armonia"))
                                        ||(res.getRespuesta().contains("armonía")))
                                    &&((res.getRespuesta().contains("melodía"))
                                        ||(res.getRespuesta().contains("melodia")))
                                    &&((res.getRespuesta().contains("percusion"))
                                        ||(res.getRespuesta().contains("percusión")))
                                    ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 5: //Hab 6 no evaluable
                                
                            break;
                            
                            case 6: //Hab 7
                                if(res.getRespuesta().contains("C, D, E, F, G, A, B")){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;
                
                    case 1://Rubro 2
                        switch(hab){
                            case 0:
                                if(((res.getRespuesta().contains("3/4"))
                                        ||(res.getRespuesta().contains("tres cuartos"))
                                        ||(res.getRespuesta().contains("3 cuartos")))
                                    &&((res.getRespuesta().contains("4/4"))
                                        ||(res.getRespuesta().contains("cuatro cuartos"))
                                        ||(res.getRespuesta().contains("4 cuartos")))
                                    &&((res.getRespuesta().contains("2/4"))
                                        ||(res.getRespuesta().contains("dos cuartos"))
                                        ||(res.getRespuesta().contains("2 cuartos")))
                                    &&((res.getRespuesta().contains("junto"))
                                        ||(res.getRespuesta().contains("pegado"))
                                        ||(res.getRespuesta().contains("con"))
                                        ||(res.getRespuesta().contains("unido"))
                                        ||(res.getRespuesta().contains(","))
                                        ||(res.getRespuesta().contains("y"))
                                        ||(res.getRespuesta().contains("con")))
                                    ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }                                
                            break;
                                
                            case 1:
                                if((((res.getRespuesta().contains("3/4"))
                                            ||(res.getRespuesta().contains("tres cuartos"))
                                            ||(res.getRespuesta().contains("3 cuartos")))
                                        &&(res.getRespuesta().contains("vals")))
                                    ||(((res.getRespuesta().contains("4/4"))
                                            ||(res.getRespuesta().contains("cuatro cuartos"))
                                            ||(res.getRespuesta().contains("4 cuartos")))
                                        &&(res.getRespuesta().contains("compasillo")))
                                    ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }                                  
                            break;
                                
                            case 2:
                                if(res.getRespuesta().contains("no")){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                
                            case 3:
                                if((res.getRespuesta().contains("si")
                                    ||(res.getRespuesta().contains("sí")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4: //No evaluable

                            break;
                                
                            case 5:
                                if((res.getRespuesta().contains("3"))
                                    ||(res.getRespuesta().contains("tres"))
                                    ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            
                            case 6:
                                if((res.getRespuesta().contains("8"))
                                    ||(res.getRespuesta().contains("ocho"))
                                    ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;
                
                    case 2://Rubro 3
                        switch(hab){
                            case 0: // No evaluable
                                
                            break;
                                
                            case 1:
                                if(res.getRespuesta().contains("no")){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 2: //No evaluable
                                
                            break;
                
                            case 3:
                                if((res.getRespuesta().contains("si")
                                    ||(res.getRespuesta().contains("sí")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4:
                                if((res.getRespuesta().contains("tono"))
                                &&(((res.getRespuesta().contains("semitono")))
                                    ||(res.getRespuesta().contains("semi-tono"))
                                    ||(res.getRespuesta().contains("semi tono")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 5:
                                if((res.getRespuesta().contains("sib")
                                    ||(res.getRespuesta().contains("síb"))
                                    ||(res.getRespuesta().contains("si bemol"))
                                    ||(res.getRespuesta().contains("sí bemol"))
                                    ||(res.getRespuesta().contains("Bb"))
                                    ||(res.getRespuesta().contains("B bemol")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            
                            case 6:
                                if((res.getRespuesta().contains("fa#")
                                    ||(res.getRespuesta().contains("fa sostenido"))
                                    ||(res.getRespuesta().contains("F#"))
                                    ||(res.getRespuesta().contains("F sostenido")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;
                
                    case 3://Rubro 4
                        switch(hab){
                            case 0: //No evaluable
                                
                            break;
                                
                            case 1:
                                if((res.getRespuesta().contains("G#m"))
                                    ||(res.getRespuesta().contains("D#m"))
                                    ||(res.getRespuesta().contains("Ebm"))
                                    ||(res.getRespuesta().contains("Bbm"))
                                    ||(res.getRespuesta().contains("Db"))
                                    ||(res.getRespuesta().contains("Gb"))
                                    ||(res.getRespuesta().contains("F#"))
                                    ||(res.getRespuesta().contains("B"))
                                    ||(res.getRespuesta().contains("sol#m"))
                                    ||(res.getRespuesta().contains("re#m"))
                                    ||(res.getRespuesta().contains("mibm"))
                                    ||(res.getRespuesta().contains("sibm"))
                                    ||(res.getRespuesta().contains("síbm"))
                                    ||(res.getRespuesta().contains("reb"))
                                    ||(res.getRespuesta().contains("solb"))
                                    ||(res.getRespuesta().contains("fa#"))
                                    ||(res.getRespuesta().contains("si"))
                                    ||(res.getRespuesta().contains("sí"))
                                    ||(res.getRespuesta().contains("G# menor"))
                                    ||(res.getRespuesta().contains("D# menor"))
                                    ||(res.getRespuesta().contains("Eb menor"))
                                    ||(res.getRespuesta().contains("Bb menor"))
                                    ||(res.getRespuesta().contains("Db mayor"))
                                    ||(res.getRespuesta().contains("Gb mayor"))
                                    ||(res.getRespuesta().contains("F# mayor"))
                                    ||(res.getRespuesta().contains("B mayor"))
                                    ||(res.getRespuesta().contains("sol# menor"))
                                    ||(res.getRespuesta().contains("re# menor"))
                                    ||(res.getRespuesta().contains("mib menor"))
                                    ||(res.getRespuesta().contains("sib menor"))
                                    ||(res.getRespuesta().contains("síb menor"))
                                    ||(res.getRespuesta().contains("reb mayor"))
                                    ||(res.getRespuesta().contains("solb mayor"))
                                    ||(res.getRespuesta().contains("fa# mayor"))
                                    ||(res.getRespuesta().contains("si mayor"))
                                    ||(res.getRespuesta().contains("sí mayor"))
                                    ||(res.getRespuesta().contains("G sostenido menor"))
                                    ||(res.getRespuesta().contains("D sostenido menor"))
                                    ||(res.getRespuesta().contains("E bemol menor"))
                                    ||(res.getRespuesta().contains("B bemol menor"))
                                    ||(res.getRespuesta().contains("D bemol mayor"))
                                    ||(res.getRespuesta().contains("G bemol mayor"))
                                    ||(res.getRespuesta().contains("F sostenido mayor"))
                                    ||(res.getRespuesta().contains("sol sostenido menor"))
                                    ||(res.getRespuesta().contains("re sostenido menor"))
                                    ||(res.getRespuesta().contains("mi bemol menor"))
                                    ||(res.getRespuesta().contains("si bemol menor"))
                                    ||(res.getRespuesta().contains("sí bemol menor"))
                                    ||(res.getRespuesta().contains("re bemol mayor"))
                                    ||(res.getRespuesta().contains("sol bemol mayor"))
                                    ||(res.getRespuesta().contains("fa sostenido mayor"))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 2: //No evaluable
                                
                            break;
                
                            case 3:
                                if((res.getRespuesta().contains("si")
                                    ||(res.getRespuesta().contains("sí")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4: //No evaluable
                                
                            break;
                                
                            case 5: //No evaluable
                                
                            break;
                            
                            case 6:
                                if((res.getRespuesta().contains("la#")
                                    ||(res.getRespuesta().contains("la sostenido"))
                                    ||(res.getRespuesta().contains("A#"))
                                    ||(res.getRespuesta().contains("A sostenido")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;

                    case 4://Rubro 5
                        switch(hab){
                            case 0:
                                if((res.getRespuesta().contains("G7"))
                                    ||(res.getRespuesta().contains("Dm"))
                                    ||(res.getRespuesta().contains("B"))
                                    ||(res.getRespuesta().contains("Bb9"))
                                    ||(res.getRespuesta().contains("F"))
                                    ||(res.getRespuesta().contains("Aaug"))
                                    ||(res.getRespuesta().contains("Dbaug"))
                                ){ //Este ejercicio puede estar incompleto
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 1:
                                if((((res.getRespuesta().contains("segunda"))
                                        ||(res.getRespuesta().contains("2da"))
                                        ||(res.getRespuesta().contains("2a")))
                                        &&(res.getRespuesta().contains("au"))
                                        &&(res.getRespuesta().contains("re#")
                                            ||(res.getRespuesta().contains("D#"))
                                            ||(res.getRespuesta().contains("re sostenido"))
                                            ||(res.getRespuesta().contains("D sostenido"))))
                                    &&(((res.getRespuesta().contains("tercera"))
                                        ||(res.getRespuesta().contains("3ra"))
                                        ||(res.getRespuesta().contains("3a")))
                                        &&((res.getRespuesta().contains("dis"))
                                            ||(res.getRespuesta().contains("dim")))
                                        &&(res.getRespuesta().contains("mib")
                                            ||(res.getRespuesta().contains("Eb"))
                                            ||(res.getRespuesta().contains("mi bemol"))
                                            ||(res.getRespuesta().contains("E bemol"))))
                                ){ //Este ejercicio puede estar incompleto
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 2:
                                if((res.getRespuesta().contains("intervalo")
                                    &&((res.getRespuesta().contains("acorde")))
                                        ||(res.getRespuesta().contains("ton")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                
                            case 3:
                                if((res.getRespuesta().contains("no"))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4:
                                if((res.getRespuesta().contains("cuarto")
                                    ||(res.getRespuesta().contains("4to"))
                                    ||(res.getRespuesta().contains("4"))
                                    ||(res.getRespuesta().contains("segundo"))
                                    ||(res.getRespuesta().contains("2do"))
                                    ||(res.getRespuesta().contains("2")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 5:
                                if((res.getRespuesta().contains("5")
                                    ||(res.getRespuesta().contains("cinco")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            
                            case 6:
                                if((res.getRespuesta().contains("menor"))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;

                    case 5://Rubro 6
                        switch(hab){
                            case 0:
                                if((res.getRespuesta().contains("glissando")
                                    ||(res.getRespuesta().contains("fioritura"))
                                    ||(res.getRespuesta().contains("cadenza"))
                                    ||(res.getRespuesta().contains("calderón"))
                                    ||(res.getRespuesta().contains("calderon"))
                                    ||(res.getRespuesta().contains("fermata")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 1:
                                if((res.getRespuesta().contains("Ital")
                                    ||(res.getRespuesta().contains("ital")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 2: //No evaluable
                                
                            break;
                
                            case 3:
                                if((res.getRespuesta().contains("ornamenta"))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;

                            case 4:
                                if((res.getRespuesta().contains("bajo")
                                    &&((res.getRespuesta().contains("pentagrama"))
                                        ||(res.getRespuesta().contains("notas"))
                                        ||(res.getRespuesta().contains("linea"))
                                        ||(res.getRespuesta().contains("línea"))))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                                
                            case 5: //No evaluable
                                
                            break;
                            
                            case 6:
                                if((res.getRespuesta().contains("percusión")
                                    ||(res.getRespuesta().contains("percusion")))
                                ){
                                    cont.agregaaAprobadas(lista.lista_preg[rubro][hab]);
                                }
                                else{
                                    cont.agregaaReprobadas(lista.lista_preg[rubro][hab]);
                                }
                            break;
                            }
                    break;
                }
            }
            else{
                System.out.println("La pregunta ya ha sido contestada antes\n");
            }
        }
        else{
            int r=rubro+1;
            int h=hab+1;
            System.out.println("La pregunta ("+r+","+h+") no es evaluable");
            cont.agregaaNoeval(lista.lista_preg[rubro][hab]);
            respuestas_noev.add(res);
        }
    }
    
    private int convierteRubro (String rubro){
        int num=-1;
        switch(rubro){
            case "NP":
                num=0;
            break;
            
            case "CT":
                num=1;
            break;

            case "EI":
                num=2;
            break;
        
            case "TAT":
                num=3;
            break;

            case "AS":
                num=4;
            break;
        
            case "ADO":
                num=5;
            break;
        }
        if((num<=-1)||(num>=6)){
            System.out.println("Error en función convierteRubro."
                + "Esto no debería suceder");
            return -1;
        }
        else{
            return num;
        }
    }
    
    private int convierteHab (String habilidad){
        int num=-1;
        switch(habilidad){
            case "creatividad":
                num=0;
            break;
            
            case "investigacion":
                num=1;
            break;

            case "analisis":
                num=2;
            break;
        
            case "comparacion":
                num=3;
            break;

            case "estructuracion":
                num=4;
            break;
        
            case "comprension":
                num=5;
            break;
            
            case "memorizacion":
                num=6;
            break;
            default:
                num=-1;
            break;
        }
        if(num==-1){
            System.out.println("Error en función convierteEjericio.\n"
                + "Esto no debería suceder");
            return -1;
        }
        else{
            return num;
        }
    }    
    
    public Contador getContador(){
        return cont;
    }
}
