package SistemaExperto;

public class Respuesta {
    private final Pregunta pregunta_asoc; //La pregunta que se responde
    private final String respuesta;       //La respuesta que el alumno aporta

    public Respuesta(Pregunta p, String resp){
        this.pregunta_asoc=p;
        this.respuesta=resp;
    }
    
    public Pregunta getPregunta_asoc() {
        return pregunta_asoc;
    }

    public String getRespuesta() {
        return respuesta;
    }    
}
