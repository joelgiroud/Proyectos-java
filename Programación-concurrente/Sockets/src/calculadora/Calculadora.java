package calculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock; 

public class Calculadora implements Runnable{
	static final int PUERTO=4002;
	Socket socket;
	LectorLogins lector;
	List<Login> registro;
	String usr;
	String pwd;
	Login actual;
	ReentrantLock candado;
	
	public Calculadora(){
	
		initServidor();			//Inicia servidor
	}

	public Calculadora(Socket s) {
		this.socket=s;				//
	}


	//SERVIDOR
	public void initServidor(){
		ServerSocket enlace;
		Socket socket;

		try{
			//Aqu� deber�a ir el proceso de autenticaci�n, antes de ir a los hilos
			
			
			enlace = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 5000*/

			while(true) {	//Este while ser�a para no conformarse con una sola conexi�n a cliente
				System.out.println("Esperando una conexion:");
				socket = enlace.accept();

				Calculadora hilo = new Calculadora(socket);
				//Creamos una instancia del servidor multihilado con el otro cosntructor
				
				Thread tcliente = new Thread(hilo);	//Inyectamos dicha instancia
				tcliente.start();		//El hilo principal contin�a con la ejecuci�n, el otro interact�a con un cliente
				
				
				//Inicia el socket, ahora esta esperando una conexion por parte del cliente
				System.out.println("Un cliente se ha conectado.");
			}

		}catch(Exception e ){
			System.out.println("Error: "+e.getMessage());
		}
	}

	@Override
	public void run() {	//ESTO ES LO QUE HAR� CADA HILO
		candado = new ReentrantLock();
		LectorLogins lector = new LectorLogins("/registro.txt", candado);
		registro = lector.getLogins();
		this.lector = lector;
		
		//Canales de entrada y salida de datos
		PrintWriter salida=null;
		String mensajeRecibido="";
		BufferedReader entrada=null;
		boolean logeado = false;
		String usr;
		String pwd;
		
		/*Si no me equivoco, el siguiente bloque de c�digo
		 * es para establecer la comunicaci�n por mensajes
		 */
		try {
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {
			salida = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.println("Confirmando conexion al cliente....");
		
		//salida.println(""); Para enviar mensajes al cliente
		
		do {
			salida.println("�Deseas registrar un usuario? si/no...\n");
			try {
				mensajeRecibido = entrada.readLine();
				//System.out.println(mensajeRecibido);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(mensajeRecibido.equals("si")) {
				String USR;
				String PWD;
				
				salida.println("Inserta el nombre de usuario...\n");
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				USR = mensajeRecibido;
				
				salida.println("Inserta la contrase�a...\n");
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PWD = mensajeRecibido;
				
				try {
					if( registrar(USR, PWD) ) {
						salida.println("Registro exitoso.<>\n");
						}
					else {
						salida.println("Registro NO exitoso.<>\n");
					}
				} catch(NullPointerException e) {
					salida.println("Registro NO exitoso.<>\n");
				}
					
			}
			else {
				if (mensajeRecibido.equals("no")) {
					break;
				}
			}
		}
		while (true);
		
			String salir = "0";
			do {
				
				salida.println("Ingresa el usuario.\n");
				
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				usr = mensajeRecibido;
			
				salida.println("\nIngresa la contrase�a.\n");
			
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pwd = mensajeRecibido;
			
				logeado = login(usr, pwd, salida, lector);
			
				if( !logeado ) {
					salida.println("Datos err�neos<>\nSi deseas salir ingresa un '1',"
						+ " de lo contrario cualquier otro n�mero que no sea ese...\n");
					try {
						mensajeRecibido = entrada.readLine();
						//System.out.println(mensajeRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					salir = mensajeRecibido;
				}
				else {
					System.out.println("Acceso condecido...");
					break;
				}
			}
			while(!salir.contains("1"));
		
		
		
		//AQU� COMIENZAN LAS OPERACIONES
		if(logeado && this.actual.isActivo()) {
			int num = 0;
			int num2 = 0;
			boolean exit = false;
			
			do {
				String str = this.actual.getUsuario() + ","
					+ " bienvenido al men� de la calculadora<>\r\n"
					+ "A continuaci�n, ingresa la operaci�n deseada...<>\r\n"
					+ "0)\tSalir...<>\r\n"
					+ "1)\tSumar n n�meros...<>\r\n"
					+ "2)\tMultiplicar n n�meros...<>\r\n"
					+ "3)\tRestar dos n�meros...<>\r\n"
					+ "4)\tDividir dos n�meros.\r\n\r\n";
				/*
				 *Los <> sirven para que, en el c�digo del cliente,
				 *no exista conflicto en la espera de mensajes,
				 *ya que, cada l�nea deber�a separarse en cada mensaje diferente. 
				 */
				
				salida.println(str);
				
				
				try {
					candado.lock();
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
					candado.unlock();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {	//Nos aseguramos de que sea un n�mero
					num = Integer.parseInt(mensajeRecibido);
				} catch (NumberFormatException e) {
				    System.out.println("Error. El dato no es num�rico");
				    num = 9;
				}
				
				
				switch (num){
				case 0:
					exit = true;
					break;
					
				case 1:
					Suma_n(salida, entrada, this.actual.getUsuario(), candado);
					try {
						mensajeRecibido = entrada.readLine();
						//System.out.println(mensajeRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					  }
					
					try {	//Nos aseguramos de que sea un n�mero
						num2 = Integer.parseInt(mensajeRecibido);
					} catch (NumberFormatException e) {
					    System.out.println("Error. El dato no es num�rico");
					    num2 = 0;
					}
					
					if(num2==0) {
						exit=true;
					}
					else {
						salida.println("Regresando al men� anterior...<>\n");
					}
					

					break;
					
				case 2:
					Mult_n(salida, entrada, this.actual.getUsuario(), candado);
					try {
						mensajeRecibido = entrada.readLine();
						//System.out.println(mensajeRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					  }
					
					try {	//Nos aseguramos de que sea un n�mero
						num2 = Integer.parseInt(mensajeRecibido);
					} catch (NumberFormatException e) {
					    System.out.println("Error. El dato no es num�rico");
					    num2 = 0;
					}
					
					if(num2==0) {
						exit=true;
					}
					else {
						salida.println("Regresando al men� anterior...<>\n");
					}
					
					break;
					
				case 3:
					Resta(salida, entrada, this.actual.getUsuario(), candado);
					try {
						mensajeRecibido = entrada.readLine();
						//System.out.println(mensajeRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					  }
					
					try {	//Nos aseguramos de que sea un n�mero
						num2 = Integer.parseInt(mensajeRecibido);
					} catch (NumberFormatException e) {
					    System.out.println("Error. El dato no es num�rico");
					    num2 = 0;
					}
					
					if(num2==0) {
						exit=true;
					}
					else {
						salida.println("Regresando al men� anterior...<>\n");
					}
					
					break;
					
				case 4:
					Div(salida, entrada, this.actual.getUsuario(), candado);
					try {
						mensajeRecibido = entrada.readLine();
						//System.out.println(mensajeRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					  }
					
					try {	//Nos aseguramos de que sea un n�mero
						num2 = Integer.parseInt(mensajeRecibido);
					} catch (NumberFormatException e) {
					    System.out.println("Error. El dato no es num�rico");
					    num2 = 0;
					}
					
					if(num2==0) {
						exit=true;
					}
					else {
						salida.println("Regresando al men� anterior...<>\n");
					}
					
					break;
				
				default:
					System.out.println("Opci�n no v�lida; intenta de nuevo.<>\n");
				}
			}
			while(!exit);

			System.out.println("Cerrando conexion...");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Cerrando conexion...");
			candado.lock();
			logeado=false;
			this.actual.setActivo(logeado);
			candado.unlock();
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	private void Suma_n(PrintWriter salida, BufferedReader entrada, String uname, ReentrantLock candado) {
		int term = 0;
		int aux = 0;
		int sum = 0;
		String mensajeRecibido="";
		String str;
		
		salida.println("�De cu�ntos t�rminos ser� la suma?\n");
		candado.lock();
		try {
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		candado.unlock();
		
		try {	//Nos aseguramos de que sea un n�mero
			term = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		
		if(term>1) {
			candado.lock();
			for(int i=0; i<term; i++) {
				str = "Ingresa el t�rmino " + Integer.toString(i+1) + ".\n";
				salida.println(str);
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
				// 	TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {	//Nos aseguramos de que sea un n�mero
					aux = Integer.parseInt(mensajeRecibido);
				} catch (NumberFormatException e) {
				    System.out.println("Error. El dato no es num�rico");
				    return;
				}
				
				sum += aux;
			}
			
			str = "\n" + uname + ", el resultado es " + Integer.toString(sum) + ".<>\n"
					+ "0)\tSalir<>\n" + "1)\tRegresar\n";
			salida.println(str);
			candado.unlock();
		}
		else {
			salida.println("Error con los datos ingresados<>\n");
		}
		return;
	}
	
	private void Mult_n(PrintWriter salida, BufferedReader entrada, String uname, ReentrantLock candado) {
		String mensajeRecibido="";
		int term = 0;
		int aux = 1;
		int mult = 1;
		String str;
		
		salida.println("�De cu�ntos t�rminos ser� la multiplicaci�n?\n");
		try {
			candado.lock();
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
			candado.unlock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	//Nos aseguramos de que sea un n�mero
			term = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		if(term>1) {
			candado.lock();
			for(int i=0; i<term; i++) {
				str = "Ingresa el t�rmino " + Integer.toString(i+1) + ".\n";
				salida.println(str);
				try {
					mensajeRecibido = entrada.readLine();
					//System.out.println(mensajeRecibido);
				} catch (IOException e1) {
				// 	TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {	//Nos aseguramos de que sea un n�mero
					aux = Integer.parseInt(mensajeRecibido);
				} catch (NumberFormatException e) {
				    System.out.println("Error. El dato no es num�rico");
				    return;
				}
				
				mult = mult * aux;
			}
			
			str = "\n" + uname + ", el resultado es " + Integer.toString(mult) + ".<>\n"
					+ "0)\tSalir<>\n" + "1)\tRegresar\n";
			salida.println(str);
			candado.unlock();
		}
		else {
			salida.println("Error con los datos ingresados\n");
		}
		return;
	}
	
	private void Resta(PrintWriter salida, BufferedReader entrada, String uname, ReentrantLock candado) {
		String mensajeRecibido="";
		String str;
		int a = 0;
		int b = 0;
		
		salida.println("Ingresa el primer n�mero\n");
		try {
			candado.lock();
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
			candado.unlock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	//Nos aseguramos de que sea un n�mero
			a = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		salida.println("Ingresa el segundo n�mero\n");
		try {
			candado.lock();
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
			candado.unlock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	//Nos aseguramos de que sea un n�mero
			b = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		str = "\n" + uname + ", el resultado es " + Integer.toString(a-b) + ".<>\n"
				+ "0)\tSalir<>\n" + "1)\tRegresar\n";
		salida.println(str);
		
	}
	
	private void Div(PrintWriter salida, BufferedReader entrada, String uname, ReentrantLock candado) {
		String mensajeRecibido="";
		String str;
		int a = 0;
		int b = 0;
		
		salida.println("Ingresa el primer n�mero\n");
		try {
			candado.lock();
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
			candado.unlock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	//Nos aseguramos de que sea un n�mero
			a = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		salida.println("Ingresa el segundo n�mero\n");
		try {
			candado.lock();
			mensajeRecibido = entrada.readLine();
			//System.out.println(mensajeRecibido);
			candado.unlock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	//Nos aseguramos de que sea un n�mero
			b = Integer.parseInt(mensajeRecibido);
		} catch (NumberFormatException e) {
		    System.out.println("Error. El dato no es num�rico");
		    return;
		}
		
		str = "\n" + uname + ", el resultado es " + Integer.toString(a/b) + ".<>\n"
				+ "0)\tSalir<>\n" + "1)\tRegresar\n";
		salida.println(str);
	}
	
	
	
	private boolean login(String usuario, String clave, PrintWriter salida, LectorLogins lector) {
		boolean success = false;
		candado.lock();
		registro = lector.getLogins();	//Actualizamos el registro
		
		//System.out.println("\nVerificando datos...\n");
		for(Login e:registro) {
			if (e.getUsuario().equals(usuario) && !e.getPassword().equals(clave)) {
				System.out.println("Contrase�a incorrecta\n");
				salida.println("Datos err�neos, intenta de nuevo<>\n");
				success = false;
				break;
			}
			if(e.getPassword().equals(clave) && !e.getUsuario().equals(usuario)) {
				System.out.println("Usuario incorrecto\n");
				salida.println("Datos err�neos, intenta de nuevo<>\n");
				success = false;
				break;
			}
			if(e.getPassword().equals(clave) && e.getUsuario().equals(usuario)) {
				System.out.println("Usuario logeado\n");
				salida.println("Acceso concedido.<>\n");
				this.actual = e;
				this.actual.setActivo(true);
				success = true;
				break;
				//return success;
			}
		}

		candado.unlock();
		
		return success;
	}

	private boolean registrar(String usuario, String clave) {
		if( (usuario != null && clave != null) && (!usuario.equals("") && !clave.equals("")) ) {
			return lector.insertaLogin(usuario, clave);
		}
		return false;
	}
	
	/*public static void main(String[] args) {		//Por si se desea ejecutar sin la clase main
		@SuppressWarnings("unused")
		Calculadora C = new Calculadora();
	}*/
	
}
