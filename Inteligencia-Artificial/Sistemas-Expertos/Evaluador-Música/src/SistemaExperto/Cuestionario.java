package SistemaExperto;
import java.util.concurrent.ThreadLocalRandom;

//NP=Notación y Pentagrama
//CT=Compases y Tiempos
//EI=Escalas e Intervalos
//TAT=Tonalidad, ,Armaduras y Transposición
//AS=Armonía y Sonancia
//ADO=Articulaciones, Dinámicas y Ornamentos
public class Cuestionario {
    //Preg_[Rubro][Habilidad]
    Pregunta[][] lista_preg = new Pregunta[6][7];
    Respuesta[][] respuestas_correctas = new Respuesta[6][7];
    boolean autocompletar;

    public Cuestionario(boolean autoc) {
    //Pregunta(Hab,Rubro,Evaluable,cont), autoc es para pedir la lista con o sin las preguntas
        if (autoc) {        //Habilidades escritas SIN ACENTOS
            lista_preg[0][0] = new Pregunta("creatividad", "NP", false,
                    "Haga un resumen de todo lo que ha aprendido de<br>"
                            + " este tema en todo el curso");
            lista_preg[0][1] = new Pregunta("investigacion", "NP", true,
                    "Las notas musicales Do y Sí, tuvieron otros nombres<br>"
                            + " en el transcurso de la historia.<br>"
                            + "Menciona el nombre de una de las dos:<br>"
                            + " (<q>(do o sí) (respuesta)<q>).");
            lista_preg[0][2] = new Pregunta("analisis", "NP", true,
                    "¿Por qué las notas Do(central) y La(agudo)<br>"
                            + "en clave de sol se escriben con una línea en medio?");
            lista_preg[0][3] = new Pregunta("comparacion", "NP", true,
                    "¿Cuál de todas las claves musicales es la que<br>"
                            + "puede representar las notas más agudas?");
            lista_preg[0][4] = new Pregunta("estructuracion", "NP", true,
                    "¿Cuáles son los tres componentes de la música?");
            lista_preg[0][5] = new Pregunta("comprension", "NP", false,
                    "¿Por qué una clave musical se coloca<br>"
                            + "específicamente en cierta línea?");
            lista_preg[0][6] = new Pregunta("memorizacion", "NP", true,
                    "Escriba el equivalente en sistema Anglosajón, en su debido orden,<br>"
                    + "de las notas musicales, siguiendo este formato:<br>Z, Z, Z, Z, Z, Z, Z");

            lista_preg[1][0] = new Pregunta("creatividad", "CT", true,
                    "¿Cómo podría interpretarse un compás de 9/4?");
            lista_preg[1][1] = new Pregunta("investigacion", "CT", true,
                    "¿Qué otro nombre tienen los compases 3/4 o 4/4?<br>"
                    + "Menciona uno de los dos compases y su nombre");
            lista_preg[1][2] = new Pregunta("analisis", "CT", true,
                    "¿Es posible escribir un compás con unidades de tiempo<br>"
                    + "(el divisor, por así llamarle) impares?");
            lista_preg[1][3] = new Pregunta("comparacion", "CT", true,
                    "Se tienen dos canciones escritas en 3/4, llamémosles A y B.<br>"
                            + "Ambas canciones suenan igual.<br>"
                            + "¿Es posible que B sea escrita en"
                    + " 6/8 sin que suene diferente a A?");
            lista_preg[1][4] = new Pregunta("estructuracion", "CT", false,
                    "Describa los pasos y reglas para escribir notas musicales<br>"
                            + "en relación con el tema visto");
            lista_preg[1][5] = new Pregunta("comprension", "CT", true,
                    "¿Si se tiene un compás de 12/8, cuántas notas blancas, a lo máximo,<br>"
                    + "se pueden colocar por compás?");
            lista_preg[1][6] = new Pregunta("memorizacion", "CT", true,
                    "¿Si se tiene un compás de 4/4, cuántas notas corcheas, a lo máximo,<br>"
                    + "se pueden colocar por compás?");

            lista_preg[2][0] = new Pregunta("creatividad", "EI", false,
                    "Nombre algún sonido que la gente conozca (comerciales, videojuegos,<br>"
                    + "personajes, canciones) en donde se reproduzca un intervalo de notas.");
            lista_preg[2][1] = new Pregunta("investigacion", "EI", true,
                    "¿Mí# y F siempre han sido el mismo sonido?");
            lista_preg[2][2] = new Pregunta("analisis", "EI", false,
                    "¿Cuántas escalas musicales existen?<br>"
                            + "Justifique su respuesta");
            lista_preg[2][3] = new Pregunta("comparacion", "EI", true,
                    "¿Sonoramente, es lo mismo una séptima mayor<br>"
                            + " y una octava disminuida?");
            lista_preg[2][4] = new Pregunta("estructuracion", "EI", true,
                    "Defina en qué unidades de distancia sonora se<br>"
                            + " divide una escala musical");
            lista_preg[2][5] = new Pregunta("comprension", "EI", true,
                    "¿Cuál es la sexta menor de Re?");
            lista_preg[2][6] = new Pregunta("memorizacion", "EI", true,
                    "¿Cuál es la cuarta aumentada de Do?");

            lista_preg[3][0] = new Pregunta("creatividad", "TAT", false,
                    "Nombra una canción en la que se pase de una tonalidad<br>"
                            + "menor a una mayor o viceversa");
            lista_preg[3][1] = new Pregunta("investigacion", "TAT", true,
                    "Nombra una tonalidad que tenga 5 o más sostenidos/bemoles<br>");
            lista_preg[3][2] = new Pregunta("analisis", "TAT", false,
                    "¿Cuáles son las utilidades de implementar grados tonales en la música?");
            lista_preg[3][3] = new Pregunta("comparacion", "TAT", true,
                    "¿Es el cuarto grado de La menor igual al segundo grado de Do?");
            lista_preg[3][4] = new Pregunta("estructuracion", "TAT", false,
                    "¿Qué relación tiene el círculo de quintas con<br>"
                            + " la armadura de una tonalidad?");
            lista_preg[3][5] = new Pregunta("comprension", "TAT", false,
                    "¿Cuál es la diferencia entre grado e intervalo?");
            lista_preg[3][6] = new Pregunta("memorizacion", "TAT", true,
                    "¿Cuál es la tercera nota de C#/G# si este acorde se<br>"
                    + "transposiciona 2 tonos y medio arriba?");

            lista_preg[4][0] = new Pregunta("creatividad", "AS", true,
                    "¿Si estoy en tonalidad de Sol y deseo resolver a su cuarto grado<br>"
                    + "qué acordes (básicos) puedo tocar como transición?<br>"
                    + "Escríbela en este formato: Xbm. Mencione dos acordes");
            lista_preg[4][1] = new Pregunta("investigacion", "AS", true,
                    "¿Cuál sería la diferencia entrelas NOTAS Re# y Míb?<br>"
                            + "Desde la perspectiva de Do?");
            lista_preg[4][2] = new Pregunta("analisis", "AS", true,
                    "¿De modo sonoro, es decir, sin tener un pentagrama a la vista,<br>"
                            + "cómo podría uno identificar una disonancia?");
            lista_preg[4][3] = new Pregunta("comparacion", "AS", true,
                    "¿Es lo mismo un E6 que un C#m7/G?");
            lista_preg[4][4] = new Pregunta("estructuracion", "AS", true,
                    "A parte de quinto grado, mencione el grado por el que<br>"
                            + "usualmente la mayoría de canciones suelen ir<br>"
                            + "cuando están en tonalidad mayor");
            lista_preg[4][5] = new Pregunta("comprension", "AS", true,
                    "¿Cuántas notas tiene el acorde C9(sus2)?");    // Mencione una
            lista_preg[4][6] = new Pregunta("memorizacion", "AS", true,
                    "¿Si se está ejecutando la escala armónica natural de Re<br>"
                            + "y me encuentro en el segundo grado, este acorde será mayor o menor?");

            lista_preg[5][0] = new Pregunta("creatividad", "ADO", true,
                    "Nombre un ornamento musical en la que su efecto no pueda<br>"
                            + "escribirse naturalmente en la ausencia de esta");
            lista_preg[5][1] = new Pregunta("investigacion", "ADO", true,
                    "¿En qué idioma están escritas las dinámicas musicales?");
            lista_preg[5][2] = new Pregunta("analisis", "ADO", false,
                    "¿Cuál es la principal diferencia entre una articulación y una dinámica?");
            lista_preg[5][3] = new Pregunta("comparacion", "ADO", true,
                    "¿Qué juega un papel más vital en la música,<br>"
                            + "las acentuaciones o las ornamentas?");
            lista_preg[5][4] = new Pregunta("estructuracion", "ADO", true,
                    "¿En qué parte de un pentagrama deben ir anotadas<br>"
                            + "las dinámicas musicales?");
            lista_preg[5][5] = new Pregunta("comprension", "ADO", false,
                    "¿Con qué finalidad están escritas las articulaciones<br>"
                            + "y dinámicas musicales?");
            lista_preg[5][6] = new Pregunta("memorizacion", "ADO", true,
                    "¿En cuál de los tres componentes básicos de la música<br>"
                            + "se utilizan las anti-articulaciones?");

            //ESTAS RESPUESTAS NO SE OCUPARÁN, SON POR MERA INFORMACIÓN
            respuestas_correctas[0][0] = new Respuesta(lista_preg[0][0],
                    "El alumno deberá responder relacionando palbras clave"
                    + "(Jerarquía, Agudez, Claves, etc...)");
            respuestas_correctas[0][1] = new Respuesta(lista_preg[0][1],
                    "Do=UT, Sí=SA");
            respuestas_correctas[0][2] = new Respuesta(lista_preg[0][2],
                    "Es una costumbre adoptada por escribas para simular"
                    + "espacios y líneas extra en el pentagrama");
            respuestas_correctas[0][3] = new Respuesta(lista_preg[0][3],
                    "Sol");
            respuestas_correctas[0][4] = new Respuesta(lista_preg[0][4],
                    "Armonía, melodía y percusión");
            respuestas_correctas[0][5] = new Respuesta(lista_preg[0][5],
                    "Es una referencia que sirve para saber qué nota (sonora)"
                    + "es la que se está escribiendo");
            respuestas_correctas[0][6] = new Respuesta(lista_preg[0][6],
                    "C, D, E, F, G, A, B");

            respuestas_correctas[1][0] = new Respuesta(lista_preg[1][0],
                    "Como uno de 4/4 pegado a uno de 3/4 pegado a uno de 2/4");
            respuestas_correctas[1][1] = new Respuesta(lista_preg[1][1],
                    "Vals para 3/4, Compasillo para 4/4");
            respuestas_correctas[1][2] = new Respuesta(lista_preg[1][2],
                    "No");
            respuestas_correctas[1][3] = new Respuesta(lista_preg[1][3],
                    "Sí, aunque sería complicado si es una obra grande");
            respuestas_correctas[1][4] = new Respuesta(lista_preg[1][4],
                    "Primero se escribe la clave musical, después el compás,"
                    + "las notas y la barra de final");
            respuestas_correctas[1][5] = new Respuesta(lista_preg[1][5],
                    "2");
            respuestas_correctas[1][6] = new Respuesta(lista_preg[1][6],
                    "8");

            respuestas_correctas[2][0] = new Respuesta(lista_preg[2][0],
                    "Mario Bros (cuarta justa), o los Simpson (cuarta aumentada)"
                    + ", por citar dos ejemplos");
            respuestas_correctas[2][1] = new Respuesta(lista_preg[2][1],
                    "No. En la antigüedad no era así");
            respuestas_correctas[2][2] = new Respuesta(lista_preg[2][2],
                    "En cada cultura y tiempo existen contadas escalas musicales,"
                    + "pero, en términos generales: MUCHAS");
            respuestas_correctas[2][3] = new Respuesta(lista_preg[2][3],
                    "Sí");
            respuestas_correctas[2][4] = new Respuesta(lista_preg[2][4],
                    "Tonos y Semitonos");
            respuestas_correctas[2][5] = new Respuesta(lista_preg[2][5],
                    "Sib, La# sería su quinta aumentada");
            respuestas_correctas[2][6] = new Respuesta(lista_preg[2][6],
                    "Fa#");

            respuestas_correctas[3][0] = new Respuesta(lista_preg[3][0],
                    "Ashes High (Iron maiden) o Sombras (popular mexicana),"
                    + "por citar dos ejemplos");
            respuestas_correctas[3][1] = new Respuesta(lista_preg[3][1],
                    "Sol#m, Re#m, Sol#m, Reb, Solb, Sí");
            respuestas_correctas[3][2] = new Respuesta(lista_preg[3][2],
                    "Son una herramienta para saber la familia natural de acordes"
                    + "en alguna tonalidad");
            respuestas_correctas[3][3] = new Respuesta(lista_preg[3][3],
                    "Sí");
            respuestas_correctas[3][4] = new Respuesta(lista_preg[3][4],
                    "Conforme más lejos (ene quitnas) se encuentre uno de la tónica,"
                    + "más compleja será la armadura");
            respuestas_correctas[3][5] = new Respuesta(lista_preg[3][5],
                    "Un intervalo es un concepto para sonidos (tonos), mientras que"
                    + "un grado es un lugar en alguna escala");
            respuestas_correctas[3][6] = new Respuesta(lista_preg[3][6],
                    "La# o A#");

            respuestas_correctas[4][0] = new Respuesta(lista_preg[4][0],
                    "Sol7 o Rem y Sol7");
            respuestas_correctas[4][1] = new Respuesta(lista_preg[4][1],
                    "Re# es una segunda aumentada, Mib es una tercera menor");
            respuestas_correctas[4][2] = new Respuesta(lista_preg[4][2],
                    "Cuando se mete un intervalo que no debería estar en el acorde");
            respuestas_correctas[4][3] = new Respuesta(lista_preg[4][3],
                    "No, aunque denotan la misma figura armónica");
            respuestas_correctas[4][4] = new Respuesta(lista_preg[4][4],
                    "El cuarto grado o el segundo grado");
            respuestas_correctas[4][5] = new Respuesta(lista_preg[4][5],
                    "5 notas");
            respuestas_correctas[4][6] = new Respuesta(lista_preg[4][6],
                    "Menor");

            respuestas_correctas[5][0] = new Respuesta(lista_preg[5][0],
                    "Trémolo");
            respuestas_correctas[5][1] = new Respuesta(lista_preg[5][1],
                    "Italiano");
            respuestas_correctas[5][2] = new Respuesta(lista_preg[5][2],
                    "Una acentuación es algo que cambia estrictamente el sonido de"
                    + "una nota, una dinámica cambia la manera de interpretarla");
            respuestas_correctas[5][3] = new Respuesta(lista_preg[5][3],
                    "Las acentuaciones");
            respuestas_correctas[5][4] = new Respuesta(lista_preg[5][4],
                    "Debajo del pentagrama");
            respuestas_correctas[5][5] = new Respuesta(lista_preg[5][5],
                    "Las articulaciones funcionan añadiendo nuevas reglas de sonido, las "
                    + "dinámicas funcionan cambiando el sonido a subjetividad");
            respuestas_correctas[5][6] = new Respuesta(lista_preg[5][6],
                    "En la percusión");
        }
    }

    @SuppressWarnings("empty-statement")
    public Pregunta getPregunta(int rubro){ //OBTENER PREGUNTA ALEATORIA
        int k;
        k = ThreadLocalRandom.current().nextInt(0, 6 + 1);;  // Valor entre 0 y 6, ambos incluidos.
        return lista_preg[rubro][k];
    }

    public Pregunta getPregunta(){ //OBTENER PREGUNTA ALEATORIA DE RUBRO ALEATORIO
        int k,j;
        k = ThreadLocalRandom.current().nextInt(0, 6 + 1);;  // Valor entre 0 y 6, ambos incluidos.
        j = ThreadLocalRandom.current().nextInt(0, 5 + 1);;  // Valor entre 0 y 5, ambos incluidos.
        return lista_preg[j][k];
    }
}
