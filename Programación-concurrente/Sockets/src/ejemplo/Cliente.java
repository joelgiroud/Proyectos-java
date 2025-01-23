package ejemplo;

import java.net.*;

import java.io.*;
import java.util.Scanner;


public class Cliente {

    final String HOST = "localhost";
    final int PUERTO=4002;
    Scanner teclado = new Scanner(System.in);
    
    Socket socket;

    PrintWriter mensaje;
    BufferedReader entrada;
    String mensajeRecibido;
    String str;
    
    //Cliente

    public void initCliente(){



        try{
            socket = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/

            //creamos el flujo de datos por el que se enviara un mensaje
            mensaje = new PrintWriter(socket.getOutputStream(), true);;	//Para escribir
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//Para leer

            System.out.println("A intercambiar mensajes....\n");
            
            System.out.println("Mensaje a enviar:");
            str = teclado.next();
            //enviamos el mensaje
            mensaje.println(str);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);

            //cerramos la conexion
            socket.close();

        } catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
          }

    }
    public static void  main(String[] args){
        Cliente c = new Cliente();   
        c.initCliente();  		//Iniciar cliente
    }
}

