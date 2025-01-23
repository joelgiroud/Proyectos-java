package ejemplo;

import java.net.*;
import java.io.*;

public class ServidorMultiHilado implements Runnable {
	static final int PUERTO=4002;
	Socket s;

	public ServidorMultiHilado(){
		initServidor();			//Inicia servidor
	}

	public ServidorMultiHilado(Socket s) {
		this.s=s;				//
	}


	//SERVIDOR
	public void initServidor(){
		ServerSocket enlace;
		Socket socket;

		try{
			enlace = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 5000*/

			while(true) {	//Este while sería para no conformarse con una sola conexión a cliente
				System.out.println("Esperando una conexion:");
				socket = enlace.accept();

				ServidorMultiHilado hilo = new ServidorMultiHilado(socket);
				//Creamos una instancia del servidor multihilado con el otro cosntructor
				
				Thread tcliente = new Thread(hilo);	//Inyectamos dicha instancia
				tcliente.start();		//El hilo principal continúa con la ejecución, el otro interactúa con un cliente
				
				
				//Inicia el socket, ahora esta esperando una conexion por parte del cliente
				System.out.println("Un cliente se ha conectado.");
			}

		}catch(Exception e ){
			System.out.println("Error: "+e.getMessage());
		}
	}

	@Override
	public void run() {
		//Canales de entrada y salida de datos
		PrintWriter salida=null;
		String mensajeRecibido="";
		BufferedReader entrada=null;

		try {
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		  }

		try {
			salida = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		  }

		System.out.println("Confirmando conexion al cliente....");
		salida.println("Mensaje 1");

		try {
			mensajeRecibido = entrada.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(mensajeRecibido);
		salida.println("Mensaje 2");
		salida.println("Mensaje 3");

		System.out.println("Cerrando conexion...");
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }

	}
	public static void  main(String[] args){
		ServidorMultiHilado s = new ServidorMultiHilado();   
	}
}
