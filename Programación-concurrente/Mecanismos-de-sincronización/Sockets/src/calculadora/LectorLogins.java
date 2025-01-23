package calculadora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LectorLogins {
	private String ruta;	//La ruta del archivo incluyendo el nombre
	List<Login> logins;
	ReentrantLock candado;
	
    public LectorLogins(String rt, ReentrantLock candado){
    	this.ruta=rt;
    	this.candado = candado;
    	logins = iniciarLectura(ruta, candado);
        /*// let's print all the person read from CSV file
        	for (login b : logins) {
            	System.out.println(b);
        	}
        */
    }
    

    
    public void escribe(String usr, String pass, ReentrantLock candado) {
    	try {
    		candado.lock();
    		
    		if(usr == null || pass == null) {
    			System.out.println("No debe haber datos vacíos.");
    			return;
    		}
    		else {
    	      FileWriter escritor = new FileWriter(ruta);
    	      escritor.write(usr + "," + pass);
    	      escritor.close();
    	      //System.out.println("Datos escritos exitosamente.");
    		}
    		candado.unlock();
    		
    	} catch (IOException e) {
    		System.out.println("Un error ocurrió al intentar escribir los datos.");
    		e.printStackTrace();
    	  }
    }
    
    public void cambiarRuta(String nueva) {	//No tiene candado, aguas
    	this.ruta=nueva;
    }
    
    public List<Login> getLogins() {
    	return this.logins;
    }
    
    boolean insertaLogin(String uname, String pwd) {
    	Login log = crearLogin( new String[]{uname, pwd}, this.candado);
    	
        String currentPath = "";
			try {
				currentPath = new java.io.File(".").getCanonicalPath();
				currentPath = currentPath + "\\src\\calculadora\\";		//No tengo idea, pero tengo que agregar esto: WINDOWS
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
		if(inyectar(log, currentPath + "registro.txt")) {
			logins = iniciarLectura(ruta, candado);
			return true;
		}
		else {
			return false;
		}
    }

	String muestraLogins() {
		String mensaje = "\tUsuario\tContraseña\n\n";
    	int i=1;
    	
    	for(Login e:logins){
    		mensaje += i + ":\t" + e.toString() + "\n";
    		i++;
    	}
    	
    	return mensaje;
    }

    private boolean inyectar(Login log, String path) {
        if( (log.getPassword()!=null && log.getUsuario()!=null) && path!=null) {
    		String str = log.getUsuario() + "," + log.getPassword();
        	BufferedWriter writer;
        	
			try {
				writer = new BufferedWriter(new FileWriter(path, true));
	        	writer.append('\n');
	        	writer.append(str);
	        
	        	writer.close();
	        	return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
        }
        return false;
	}

    private static List<Login> iniciarLectura(String archivo, ReentrantLock candado) {
        List<Login> login = new ArrayList<>();
        
        String currentPath = "";
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
			currentPath = currentPath + "\\src\\calculadora";		//No tengo idea, pero tengo que agregar esto: WINDOWS
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Current dir:" + currentPath);
        
        Path ruta = Paths.get(currentPath+archivo);
        //System.out.println(ruta.toAbsolutePath());	POR FINES DE DEPURACIÓN

        //Crea una instancia de BufferedReaderusando try
        try (BufferedReader br = Files.newBufferedReader(ruta,
                StandardCharsets.US_ASCII)) {

            // Lee la primera línea del archivo de texto
            String line = br.readLine();

            while (line != null) {	//Hasta que todas las líneas sean leídas

               /* Se usa String.split para cargar un string con los
            	* valores de cada línea del archivo
                * usando coma como delimitador
                */
                String[] attributes = line.split(",");

                //Crearemos una instancia tipo login con los datos leídos
                Login sesion = crearLogin(attributes, candado);	

                // Añadiendo login al ArrayList
                login.add(sesion);

                // Lee la siguiente línea antes del sig ciclo
                // Si se alcanzó el EOF, la línea será NULL
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return login;
    }

    private static Login crearLogin(String[] metadata, ReentrantLock candado) {	//Método para la clase login
    	
    	candado.lock();
    	String usuario = metadata[0];
        String password = metadata[1];
        candado.unlock();
        
        // crea y regresa un login
        return new Login(usuario, password);
    }

}
