package SistemaExperto;
import Rule.*;


public class SistemaExperto {
    BooleanRuleBase base_reglas = new BooleanRuleBase("Base de Reglas");
    
    RuleVariable Zapatillas;    //Los creamos de acuerdo a la observación....
    RuleVariable Zapatos;
    RuleVariable Pico;
    RuleVariable Collar;
    RuleVariable OrejasRedondas;
    
    RuleVariable ResultadoCalzado;
    RuleVariable ResultadoOral;
    RuleVariable ResultadoCollar;
    RuleVariable ResultadoOrejasRedondas;
    String resultado="";    //La ocuparemos para...
    
    public String getResCalzado(String bool1, String bool2){
        //Esta función pedirá dos valores ('si', 'no'), los guardamos en dichas variables
        Base_de_Conocimiento();
        Zapatillas.setValue(bool1);    
        Zapatos.setValue(bool2);
        base_reglas.forwardChain(); //Encadenamiento hacia adelante
        resultado=ResultadoCalzado.getValue(); //De las primeras reglas
        if(resultado==null){    //si los valores que pide son inconsistentes
            return "No se puede tener zapatillas y zapatos al mismo tiempo";
        }
        else{
            return resultado;   
        }
        
    }
    
    public String getResOral(String resultadoCalzado, String pico){
        //Esta función pedirá dos valores ('si', 'no'), los guardamos en dichas variables
        Base_de_Conocimiento();
        ResultadoCalzado.setValue(resultadoCalzado);    
        Pico.setValue(pico);
        base_reglas.forwardChain(); //Encadenamiento hacia adelante
        resultado=ResultadoOral.getValue(); 
        return resultado;   //si los valores que pide son inconsistentes devolverá un null
    }
    
    public String getResOrejas(String resultadoCalzado, String orejasredondas){
        //Esta función pedirá dos valores ('si', 'no'), los guardamos en dichas variables
        Base_de_Conocimiento();
        ResultadoCalzado.setValue(resultadoCalzado);    
        OrejasRedondas.setValue(orejasredondas);
        base_reglas.forwardChain(); //Encadenamiento hacia adelante
        resultado=ResultadoOrejasRedondas.getValue(); 
        return resultado;   //si los valores que pide son inconsistentes devolverá un null
    }    
    
    public String getResCollar(String resultadoCalzado, String collar){
        //Esta función pedirá dos valores ('si', 'no'), los guardamos en dichas variables
        Base_de_Conocimiento();
        ResultadoCalzado.setValue(resultadoCalzado);    
        Collar.setValue(collar);
        base_reglas.forwardChain(); //Encadenamiento hacia adelante
        resultado=ResultadoCollar.getValue(); 
        return resultado;   //si los valores que pide son inconsistentes devolverá un null
    }      
    
    
    public void Base_de_Conocimiento(){
        Zapatillas = new RuleVariable(base_reglas,"Zapatillas");    //Constructor:(BooleanRuleBase, Etiqueta)
        Zapatos = new RuleVariable(base_reglas,"Zapatos");
        Pico = new RuleVariable(base_reglas,"Pico");
        Collar = new RuleVariable(base_reglas,"Collar");
        OrejasRedondas = new RuleVariable(base_reglas,"Orejas Redondas");
    
        ResultadoCalzado = new RuleVariable(base_reglas,"Calzado");
        ResultadoOral = new RuleVariable(base_reglas,"Cavidad Oral");
        ResultadoCollar = new RuleVariable(base_reglas,"Collar");
        ResultadoOrejasRedondas = new RuleVariable(base_reglas,"Tipo de Oreja");
        
        Condition igual_a = new Condition("=");     //Creamos una condición lógica
        
        //Ahora definiremos las reglas
        //podemos hacer esto en bas ea un árbol de reglas
        Rule regla1 = new Rule(base_reglas, "regla 1", 
        new Clause[]{
            new Clause(Zapatillas,igual_a,"si"), //Si el personaje SÍ tiene zapatillas...
            new Clause(Zapatos,igual_a,"no")},  //...y NO tiene zapatos...
        new Clause(ResultadoCalzado,igual_a,"Tiene Zapatillas") //Concluímos que tiene zapatillas
        );
        
        Rule regla2 = new Rule(base_reglas, "regla 2", 
        new Clause[]{
            new Clause(Zapatillas,igual_a,"no"), //Si el personaje NO tiene zapatillas...
            new Clause(Zapatos,igual_a,"si")},  //...y SÍ tiene zapatos...
        new Clause(ResultadoCalzado,igual_a,"Tiene Zapatos") //Concluímos que tiene zapatos
        );
        
        Rule regla3 = new Rule(base_reglas, "regla 3", 
        new Clause[]{
            new Clause(Zapatillas,igual_a,"no"), //Si el personaje NO tiene zapatillas...
            new Clause(Zapatos,igual_a,"no")},  //...y NO tiene zapatos...
        new Clause(ResultadoCalzado,igual_a,"No Tiene Calzado") //Concluímos que NO tiene calzado
        );       
        
        Rule regla4 = new Rule(base_reglas, "regla 4", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"Tiene Zapatillas"), //Si el personaje SÍ tiene calzado (Zapatillas)...
            new Clause(Pico,igual_a,"si")},  //...y SÍ tiene pico...
        new Clause(ResultadoOral,igual_a,"Daisy") //Concluímos que es Daisy
        );         
        
        Rule regla5 = new Rule(base_reglas, "regla 5", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"Tiene Zapatillas"), //Si el personaje NO tiene zapatillas...
            new Clause(Pico,igual_a,"no")},  //...y NO tiene pico...
        new Clause(ResultadoOral,igual_a,"Minnie") //Concluímos que es Minnie
        );         
        
        Rule regla6 = new Rule(base_reglas, "regla 6", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"Tiene Zapatos"), //Si el personaje SÍ tiene calzado (Zapatos)...
            new Clause(OrejasRedondas,igual_a,"si")},  //...y SÍ tiene orejas redondas...
        new Clause(ResultadoOrejasRedondas,igual_a,"Mickey") //Concluímos que es Mickey
        );
        
        Rule regla7 = new Rule(base_reglas, "regla 7", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"Tiene Zapatos"), //Si el personaje SÍ tiene calzado (Zapatos)...
            new Clause(OrejasRedondas,igual_a,"no")},  //...y NO tiene orejas redondas...
        new Clause(ResultadoOrejasRedondas,igual_a,"Goofy") //Concluímos que es Goofy
        );                        
        
        Rule regla8 = new Rule(base_reglas, "regla 8", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"No Tiene Calzado"), //Si el personaje NO tiene calzado...
            new Clause(Collar,igual_a,"si")},  //...y SÍ tiene collar...
        new Clause(ResultadoCollar,igual_a,"Pluto") //Concluímos que es Pluto
        );  
        
        Rule regla9 = new Rule(base_reglas, "regla 9", 
        new Clause[]{
            new Clause(ResultadoCalzado,igual_a,"No Tiene Calzado"), //Si el personaje NO tiene calzado...
            new Clause(Collar,igual_a,"no")},  //...y NO tiene collar...
        new Clause(ResultadoCollar,igual_a,"Donald") //Concluímos que es Donald
        );          
    }
    
}
