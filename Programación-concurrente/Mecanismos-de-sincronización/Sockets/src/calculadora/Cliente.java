package calculadora;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import static java.lang.Thread.sleep;


public class Cliente {

    final String HOST = "localhost";
    final int PUERTO=4002;

    Socket socket;

    PrintWriter mensaje;
    BufferedReader entrada;
    String mensajeRecibido;
    Scanner teclado = new Scanner(System.in);
    String aux;
    
    
    //Cliente

    public void initCliente(){
    	


        try{
            socket = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/

            //creamos el flujo de datos por el que se enviara un mensaje
            mensaje = new PrintWriter(socket.getOutputStream(), true);;	//Para escribir
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//Para leer

            System.out.println("A intercambiar mensajes....");
            
            do{
                sleep(100);
                
            	mensajeRecibido = entrada.readLine();
                if(!mensajeRecibido.equals("")){
                    System.out.println("Mensaje recibido: " + mensajeRecibido + "\n");
                    if(!mensajeRecibido.contains("<>")){
                        System.out.println("Mensaje a enviar:");
                        aux = teclado.next();
                        mensaje.println(aux);
                        System.out.println("");
                    }
                }
                if(!socket.isConnected()){
                    break;
                }
            }
            while(true);
            teclado.close();
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

