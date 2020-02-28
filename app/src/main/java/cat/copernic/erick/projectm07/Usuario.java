package cat.copernic.erick.projectm07;

public class Usuario {
    private String user;
    private String pass;
    private String nombreUsuario;
    private int edad;

    public Usuario() {

    }

    public Usuario(String user, String pass, String nombreUsuario, int edad) {
        this.user = user;
        this.pass = pass;
        this.nombreUsuario = nombreUsuario;
        this.edad = edad;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
