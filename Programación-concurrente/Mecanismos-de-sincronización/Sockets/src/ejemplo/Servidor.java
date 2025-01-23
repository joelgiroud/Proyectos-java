package ejemplo;

import java.net.*;
import java.io.*;


public class Servidor {

    final int PUERTO=4002;

    ServerSocket enlace;		//Este ser� el z�calo de comunicaciones
    Socket socket;				//Uno por cada conexi�n

    PrintWriter salida;		//Para escribir datos en el canal de comunicaci�n
    String mensajeRecibido;	//Cadena para manejar mensajes

    //SERVIDOR
    public void initServidor(){

        BufferedReader entrada;		
        try{

            enlace = new ServerSocket(PUERTO );/* Crea socket servidor que escuchara en puerto 5000*/
            System.out.println("Esperando una conexion:");
            socket = enlace.accept();	//Aqu� espera una conexi�n
            //Inicia el socket, ahora esta esperando una conexion por parte del cliente

            System.out.println("Un cliente se ha conectado.");

            //Canales de entrada y salida de datos
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//Leer mensajes
            salida = new PrintWriter(socket.getOutputStream(), true);	//Enviar mensajes

            System.out.println("Confirmando conexion al cliente....");

            salida.println("Mensaje 1");
            
            mensajeRecibido = entrada.readLine();
            System.out.println("Cliente: " + mensajeRecibido);
            
            salida.println("Mensaje 2");
            salida.println("Mensaje 3");

            System.out.println("Cerrando conexion...");
            socket.close();
            enlace.close();

        } catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
          }
    }
    public static void  main(String[] args){

        Servidor s = new Servidor();   
        s.initServidor();				//Inicia servidor

    }
}
