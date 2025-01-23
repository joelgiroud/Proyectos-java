package SistemaExperto;
//Se trata de un objeto tipo pregunta
public class Pregunta {
    private final String contenido;     //La pregunta en si
    private final String habilidad;     //La habilidad correspondiente
    private final String atributo;      //El rubro de la pregunta
    private static String respuesta;    //
    private final  boolean evaluable;   //Nos dice si el SE la debe evaluar

    public Pregunta(String hab, String att, boolean cond, String cont){
        this.contenido=cont;
        this.atributo=att;
        this.evaluable=cond;
        this.habilidad=hab;
    }

    public String getContenido() {
        return contenido;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public String getAtributo() {
        return atributo;
    }

    public boolean isEvaluable() {
        return evaluable;
    }

    public String getRespuesta() {
        return respuesta;
    } 
}
