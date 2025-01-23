package calculadora;

public class Login {
	private String usuario;
    private String password;
    private boolean activo;

    public Login(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String name) {
        this.usuario = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String str) {
        this.password = str;
    }
    
    boolean isActivo() {
    	return activo;
    }
    
    void setActivo(boolean asd) {
    	this.activo = asd;
    }
    
    @Override
    public String toString() {
        return usuario + "\t" + password;
    }
}
